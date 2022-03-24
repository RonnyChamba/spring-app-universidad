package com.curso.spring.app_universidad.entidades;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Estudiante extends Miembro {

	private String pasatiempo;
	private Curso curso;
	
	public Estudiante() {}

	public Estudiante(String cedula, String nombre, String codigo, String pasatiempo) {
		super(cedula, nombre, codigo);
		this.pasatiempo = pasatiempo;
		
	}

	public String getPasatiempo() {
		return pasatiempo;
	}

	public void setPasatiempo(String pasatiempo) {
		this.pasatiempo = pasatiempo;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
		curso.addEstudiante(this);
		
		
	}
	
	@Override
	public String toString() {
		
		String mensaje =String.format("Estudiante [ pasatiempo= %s   %s, Curso = %s]", pasatiempo, super.toString(), (curso==null? curso: curso.getCodigo()));
		
		return mensaje;
	}
		
}
