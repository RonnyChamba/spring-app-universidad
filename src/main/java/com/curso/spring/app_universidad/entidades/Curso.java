package com.curso.spring.app_universidad.entidades;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
public class Curso {
	private String codigo;
	private EnumNivel nivel;
	private String paralelo;
	private List<Materia> materias;
	private List<Estudiante> estudiantes;
	private List<Docente> docentes;

	
	public Curso() {
	}

	public Curso(String codigo, EnumNivel nivel, String paralelo) {
		this.codigo = codigo;
		this.nivel = nivel;
		this.paralelo = paralelo;
	}

	@PostConstruct
	private void init() {
		materias = new ArrayList<Materia>();
		estudiantes = new ArrayList<Estudiante>();
		docentes = new ArrayList<Docente>();
	
	}
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public EnumNivel getNivel() {
		return nivel;
	}

	public void setNivel(EnumNivel nivel) {
		this.nivel = nivel;
	}

	public String getParalelo() {
		return paralelo;
	}

	public void setParalelo(String paralelo) {
		this.paralelo = paralelo;
	}


	public List<Materia> getMaterias() {
		return materias;
	}

	public void setMaterias(List<Materia> materias) {
		this.materias = materias;
	}
	
	public void addMateria(Materia materia) {
		
		materias.add(materia);
		materia.setCurso(this);
		
	}

	public void addEstudiante(Estudiante estudiante) {
		
		 this.estudiantes.add(estudiante);
	}
	public List<Estudiante> getEstudiantes() {
		return estudiantes;
	}

	public void setEstudiantes(List<Estudiante> estudiantes) {
		this.estudiantes = estudiantes;
	}

	public void addDocente(Docente docente) {
		
		 this.docentes.add(docente);
	}

	public List<Docente> getDocentes() {
		return docentes;
	}

	public void setDocentes(List<Docente> docentes) {
		this.docentes = docentes;
	}
	

	@Override
	public String toString() {
		
		String mensaje = String.format("Curso [codigo = %s, nivel= %s, paralelo= %s, materias= %s, estudiantes= %s, docentes= %s]",
				codigo, nivel.name(), paralelo, materias.size(), estudiantes.size(), docentes.size());
		return mensaje;
	}
		
}

