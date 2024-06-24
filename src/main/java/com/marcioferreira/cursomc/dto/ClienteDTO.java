package com.marcioferreira.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.marcioferreira.cursomc.domain.Cliente;
import com.marcioferreira.cursomc.services.validation.ClienteUpdate;

/* @ClienteUpdate - anotação criada para validar email na atualização do registro - ver service.validation */
@ClienteUpdate
public class ClienteDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/* criada para resolver o problema de recuperação de
	 *  enderecos relacionados quando buscar todos as cliente */

	private Integer id;
	
	/* validação de campo vazio e tamanho*/
	@NotEmpty(message="Preenchimento Obrigatório")
	@Length(min=5, max=120, message="Entre 5 e 120 caracteres")
	private String nome;
	
	/* validação de campo vazio e email*/
	@NotEmpty(message="Preenchimento Obrigatório")
	@Email(message="Email inválido")
	private String email;
	
	public ClienteDTO() {
		
	}

	/* construtor para receber Cliente */
	public ClienteDTO(Cliente obj) {
		id = obj.getId();
		nome = obj.getNome();
		email = obj.getEmail();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
