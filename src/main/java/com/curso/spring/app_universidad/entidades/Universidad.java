package com.curso.spring.app_universidad.entidades;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

import com.curso.spring.app_universidad.service.ICurso;
import com.curso.spring.app_universidad.service.IDocente;
import com.curso.spring.app_universidad.service.IEstudiante;
import com.curso.spring.app_universidad.service.IMateria;
import com.curso.spring.app_universidad.service.ServiceCursoImpl;
import com.curso.spring.app_universidad.service.ServiceDocenteImpl;
import com.curso.spring.app_universidad.service.ServiceEstudianteImpl;
import com.curso.spring.app_universidad.service.ServiceMateriaImpl;

@Component
public class Universidad {

	@Autowired
	private ICurso serviceCurso;
	
	@Autowired
	private IMateria serviceMateria;
		
	@Autowired
	private  IEstudiante serviceEstudiante;
	
	@Autowired
	private IDocente serviceDocente;
	
	private Scanner keyInput;
	
	private String opcionPrincipal;
	
	private AbstractApplicationContext context;
	public Universidad() {
		
		keyInput = new Scanner(System.in);
		
	}

	
	public void setContext(AbstractApplicationContext context) {
		this.context = context;
	}
	public void start() {

		boolean bandera = true;

		do {

			showMenuOpciones();

			boolean isExistOpcionPrincipal = getMapMenuPrincipal().keySet().stream()
					.anyMatch(optionMenu -> optionMenu.equalsIgnoreCase(opcionPrincipal));
			if (isExistOpcionPrincipal) {
				
				if (opcionPrincipal.equals("5")) break;
				
				String itemOpcionSeleccionada = showMenuItemOpciones();

				boolean isExistsItemSeleccionada = getMapItemModeloCrud().keySet().stream()
						.anyMatch(optionKey -> optionKey.equalsIgnoreCase(itemOpcionSeleccionada));

				if (isExistsItemSeleccionada) {

					switch (opcionPrincipal) {
					case "1":

						operacionesCrudCurso(itemOpcionSeleccionada);

						break;
					case "2":
						operacionesCrudMateria(itemOpcionSeleccionada);

						break;
					case "3":

						operacionesCrudEstudiante(itemOpcionSeleccionada);
						break;
					case "4":

						operacionesCrudDocente(itemOpcionSeleccionada);
						break;
						
					case "5":
						bandera = false;
						keyInput.close();
						break;

					default:
						break;
					}
				} else
					System.out.println("\t\tNo existe la item opcion " + itemOpcionSeleccionada);

			} else
				System.out.println(String.format("Opción %s %s ", opcionPrincipal, "no existe"));

			
		} while (bandera);

	}

	private void showMenuOpciones() {

		Map<String, String> menuModelos = getMapMenuPrincipal();
		String mensajeMenu = "\nMenú  Principal  Universidad\n";

		for (String key : menuModelos.keySet()) {
			mensajeMenu += String.format("\n%s) %s", key, menuModelos.get(key));
		}

		System.out.println(mensajeMenu);
		System.out.print("Ingrese opcion: ");
		opcionPrincipal = keyInput.nextLine();
	}

	private String showMenuItemOpciones() {

		String itemFullMenuOpcion = menuItemOpcionesCrud();
		System.out.println(itemFullMenuOpcion);
		System.out.print("\t\t Seleccione una tarea: ");

		String itemOpcionSeleccionada = keyInput.nextLine();

		return itemOpcionSeleccionada;
	}

	private String menuItemOpcionesCrud() {

		Map<String, String> menuItemModelos = getMapItemModeloCrud();
		String menuOpcion = getMapMenuPrincipal().get(opcionPrincipal);

		String mensajeMenuItem = String.format("\t************ Operaciones %s **************", menuOpcion);

		for (String keyItem : menuItemModelos.keySet()) {

			if (menuOpcion.equalsIgnoreCase("curso") || menuOpcion.equalsIgnoreCase("estudiante")
					|| menuOpcion.equalsIgnoreCase("docente")) {

				mensajeMenuItem += String.format("\n\t\t %s)  %s", keyItem, menuItemModelos.get(keyItem));
			} else
				mensajeMenuItem += String.format("\n\t\t %s)  %s %s", keyItem, menuItemModelos.get(keyItem),
						menuOpcion);
		}

		return mensajeMenuItem;
	}

	private Map<String, String> getMapMenuPrincipal() {

		Map<String, String> mapModelo = new HashMap<String, String>();

		mapModelo.put("1", "Curso");
		mapModelo.put("2", "Materia");
		mapModelo.put("3", "Estudiante");
		mapModelo.put("4", "Docente");
		mapModelo.put("5", "Salir");

		return mapModelo;

	}

	private Map<String, String> getMapItemModeloCrud() {

		Map<String, String> mapModelo = new HashMap<String, String>();

		mapModelo.put("a", "Agregar");
		mapModelo.put("b", "Editar");
		mapModelo.put("c", "Eliminar");
		mapModelo.put("d", "Listar");
		mapModelo.put("e", "Buscar");

		addItemMap(mapModelo);
		return mapModelo;

	}

	private void addItemMap(Map<String, String> mapModelo) {

		String materiaPrincipal = getMapMenuPrincipal().get(opcionPrincipal);

		if (materiaPrincipal.equalsIgnoreCase("curso")) {
			mapModelo.put("f", "Asignar Materia");
		} else if (materiaPrincipal.equalsIgnoreCase("estudiante") || (materiaPrincipal.equalsIgnoreCase("docente"))) {

			mapModelo.put("f", "Matricular");
		}

	}

	private void operacionesCrudCurso(String itemOpciom) {

		String codigo =  "";
		String nivel = "";
		String paraleo = "";
		Curso curso = null;
		Optional<Curso> optionalCurso = Optional.empty();
		switch (itemOpciom) {
		
		case "a":
			
			//curso = new Curso();
			
			curso = context.getBean("curso", Curso.class);
			codigo =  "CU-" +UUID.randomUUID().toString().substring(0,5);
			System.out.print( String.format("\t\t Ingrese Nivel : (1,2,3,4,6,6,7): "));
			nivel = keyInput.nextLine();
			
			System.out.print("\t\t Ingrese Paralelo: ");
			paraleo = keyInput.nextLine();
			
			curso.setCodigo(codigo);
			curso.setParalelo(paraleo);
			
			curso.setNivel( ((ServiceCursoImpl) serviceCurso).getEnumNivel(nivel));
			
			serviceCurso.agregar(curso);
			
			break;
		case "b":
			
			System.out.print("\t\t Ingrese código del curso a editar: ");
			codigo =  keyInput.nextLine();
			
			 optionalCurso = serviceCurso.buscarById(codigo);
			 
			if (optionalCurso.isPresent()) {
			
				System.out.print("\t\t\t Ingrese Nuevo Nivel (1,2,3,4,6,6,7): ");
				nivel = keyInput.nextLine();
				
				System.out.print("\t\t\t Ingrese Nuevo Paralelo: ");
				paraleo = keyInput.nextLine();
				
				curso = context.getBean("curso", Curso.class);
				curso.setCodigo( optionalCurso.get().getCodigo());
				curso.setNivel(	((ServiceCursoImpl) serviceCurso).getEnumNivel(nivel));
				curso.setParalelo(paraleo);
				
				serviceCurso.editar(curso);
				
			}else 
				System.out.println(String.format("\t\t El curso  con código '%s' no existe ", codigo));
			
			break;
		case "c":
			System.out.print("\t\t Ingrese código del curso a eliminar: ");
			codigo =  keyInput.nextLine();
			
			optionalCurso = serviceCurso.buscarById(codigo);
			
			if (optionalCurso.isPresent()) {
				
				serviceCurso.eliminar(codigo);
				
			}else  System.out.println(String.format("\t\t El curso  con código '%s' no existe ", codigo));
			

			break;
		case "d":
			
			((ServiceCursoImpl) serviceCurso).imprimir();
			break;
			
		case "e":
			
			System.out.print("\t\t Ingrese código del curso a buscar: ");
			codigo =  keyInput.nextLine();
			optionalCurso = serviceCurso.buscarById(codigo);
			
			if (optionalCurso.isPresent()) {
				System.out.println(String.format("\t\t\t %s", optionalCurso.get()));
				
				
			}else System.out.println(String.format("\t\t El curso  con código '%s' no existe ", codigo));
					
			break;

		case "f":
				
			System.out.print("\t\t Ingrese código del curso destino: ");
			codigo =  keyInput.nextLine();
			optionalCurso = serviceCurso.buscarById(codigo);
			
			if (optionalCurso.isPresent()) {
				
				System.out.print("\t\t Ingrese código de la Materia: ");
				String codigoMateria = keyInput.nextLine();
				
				Materia materia = serviceMateria.buscarById(codigoMateria).orElse(null);
				if (materia !=null) serviceCurso.agregarMateria(optionalCurso.get().getCodigo(), materia);
				
				else System.out.println(String.format("\t\t La Materia con codigo %s no existe", codigoMateria));
				
			}else System.out.println(String.format("\t\t El curso  con código '%s' no existe ", codigo));
		
			
			break;
		default:
			break;
		}

	}
	
	private void operacionesCrudMateria(String itemOpciom) {

		String codigo =  "";
		String nombre = "";
		int creditos = 0;
		String creditosTexto = "";
		Materia materia = null;
		Optional<Materia> optionalMateria = Optional.empty();
		
		switch (itemOpciom) {
		
		case "a":
			
			materia = context.getBean("materia", Materia.class);
			codigo =  "MA-" +UUID.randomUUID().toString().substring(0,5);
			
			System.out.print( String.format("\t\t Ingrese Nombre: "));
			nombre = keyInput.nextLine();
			
			System.out.print("\t\t Ingrese número de creaditos: ");
			creditosTexto = keyInput.nextLine();
			
			if (creditosTexto.matches("\\d+")) {
				creditos = Integer.parseInt(creditosTexto);
			}
			
			
			materia.setCodigo(codigo);
			materia.setNombre(nombre);
			materia.setCreditos(creditos);
			
			serviceMateria.agregar(materia);
			
			break;
		case "b":
			
			System.out.print("\t\t Ingrese código materia a editar: ");
			codigo =  keyInput.nextLine();
			
			 optionalMateria = serviceMateria.buscarById(codigo);
			 
			if (optionalMateria.isPresent()) {
			
				System.out.print("\t\t\t Ingrese nuevo nombre materia: ");
				nombre = keyInput.nextLine();
				
				System.out.print("\t\t\t Ingrese nuevo numero creditos: ");
				creditosTexto = keyInput.nextLine();
				
				if (creditosTexto.matches("\\d+")) {
					creditos = Integer.parseInt(creditosTexto);
				}
						
				materia = context.getBean("materia", Materia.class);
				
				materia.setCodigo(optionalMateria.get().getCodigo());
				materia.setNombre(nombre);
				materia.setCreditos(creditos);
				
				serviceMateria.editar(materia);
				
			}else 
				System.out.println(String.format("\t\t La materia con código '%s' no existe ", codigo));
			
			break;
		case "c":
			System.out.print("\t\t Ingrese código materia a eliminar: ");
			codigo =  keyInput.nextLine();
			
			optionalMateria = serviceMateria.buscarById(codigo);
			
			if (optionalMateria.isPresent()) {
				
				serviceMateria.eliminar(codigo);
				
			}else  System.out.println(String.format("\t\t La materia  con código '%s' no existe ", codigo));
			

			break;
		case "d":
			
			((ServiceMateriaImpl) serviceMateria).imprimir();
			break;
			
		case "e":
			
			System.out.print("\t\t Ingrese código materia a buscar: ");
			codigo =  keyInput.nextLine();
			optionalMateria = serviceMateria.buscarById(codigo);
			
			if (optionalMateria.isPresent()) {
				System.out.println(String.format("\t\t\t %s", optionalMateria.get()));
				
				
			}else System.out.println(String.format("\t\t La materia con código '%s' no existe ", codigo));
					
			break;
			
		default:
			break;
		}
	}

	
	private void operacionesCrudEstudiante(String itemOpciom) {

		String codigo =  "";
		String cedula = "";
		String nombre = "";
		String pasatiempo = "";
		
		Estudiante estudiante = null;
		Optional<Estudiante> optionalEstudiante = Optional.empty();
		switch (itemOpciom) {
		
		case "a":
						
			estudiante = context.getBean("estudiante", Estudiante.class);
			
			codigo =  "ES-" +UUID.randomUUID().toString().substring(0,5);
			System.out.print( String.format("\t\t Ingrese cédula: "));
			cedula = keyInput.nextLine();
			
			System.out.print("\t\t Ingrese nombre: ");
			nombre = keyInput.nextLine();
			
			
			System.out.print("\t\t Ingrese pasatiempo: ");
			pasatiempo = keyInput.nextLine();
			
			estudiante.setCodigo(codigo);
			estudiante.setCedula(cedula);
			estudiante.setNombre(nombre);
			estudiante.setPasatiempo(pasatiempo);
			
			serviceEstudiante.agregar(estudiante);
			break;
		case "b":
			
			System.out.print("\t\t Ingrese cédula estudiante a editar: ");
			cedula =  keyInput.nextLine();
			
			 optionalEstudiante = serviceEstudiante.buscarById(cedula);
			 
			if (optionalEstudiante.isPresent()) {
			
				System.out.print("\t\t\t Ingrese nuevo nombre: ");
				nombre = keyInput.nextLine();
				
				System.out.print("\t\t\t Ingrese nuevo pasatiempo: ");
				pasatiempo = keyInput.nextLine();
				
				estudiante = context.getBean("estudiante", Estudiante.class);
				estudiante.setCedula(optionalEstudiante.get().getCedula());
				estudiante.setNombre(nombre);
				estudiante.setPasatiempo(pasatiempo);
				
				serviceEstudiante.editar(estudiante);
				
			}else 
				System.out.println(String.format("\t\t La cédula '%s' no existe ", cedula));
			
			break;
		case "c":
			System.out.print("\t\t Ingrese cédula estudiante a eliminar: ");
			cedula =  keyInput.nextLine();
			
			serviceEstudiante.eliminar(cedula);
			

			break;
		case "d":
			
			
			((ServiceEstudianteImpl) serviceEstudiante ).imprimir();			
			break;
			
		case "e":
			
			System.out.print("\t\t Ingrese cédula estudiante a buscar: ");
			cedula =  keyInput.nextLine();
			optionalEstudiante = serviceEstudiante.buscarById(cedula);
			if (optionalEstudiante.isPresent()) {
				
				System.out.println(String.format("\t\t\t %s", optionalEstudiante.get()));
				
			}else System.out.println(String.format("\t\t La cédula '%s' no existe", cedula));
					
			break;

		case "f":
				
			System.out.print("\t\t Ingrese cedula del estudiante a matricular: ");
			cedula =  keyInput.nextLine();
			System.out.print("\t\t Ingrese codigo del curso destino: ");
			String codigoCurso =  keyInput.nextLine();
			
		    ((ServiceEstudianteImpl) serviceEstudiante ).matricular(cedula, codigoCurso);
			
		    break;
		default:
			break;
		}

	}
	private void operacionesCrudDocente(String itemOpciom) {

		String codigo =  "";
		String cedula = "";
		String nombre = "";
		String contrato = "";
		
		Docente docente = null;
		Optional<Docente> optionalDocente = Optional.empty();
		switch (itemOpciom) {
		
		case "a":
						
			docente = context.getBean("docente", Docente.class);
			
			codigo =  "DC-" +UUID.randomUUID().toString().substring(0,5);
			System.out.print( String.format("\t\t Ingrese cédula: "));
			cedula = keyInput.nextLine();
			
			System.out.print("\t\t Ingrese nombre: ");
			nombre = keyInput.nextLine();
			
			
			System.out.print("\t\t Ingrese contrato: ");
			contrato = keyInput.nextLine();
			
			docente.setCodigo(codigo);
			docente.setCedula(cedula);
			docente.setNombre(nombre);
			docente.setContrato(contrato);
			
		
			serviceDocente.agregar(docente);
			break;
		case "b":
			
			System.out.print("\t\t Ingrese cédula docente a editar: ");
			cedula =  keyInput.nextLine();
			
			 optionalDocente = serviceDocente.buscarById(cedula);
			 
			if (optionalDocente.isPresent()) {
			
				System.out.print("\t\t\t Ingrese nuevo nombre: ");
				nombre = keyInput.nextLine();
				
				System.out.print("\t\t\t Ingrese nuevo contrato: ");
				contrato = keyInput.nextLine();
				
				docente = context.getBean("docente", Docente.class);
				docente.setCedula(optionalDocente.get().getCedula());
				docente.setNombre(nombre);
				docente.setContrato(contrato);
				
				serviceDocente.editar(docente);
				
			}else 
				System.out.println(String.format("\t\t La cédula '%s' no existe ", cedula));
			
			break;
		case "c":
			System.out.print("\t\t Ingrese cédula docente a eliminar: ");
			cedula =  keyInput.nextLine();
			
			serviceDocente.eliminar(cedula);
			

			break;
		case "d":
			
			
			((ServiceDocenteImpl) serviceDocente ).imprimir();			
			break;
			
		case "e":
			
			System.out.print("\t\t Ingrese cédula docente a buscar: ");
			cedula =  keyInput.nextLine();
			optionalDocente = serviceDocente.buscarById(cedula);
			if (optionalDocente.isPresent()) {
				
				System.out.println(String.format("\t\t\t %s", optionalDocente.get()));
				
			}else System.out.println(String.format("\t\t La cédula '%s' no existe", cedula));
					
			break;

		case "f":
				
			System.out.print("\t\t Ingrese cedula del docente a matricular: ");
			cedula =  keyInput.nextLine();
			System.out.print("\t\t Ingrese codigo del curso destino: ");
			String codigoCurso =  keyInput.nextLine();
			
		    ((ServiceDocenteImpl) serviceDocente).matricular(cedula, codigoCurso);
			
		    break;
		default:
			break;
		}

	}
}
