package com.marcioferreira.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.marcioferreira.cursomc.domain.Categoria;
import com.marcioferreira.cursomc.domain.Produto;
import com.marcioferreira.cursomc.repositories.CategoriaRepository;
import com.marcioferreira.cursomc.repositories.ProdutoRepository;
import com.marcioferreira.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Produto buscar(Integer Id) {
		 Optional<Produto> obj = repo.findById(Id);
		 return obj.orElseThrow(() -> new ObjectNotFoundException(
		  "Objeto não encontrado! Id: " + Id + ", Tipo: " + Produto.class.getName()));
		 }

	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		return repo.search(nome, categorias, pageRequest);
		/* lembrando do findDistinctByNomeContaingAndCategoriasIn - ver Repository*/
		
	}
}