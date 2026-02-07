package co.edu.unbosque.purpleindustries.persistance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.edu.unbosque.purpleindustries.model.*;

public class PacienteDAO implements OperacionDAO<Paciente> {

	private ArrayList<Paciente> listaPacientes;
	private final String TEXT_FILE_NAME = "paciente";

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
	public int actualizar(int documento, Paciente datoAActualizar) {
		if (documento < 0) {
			return 0;
		} else {
			Paciente p = getPacienteById(documento);
			if (p!=null) {
				p.setNombre(datoAActualizar.getNombre());
				p.setFechaDeNacimiento(datoAActualizar.getFechaDeNacimiento());
				p.setDocumento(datoAActualizar.getDocumento());
				p.setAltura(datoAActualizar.getAltura());
				p.setPeso(datoAActualizar.getPeso());
				p.setRh(datoAActualizar.getRh());
			}
			
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
			escribirEnArchivo();
		}
		
	}

	public void escribirEnArchivo() {
		List<List<Object>> datos = new ArrayList<>();

		for (Paciente paciente : listaPacientes) {
			List<Object> fila = new ArrayList<>();
			fila.add(paciente.getNombre());
			fila.add(paciente.getFechaDeNacimiento());
			fila.add(paciente.getDocumento());
			fila.add(paciente.getAltura());
			fila.add(paciente.getPeso());
			fila.add(paciente.getRh());
			fila.add(paciente.getTriage());
			fila.add(paciente.getDiagnostico());
			datos.add(fila);
		}

		SheetsManager.crear(datos, TEXT_FILE_NAME);

	}

	public void cargarDesdeArchivo() {
		List<List<String>> datos = SheetsManager.leerDesdeSheets(TEXT_FILE_NAME, "A:H");

		for (int i = 0; i < datos.size(); i++) {
			List<String> fila = datos.get(i);
			if (fila.size() >= 8) {
				try {
					String nombre = fila.get(0);
					String fechaDeNacimiento = fila.get(1);
					int documento = Integer.parseInt(fila.get(2));
					double altura = Double.parseDouble(fila.get(3));
					double peso = Double.parseDouble(fila.get(4));
					String rh = fila.get(5);
					int triage = Integer.parseInt(fila.get(6));
					String diagnostico = fila.get(7);
					listaPacientes.add(
							new Paciente(nombre, fechaDeNacimiento, documento, altura, peso, rh, triage, diagnostico));
				} catch (NumberFormatException e) {
					System.err.println("Error en formato de números en fila " + (i + 1));
				} catch (Exception e) {
					System.err.println("Error procesando fila " + (i + 1) + ": " + e.getMessage());
				}
			}
		}

	}

}
