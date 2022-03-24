package com.curso.spring.app_universidad.entidades;

public abstract class Persona {

	private String cedula;
	private String nombre;
	
	public Persona() {}
	
	public Persona(String cedula, String nombre) {
	
		this.cedula = cedula;
		this.nombre = nombre;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Persona [cedula=" + cedula + ", nombre=" + nombre + "]";
	}
	
}
