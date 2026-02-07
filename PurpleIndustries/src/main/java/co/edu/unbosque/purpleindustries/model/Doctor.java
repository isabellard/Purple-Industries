package co.edu.unbosque.purpleindustries.model;

public class Doctor extends Persona {

	private String especialidad;
	
	public Doctor() {
		// TODO Auto-generated constructor stub
	}

	public Doctor(String nombre, String fechaDeNacimiento, int documento) {
		super(nombre, fechaDeNacimiento, documento);
		// TODO Auto-generated constructor stub
	}

	public Doctor(String especialidad) {
		super();
		this.especialidad = especialidad;
	}

	public Doctor(String nombre, String fechaDeNacimiento, int documento, String especialidad) {
		super(nombre, fechaDeNacimiento, documento);
		this.especialidad = especialidad;
	}

	@Override
	public String toString() {
		return super.toString() +  "\n Especialidad: " + especialidad + "\n--------------------------------";
	}

	/**
	 * @return the especialidad
	 */
	public String getEspecialidad() {
		return especialidad;
	}

	/**
	 * @param especialidad the especialidad to set
	 */
	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}
	
	
	
	
	
}
