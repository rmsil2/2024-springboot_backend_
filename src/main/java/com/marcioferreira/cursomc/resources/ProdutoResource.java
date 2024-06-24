package com.marcioferreira.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marcioferreira.cursomc.domain.Produto;
import com.marcioferreira.cursomc.dto.ProdutoDTO;
import com.marcioferreira.cursomc.resources.utils.URL;
import com.marcioferreira.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		
		Produto obj = service.buscar(id);
		return ResponseEntity.ok().body(obj);
		
	}
	
	/* para paginar as produtos */
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(name="nome", defaultValue="") String  nome,
			@RequestParam(name="categorias", defaultValue="0") String categorias, 
			@RequestParam(name="page", defaultValue="0") Integer page, 
			@RequestParam(name="linesPerPage", defaultValue="6") Integer linesPerPage, 
			@RequestParam(name="direction", defaultValue="ASC") String direction, 
			@RequestParam(name="orderBy", defaultValue="nome") String orderBy) {
		
		String nomeDecoded = URL.decodeParam(nome);
		List<Integer> ids = URL.decodeIntList(categorias);
		Page<Produto> list = service.search(nomeDecoded, ids, page, linesPerPage, direction, orderBy);
		Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj));
		return ResponseEntity.ok().body(listDto);
		
	}
	

}
