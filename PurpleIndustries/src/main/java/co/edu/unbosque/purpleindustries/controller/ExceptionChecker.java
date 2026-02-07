package co.edu.unbosque.purpleindustries.controller;

import co.edu.unbosque.purpleindustries.util.exception.EmptyFieldException;
import co.edu.unbosque.purpleindustries.util.exception.InvalidFormatException;
import co.edu.unbosque.purpleindustries.util.exception.NegativeValueException;
import co.edu.unbosque.purpleindustries.util.exception.OutOfRangeException;

public class ExceptionChecker {

	// PERSONA
	public static void checkNombre(String nombre) throws EmptyFieldException, InvalidFormatException {

		if (nombre == null || nombre.trim().isEmpty()) {
			throw new EmptyFieldException("nombre");
		}

		if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
			throw new InvalidFormatException("nombre");
		}
	}

	public static void checkFechaNacimiento(String fecha) throws EmptyFieldException, InvalidFormatException {

		if (fecha == null || fecha.trim().isEmpty()) {
			throw new EmptyFieldException("fecha de nacimiento");
		}

		// formato dd/mm/yyyy
		if (!fecha.matches("\\d{2}/\\d{2}/\\d{4}")) {
			throw new InvalidFormatException("fecha de nacimiento");
		}
	}

	public static void checkId(int id) throws NegativeValueException, OutOfRangeException {

		if (id <= 0) {
			throw new NegativeValueException("id");
		}

		if (id > 999999999) {
			throw new OutOfRangeException("id", "1 - 9999999999");
		}
	}

	// PACIENTE
	public static void checkRh(String rh) throws EmptyFieldException, InvalidFormatException {

		if (rh == null || rh.trim().isEmpty()) {
			throw new EmptyFieldException("rh");
		}

		if (!rh.matches("(O|A|B|AB)[+-]")) {
			throw new InvalidFormatException("rh");
		}
	}

	public static void checkDocumento(int documento) throws NegativeValueException, OutOfRangeException {

		if (documento <= 0) {
			throw new NegativeValueException("documento");
		}

		if (documento > 999999999) {
			throw new OutOfRangeException("documento", "1 - 999999999");
		}
	}

	public static void checkAltura(double altura) throws NegativeValueException, OutOfRangeException {

		if (altura <= 0) {
			throw new NegativeValueException("altura");
		}

		if (altura < 0.3 || altura > 2.5) {
			throw new OutOfRangeException("altura", "0.3 - 2.5 m");
		}
	}

	public static void checkPeso(double peso) throws NegativeValueException, OutOfRangeException {

		if (peso <= 0) {
			throw new NegativeValueException("peso");
		}

		if (peso < 1 || peso > 500) {
			throw new OutOfRangeException("peso", "1 - 500 kg");
		}
	}

	public static void checkTriage(int triage) throws OutOfRangeException {
		if (triage < 1 || triage > 5) {
			throw new OutOfRangeException("triage", "1 - 5");
		}
	}

	public static void checkDiagnostico(String diagnostico) throws EmptyFieldException {

		if (diagnostico == null || diagnostico.trim().isEmpty()) {
			throw new EmptyFieldException("diagnóstico");
		}
	}

	// DOCTOR

	public static void checkEspecialidad(String especialidad) throws EmptyFieldException, InvalidFormatException {

		if (especialidad == null || especialidad.trim().isEmpty()) {
			throw new EmptyFieldException("especialidad");
		}

		if (!especialidad.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9 ]+")) {
			throw new InvalidFormatException("La especialidad solo puede contener letras, números y espacios");
		}

		if (especialidad.matches("\\d+")) {
			throw new InvalidFormatException("La especialidad no puede ser solo números");
		}
	}

}