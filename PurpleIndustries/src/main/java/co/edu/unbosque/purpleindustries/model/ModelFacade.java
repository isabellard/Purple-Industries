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
