package com.generation.lojagames.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_categoria")
public class Categoria {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String descricao;
	
	 @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
	 @JsonIgnoreProperties("categoria")
	 private List <ProdutoModel> produtomodel;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<ProdutoModel> getProdutomodel() {
		return produtomodel;
	}

	public void setProdutomodel(List<ProdutoModel> produtomodel) {
		this.produtomodel = produtomodel;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	

	
}
