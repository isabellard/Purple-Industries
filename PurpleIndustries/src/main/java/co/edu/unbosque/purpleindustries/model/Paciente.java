package co.edu.unbosque.purpleindustries.model;

import java.time.LocalDate;

public class Paciente extends Persona {

	private double altura;
	private String peso;
	private String rh;
	private int triage;
	private String diagnostico;
	private LocalDate fechaIngreso;
	

	public Paciente() {
		// TODO Auto-generated constructor stub
	}

	public Paciente(String nombre, String fechaDeNacimiento, String documento, String email) {
		super(nombre, fechaDeNacimiento, documento, email);
		this.fechaIngreso = (fechaIngreso != null) ? fechaIngreso : LocalDate.now();
		// TODO Auto-generated constructor stub
	}



	public Paciente(double altura, String peso, String rh, int triage, String diagnostico, LocalDate fechaIngreso) {
		super();
		this.altura = altura;
		this.peso = peso;
		this.rh = rh;
		this.triage = triage;
		this.diagnostico = diagnostico;
		this.fechaIngreso = (fechaIngreso != null) ? fechaIngreso : LocalDate.now();
	}

	

	public Paciente(String nombre, String fechaDeNacimiento, String documento, String email, double altura, String peso,
			String rh, int triage, String diagnostico, LocalDate fechaIngreso) {
		super(nombre, fechaDeNacimiento, documento, email);
		this.altura = altura;
		this.peso = peso;
		this.rh = rh;
		this.triage = triage;
		this.diagnostico = diagnostico;
		this.fechaIngreso = (fechaIngreso != null) ? fechaIngreso : LocalDate.now();
	}

	@Override
	public String toString() {
		return super.toString() + "\n Altura: " + altura + "\n Peso: " + peso + "\n Rh: " + rh + "\n Triage: " + triage
				+ "\n Diagnóstico: " + diagnostico + "\n Fecha de Ingreso: " + fechaIngreso + "\n--------------------------------";
	}

	/**
	 * @return the altura
	 */
	public double getAltura() {
		return altura;
	}

	/**
	 * @return the peso
	 */
	public String getPeso() {
		return peso;
	}

	/**
	 * @return the rh
	 */
	public String getRh() {
		return rh;
	}

	/**
	 * @return the triage
	 */
	public int getTriage() {
		return triage;
	}

	/**
	 * @return the diagnostico
	 */
	public String getDiagnostico() {
		return diagnostico;
	}

	/**
	 * @param altura the altura to set
	 */
	public void setAltura(double altura) {
		this.altura = altura;
	}

	public LocalDate getFechaIngreso() { 
		return fechaIngreso; 
	}

    public void setFechaIngreso(LocalDate fechaIngreso) { 
		this.fechaIngreso = fechaIngreso; 
	}

	/**
	 * @param peso the peso to set
	 */
	public void setPeso(String peso) {
		this.peso = peso;
	}

	/**
	 * @param rh the rh to set
	 */
	public void setRh(String rh) {
		this.rh = rh;
	}

	/**
	 * @param triage the triage to set
	 */
	public void setTriage(int triage) {
		this.triage = triage;
	}

	/**
	 * @param diagnostico the diagnostico to set
	 */
	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}
}
