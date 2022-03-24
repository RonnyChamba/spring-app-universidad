package com.curso.spring.app_universidad.service;

import java.util.List;
import java.util.Optional;

import com.curso.spring.app_universidad.entidades.Curso;
import com.curso.spring.app_universidad.entidades.Materia;

public interface ICurso {
	
	void agregar(Curso curso);
	void eliminar(String codigo);
	void eliminarMateria(String codigo);
	void eliminarEstudiante(String cedula);
	void eliminarDocente(String cedula);
	void editar(Curso Curso);
	List<Curso> listar();
	Optional<Curso> buscarById(String codigo);
	void agregarMateria(String codigoCurso, Materia materia);
	
}
