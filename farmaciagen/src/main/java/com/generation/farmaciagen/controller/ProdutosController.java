package com.generation.farmaciagen.controller;

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

import com.generation.farmaciagen.model.ProdutosModel;
import com.generation.farmaciagen.repository.CategoriaRepository;
import com.generation.farmaciagen.repository.ProdutosRepository;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutosController {

	@Autowired
	private ProdutosRepository produtosRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping
	public ResponseEntity<List<ProdutosModel>> getAll() {
		return ResponseEntity.ok(produtosRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProdutosModel> getById(@PathVariable Long id) {
		return produtosRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@GetMapping("/medicamento/{medicamento}")
	public ResponseEntity<List<ProdutosModel>> getByMedicamento(@PathVariable String medicamento) {
		return ResponseEntity.ok(produtosRepository.findAllByMedicamentoContainingIgnoreCase(medicamento));
	}

	@PostMapping
	public ResponseEntity<ProdutosModel> post(@Valid @RequestBody ProdutosModel produtosmodel) {
		if (categoriaRepository.existsById(produtosmodel.getCategoria().getId()))
			return ResponseEntity.status(HttpStatus.CREATED).body(produtosRepository.save(produtosmodel));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	@PutMapping
	public ResponseEntity<ProdutosModel> put(@Valid @RequestBody ProdutosModel produtosmodel) {
		if (produtosRepository.existsById(produtosmodel.getId())) {

			if (categoriaRepository.existsById(produtosmodel.getCategoria().getId()))
				return ResponseEntity.status(HttpStatus.OK).body(produtosRepository.save(produtosmodel));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<ProdutosModel> produtosmodel = produtosRepository.findById(id);

		if (produtosmodel.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		produtosRepository.deleteById(id);
	}

}
