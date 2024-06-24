package com.marcioferreira.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.marcioferreira.cursomc.domain.Categoria;

/* classe para validacao de categoria e as relações */
public class CategoriaDTO  implements Serializable {
	private static final long serialVersionUID = 1L;

	/* criada para resolver o problema de recuperação de
	 *  produtos relacionados quando buscar todos as ccategorias */
	
	private Integer Id;
	
	/* validação de campo vazio e tamanho*/
	@NotEmpty(message="Preenchimento Obrigatório")
	@Length(min=5, max=80, message="Tamanho entre 5 e 80 caracteres")
	private String nome;
	
	public CategoriaDTO() {
		
	}

	/* construtor para receber Categoria */
	public CategoriaDTO(Categoria obj) {
		Id = obj.getId();
		nome = obj.getNome();
	}
	
	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	
}
