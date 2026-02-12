package co.edu.unbosque.purpleindustries.model;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public abstract class Persona {

	private String nombre;
	private String fechaDeNacimiento;
	private int edad;
	private String signoZodiacal;
	private int documento;
	private String email;

	public Persona() {
		// TODO Auto-generated constructor stub
	}

	public Persona(String nombre, String fechaDeNacimiento, int documento, String email) {
		super();
		this.nombre = nombre;
		this.fechaDeNacimiento = fechaDeNacimiento;
		this.documento = documento;
		setFechaDeNacimiento(fechaDeNacimiento);
		this.email = email;
	}

	private int calcularEdad() {
		return calcularEdadConFecha(fechaDeNacimiento);
	}

	public static int calcularEdadConFecha(String fecha) {
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate nacimiento = LocalDate.parse(fecha, formato);
		return Period.between(nacimiento, LocalDate.now()).getYears();
	}

	public boolean cumpleEdadMinima(int edadMinima) {
		return edad >= edadMinima;
	}

	private String calcularSignoZodiacal() {

		String[] partes = fechaDeNacimiento.split("/");

		int dia = Integer.parseInt(partes[0]);
		int mes = Integer.parseInt(partes[1]);

		String[] signos = { "Capricornio", "Acuario", "Piscis", "Aries", "Tauro", "Géminis", "Cáncer", "Leo", "Virgo",
				"Libra", "Escorpio", "Sagitario" };

		int[] diasCambio = { 20, 19, 21, 20, 21, 21, 23, 23, 23, 23, 22, 22 };

		if (dia < diasCambio[mes - 1]) {
			return signos[mes - 1];
		} else {
			return signos[mes % 12];
		}
	}

	@Override
	public String toString() {
		return "Persona [nombre=" + nombre + ", fechaDeNacimiento=" + fechaDeNacimiento + ", edad=" + edad
				+ ", signoZodiacal=" + signoZodiacal + ", documento=" + documento + ", email=" + email + "]";
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
	 * @param documento the documento to set
	 */
	public void setDocumento(int documento) {
		this.documento = documento;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param fechaDeNacimiento the fechaDeNacimiento to set
	 */
	public void setFechaDeNacimiento(String fechaDeNacimiento) {
		this.fechaDeNacimiento = fechaDeNacimiento;
		this.edad = calcularEdad();
		this.signoZodiacal = calcularSignoZodiacal();
	}

	public int getEdad() {
		return edad;
	}

	public String getSignoZodiacal() {
		return signoZodiacal;
	}

	/**
	 * @param signoZodiacal the signoZodiacal to set
	 */
	public void setSignoZodiacal(String signoZodiacal) {
		this.signoZodiacal = signoZodiacal;
	}

}