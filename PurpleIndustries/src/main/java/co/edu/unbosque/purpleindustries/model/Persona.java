package co.edu.unbosque.purpleindustries.model;

public abstract class Persona {

	private String nombre;
	private String fechaDeNacimiento;
	private int documento;

	public Persona() {
		// TODO Auto-generated constructor stub
	}

	public Persona(String nombre, String fechaDeNacimiento, int documento) {
		super();
		this.nombre = nombre;
		this.fechaDeNacimiento = fechaDeNacimiento;
		this.documento = documento;
	}

	@Override
	public String toString() {
		return "\nNombre: " + nombre + "\n Fecha de nacimiento: " + fechaDeNacimiento + "\n Documento: " + documento;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @return the fechaDeNacimiento
	 */
	public String getFechaDeNacimiento() {
		return fechaDeNacimiento;
	}

	/**
	 * @return the documento
	 */
	public int getDocumento() {
		return documento;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @param fechaDeNacimiento the fechaDeNacimiento to set
	 */
	public void setFechaDeNacimiento(String fechaDeNacimiento) {
		this.fechaDeNacimiento = fechaDeNacimiento;
	}

	/**
	 * @param documento the documento to set
	 */
	public void setDocumento(int documento) {
		this.documento = documento;
	}

}
