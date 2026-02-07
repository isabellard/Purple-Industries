package co.edu.unbosque.purpleindustries.util.exception;

public class InvalidFormatException extends Exception {

	public InvalidFormatException(String campo) {
		super("El formato del campo '" + campo + "' no es válido, vuelva a intentar.");
	}
}

