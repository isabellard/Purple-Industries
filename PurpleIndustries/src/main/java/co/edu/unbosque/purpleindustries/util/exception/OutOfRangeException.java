package co.edu.unbosque.purpleindustries.util.exception;

public class OutOfRangeException extends Exception {

	public OutOfRangeException(String campo, String rango) {
		super("El valor del campo '" + campo + "' está fuera del rango permitido (" + rango + "), vuelva a intentar.");
	}
}
