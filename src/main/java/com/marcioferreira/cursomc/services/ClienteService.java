package com.marcioferreira.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marcioferreira.cursomc.domain.Cidade;
import com.marcioferreira.cursomc.domain.Cliente;
import com.marcioferreira.cursomc.domain.Endereco;
import com.marcioferreira.cursomc.domain.enums.TipoCliente;
import com.marcioferreira.cursomc.dto.ClienteDTO;
import com.marcioferreira.cursomc.dto.ClienteNewDTO;
import com.marcioferreira.cursomc.repositories.ClienteRepository;
import com.marcioferreira.cursomc.repositories.EnderecoRepository;
import com.marcioferreira.cursomc.services.exception.DataIntegrityException;
import com.marcioferreira.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Cliente buscar(Integer Id) {
		 Optional<Cliente> obj = repo.findById(Id);
		 return obj.orElseThrow(() -> new ObjectNotFoundException(
		  "Objeto não encontrado! Id: " + Id + ", Tipo: " + Cliente.class.getName()));
		 }

	/* metodo visto no resource para inserir em cliente */
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	/* metodo visto no resource para update em cliente */
	/* metodo interno updateData criado para garantir 
	 * alteração apenas dos campos que podem ser alterados*/
	public Cliente update(Cliente obj) {
		Cliente newObj = buscar(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	/* metodo visto no resource para deletar em cliente */
	public void delete(Integer id) {
		buscar(id);
		/* exception lancada para tratar a relacao com produto, quando existe produto para a cliente 
		 * com tratamento po exception criada em services.exception*/
		try {
			repo.deleteById(id); /* conforme anotacao - a partir do spring 2.x.x*/
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir cliente com pedidos relacionados");
		}
	}
	
	public List<Cliente> buscarTd() {
		return repo.findAll();
	}
	
	/* para paginar as clientes */
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	/* para validação de campos */
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}
	
	/* para validação de campos */
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2()!=null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3()!=null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}
	
	/* atualizar apenas os campos que podem ser atualizados */
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}