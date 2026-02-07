package co.edu.unbosque.purpleindustries.util.exception;

public class NegativeValueException extends Exception {

	public NegativeValueException(String campo) {
		super("El campo '" + campo + "' no puede ser negativo o cero, vuelva a intentar.");
	}

}
