package com.generation.farmaciagen.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_categorias")
public class Categoria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Não pode ser nulo")
	private String CategoriaMedicamento;
	
	@NotBlank(message = "Não pode ser nulo")
	private String descricao;
	
	@OneToMany(mappedBy = "categoria", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("categoria")
	private List<ProdutosModel> produtosmodel;
	
	public List<ProdutosModel> getProdutosmodel() {
		return produtosmodel;
	}

	public void setProdutosmodel(List<ProdutosModel> produtosmodel) {
		this.produtosmodel = produtosmodel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategoriaMedicamento() {
		return CategoriaMedicamento;
	}

	public void setCategoriaMedicamento(String categoriaMedicamento) {
		CategoriaMedicamento = categoriaMedicamento;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}
