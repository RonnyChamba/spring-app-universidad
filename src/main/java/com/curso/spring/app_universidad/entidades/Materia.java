package com.curso.spring.app_universidad.entidades;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Materia  {
	
	private String codigo;
	private String nombre;
	private int creditos;
	private Curso curso;
	
	public Materia(String codigo, String nombre, int creditos) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.creditos = creditos;
	}
	
	public Materia() {
		
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCreditos() {
		return creditos;
	}

	public void setCreditos(int creditos) {
		this.creditos = creditos;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	
	@Override
	public String toString() {
		
		String mensaje = String.format("Materia [codigo = %s, nombre= %s, creditos= %s, curso = %s]", codigo, nombre, creditos, 
				(curso==null? null : curso.getCodigo()));
		return mensaje;
	}


	
	
	
	
}
