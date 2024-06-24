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

import com.marcioferreira.cursomc.domain.Cliente;
import com.marcioferreira.cursomc.dto.ClienteDTO;
import com.marcioferreira.cursomc.dto.ClienteNewDTO;
import com.marcioferreira.cursomc.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		
		Cliente obj = service.buscar(id);
		return ResponseEntity.ok().body(obj);
		
	}
	
	/* para inserir categoria no bd com metodo iinsert do service*/
	/* incluindo validacao - 08-06 - criacao do fromDTo em service e 
	 * aqui foio alterado para CategoriaDTO */
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDto) {
		Cliente obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
		
	}

	/* para atualizar cliente no bd com metodo update do service*/
	/* incluindo validacao - 08-06 - criacao do fromDTo em service e 
	 * aqui foio alterado para ClienteDTO */
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id) {
		Cliente obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
	
		return ResponseEntity.noContent().build();
		
	}
	
	/* para deletar cliente no bd com metodo delete do service*/
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
	
		return ResponseEntity.noContent().build();
		
	}
	
	/* para listar tds cliente no bd com metodo do service
	 * usando DTo para não retornar os produtos relacionados*/
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() {
		
		List<Cliente> list = service.buscarTd();
		List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
		
	}
	
	/* para paginar as clientes */
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(@RequestParam(name="page", defaultValue="0") Integer page, 
			@RequestParam(name="linesPerPage", defaultValue="6") Integer linesPerPage, 
			@RequestParam(name="direction", defaultValue="ASC") String direction, 
			@RequestParam(name="orderBy", defaultValue="nome") String orderBy) {
		
		Page<Cliente> list = service.findPage(page, linesPerPage, direction, orderBy);
		Page<ClienteDTO> listDto = list.map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok().body(listDto);
		
	}
	
	
	
}
