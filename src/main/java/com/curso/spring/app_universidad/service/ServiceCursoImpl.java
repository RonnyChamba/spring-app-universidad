package com.curso.spring.app_universidad.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.curso.spring.app_universidad.entidades.Curso;
import com.curso.spring.app_universidad.entidades.Docente;
import com.curso.spring.app_universidad.entidades.EnumNivel;
import com.curso.spring.app_universidad.entidades.Estudiante;
import com.curso.spring.app_universidad.entidades.Materia;

@Component
public class ServiceCursoImpl implements ICurso {

	private List<Curso> cursos;

	public ServiceCursoImpl() {
		cursos = new ArrayList<Curso>();
	}

	@Override
	public void agregar(Curso curso) {

		if (!isCursoExits(curso)) {

			cursos.add(curso);

		} else
			System.out.println(String.format("\t\t El curso '%s %s' ya esta registrado", curso.getNivel().name(),
					curso.getParalelo()));

	}

	@Override
	public void eliminar(String codigo) {
		if (buscarById(codigo).isPresent()) {

			Iterator<Curso> cursosIterator = cursos.iterator();

			while (cursosIterator.hasNext()) {

				Curso cursoItem = cursosIterator.next();
				if (cursoItem.getCodigo().equals(codigo)) {

					cursosIterator.remove();
					System.out.println(String.format("\t\t Curso '%s %s' eliminado correctamente",  cursoItem.getNivel().name(), cursoItem.getParalelo()));
		
					break;
				}

			}

		}

	}
	
	@Override
	public void eliminarMateria(String codigoMateria) {
		
		for (Curso curso: cursos) {
			
			  Iterator<Materia> iteradorMateria = curso.getMaterias().iterator();
			  
			  while(iteradorMateria.hasNext()) {
				  
				  Materia materiaItem = iteradorMateria.next();
				  
				  if (materiaItem.getCodigo().equals(codigoMateria)) {
					  
					  iteradorMateria.remove();
					  break;
				  }
				  
			  }
		}		
		
	}
	
	@Override
	public void eliminarEstudiante(String cedula) {
		
		for (Curso curso: cursos) {
			
			  Iterator<Estudiante> iteradorEstudiante = curso.getEstudiantes().iterator();
			  
			  while(iteradorEstudiante.hasNext()) {
				  
				  Estudiante estudianteItem = iteradorEstudiante.next();
				  
				  if (estudianteItem.getCedula().equals(cedula)) {
					  
					  iteradorEstudiante.remove();
					  break;
				  }
				  
			  }
		}		
		
	}
	@Override
	public void eliminarDocente(String cedula) {
		for (Curso curso: cursos) {
			
			  Iterator<Docente> iteradorDocente = curso.getDocentes().iterator();
			  
			  while(iteradorDocente.hasNext()) {
				  
				  Docente docenteItem = iteradorDocente.next();
				  
				  if (docenteItem.getCedula().equals(cedula)) {
					  
					  iteradorDocente.remove();
					  break;
				  }
				  
			  }
		}		
		
	}

	@Override
	public void editar(Curso curso) {
		Curso cursoEditar = buscarById(curso.getCodigo()).get();

		for (Curso itemCurso : cursos) {
			
			if (itemCurso.getCodigo().equalsIgnoreCase(cursoEditar.getCodigo())) {
				
				cursoEditar.setNivel(curso.getNivel());
				cursoEditar.setParalelo(curso.getParalelo());
				break;
			}
			
		}
		

	}

	@Override
	public List<Curso> listar() {
		
		return cursos;
	}

	
	public void imprimir() {
		System.out.println("\n\t\t *********** Lista de Cursos ************");
		if (cursos.size() > 0) {

			Map<String, List<Curso>> grupoCursos = cursos.stream()
					.collect(Collectors.groupingBy(item -> item.getNivel().name()));

			for (String keyNivel : grupoCursos.keySet()) {
				System.out.println(String.format("\t\t Nivel: %s", keyNivel));

				List<Curso> listCursos = grupoCursos.get(keyNivel);
				if (listCursos.size()>0) {
					listCursos.forEach(item -> System.out.println(String.format("\t\t\t %s", item)));
				}else System.out.println("\t\t\t Este nivel no tiene cursos registrados");

				
			}

		}else System.out.println("\t\t No existe cursos registrados");

	}

	@Override
	public void agregarMateria(String codigoCurso, Materia materia) {
		Optional<Curso> optional = buscarById(codigoCurso);

		if (optional.isPresent()) {

			optional.get().addMateria(materia);
			System.out.println("\t\t Materia agregada correctamente");
		}
		
	}
	@Override
	public Optional<Curso> buscarById(String codigo) {
	
		Optional<Curso> optionalCurso = cursos.stream().filter(itemCurso -> itemCurso.getCodigo().equals(codigo))
				.findFirst();

		return optionalCurso;
	}
	
	public List<Curso> listByNivel(EnumNivel nivel) {

		List<Curso> cursoNivel = cursos.stream().filter(itemCurso -> itemCurso.getNivel().name().equals(nivel.name()))
				.collect(Collectors.toList());

		return cursoNivel;
	}
	
	
	private boolean isCursoExits(Curso curso) {

		return cursos.stream().anyMatch(itemCurso -> (itemCurso.getNivel().name().equals(curso.getNivel().name())
				&& itemCurso.getParalelo().equalsIgnoreCase(curso.getParalelo())));

	}
	public EnumNivel getEnumNivel(String nivelNumero) {
		
		if (nivelNumero.equals("1"))
		    return EnumNivel.PRIMERO;
		
		else if (nivelNumero.equals("2"))
		    return EnumNivel.SEGUNDO;
		else if (nivelNumero.equals("3"))
		    return EnumNivel.TERCERO;
		
		else if (nivelNumero.equals("4"))
		    return EnumNivel.CUARTO;
		else if (nivelNumero.equals("5"))
		    return EnumNivel.QUINTO;
		
		else if (nivelNumero.equals("6"))
		    return EnumNivel.SEXTO;
		
		else if (nivelNumero.equals("7"))
		    return EnumNivel.SEPTIMO;
		
		else return EnumNivel.PRIMERO;
	}




}
