package co.edu.unbosque.controller;

import java.util.InputMismatchException;

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
		boolean salir= false;
		
		 while(!salir) {
				try {
					con.imprimirConSalto("\n 🏥🚑💉💖Bienvenido al sistema de pacientes🏥🚑💉💖");
					con.imprimirConSalto("1. Ingresar un paciente");
					con.imprimirConSalto("2. Menu para doctores");
					con.imprimirConSalto("3. Diagnosticar pacientes");
					con.imprimirConSalto("0. salir");
					con.imprimirConSalto("Elija una opcion");
					
					int opcion = con.leerEntero();
					con.leerLinea();
					
					if (opcion < 0 || opcion > 3) {
						System.out.println(" Opción incorrecta. Ingrese un número entre 0 y 3.");
						continue;
					}
					
					switch (opcion) {
					case 1: 
						boolean paciente = true;
						while (paciente) {
							
							con.imprimirConSalto("Ingresa el nombre del paciente 🧑🏼‍⚕️✨");
							String nombre= con.leerLinea();
							con.imprimirConSalto(nombre);
						
							con.imprimirConSalto("Ingrese el Numero de identidad del paciente 🧑🏼‍⚕️✨");
							int id = con.leerEntero();
							con.imprimirConSalto("");
							
							con.imprimirConSalto("Ingrese el tipo de sangre del paciente 🧑🏼‍⚕️✨ ");
							String sangre= con.leerLinea();
							con.imprimirConSalto("");
							
							con.imprimirConSalto("Ingrese el peso del paciente 🧑🏼‍⚕️✨");
							int peso = con.leerEntero();
							con.imprimirConSalto("");
							
							con.imprimirConSalto("Ingrese la altura del paciente 🧑🏼‍⚕️✨");
							int altura = con.leerEntero();
							con.imprimirConSalto("");
							
							Paciente nuevo = new Paciente(nombre, sangre, altura);
							mf.getPacienteDAO().crear(nuevo);
							
							
						
						}
						break;
					case 2: 
						
						
					}
					
					
					
					
					
					
					
					
					
					

					
				}catch(InputMismatchException e ) {
					con.quemarLinea();
				}
			}
	}
}