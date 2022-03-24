package com.curso.spring.app_universidad.service;

import java.util.List;
import java.util.Optional;
import com.curso.spring.app_universidad.entidades.Materia;

public interface IMateria {

	void agregar(Materia materia);
	void eliminar(String codigo);
	void editar(Materia materia);
	List<Materia> listar();
	Optional<Materia> buscarById(String codigo);
}
