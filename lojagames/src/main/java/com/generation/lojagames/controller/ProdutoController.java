package com.generation.lojagames.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.generation.lojagames.model.ProdutoModel;
import com.generation.lojagames.repository.CategoriaRepository;
import com.generation.lojagames.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping
	public ResponseEntity<List<ProdutoModel>> getAll(){
		return ResponseEntity.ok(produtoRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProdutoModel> getById(@PathVariable Long id){
		return produtoRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());		
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<ProdutoModel>> getByNome(@PathVariable String nome){
		return ResponseEntity.ok(produtoRepository
				.findAllByNomeContainingIgnoreCase(nome));
	}
	
	@PostMapping
	public ResponseEntity<ProdutoModel> post(@Valid @RequestBody ProdutoModel produtoModel){
			if(categoriaRepository.existsById(produtoModel.getCategoria().getId()))
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(produtoRepository.save(produtoModel));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	@PutMapping
	public ResponseEntity<ProdutoModel> put(@Valid @RequestBody ProdutoModel produtomodel) {
		if (produtoRepository.existsById(produtomodel.getId())) {

			if (categoriaRepository.existsById(produtomodel.getCategoria().getId()))
				return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.save(produtomodel));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<ProdutoModel> postagem = produtoRepository.findById(id);
		
		if(postagem.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		produtoRepository.deleteById(id);
	}


}
