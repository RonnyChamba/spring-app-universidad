package com.curso.spring.app_universidad.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.curso.spring.app_universidad.entidades.Materia;

@Component
public class ServiceMateriaImpl implements IMateria {
	
	private List<Materia> materias;
	
	@Autowired
	private ICurso serviceCurso;

	public ServiceMateriaImpl() {
		materias = new ArrayList<Materia>();
	}

	@Override
	public void agregar(Materia materia) {

		materias.add(materia);

	}

	@Override
	public void eliminar(String codigo) {

		if (buscarById(codigo).isPresent()) {

			Iterator<Materia> iterator = materias.iterator();

			while (iterator.hasNext()) {

				Materia materia = iterator.next();
		
				if (materia.getCodigo().equals(codigo)) {

					iterator.remove();
					System.out.println("\n\t\t  Materia Eliminada " + materia.getNombre()+"  correctamente");
					// Eliminar la materia tambien del curso
					serviceCurso.eliminarMateria(materia.getCodigo());
					
					break;
				}
			}
		}

	}

	@Override
	public void editar(Materia materia) {
		Materia materiaEditar = buscarById(materia.getCodigo()).get();

		for (Materia itemMateria : materias) {

			if (itemMateria.getCodigo().equals(materia.getCodigo())) {

				materiaEditar
						.setNombre(materia.getNombre().isEmpty() ? materiaEditar.getNombre() : materia.getNombre());

				materiaEditar.setCreditos(materia.getCreditos());

				break;
			}

		}
	}

	@Override
	public List<Materia> listar() {

		return materias;
	}

	@Override
	public Optional<Materia> buscarById(String codigo) {
		Optional<Materia> optionalMateria = materias.stream().filter(materia -> materia.getCodigo().equals(codigo))
				.findFirst();

		return optionalMateria;
	}

	public void imprimir() {

		System.out.println("\t\t***************** Lista Materias ************** ");

		if (materias.isEmpty())
			System.out.println("\t\t\t No hay materias registradas");
		else {
			for (Materia itemMateria : materias)
				System.out.println(String.format("\t\t\t %s", itemMateria));
		}

	}

}
