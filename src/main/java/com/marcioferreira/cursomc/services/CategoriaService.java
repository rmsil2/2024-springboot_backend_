package com.marcioferreira.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.marcioferreira.cursomc.domain.Categoria;
import com.marcioferreira.cursomc.dto.CategoriaDTO;
import com.marcioferreira.cursomc.repositories.CategoriaRepository;
import com.marcioferreira.cursomc.services.exception.DataIntegrityException;
import com.marcioferreira.cursomc.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer Id) {
		 Optional<Categoria> obj = repo.findById(Id);
		 return obj.orElseThrow(() -> new ObjectNotFoundException(
		  "Objeto não encontrado! Id: " + Id + ", Tipo: " + Categoria.class.getName()));
		 }

	/* metodo visto no resource para inserir em categoria */
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	/* metodo visto no resource para update em categoria */
	/* metodo interno updateData criado para garantir 
	 * alteração apenas dos campos que podem ser alterados*/
	public Categoria update(Categoria obj) {
		Categoria newObj = buscar(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	/* metodo visto no resource para deletar em categoria */
	public void delete(Integer id) {
		buscar(id);
		/* exception lancada para tratar a relacao com produto, quando existe produto para a categoria 
		 * com tratamento po exception criada em services.exception*/
		try {
			repo.deleteById(id); /* conforme anotacao - a partir do spring 2.x.x*/
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir categoria co produtos");
		}
	}
	
	public List<Categoria> buscarTd() {
		return repo.findAll();
	}
	
	/* para paginar as categorias */
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	/* para validação de campos */
	public Categoria fromDTO(CategoriaDTO objDto) {
		return new Categoria(objDto.getId(), objDto.getNome());
	}
	
	/* atualizar apenas os campos que podem ser atualizados */
	private void updateData(Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());
	}
}