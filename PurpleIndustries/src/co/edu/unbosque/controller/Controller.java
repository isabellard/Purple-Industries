package co.edu.unbosque.controller;

import java.util.InputMismatchException;

import co.edu.unbosque.model.Doctor;
import co.edu.unbosque.model.ModelFacade;
import co.edu.unbosque.model.Paciente;
import co.edu.unbosque.view.Console;

public class Controller {

	private Console con;
	private ModelFacade mf;

	public Controller() {
		con = new Console();
		mf = new ModelFacade();
	}

	public void run() {
		boolean salir = false;

		while (!salir) {
			try {
				con.imprimirConSalto("\n 🏥🚑💉💖Bienvenido al sistema de pacientes🏥🚑💉💖");
				con.imprimirConSalto("\nElija una opcion:");
				con.imprimirConSalto("1. Ingresar un paciente");
				con.imprimirConSalto("2. Ingresar un nuevo doctor");
				con.imprimirConSalto("3. Diagnosticar pacientes");
				con.imprimirConSalto("0. salir");

				int opcion = con.leerEntero();
				con.leerLinea();

				if (opcion < 0 || opcion > 3) {
					System.out.println(" Opción incorrecta. Ingrese un número entre 0 y 3.");
					continue;
				}

				switch (opcion) {
				case 1:

					try {

						con.imprimirConSalto("Ingresa el nombre del paciente 🧑🏼‍⚕️✨");
						String nombre = con.leerLinea();
						con.imprimirConSalto(nombre);

						con.imprimirConSalto("Ingrese la fecha de nacimiento del paciente 🧑🏼‍⚕️✨");
						String fechaDeNacimiento = con.leerLinea();
						con.imprimirConSalto("");

						con.imprimirConSalto("Ingrese el Numero de identidad del paciente 🧑🏼‍⚕️✨");
						int documento = con.leerEntero();
						con.imprimirConSalto("");
						con.quemarLinea();

						con.imprimirConSalto("Ingrese el tipo de sangre del paciente 🧑🏼‍⚕️✨ ");
						String rh = con.leerLinea();
						con.imprimirConSalto("");

						con.imprimirConSalto("Ingrese el peso del paciente 🧑🏼‍⚕️✨");
						double peso = con.leerEntero();
						con.imprimirConSalto("");
						con.quemarLinea();

						con.imprimirConSalto("Ingrese la altura del paciente 🧑🏼‍⚕️✨");
						double altura = con.leerEntero();
						con.imprimirConSalto("");
						con.quemarLinea();

						Paciente nuevo = new Paciente(nombre, fechaDeNacimiento, documento, altura, peso, rh, 1, "");
						mf.getPacienteDAO().crear(nuevo);
						
						

					} catch (Exception e) {
						// TODO: handle exception
					}
					break;

				case 2:

					try {

						con.imprimirConSalto("Ingresa el nombre del doctor 🧑🏼‍⚕️✨");
						String nombre = con.leerLinea();
						con.imprimirConSalto(nombre);

						con.imprimirConSalto("Ingrese la fecha de nacimiento del doctor 🧑🏼‍⚕️✨");
						String fechaDeNacimiento = con.leerLinea();
						con.imprimirConSalto("");

						con.imprimirConSalto("Ingrese el numero de documento del doctor 🧑🏼‍⚕️✨");
						int documento = con.leerEntero();
						con.imprimirConSalto("");
						con.quemarLinea();

						con.imprimirConSalto("Ingrese la especialidad del doctor 🧑🏼‍⚕️✨");
						String especialidad = con.leerLinea();
						con.imprimirConSalto("");

						Doctor nuevo = new Doctor(nombre, fechaDeNacimiento, documento, especialidad);
						mf.getDoctorDAO().crear(nuevo);

					} catch (Exception e) {
						// TODO: handle exception
					}

					break;

				case 3:
					try {
						con.imprimirConSalto("Seleccionar el paciente a diagnosticar 😷🤒🧑🏼");
						con.imprimirConSalto("");
						con.imprimirConSalto(mf.getPacienteDAO().mostrarTodo());
						con.imprimirConSalto("ingrese el numero de identificacion del paciente a seleccionar: ");
						int documento = con.leerEntero();
						con.quemarLinea();
						
						con.imprimirConSalto("El pasiente seleccionado es:");
						con.imprimirConSalto(mf.getPacienteDAO().getPacienteById(documento).toString());
						con.imprimirConSalto("\nIngrese el numero del triage del paciente 😷🧑🏼‍⚕️✨");
						con.imprimirConSalto("1. Resucitacion 🩻");
						con.imprimirConSalto("2. Emergencia 🏥 ");
						con.imprimirConSalto("3. Urgencia 🚑");
						con.imprimirConSalto("4. Urgencia menor 🤒 ");
						con.imprimirConSalto("5. Sin Urgencia 🩺👨🏼‍⚕️ ");
						int triage= con.leerEntero();
						con.quemarLinea();
						
						con.imprimirConSalto("ingrese el diagnotico final del paciente: ");
						String diagnostico= con.leerLinea();
						
						mf.getPacienteDAO().modificarDatosMedicos(documento, triage, diagnostico);
						con.imprimirConSalto("Los datos del paciente ahora son: ");
						con.imprimirConSalto(mf.getPacienteDAO().getPacienteById(documento).toString());
						
					} catch (Exception e) {
						// TODO: handle exception
					}
						break;
				case 0:	
					salir=true;
					con.imprimirConSalto("Saliendo del programa, hasta luego!!! 😷");
					
				}
			} catch (InputMismatchException e) {
				con.quemarLinea();
			}

		}
	}
}
