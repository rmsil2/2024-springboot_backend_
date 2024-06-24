package com.marcioferreira.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.marcioferreira.cursomc.domain.Categoria;
import com.marcioferreira.cursomc.dto.CategoriaDTO;
import com.marcioferreira.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		
		Categoria obj = service.buscar(id);
		return ResponseEntity.ok().body(obj);
		
	}

	/* para inserir categoria no bd com metodo iinsert do service*/
	/* incluindo validacao - 08-06 - criacao do fromDTo em service e 
	 * aqui foio alterado para CategoriaDTO */
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDto) {
		Categoria obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
		
	}
	
	/* para atualizar categoria no bd com metodo update do service*/
	/* incluindo validacao - 08-06 - criacao do fromDTo em service e 
	 * aqui foio alterado para CategoriaDTO */
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO objDto, @PathVariable Integer id) {
		Categoria obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
	
		return ResponseEntity.noContent().build();
		
	}
	
	/* para deletar categoria no bd com metodo delete do service*/
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
	
		return ResponseEntity.noContent().build();
		
	}
	
	/* para listar tds categoria no bd com metodo do service
	 * usando DTo para não retornar os produtos relacionados*/
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		
		List<Categoria> list = service.buscarTd();
		List<CategoriaDTO> listDto = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
		
	}
	
	/* para paginar as categorias */
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(@RequestParam(name="page", defaultValue="0") Integer page, 
			@RequestParam(name="linesPerPage", defaultValue="6") Integer linesPerPage, 
			@RequestParam(name="direction", defaultValue="ASC") String direction, 
			@RequestParam(name="orderBy", defaultValue="nome") String orderBy) {
		
		Page<Categoria> list = service.findPage(page, linesPerPage, direction, orderBy);
		Page<CategoriaDTO> listDto = list.map(obj -> new CategoriaDTO(obj));
		return ResponseEntity.ok().body(listDto);
		
	}
	
}
	
