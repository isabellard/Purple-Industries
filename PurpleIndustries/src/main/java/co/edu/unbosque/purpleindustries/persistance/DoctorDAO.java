package co.edu.unbosque.purpleindustries.persistance;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.purpleindustries.model.*;

public class DoctorDAO implements OperacionDAO<Doctor> {

	private ArrayList<Doctor> listaDoctores;
	private final String TEXT_FILE_NAME = "doctor";

	public DoctorDAO() {
		listaDoctores = new ArrayList<>();
		cargarDesdeArchivo();
	}

	@Override
	public void crear(Doctor nuevoDato) {
		listaDoctores.add(nuevoDato);
		escribirEnArchivo();
	}

	@Override
	public int eliminar(int index) {
		if (index < 0 || index >= listaDoctores.size()) {
			return 0;
		} else {
			listaDoctores.remove(index);
			escribirEnArchivo();
			return 1;
		}
	}

	@Override
	public int eliminar(Doctor datoAEliminar) {
		if (listaDoctores.remove(datoAEliminar)) {
			escribirEnArchivo();
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public String mostrarTodo() {
		String salida = "";
		for (Doctor doctor : listaDoctores) {
			salida += doctor.toString();
		}
		return salida;
	}

	@Override
	public int cantidad() {
		return listaDoctores.size();
	}

	@Override
	public int comprobarPosicion(int index) {
		if (index < 0 || index >= listaDoctores.size()) {
			return 0;
		} else {
			return 1;
		}
	}

	public int actualizar(int documento, Doctor datoAActualizar) {
		if (documento < 0) {
			return 0;
		} else {
			Doctor p = getDoctorById(documento);
			if (p != null) {
				p.setNombre(datoAActualizar.getNombre());
				p.setFechaDeNacimiento(datoAActualizar.getFechaDeNacimiento());
				p.setDocumento(datoAActualizar.getDocumento());
				p.setEspecialidad(datoAActualizar.getEspecialidad());
			}

			escribirEnArchivo();
			return 1;
		}
	}

	public Doctor getDoctorById(int documento) {
		for (Doctor p : listaDoctores) {
			if (p.getDocumento() == documento) {
				return p;
			}
		}
		return null;

	}

	public void escribirEnArchivo() {
		List<List<Object>> datos = new ArrayList<>();

		for (Doctor doctor : listaDoctores) {
			List<Object> fila = new ArrayList<>();
			fila.add(doctor.getNombre());
			fila.add(doctor.getFechaDeNacimiento());
			fila.add(doctor.getDocumento());
			fila.add(doctor.getEspecialidad());
			datos.add(fila);
		}

		SheetsManager.crear(datos, TEXT_FILE_NAME);

	}

	public void cargarDesdeArchivo() {
		List<List<String>> datos = SheetsManager.leerDesdeSheets(TEXT_FILE_NAME, "A:D");

		for (int i = 0; i < datos.size(); i++) {
			List<String> fila = datos.get(i);
			if (fila.size() >= 8) {
				try {
					String nombre = fila.get(0);
					String fechaDeNacimiento = fila.get(1);
					int documento = Integer.parseInt(fila.get(2));
					String especialidad = fila.get(3);
					listaDoctores.add(new Doctor(nombre, fechaDeNacimiento, documento, especialidad));
				} catch (NumberFormatException e) {
					System.err.println("Error en formato de números en fila " + (i + 1));
				} catch (Exception e) {
					System.err.println("Error procesando fila " + (i + 1) + ": " + e.getMessage());
				}
			}
		}

	}

}
