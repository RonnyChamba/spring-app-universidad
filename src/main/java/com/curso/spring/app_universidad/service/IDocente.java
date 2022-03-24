package com.curso.spring.app_universidad.service;

import java.util.List;
import java.util.Optional;

import com.curso.spring.app_universidad.entidades.Docente;
public interface IDocente {

	void agregar(Docente docente);
	void eliminar(String cedula);
	void editar(Docente docente);
	List<Docente > listar();
	Optional<Docente>buscarById(String cedula);
}
