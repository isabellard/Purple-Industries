package co.edu.unbosque.purpleindustries.view;

import java.util.Scanner;

public class Console {

	private Scanner lector;

	public Console() {
		lector = new Scanner(System.in);

	}

	public int leerEntero() {
		return lector.nextInt();
	}

	public long leerLong() {
		return lector.nextLong();
	}

	public float leerfloat() {
		return lector.nextFloat();
	}

	public double leerDouble() {
		return lector.nextDouble();
	}

	public String leerPalabra() {
		return lector.next();

	}

	public String leerLinea() {
		return lector.nextLine();

	}

	public char leerCaracter() {
		return lector.next().charAt(0);
	}

	public short leerShort() {
		return lector.nextShort();
	}

	public boolean leerBooleano() {

		String entrada = leerLinea().toLowerCase();
		if (entrada.contains("si")) {
			return true;
		} else {
			return false;
		}
	}

	public void imprimirConSalto(String texto) {
		System.out.println(texto + "");
	}

	public void imprimirSinSalto(String texto) {
		System.out.print(texto + "");
	}

	public void quemarLinea() {
		lector.nextLine();
	}
}
