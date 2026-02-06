package co.edu.unbosque.persistance;

import java.util.ArrayList;
import java.util.Iterator;

import co.edu.unbosque.model.Doctor;
import co.edu.unbosque.model.Paciente;

public class PacienteDAO implements OperacionDAO<Paciente> {

	private ArrayList<Paciente> listaPacientes;
	private final String TEXT_FILE_NAME = "pacientes.csv";

	public PacienteDAO() {
		listaPacientes = new ArrayList<>();
		cargarDesdeArchivo();
	}

	@Override
	public void crear(Paciente nuevoDato) {
		listaPacientes.add(nuevoDato);
		escribirEnArchivo();

	}

	@Override
	public int eliminar(int index) {
		if (index < 0 || index >= listaPacientes.size()) {
			return 0;
		} else {
			listaPacientes.remove(index);
			escribirEnArchivo();
			return 1;
		}
	}

	@Override
	public int eliminar(Paciente datoAEliminar) {
		if (listaPacientes.remove(datoAEliminar)) {
			escribirEnArchivo();
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public int actualizar(int index, Paciente datoAActualizar) {
		if (index < 0 || index > listaPacientes.size()) {
			return 0;
		} else {
			listaPacientes.set(index, datoAActualizar);
			escribirEnArchivo();
			return 1;
		}
	}
	
	

	@Override
	public String mostrarTodo() {
		String salida = "";
		for (Paciente paciente : listaPacientes) {
			salida += paciente.toString();
		}
		return salida;
	}

	@Override
	public int cantidad() {
		return listaPacientes.size();
	}

	@Override
	public int comprobarPosicion(int index) {
		if (index < 0 || index >= listaPacientes.size()) {
			return 0;
		} else {
			return 1;
		}
	}

	public void escribirEnArchivo() {
		String contenido = "";

		for (int i = 0; i < listaPacientes.size(); i++) {
			contenido += listaPacientes.get(i).getNombre() + ";";
			contenido += listaPacientes.get(i).getFechaDeNacimiento() + ";";
			contenido += listaPacientes.get(i).getDocumento() + ";";
			contenido += listaPacientes.get(i).getAltura() + ";";
			contenido += listaPacientes.get(i).getPeso() + ";";
			contenido += listaPacientes.get(i).getRh() + ";";
			contenido += listaPacientes.get(i).getTriage() + ";";
			contenido += listaPacientes.get(i).getDiagnostico() + "\n";

		}

		FileManager.escribirArchivoDeTexto(TEXT_FILE_NAME, contenido);

	}

	public void cargarDesdeArchivo() {

		String contenido = FileManager.leerArchivoTexto(TEXT_FILE_NAME);

		if (contenido.isBlank() || contenido.isEmpty()) {
			return;
		}
		String[] filas = contenido.split("\n");
		for (int i = 0; i < filas.length; i++) {
			String[] columnas = filas[i].split(";");

			String nombre = columnas[0];
			String fechaDeNacimiento = columnas[1];
			int documento = Integer.parseInt(columnas[2]);
			double altura = Double.parseDouble(columnas[3]);
			double peso = Double.parseDouble(columnas[4]);
			String rh = columnas[5];
			int triage = Integer.parseInt(columnas[6]);
			String diagnostico = columnas[7];
			listaPacientes
					.add(new Paciente(nombre, fechaDeNacimiento, documento, altura, peso, rh, triage, diagnostico));

		}

	}

	public Paciente getPacienteById(int documento) {
		for (Paciente p : listaPacientes) {
			if (p.getDocumento() == documento) {
				return p;
			}
		}
		return null;
		
	}
	
	public void modificarDatosMedicos(int documento, int triage, String diagnostico) {
		Paciente p = getPacienteById(documento);
		
		if (p!=null) {
			p.setTriage(triage);
			p.setDiagnostico(diagnostico);
		}
		
	}

}
