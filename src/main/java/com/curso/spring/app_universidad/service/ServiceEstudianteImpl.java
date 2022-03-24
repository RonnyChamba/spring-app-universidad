package com.curso.spring.app_universidad.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.curso.spring.app_universidad.entidades.Curso;
import com.curso.spring.app_universidad.entidades.EnumNivel;
import com.curso.spring.app_universidad.entidades.Estudiante;

@Component
public class ServiceEstudianteImpl implements IEstudiante {

	private List<Estudiante> estudiantes;

	@Autowired
	private ICurso serviceCurso;

	public ServiceEstudianteImpl() {

		estudiantes = new ArrayList<Estudiante>();
	}

	@Override
	public void agregar(Estudiante estudiante) {

		if (buscarById(estudiante.getCedula()).isEmpty()) {
			estudiantes.add(estudiante);
		} else
			System.out.println(String.format("\t\t La cédula %s ya esta registrada", estudiante.getCedula()));

	}

	@Override
	public void eliminar(String cedula) {
		if (buscarById(cedula).isPresent()) {

			Iterator<Estudiante> iteratorEstudiante = estudiantes.iterator();

			while (iteratorEstudiante.hasNext()) {

				Estudiante estudiante = iteratorEstudiante.next();

				if (estudiante.getCedula().equals(cedula)) {

					iteratorEstudiante.remove();
					
					serviceCurso.eliminarEstudiante(estudiante.getCedula());
					System.out.println(String.format("\t\t Estudiante '%s - %s' eliminado correctamente",
							estudiante.getNombre(), estudiante.getCedula()));
					break;
				}
			}
		}else System.out.println(String.format("\t\t La cédula %s no existe", cedula));

	}

	@Override
	public void editar(Estudiante estudiante) {

		// Estudiante estudianteEditar = buscarById(estudiante.getCedula()).get();

		for (Estudiante itemEstudiante : estudiantes) {

			if (itemEstudiante.getCedula().equals(estudiante.getCedula())) {

				itemEstudiante.setPasatiempo(estudiante.getPasatiempo().isEmpty() ? itemEstudiante.getPasatiempo()
						: estudiante.getPasatiempo());
				itemEstudiante.setNombre(
						estudiante.getNombre().isEmpty() ? itemEstudiante.getNombre() : estudiante.getNombre());

				break;
			}
		}

	}

	@Override
	public Optional<Estudiante> buscarById(String cedula) {
		Optional<Estudiante> optionalEstudiante = estudiantes.stream()
				.filter(estudiante -> estudiante.getCedula().equals(cedula)).findFirst();

		return optionalEstudiante;
	}

	public void imprimir() {

		imprimirMatriculado();
		
		imprimirNoMatriculado();

	}

	private void imprimirMatriculado() {

		Map<EnumNivel, Map<String, List<Estudiante>>> mapEstudianteByNivel = estudiantes.stream()
				.filter(item -> item.getCurso() != null)
				.collect(Collectors.groupingBy(item -> item.getCurso().getNivel(),
						Collectors.groupingBy(item -> item.getCurso().getParalelo())));

		
		
		System.out.println("\t\t Estudiantes matriculados");
		if (!mapEstudianteByNivel.isEmpty()) {

			for (EnumNivel nivel : mapEstudianteByNivel.keySet()) {

				System.out.println(String.format("\t\t\t Nivel: %s", nivel.name()));

				Map<String, List<Estudiante>> mapEstudianteByParalelo = mapEstudianteByNivel.get(nivel);

				for (String itemParalelo : mapEstudianteByParalelo.keySet()) {

					System.out.println(String.format("\t\t\t\t Paralelo: %s", itemParalelo));

					List<Estudiante> listEstudianteByParalelo = mapEstudianteByParalelo.get(itemParalelo);

					for (Estudiante est : listEstudianteByParalelo) {

						System.out.println(String.format("\t\t\t\t\t %s", est));
					}
				}

			}

		} else
			System.out.println("\t\t\t No existen Estudiantes matriculados");

	}

	private void imprimirNoMatriculado() {
		
		System.out.println("\t\t Personas registradas Pero no Matriculadas");
		
		List<Estudiante> listNoEstudiante = estudiantes.stream().filter(item -> item.getCurso() == null).collect(Collectors.toList());
		
		if (!listNoEstudiante.isEmpty()) {
			
			listNoEstudiante.forEach(item -> System.out.println(String.format("\t\t\t %s", item)));
		}else System.out.println("\t\t\t No existe Personas registradas  NO Matriculadas");

	}

	public void matricular(String cedula, String codigoCurso) {

		Optional<Estudiante> optionalEstudiante = buscarById(cedula);

		if (optionalEstudiante.isPresent()) {

			Estudiante estudiante = optionalEstudiante.get();

			if (!isMatriculado(estudiante.getCedula())) {

				Optional<Curso> optinalCurso = serviceCurso.buscarById(codigoCurso);

				if (optinalCurso.isPresent()) {
					estudiante.setCurso(optinalCurso.get());
					System.out.println(String.format("\t\t Estudiante ' %s ' matriculado en ' %s %s' correctamente",
							estudiante.getNombre(), optinalCurso.get().getNivel().name(),
							optinalCurso.get().getParalelo()));

				} else
					System.out.println(String.format("\t\t El curso '%s' no esta registrado", codigoCurso));

			} else
				System.out.println(String.format("\t\t El estudiante '%s' ya esta matriculado", cedula));

		} else
			System.out.println(String.format("\t\t La cédula  '%s' no esta registrada", cedula));

	}

	public List<Estudiante> buscarByPararelo(EnumNivel nivel) {

		List<Estudiante> estudiantesNivel = estudiantes.stream()
				.filter(itemEstudiante -> itemEstudiante.getCurso().getNivel().name().equals(nivel.name()))
				.collect(Collectors.toList());

		return estudiantesNivel;

	}

	@Override
	public List<Estudiante> listar() {
		return estudiantes;
	}

	public boolean isMatriculado(String cedula) {

		return estudiantes.stream().filter(item -> item.getCedula().equals(cedula))
				.filter(item -> item.getCurso() != null).findFirst().isPresent();
	}

}
