package co.edu.unbosque.purpleindustries.util.exception;

public class EmptyFieldException extends Exception {

	public EmptyFieldException(String campo) {
		super("El campo '" + campo + "' no puede estar vacío o ser nulo, vuelva a intentar.");
	}

}