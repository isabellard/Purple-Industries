package co.edu.unbosque.purpleindustries.util.exception;

public class EmailException extends Exception{
	
	public EmailException(String campo) {
		super("El email '" + campo + "' no es un campo válido.");
	}

}
 
