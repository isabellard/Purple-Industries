package co.edu.unbosque.purpleindustries.controller;

import co.edu.unbosque.purpleindustries.model.Doctor;
import co.edu.unbosque.purpleindustries.model.ModelFacade;
import co.edu.unbosque.purpleindustries.model.Paciente;
import co.edu.unbosque.purpleindustries.model.Persona;
import co.edu.unbosque.purpleindustries.util.exception.EmailException;
import co.edu.unbosque.purpleindustries.util.exception.EmptyFieldException;
import co.edu.unbosque.purpleindustries.util.exception.IdAlreadyExists;
import co.edu.unbosque.purpleindustries.util.exception.InvalidFormatException;
import co.edu.unbosque.purpleindustries.util.exception.NegativeValueException;
import co.edu.unbosque.purpleindustries.util.exception.OutOfRangeException;
import co.edu.unbosque.purpleindustries.view.Console;

import java.time.YearMonth;
import java.util.InputMismatchException;
import java.util.List;
import java.time.LocalDate;

public class Controller {

	private Console con;
	private static ModelFacade mf;
	private GeneradorReportePacientesPDF pdf;

	public Controller() {
		con = new Console();
		mf = new ModelFacade();
		pdf = new GeneradorReportePacientesPDF();
	}

	public void run() {
		boolean salir = false;

		while (!salir) {
			try {
				con.imprimirConSalto("\n 🏥🚑💉💖Bienvenido al sistema de pacientes🏥🚑💉💖");
				con.imprimirConSalto("\nElija una opcion:");
				con.imprimirConSalto("1. Ingresar un paciente");
				con.imprimirConSalto("2. Ingresar un nuevo doctor");
				con.imprimirConSalto("3. Actualizar un paciente");
				con.imprimirConSalto("4. Actualizar un doctor");
				con.imprimirConSalto("5. Eliminar un paciente");
				con.imprimirConSalto("6. Eliminar un doctor");
				con.imprimirConSalto("7. Ver todos los pacientes");
				con.imprimirConSalto("8. Ver todos los doctores");
				con.imprimirConSalto("9. Diagnosticar pacientes");
				con.imprimirConSalto("10. Ingresar tiempo de atencion del paciente");
				con.imprimirConSalto("10. Descargar reporte");
				con.imprimirConSalto("0. salir");

				int opcion;

				try {
					opcion = con.leerEntero();
					con.leerLinea();
				} catch (InputMismatchException e) {
					con.imprimirConSalto(" Opción inválida. Debe ingresar un número.");
					con.quemarLinea();
					continue;
				}

				if (opcion < 0 || opcion > 11) {
					System.out.println(" Opción incorrecta. Ingrese un número entre 0 y 9.");
					continue;
				}

				switch (opcion) {
				case 1:
					try {
						String nombre = "";
						String nombreModificado = "";
						while (true) {
							con.imprimirConSalto("Ingresa el nombre del paciente 🧑🏼‍⚕️✨");
							nombre = con.leerLinea();

							try {
								ExceptionChecker.checkNombre(nombre);
								nombreModificado = mf.modificarNombre(nombre);
								con.imprimirConSalto("Nombre registrado: " + nombreModificado);
								break;
							} catch (EmptyFieldException | InvalidFormatException e) {
								con.imprimirConSalto("Error: " + e.getMessage());
							}
						}

						String fechaDeNacimiento = "";
						int edad = 0;
						while (true) {
							con.imprimirConSalto("Ingrese la fecha de nacimiento (dd/mm/aaaa) 🧑🏼‍⚕️✨");
							fechaDeNacimiento = con.leerLinea();

							try {
								ExceptionChecker.checkFechaNacimiento(fechaDeNacimiento);
								edad = Persona.calcularEdadConFecha(fechaDeNacimiento);

								if (edad < 12) {
									con.imprimirConSalto("Error: la edad del paciente no puede ser menor a 12 años");
									continue;
								}

								con.imprimirConSalto("Fecha registrada: " + fechaDeNacimiento);
								break;
							} catch (EmptyFieldException | InvalidFormatException e) {
								con.imprimirConSalto("Error: " + e.getMessage());
							}
						}

						String id = "";
						while (true) {
							con.imprimirConSalto("Ingrese el número de identidad del paciente 🧑🏼‍⚕️✨");
							try {
								int documento = con.leerEntero();
								con.quemarLinea();
								ExceptionChecker.checkDocumento(documento);
								id = mf.rellenarDocumento(documento);
								ExceptionChecker.checkDocumentoString(mf.documentoExiste(id));
								con.imprimirConSalto("Documento registrado: " + documento);
								break;
							} catch (InputMismatchException e) {
								con.imprimirConSalto("Error: debe ingresar solo números.");
								con.quemarLinea();
							} catch (NegativeValueException | OutOfRangeException | IdAlreadyExists e) {
								con.imprimirConSalto("Error: " + e.getMessage());
							}
						}

						String email = "";

						while (true) {
							con.imprimirConSalto("Ingrese el email del paciente 🧑🏼‍⚕️✨");
							email = con.leerLinea();
							try {
								ExceptionChecker.checkEmail(email);
								con.imprimirConSalto("Email registrado: " + email);
								break;
							} catch (EmailException e) {
								con.imprimirConSalto("Error: " + e.getMessage());
							}
						}

						String rh = parseRH(preguntarEstado(List.of("O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"),
								"Ingrese el tipo de sangre del paciente 🧑🏼‍⚕️✨"));
						con.imprimirConSalto("RH registrado: " + rh);

						String peso = "";
						double pesoD = 0;
						while (true) {
							con.imprimirConSalto("Ingrese el peso del paciente (kg) 🧑🏼‍⚕️✨");
							try {
								pesoD = con.leerDouble();
								ExceptionChecker.checkPeso(pesoD);
								peso = unidadesPeso(pesoD);
								con.imprimirConSalto("Peso registrado: " + pesoD + " kg");
								break;
							} catch (InputMismatchException e) {
								con.imprimirConSalto("Error: debe ingresar un número válido.");
								con.quemarLinea();
							} catch (NegativeValueException | OutOfRangeException e) {
								con.imprimirConSalto("Error: " + e.getMessage());
							}
						}

						double altura = 0;
						while (true) {
							con.imprimirConSalto("Ingrese la altura del paciente (m) 🧑🏼‍⚕️✨");
							try {
								altura = con.leerDouble();
								con.quemarLinea();
								ExceptionChecker.checkAltura(altura);
								con.imprimirConSalto("Altura registrada: " + altura + " m");
								break;
							} catch (InputMismatchException e) {
								con.imprimirConSalto("Error: debe ingresar un número válido.");
								con.quemarLinea();
							} catch (NegativeValueException | OutOfRangeException e) {
								con.imprimirConSalto("Error: " + e.getMessage());
							}
						}

						Paciente nuevo = new Paciente(nombreModificado, fechaDeNacimiento, id, email, altura, peso, rh,
								1, "Sin registrar", LocalDate.now(), "Sin registrar");
						mf.getPacienteDAO().crear(nuevo);

					} catch (Exception e) {

						con.imprimirConSalto("Error: " + e.getMessage());

					}
					break;

				case 2:

					try {
						String nombre = "";
						while (true) {
							con.imprimirConSalto("Ingresa el nombre del doctor 🧑🏼‍⚕️✨");
							nombre = con.leerLinea();

							try {
								ExceptionChecker.checkNombre(nombre);
								con.imprimirConSalto("Nombre registrado: " + nombre);
								break;
							} catch (EmptyFieldException | InvalidFormatException e) {
								con.imprimirConSalto("Error: " + e.getMessage());
							}
						}
						String fechaDeNacimiento = "";
						int edad = 0;
						while (true) {
							con.imprimirConSalto("Ingrese la fecha de nacimiento del doctor (dd/mm/aaaa) 🧑🏼‍⚕️✨");
							fechaDeNacimiento = con.leerLinea();

							try {
								ExceptionChecker.checkFechaNacimiento(fechaDeNacimiento);
								edad = Persona.calcularEdadConFecha(fechaDeNacimiento);

								if (edad < 21) {
									con.imprimirConSalto("Error: la edad del paciente no puede ser menor a 21 años");
									continue;
								}
								con.imprimirConSalto("Fecha registrada: " + fechaDeNacimiento);
								break;
							} catch (EmptyFieldException | InvalidFormatException e) {
								con.imprimirConSalto("Error: " + e.getMessage());
							}
						}

						String id = "";
						while (true) {
							con.imprimirConSalto("Ingrese el número de documento del doctor 🧑🏼‍⚕️✨");
							try {
								int documento = con.leerEntero();
								con.quemarLinea();
								ExceptionChecker.checkDocumento(documento);
								id = mf.rellenarDocumento(documento);
								ExceptionChecker.checkDocumentoString(mf.documentoExiste(id));
								con.imprimirConSalto("Documento registrado: " + id);
								break;
							} catch (InputMismatchException e) {
								con.imprimirConSalto("Error: debe ingresar solo números.");
								con.quemarLinea();
							} catch (NegativeValueException | OutOfRangeException | IdAlreadyExists e) {
								con.imprimirConSalto("Error: " + e.getMessage());
							}
						}

						String email = "";

						while (true) {
							con.imprimirConSalto("Ingrese el email del paciente 🧑🏼‍⚕️✨");
							email = con.leerLinea();
							try {
								ExceptionChecker.checkEmail(email);
								con.imprimirConSalto("Email registrado: " + email);
								break;
							} catch (EmailException e) {
								con.imprimirConSalto("Error: " + e.getMessage());
							}
						}

						String especialidad = "";
						while (true) {
							con.imprimirConSalto("Ingrese la especialidad del doctor 🧑🏼‍⚕️✨");
							especialidad = con.leerLinea();

							try {
								ExceptionChecker.checkEspecialidad(especialidad);
								con.imprimirConSalto("Especialidad registrada: " + especialidad);
								break;
							} catch (EmptyFieldException | InvalidFormatException e) {
								con.imprimirConSalto("Error: " + e.getMessage());
							}
						}

						Doctor nuevo = new Doctor(nombre, fechaDeNacimiento, id, email, especialidad);
						mf.getDoctorDAO().crear(nuevo);

					} catch (Exception e) {

						con.imprimirConSalto("Error: " + e.getMessage());

					}
					break;

				case 3:
					String id = "";
					while (true) {
						con.imprimirConSalto("Seleccionar el paciente para actualizar 😷🤒🧑🏼");
						con.imprimirConSalto(mf.getPacienteDAO().mostrarTodo());
						con.imprimirConSalto("Ingrese el número de identificación del paciente:");

						try {
							int documentoPaciente1 = con.leerEntero();
							con.quemarLinea();
							ExceptionChecker.checkDocumento(documentoPaciente1);
							id = mf.rellenarDocumento(documentoPaciente1);
							break;
						} catch (InputMismatchException e) {
							con.imprimirConSalto("Error: debe ingresar un número válido.");
							con.quemarLinea();
						} catch (NegativeValueException | OutOfRangeException e) {
							con.imprimirConSalto("Error: " + e.getMessage());
						}
					}

					if (mf.documentoExiste(id) == false) {
						System.out.println("El documento no existe. Vuelva a intentarlo");
						break;
					}

					String nombre = "";
					while (true) {
						con.imprimirConSalto("Ingresa el nombre del paciente 🧑🏼‍⚕️✨");
						nombre = con.leerLinea();

						try {
							ExceptionChecker.checkNombre(nombre);
							con.imprimirConSalto("Nombre registrado: " + nombre);
							break;
						} catch (EmptyFieldException | InvalidFormatException e) {
							con.imprimirConSalto("Error: " + e.getMessage());
						}
					}

					String fechaDeNacimiento = "";
					int edad2 = 0;
					while (true) {
						con.imprimirConSalto("Ingrese la fecha de nacimiento (dd/mm/aaaa) 🧑🏼‍⚕️✨");
						fechaDeNacimiento = con.leerLinea();

						try {
							ExceptionChecker.checkFechaNacimiento(fechaDeNacimiento);
							edad2 = Persona.calcularEdadConFecha(fechaDeNacimiento);

							if (edad2 < 12) {
								con.imprimirConSalto("Error: la edad del paciente no puede ser menor a 12 años");
								continue;
							}

							con.imprimirConSalto("Fecha registrada: " + fechaDeNacimiento);
							break;
						} catch (EmptyFieldException | InvalidFormatException e) {
							con.imprimirConSalto("Error: " + e.getMessage());
						}
					}

					String email = "";

					while (true) {
						con.imprimirConSalto("Ingrese el email del paciente 🧑🏼‍⚕️✨");
						email = con.leerLinea();
						try {
							ExceptionChecker.checkEmail(email);
							con.imprimirConSalto("Email registrado: " + email);
							break;
						} catch (EmailException e) {
							con.imprimirConSalto("Error: " + e.getMessage());
						}
					}

					String rh = parseRH(preguntarEstado(List.of("O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"),
							"Ingrese el tipo de sangre del paciente 🧑🏼‍⚕️✨"));
					con.imprimirConSalto("RH registrado: " + rh);

					String peso = "";
					double pesoD = 0;
					while (true) {
						con.imprimirConSalto("Ingrese el peso del paciente (kg) 🧑🏼‍⚕️✨");
						try {
							pesoD = con.leerDouble();
							con.quemarLinea();
							ExceptionChecker.checkPeso(pesoD);
							peso = unidadesPeso(pesoD);
							con.imprimirConSalto("Peso registrado: " + peso + " kg");
							break;
						} catch (InputMismatchException e) {
							con.imprimirConSalto("Error: debe ingresar un número válido.");
							con.quemarLinea();
						} catch (NegativeValueException | OutOfRangeException e) {
							con.imprimirConSalto("Error: " + e.getMessage());
						}
					}

					double altura = 0;
					while (true) {
						con.imprimirConSalto("Ingrese la altura del paciente (m) 🧑🏼‍⚕️✨");
						try {
							altura = con.leerDouble();
							con.quemarLinea();

							ExceptionChecker.checkAltura(altura);
							con.imprimirConSalto("Altura registrada: " + altura + " m");
							break;
						} catch (InputMismatchException e) {
							con.imprimirConSalto("Error: debe ingresar un número válido.");
							con.quemarLinea();
						} catch (NegativeValueException | OutOfRangeException e) {
							con.imprimirConSalto("Error: " + e.getMessage());
						}
					}
					Paciente actual = mf.getPacienteDAO().getPacienteById(id);
					if (actual == null) {
						con.imprimirConSalto("No se encontró un paciente con ese documento.");
						break;
					}

					LocalDate fechaIngresoOriginal = actual.getFechaIngreso();

					Paciente nuevo = new Paciente(nombre, fechaDeNacimiento, id, email, altura, (peso + ""), rh, 1,
							"Sin registrar", fechaIngresoOriginal, "Sin registrar");
					mf.getPacienteDAO().actualizar(id, nuevo);
					con.imprimirConSalto("Paciente " + nombre + "actualizado correctamente");
					break;

				case 4:
					String idDoc = "";
					while (true) {
						con.imprimirConSalto("Seleccionar el doctor para actualizar 😷🤒🧑🏼");
						con.imprimirConSalto(mf.getDoctorDAO().mostrarTodo());
						con.imprimirConSalto("Ingrese el número de identificación del doctor:");

						try {
							int documentoPaciente1 = con.leerEntero();
							con.quemarLinea();
							ExceptionChecker.checkDocumento(documentoPaciente1);
							idDoc = mf.rellenarDocumento(documentoPaciente1);
							break;
						} catch (InputMismatchException e) {
							con.imprimirConSalto("Error: debe ingresar un número válido.");
							con.quemarLinea();
						} catch (NegativeValueException | OutOfRangeException e) {
							con.imprimirConSalto("Error: " + e.getMessage());
						}
					}

					if (mf.documentoExiste(idDoc) == false) {
						System.out.println("El documento no existe. Vuelva a intentarlo");
						break;
					}
					String nombre1 = "";
					while (true) {
						con.imprimirConSalto("Ingresa el nombre del doctor 🧑🏼‍⚕️✨");
						nombre1 = con.leerLinea();

						try {
							ExceptionChecker.checkNombre(nombre1);
							con.imprimirConSalto("Nombre registrado: " + nombre1);
							break;
						} catch (EmptyFieldException | InvalidFormatException e) {
							con.imprimirConSalto("Error: " + e.getMessage());
						}
					}

					String fechaDeNacimiento2 = "";
					edad2 = 0;
					while (true) {
						con.imprimirConSalto("Ingrese la fecha de nacimiento del doctor (dd/mm/aaaa) 🧑🏼‍⚕️✨");
						fechaDeNacimiento2 = con.leerLinea();

						try {
							ExceptionChecker.checkFechaNacimiento(fechaDeNacimiento2);

							edad2 = Persona.calcularEdadConFecha(fechaDeNacimiento2);

							if (edad2 < 12) {
								con.imprimirConSalto("Error: la edad del doctor no puede ser menor a 12 años");
								continue;
							}

							con.imprimirConSalto("Fecha registrada: " + fechaDeNacimiento2);
							break;
						} catch (EmptyFieldException | InvalidFormatException e) {
							con.imprimirConSalto("Error: " + e.getMessage());
						}
					}

					String email2 = "";

					while (true) {
						con.imprimirConSalto("Ingrese el email del paciente 🧑🏼‍⚕️✨");
						email = con.leerLinea();
						try {
							ExceptionChecker.checkEmail(email);
							con.imprimirConSalto("Email registrado: " + email);
							break;
						} catch (EmailException e) {
							con.imprimirConSalto("Error: " + e.getMessage());
						}
					}

					String especialidad = "";
					while (true) {
						con.imprimirConSalto("Ingrese la especialidad del doctor 🧑🏼‍⚕️✨");
						especialidad = con.leerLinea();

						try {
							ExceptionChecker.checkEspecialidad(especialidad);
							con.imprimirConSalto("Especialidad registrada: " + especialidad);
							break;
						} catch (EmptyFieldException | InvalidFormatException e) {
							con.imprimirConSalto("Error: " + e.getMessage());
						}
					}

					Doctor nuevo1 = new Doctor(nombre1, fechaDeNacimiento2, idDoc, email, especialidad);
					mf.getDoctorDAO().actualizar(idDoc, nuevo1);

				case 5:
					String documento1 = "";
					while (true) {
						con.imprimirConSalto("Seleccionar el paciente para eliminar 😷🤒🧑🏼");
						con.imprimirConSalto(mf.getPacienteDAO().mostrarTodo());
						con.imprimirConSalto("Ingrese el número de identificación del paciente:");

						try {
							int doc = con.leerEntero();
							con.quemarLinea();
							ExceptionChecker.checkDocumento(doc);
							documento1 = mf.rellenarDocumento(doc);
							break;
						} catch (InputMismatchException e) {
							con.imprimirConSalto("Error: debe ingresar un número válido.");
							con.quemarLinea();
						} catch (NegativeValueException | OutOfRangeException e) {
							con.imprimirConSalto("Error: " + e.getMessage());
						}
					}
					if (mf.documentoExiste(documento1) == false) {
						System.out.println("El documento no existe. Vuelva a intentarlo");
						break;
					}
					mf.getPacienteDAO().eliminar(mf.getPacienteDAO().getPacienteById(documento1));
					con.imprimirConSalto("Paciente eliminado");
					break;
				case 6:
					String documento2 = "";
					while (true) {
						con.imprimirConSalto("Seleccionar el doctor para eliminar 😷🤒🧑🏼");
						con.imprimirConSalto(mf.getDoctorDAO().mostrarTodo());
						con.imprimirConSalto("Ingrese el número de identificación del doctor:");

						try {
							int id1 = con.leerEntero();
							con.quemarLinea();
							ExceptionChecker.checkDocumento(id1);
							documento2 = mf.rellenarDocumento(id1);
							break;
						} catch (InputMismatchException e) {
							con.imprimirConSalto("Error: debe ingresar un número válido.");
							con.quemarLinea();
						} catch (NegativeValueException | OutOfRangeException e) {
							con.imprimirConSalto("Error: " + e.getMessage());
						}
					}
					if (mf.documentoExiste(documento2) == false) {
						System.out.println("El documento no existe. Vuelva a intentarlo");
						break;
					}
					mf.getDoctorDAO().eliminar(mf.getDoctorDAO().getDoctorById(documento2));
					con.imprimirConSalto("Doctor eliminado");
					break;
				case 7:
					con.imprimirConSalto("Mostrando pacientes....");
					con.imprimirConSalto(mf.getPacienteDAO().mostrarTodo());
					break;

				case 8:
					con.imprimirConSalto("Mostrando doctores....");
					con.imprimirConSalto(mf.getDoctorDAO().mostrarTodo());
					break;

				case 9:
					try {
						String documentoPaciente = "";
						while (true) {
							con.imprimirConSalto("Seleccionar el paciente a diagnosticar 😷🤒🧑🏼");
							con.imprimirConSalto(mf.getPacienteDAO().mostrarTodo());
							con.imprimirConSalto("Ingrese el número de identificación del paciente:");

							try {
								int pac = con.leerEntero();
								ExceptionChecker.checkDocumento(pac);
								documentoPaciente = mf.rellenarDocumento(pac);
								con.imprimirConSalto(mf.getPacienteDAO().getPacienteById(documentoPaciente).toString());
								con.quemarLinea();
								break;
							} catch (InputMismatchException e) {
								con.imprimirConSalto("Error: debe ingresar un número válido.");
								con.quemarLinea();
							} catch (NegativeValueException | OutOfRangeException e) {
								con.imprimirConSalto("Error: " + e.getMessage());
							}
						}

						if (mf.documentoExiste(documentoPaciente) == false) {
							System.out.println("El documento no existe. Vuelva a intentarlo");
							break;
						}

						int triage = 0;
						while (true) {
							con.imprimirConSalto("Ingrese el número del triage 😷🧑🏼‍⚕️✨");
							con.imprimirConSalto("1. Resucitación 🩻");
							con.imprimirConSalto("2. Emergencia 🏥");
							con.imprimirConSalto("3. Urgencia 🚑");
							con.imprimirConSalto("4. Urgencia menor 🤒");
							con.imprimirConSalto("5. Sin urgencia 🩺");

							try {
								triage = con.leerEntero();
								con.quemarLinea();
								ExceptionChecker.checkTriage(triage);
								con.imprimirConSalto("Triage registrado: " + triage);
								break;

							} catch (OutOfRangeException e) {
								con.imprimirConSalto("Error: " + e.getMessage());

							} catch (InputMismatchException e) {
								con.imprimirConSalto("Error: debe ingresar un número.");
								con.quemarLinea();
							}
						}

						String diagnostico = "";
						while (true) {
							con.imprimirConSalto("Ingrese el diagnóstico final del paciente:");
							diagnostico = con.leerLinea();

							try {
								ExceptionChecker.checkDiagnostico(diagnostico);
								con.imprimirConSalto("Diagnóstico registrado: " + diagnostico);
								break;
							} catch (EmptyFieldException e) {
								con.imprimirConSalto("Error: " + e.getMessage());
							}
						}
						mf.getPacienteDAO().modificarDatosMedicos(documentoPaciente, triage, diagnostico);
						con.imprimirConSalto("Los datos del paciente ahora son: ");
						con.imprimirConSalto(mf.getPacienteDAO().getPacienteById(documentoPaciente).toString());
						con.imprimirConSalto(" Diagnóstico registrado correctamente");
						break;

					} catch (Exception e) {
						// TODO: handle exception
					}
					break;

				case 10: {
					try {
						String documentoPaciente = "";
						while (true) {
							con.imprimirConSalto("Seleccionar el paciente tratado 😷🤒🧑🏼");
							con.imprimirConSalto(mf.getPacienteDAO().mostrarTodo());
							con.imprimirConSalto("Ingrese el número de identificación del paciente:");

							try {
								int pac = con.leerEntero();
								ExceptionChecker.checkDocumento(pac);
								documentoPaciente = mf.rellenarDocumento(pac);
								con.imprimirConSalto(mf.getPacienteDAO().getPacienteById(documentoPaciente).toString());
								con.quemarLinea();
								break;
							} catch (InputMismatchException e) {
								con.imprimirConSalto("Error: debe ingresar un número válido.");
								con.quemarLinea();
							} catch (NegativeValueException | OutOfRangeException e) {
								con.imprimirConSalto("Error: " + e.getMessage());
							}
						}

						if (mf.documentoExiste(documentoPaciente) == false) {
							System.out.println("El documento no existe. Vuelva a intentarlo");
							break;
						}

						String tiempo = "";
						while (true) {
							con.imprimirConSalto("Ingrese el tiempo de atencion");
							con.imprimirConSalto("1. Menos de 1 min 🩻");
							con.imprimirConSalto("2. Entre 2 a 5 min  🏥");
							con.imprimirConSalto("3. Entre 5 a 10 min 🚑");
							con.imprimirConSalto("4. Entre 10 a 15 min 🤒");
							con.imprimirConSalto("5. Mayor a 15 min 🩺");

							try {
								int time = con.leerEntero();
								con.quemarLinea();
								con.imprimirConSalto("Tiempo registrado: " + tiempo);
								break;
							} catch (InputMismatchException e) {
								con.imprimirConSalto("Error: debe ingresar un número.");
								con.quemarLinea();
							}
						}
					} catch (Exception e) {
						// TODO: handle exception
					}

					break;
				}
				case 11: {
					exportarReporteMensualActual();
					break;
				}
				case 0:
					salir = true;
					con.imprimirConSalto("Saliendo del programa, hasta luego!!! 😷");

				}
			} catch (InputMismatchException e) {
				con.quemarLinea();
			}

		}
	}

	public String unidadesPeso(double peso) {
		double gr;
		double libra;
		double oz;
		double mg;
		gr = peso * 1000;
		libra = peso * 2.20462;
		oz = peso * 35.274;
		mg = peso * 1e+6;

		return "Peso en gramos: " + gr + " g\nPeso en libras: " + libra + " lb\nPeso en onzas: " + oz
				+ " oz\nPeso en miligramos: " + mg + " mg\nPeso en kilos: " + peso + " kg";
	}

	public void exportarReporteMensualActual() {
		YearMonth periodoActual = YearMonth.now();
		pdf.generarReporteMensualPacientes(mf.getPacienteDAO().getListaPacientes(), periodoActual);
	}
	

	private String preguntarEstado(List<String> estados, String texto) {
		String valor = null;
		String temp;
		while (valor == null) {
			con.imprimirConSalto(texto);
			estados.forEach(estado -> con.imprimirConSalto(estado));
			temp = con.leerLinea();
			if (estados.contains(temp)) {
				valor = temp;
			} else {
				con.imprimirConSalto("El valor digitado es invalido.");
			}
		}
		return valor;
	}

	private String parseRH(String rh) {
		if (rh.equals("AB-")) {
			rh = "BA";
		}
		rh = rh.replace("-", "+");
		return rh;
	}

}
