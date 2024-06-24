package com.marcioferreira.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marcioferreira.cursomc.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

	/* springboot busca o email automaticamente */
	@Transactional(readOnly=true)
	Cliente findByEmail(String email);
	
}
