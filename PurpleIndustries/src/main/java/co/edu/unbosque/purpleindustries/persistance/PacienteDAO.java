package co.edu.unbosque.purpleindustries.persistance;

import co.edu.unbosque.purpleindustries.model.Paciente;
import co.edu.unbosque.purpleindustries.util.ConversorAltura;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
	public int actualizar(String documento, Paciente datoAActualizar) {
		if (documento == null) {
			return 0;
		} else {
			Paciente p = getPacienteById(documento);
			if (p != null) {
				p.setNombre(datoAActualizar.getNombre());
				p.setFechaDeNacimiento(datoAActualizar.getFechaDeNacimiento());
				p.setDocumento(datoAActualizar.getDocumento());
				p.setEmail(datoAActualizar.getEmail());
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

	public Paciente getPacienteById(String documento) {
		for (Paciente p : listaPacientes) {
			if (p.getDocumento().equals(documento)) {
				return p;
			}
		}
		return null;
	}

	public void modificarDatosMedicos(String documento, int triage, String diagnostico) {
		Paciente p = getPacienteById(documento);

        if (p != null) {
            p.setTriage(triage);
            p.setDiagnostico(diagnostico);
            escribirEnArchivo();
        }

    }
    
    public ArrayList<Paciente> filtrarCriticos() {
    	ArrayList<Paciente> listaCriticos = new ArrayList<>();
    	for (Paciente paciente : listaPacientes) {
			if (paciente.getTriage() == 1) {
				listaCriticos.add(paciente);
			}
		}
    	return listaCriticos;
    }
    

	public String modificarTiempoAtencion(String documento, int tiempoAtencion) {
		Paciente p = getPacienteById(documento);

		if (p != null) {
			if (tiempoAtencion == 1) {
				p.setTiempoAtencion("Menos de 1 min");
				escribirEnArchivo();
				return "Menos de 1 minuto";
			} else if (tiempoAtencion == 2) {
				p.setTiempoAtencion("Entre 2 a 5 min");
				escribirEnArchivo();
				return "Entre 2 a 5 min";
			} else if (tiempoAtencion == 3) {
				p.setTiempoAtencion("Entre 5 a 10 min");
				escribirEnArchivo();
				return "Entre 5 a 10 min";
			} else if (tiempoAtencion == 4) {
				p.setTiempoAtencion("Entre 10 a 15 min");
				escribirEnArchivo();
				return "Entre 10 a 15 min";
			} else if (tiempoAtencion == 5) {
				p.setTiempoAtencion("Mayor a 15 min");
				escribirEnArchivo();
				return "Mayor a 15 min";
			}
		}
		return null;

	}

	public void escribirEnArchivo() {
		List<List<Object>> datos = new ArrayList<>();

		for (Paciente paciente : listaPacientes) {
			List<Object> fila = new ArrayList<>();

			fila.add(paciente.getNombre());
			fila.add(paciente.getFechaDeNacimiento());
			fila.add(paciente.getEdad());
			fila.add(paciente.getSignoZodiacal());
			fila.add(paciente.getDocumento());
			fila.add(paciente.getEmail());

			
			double metros = paciente.getAltura();

			String alturaTexto =
			        "Altura en metros: " + ConversorAltura.metrosTexto(metros) + "\n" +
			        "Altura en cm: " + ConversorAltura.cmTexto(metros) + "\n" +
			        "Altura en dm: " + ConversorAltura.dmTexto(metros) + "\n" +
			        "Altura en pies: " + ConversorAltura.piesTexto(metros) + "\n" +
			        "Altura en codos: " + ConversorAltura.codosTexto(metros);

			fila.add(alturaTexto);
			fila.add(paciente.getPeso());
			fila.add(paciente.getRh());
			fila.add(paciente.getTriage());
			fila.add(paciente.getDiagnostico());

			fila.add(paciente.getFechaIngreso() != null ? paciente.getFechaIngreso().toString()
					: LocalDate.now().toString());
			fila.add(paciente.getTiempoAtencion());
			datos.add(fila);
		}

		SheetsManager.append(datos, TEXT_FILE_NAME);
	}

	public void cargarDesdeArchivo() {

		List<List<String>> datos = SheetsManager.leerDesdeSheets(TEXT_FILE_NAME, "A:P");

		for (int i = 0; i < datos.size(); i++) {

			List<String> fila = datos.get(i);

			if (fila.size() >= 17) {

				try {

					String nombre = fila.get(0);
					String fechaDeNacimiento = fila.get(1);
					int edad = Integer.parseInt(fila.get(2));
					String signoZodiaco = fila.get(3);
					String documento = fila.get(4);
					String email = fila.get(5);
					String alturaTexto = fila.get(6).replace(" m", "").replace(",", ".").trim();
					double altura = Double.parseDouble(alturaTexto);
					String peso = fila.get(11);
					String rh = fila.get(12);
					int triage = Integer.parseInt(fila.get(13));
					String diagnostico = fila.get(14);

					String fechaIngresoStr = fila.get(15);
					LocalDate fechaIngreso = LocalDate.parse(fechaIngresoStr);
					String tiempoAtencion = fila.get(16);

					listaPacientes.add(new Paciente(nombre, fechaDeNacimiento, documento, email, altura, peso, rh,
							triage, diagnostico, fechaIngreso, tiempoAtencion));

				} catch (Exception e) {

					System.err.println("Error procesando fila " + (i + 1) + ": " + e.getMessage());

				}
			}
		}
	}

    public ArrayList<Paciente> getListaPacientes() {
    return listaPacientes;
}

	
	

}
