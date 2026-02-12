package co.edu.unbosque.purpleindustries.controller;

import co.edu.unbosque.purpleindustries.model.Doctor;
import co.edu.unbosque.purpleindustries.model.ModelFacade;
import co.edu.unbosque.purpleindustries.model.Paciente;
import co.edu.unbosque.purpleindustries.util.exception.EmptyFieldException;
import co.edu.unbosque.purpleindustries.util.exception.InvalidFormatException;
import co.edu.unbosque.purpleindustries.util.exception.NegativeValueException;
import co.edu.unbosque.purpleindustries.util.exception.OutOfRangeException;
import co.edu.unbosque.purpleindustries.view.Console;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;

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
                con.imprimirConSalto("3. Actualizar un paciente");
                con.imprimirConSalto("4. Actualizar un doctor");
                con.imprimirConSalto("5. Eliminar un paciente");
                con.imprimirConSalto("6. Eliminar un doctor");
                con.imprimirConSalto("7. Ver todos los pacientes");
                con.imprimirConSalto("8. Ver todos los doctores");
                con.imprimirConSalto("9. Diagnosticar pacientes");
                con.imprimirConSalto("10. Activar bot auto add (solo se detiene cuando se apaga el programa)");
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

                if (opcion < 0 || opcion > 10) {
                    System.out.println(" Opción incorrecta. Ingrese un número entre 0 y 9.");
                    continue;
                }

                switch (opcion) {
                    case 1:
                        try {
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
                            while (true) {
                                con.imprimirConSalto("Ingrese la fecha de nacimiento (dd/mm/aaaa) 🧑🏼‍⚕️✨");
                                fechaDeNacimiento = con.leerLinea();

                                try {
                                    ExceptionChecker.checkFechaNacimiento(fechaDeNacimiento);
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

                            String rh = "";
                            while (true) {
                                con.imprimirConSalto("Ingrese el tipo de sangre del paciente 🧑🏼‍⚕️✨");
                                rh = con.leerLinea();

                                try {
                                    ExceptionChecker.checkRh(rh);
                                    con.imprimirConSalto("RH registrado: " + rh);
                                    break;
                                } catch (EmptyFieldException | InvalidFormatException e) {
                                    con.imprimirConSalto("Error: " + e.getMessage());
                                }
                            }

                            String peso = "";
                            double pesoD = 0;
                            while (true) {
                                con.imprimirConSalto("Ingrese el peso del paciente (kg) 🧑🏼‍⚕️✨");
                                try {
                                    pesoD = con.leerDouble();
                                    ExceptionChecker.checkPeso(pesoD);
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
                                    altura = con.leerEntero();
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

                            Paciente nuevo = new Paciente(nombre, fechaDeNacimiento, id, altura, peso, rh, 1,
                                    "Sin registrar");
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
                            while (true) {
                                con.imprimirConSalto("Ingrese la fecha de nacimiento del doctor (dd/mm/aaaa) 🧑🏼‍⚕️✨");
                                fechaDeNacimiento = con.leerLinea();

                                try {
                                    ExceptionChecker.checkFechaNacimiento(fechaDeNacimiento);
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

                            Doctor nuevo = new Doctor(nombre, fechaDeNacimiento, id, especialidad);
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

					if (mf.documentoExiste(id)==false) {
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
                        while (true) {
                            con.imprimirConSalto("Ingrese la fecha de nacimiento (dd/mm/aaaa) 🧑🏼‍⚕️✨");
                            fechaDeNacimiento = con.leerLinea();

                            try {
                                ExceptionChecker.checkFechaNacimiento(fechaDeNacimiento);
                                con.imprimirConSalto("Fecha registrada: " + fechaDeNacimiento);
                                break;
                            } catch (EmptyFieldException | InvalidFormatException e) {
                                con.imprimirConSalto("Error: " + e.getMessage());
                            }
                        }

                        String rh = "";
                        while (true) {
                            con.imprimirConSalto("Ingrese el tipo de sangre del paciente 🧑🏼‍⚕️✨");
                            rh = con.leerLinea();

                            try {
                                ExceptionChecker.checkRh(rh);
                                con.imprimirConSalto("RH registrado: " + rh);
                                break;
                            } catch (EmptyFieldException | InvalidFormatException e) {
                                con.imprimirConSalto("Error: " + e.getMessage());
                            }
                        }

                        double peso = 0;
                        while (true) {
                            con.imprimirConSalto("Ingrese el peso del paciente (kg) 🧑🏼‍⚕️✨");
                            try {
                                peso = con.leerEntero();
                                con.quemarLinea();
                                ExceptionChecker.checkPeso(peso);
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
                                altura = con.leerEntero();
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

                        Paciente nuevo = new Paciente(nombre, fechaDeNacimiento, id, altura, (peso+""), rh, 1,"Sin registrar");
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
					
					if (mf.documentoExiste(idDoc)==false) {
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
                        while (true) {
                            con.imprimirConSalto("Ingrese la fecha de nacimiento del doctor (dd/mm/aaaa) 🧑🏼‍⚕️✨");
                            fechaDeNacimiento2 = con.leerLinea();

                            try {
                                ExceptionChecker.checkFechaNacimiento(fechaDeNacimiento2);
                                con.imprimirConSalto("Fecha registrada: " + fechaDeNacimiento2);
                                break;
                            } catch (EmptyFieldException | InvalidFormatException e) {
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

                        Doctor nuevo1 = new Doctor(nombre1, fechaDeNacimiento2, idDoc, especialidad);
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
					if (mf.documentoExiste(documento1)==false) {
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
					if (mf.documentoExiste(documento2)==false) {
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
						
						if (mf.documentoExiste(documentoPaciente)==false) {
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
                    case 10:
                        int bandera = 0;
                        while (true) {
                            mf.getPacienteDAO().crear(crearPacienteAleatorioSeguro());
                            bandera++;
                            if (bandera == 5000) {
                                con.imprimirConSalto("se escribireron " + bandera + " filas de pacientes");
                                bandera = 0;
                            }
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

    public static Paciente crearPacienteAleatorioSeguro() {

        Random random = new Random();

        List<String> nombres = List.of(
                "Juan Perez",
                "Maria Gomez",
                "Carlos Rodriguez",
                "Ana Martinez",
                "Luis Fernandez",
                "Laura Hernandez",
                "Pedro Sanchez",
                "Sofia Ramirez",
                "Andres Torres",
                "Valentina Rojas",
                "Diego Morales",
                "Camila Castro",
                "Jorge Vargas",
                "Paula Ortiz",
                "Sebastian Medina",
                "Natalia Cruz",
                "Felipe Gutierrez",
                "Daniela Mendoza",
                "Ricardo Silva",
                "Juliana Pineda",
                "Oscar Jimenez",
                "Mariana Salazar",
                "Fernando Cabrera",
                "Lucia Aguirre",
                "Manuel Lozano",
                "Carolina Peña",
                "Hector Bravo",
                "Angela Fuentes",
                "Alberto Acosta",
                "Veronica Duarte",
                "Eduardo Rios",
                "Patricia León",
                "Roberto Cárdenas",
                "Monica Barrera",
                "Guillermo Ochoa",
                "Tatiana Beltrán",
                "Ivan Montoya",
                "Paola Quintero",
                "Miguel Ángel Reyes",
                "Diana Moreno",
                "Rafael Espinosa",
                "Lorena Campos",
                "Alejandro Villamizar",
                "Claudia Parra",
                "Francisco Amaya",
                "Karla Suárez",
                "Hugo Calderón",
                "Andrea Figueroa",
                "Esteban Prieto",
                "Viviana Mejía",
                "Santiago Galindo",
                "Marcela Buitrago",
                "Nicolas Arévalo",
                "Yolanda Giraldo",
                "Cristian Velasco",
                "Silvia Correa",
                "Mauricio Bonilla",
                "Liliana Páez",
                "Jonathan Álvarez",
                "Gloria Santamaría",
                "Wilmer Cifuentes",
                "Rosa Delgadillo",
                "Edgar Zambrano",
                "Beatriz Solano",
                "Henry Mosquera",
                "Adriana Bolaños",
                "Javier Caicedo",
                "Sandra Palacios",
                "Freddy Londoño",
                "María Fernanda López",
                "Kevin Arango",
                "Olga Cuéllar",
                "Bryan Hurtado",
                "Ingrid Téllez",
                "César Villalba",
                "Yenny Restrepo",
                "Orlando Montalvo",
                "Doris Escobar",
                "Fabián Girón",
                "Noemí Rincón",
                "Wilson Pardo",
                "Jennyfer Tapia",
                "Edison Perdomo",
                "Martha Cabrales",
                "Alexis Vanegas",
                "Rocío Chacón",
                "Samuel Bohórquez",
                "Ángela Rincón",
                "Óscar Salamanca",
                "Lina Marulanda",
                "Cristóbal Nieto",
                "Yesica Pulido",
                "René Barragán"
        );

        String nombre = nombres.get(random.nextInt(nombres.size()));

        int dia = random.nextInt(28) + 1;
        int mes = random.nextInt(12) + 1;
        int anio = random.nextInt(60) + 1960; // 1960–2019
        String fechaNacimiento = String.format("%02d/%02d/%04d", dia, mes, anio);

        int documento = random.nextInt(999_999_999) + 1;

        double altura = 0.3 + (2.5 - 0.3) * random.nextDouble();
        altura = Math.round(altura * 100.0) / 100.0;

        double peso = 1 + (500 - 1) * random.nextDouble();
        peso = Math.round(peso * 10.0) / 10.0;

        List<String> rhs = List.of("O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-");
        String rh = rhs.get(random.nextInt(rhs.size()));

        int triage = random.nextInt(5) + 1;

        List<String> diagnosticos = List.of(
                "Gripe",
                "Hipertensión",
                "Diabetes",
                "Fractura",
                "Migraña",
                "Infección respiratoria",
                "Dolor abdominal",
                "Trauma leve"
        );
        String diagnostico = diagnosticos.get(random.nextInt(diagnosticos.size()));

        return new Paciente(nombre, fechaNacimiento, documento, altura, (peso+""), rh, triage, diagnostico);
        		
        		
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


}
