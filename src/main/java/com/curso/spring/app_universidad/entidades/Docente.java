package com.curso.spring.app_universidad.entidades;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Docente  extends Miembro{
	
	private String contrato;
	private List<Curso> cursos;
	
	public Docente() {
		
	}
	
	public Docente(String cedula, String nombre, String codigo, String contrato) {
		super(cedula, nombre, codigo);
		this.contrato = contrato;
	}

	@PostConstruct
	public void init() {
		cursos = new ArrayList<Curso>();
	}
	
	public String getContrato() {
		return contrato;
	}

	public void setContrato(String contrato) {
		this.contrato = contrato;
	}
	
	public void addCurso(Curso curso) {
		
		cursos.add(curso);
		curso.addDocente(this);
	}
	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}
	

	@Override
	public String toString() {
		return "Docente [contrato=" + contrato + ","+ super.toString() + ", Cursos: "+ cursos.size()+"]";
	}

}
