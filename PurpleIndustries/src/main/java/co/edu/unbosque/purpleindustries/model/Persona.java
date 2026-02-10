package co.edu.unbosque.purpleindustries.model;

import java.time.LocalDateTime;

public abstract class Persona {

	private String nombre;
	private LocalDateTime fechaDeNacimiento;
	private int documento;

	public Persona() {
		// TODO Auto-generated constructor stub
	}

	public Persona(String nombre, LocalDateTime fechaDeNacimiento, int documento) {
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
	public LocalDateTime getFechaDeNacimiento() {
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
	public void setFechaDeNacimiento(LocalDateTime fechaDeNacimiento) {
		this.fechaDeNacimiento = fechaDeNacimiento;
	}

	/**
	 * @param documento the documento to set
	 */
	public void setDocumento(int documento) {
		this.documento = documento;
	}

}
