package co.edu.unbosque.purpleindustries.model;

public class Paciente extends Persona {

	private double altura;
	private double peso;
	private String rh;
	private int triage;
	private String diagnostico;

	public Paciente() {
		// TODO Auto-generated constructor stub
	}

	public Paciente(String nombre, String fechaDeNacimiento, int documento) {
		super(nombre, fechaDeNacimiento, documento);
		// TODO Auto-generated constructor stub
	}

	public Paciente(double altura, double peso, String rh, int triage, String diagnostico) {
		super();
		this.altura = altura;
		this.peso = peso;
		this.rh = rh;
		this.triage = triage;
		this.diagnostico = diagnostico;
	}

	public Paciente(String nombre, String fechaDeNacimiento, int documento, double altura, double peso, String rh,
			int triage, String diagnostico) {
		super(nombre, fechaDeNacimiento, documento);
		this.altura = altura;
		this.peso = peso;
		this.rh = rh;
		this.triage = triage;
		this.diagnostico = diagnostico;
	}

	@Override
	public String toString() {
		return super.toString() + "\n Altura: " + altura + "\n Peso: " + peso + "\n Rh: " + rh + "\n Triage: " + triage
				+ "\n Diagnóstico: " + diagnostico + "\n--------------------------------";
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
	public double getPeso() {
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

	/**
	 * @param peso the peso to set
	 */
	public void setPeso(double peso) {
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
