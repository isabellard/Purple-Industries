package co.edu.unbosque.purpleindustries.model;

import co.edu.unbosque.purpleindustries.persistance.*;

public class ModelFacade {

	private DoctorDAO doctorDAO;
	private PacienteDAO pacienteDAO;

	public ModelFacade() {
		doctorDAO = new DoctorDAO();
		pacienteDAO = new PacienteDAO();
	}
	
	public boolean documentoExiste(String id) {
		if (doctorDAO.getDoctorById(id) != null || pacienteDAO.getPacienteById(id)!=null) {
			return true;
		}else {
			return false;
		}
	}
	
	public String rellenarDocumento(int documento) {
		String id = String.valueOf(documento);
		int n = 10 - id.length(); 
		for (int i = 0; i < n; i++) {
			id += "*"; 
		}
		return id; 
		
	}
	
	public String modificarNombre(String nombre) {
		String[] nombrePorcionado = nombre.split(" ");
		String[] nombreFinal = new String[nombrePorcionado.length];
		int tamaño = nombrePorcionado.length;
		int pos = 0;
		boolean stop = true; 
		boolean stop2 = true;

		while (stop) {
			nombreFinal[pos] = nombrePorcionado[tamaño - 1];
			pos++;
			tamaño -= 2;
			if(tamaño <= 0) {
				stop = false;
			}
		}
		
		int tamImpar = 1;
		int tamPar = 0;
		
		if(nombrePorcionado.length % 2 == 0) {
			while(stop2) {
				nombreFinal[pos] = nombrePorcionado[tamPar];
				pos++;
				tamPar += 2;
				if(tamPar >= nombrePorcionado.length) {
					stop2 = false;
				}
			}
		}else {
			while(stop2) {
				nombreFinal[pos] = nombrePorcionado[tamImpar];
				pos++;
				tamImpar += 2;
				if(tamImpar >= nombrePorcionado.length) {
					stop2 = false;
				}
			}
		}
		String nombreModificado = "";
		
		for (int i = 0; i < nombreFinal.length; i++) {
			nombreModificado = nombreModificado + " " +nombreFinal[i];
		} 
		
		return nombreModificado;
	}
	
	/**
	 * @return the doctorDAO
	 */
	public DoctorDAO getDoctorDAO() {
		return doctorDAO;
	}

	/**
	 * @return the pacienteDAO
	 */
	public PacienteDAO getPacienteDAO() {
		return pacienteDAO;
	}

	/**
	 * @param doctorDAO the doctorDAO to set
	 */
	public void setDoctorDAO(DoctorDAO doctorDAO) {
		this.doctorDAO = doctorDAO;
	}

	/**
	 * @param pacienteDAO the pacienteDAO to set
	 */
	public void setPacienteDAO(PacienteDAO pacienteDAO) {
		this.pacienteDAO = pacienteDAO;
	}

	

}
