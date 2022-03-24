package com.curso.spring.app_universidad.entidades;


public class Miembro extends Persona{
	
	private String codigo;
	
	public Miembro() {}

	public Miembro(String cedula, String nombre, String codigo) {
		super(cedula, nombre);
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Override
	public String toString() {
		return  "Persona = " + super.toString() +    ", Miembro [codigo=" + codigo + "]";
	}
	
	
	

	
	
	
}
