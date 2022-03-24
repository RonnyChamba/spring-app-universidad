package com.curso.spring.app_universidad.service;

import java.util.List;
import java.util.Optional;

import com.curso.spring.app_universidad.entidades.Estudiante;

public interface IEstudiante {

	void agregar(Estudiante estudiante);
	void eliminar(String cedula);
	void editar(Estudiante estudiante);
	List<Estudiante> listar();
	Optional<Estudiante> buscarById(String cedula);
}
