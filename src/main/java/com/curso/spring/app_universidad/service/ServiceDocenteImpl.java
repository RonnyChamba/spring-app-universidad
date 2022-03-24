package com.curso.spring.app_universidad.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.curso.spring.app_universidad.entidades.Curso;
import com.curso.spring.app_universidad.entidades.Docente;

@Component
public class ServiceDocenteImpl implements IDocente {

	private List<Docente> docentes;

	@Autowired
	private ICurso serviceCurso;

	public ServiceDocenteImpl() {

		docentes = new ArrayList<Docente>();
	}

	@Override
	public void agregar(Docente docente) {

		if (buscarById(docente.getCedula()).isEmpty()) {
			docentes.add(docente);
		} else
			System.out.println(String.format("\t\t La cédula %s ya esta registrada", docente.getCedula()));

	}

	@Override
	public void eliminar(String cedula) {
		if (buscarById(cedula).isPresent()) {

			Iterator<Docente> iteratorDocente = docentes.iterator();

			while (iteratorDocente.hasNext()) {

				Docente docente = iteratorDocente.next();

				if (docente.getCedula().equals(cedula)) {

					iteratorDocente.remove();

					serviceCurso.eliminarDocente(docente.getCedula());
					System.out.println(String.format("\t\t Docente '%s - %s' eliminado correctamente",
							docente.getNombre(), docente.getCedula()));
					break;
				}
			}
		} else
			System.out.println(String.format("\t\t La cédula %s no existe", cedula));

	}

	@Override
	public void editar(Docente docente) {

		for (Docente itemDocente : docentes) {

			if (itemDocente.getCedula().equals(docente.getCedula())) {

				itemDocente.setContrato(
						docente.getContrato().isEmpty() ? itemDocente.getContrato() : docente.getContrato());
				itemDocente.setNombre(docente.getNombre().isEmpty() ? itemDocente.getNombre() : docente.getNombre());

				break;
			}
		}

	}

	@Override
	public Optional<Docente> buscarById(String cedula) {
		Optional<Docente> optionalDocente = docentes.stream().filter(docente -> docente.getCedula().equals(cedula))
				.findFirst();

		return optionalDocente;
	}

	public void imprimir() {
		System.out.println("\t\t*************** Lista de docentes **************");

		if (!docentes.isEmpty()) {

			for (Docente docente : docentes) {

				List<Curso> cursos = docente.getCursos();
				
		
				System.out.println(String.format("\t\t %s -%s", docente.getNombre(), docente.getCedula()));
				
				if (!cursos.isEmpty()) {
					
				
				cursos.forEach(itemCurso -> System.out.println(String.format("\t\t\t %s", itemCurso)));
				}else System.out.println("\t\t\t No tiene cursos asignados");

			}
		}else System.out.println("\t\t No existe docentes registrados");

	}

	public void matricular(String cedula, String codigoCurso) {

		Optional<Docente> optionalDocente = buscarById(cedula);

		if (optionalDocente.isPresent()) {

			Optional<Curso> optinalCurso = serviceCurso.buscarById(codigoCurso);

			if (optinalCurso.isPresent()) {

				Docente docente = optionalDocente.get();
				Curso curso = optinalCurso.get();

				if (!isMatriculado(docente.getCedula(), curso.getCodigo())) {

					docente.addCurso(curso);

					System.out.println(String.format("\t\t Docente ' %s ' fue agregado al curso ' %s %s' correctamente",
							docente.getNombre(), curso.getNivel().name(), curso.getParalelo()));

				} else
					System.out.println(String.format("\t\t El Docente '%s - %s' ya esta agregado en ' %s %s' ",
							docente.getNombre(), docente.getCedula(), curso.getNivel().name(), curso.getParalelo()));

			} else
				System.out.println(String.format("\t\t El curso '%s' no esta registrado", codigoCurso));

		} else
			System.out.println(String.format("\t\t La cédula  '%s' no esta registrada", cedula));

	}

	@Override
	public List<Docente> listar() {
		return docentes;
	}

	public boolean isMatriculado(String cedula, String codigoCurso) {

		return docentes.stream().filter(item -> item.getCedula().equals(cedula)).filter(
				item -> item.getCursos().stream().anyMatch(itemCurso -> itemCurso.getCodigo().equals(codigoCurso)))
				.findFirst().isPresent();
	}

}
