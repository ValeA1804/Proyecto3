package Proyecto.consolas;


import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import Proyecto.CuentaUsuario;
import Proyecto.Empleado;
import Proyecto.Menu;
import Proyecto.PersistenciaManager;
import Proyecto.Turno;
import Proyecto.Enum.Rol;

public class mainEmpleado {

	    static Scanner scanner = new Scanner(System.in);

	    public static void main(String[] args) {
	        System.out.println(new File("Data").getAbsolutePath());
	        new File("Data").mkdirs();

	        ArrayList<CuentaUsuario> cuentas = PersistenciaManager.cargarCuentas();

	        boolean ejecutando = true;
	        while (ejecutando) {
	            System.out.println("\n=== BOARD GAME CAFE - EMPLEADO ===");
	            System.out.print("Usuario: ");
	            String usuario = scanner.nextLine();
	            System.out.print("Contrasena: ");
	            String contrasena = scanner.nextLine();

	            CuentaUsuario sesion = null;
	            for (CuentaUsuario cuenta : cuentas) {
	                if (cuenta.getUsuario().equals(usuario) && cuenta.getContraseña().equals(contrasena)
	                        && cuenta.getRol() == Rol.EMPLEADO) {
	                    sesion = cuenta;
	                    break;
	                }
	            }

	            if (sesion == null) {
	                System.out.println("Error: Usuario o contrasena incorrectos, o no es empleado.");
	            } else {
	                System.out.println("Bienvenido " + sesion.getNombre());
	                menuEmpleado((Empleado) sesion);
	                ejecutando = false;
	            }
	        }
	        scanner.close();
	    }

	    static void menuEmpleado(Empleado empleado) {
	        boolean enMenu = true;
	        while (enMenu) {
	            System.out.println("\n=== MENU EMPLEADO ===");
	            System.out.println("1. Ver mis turnos");
	            System.out.println("2. Solicitar cambio de turno");
	            System.out.println("3. Cerrar sesion");
	            System.out.print("Opcion: ");
	            String opcion = scanner.nextLine();

	            if (opcion.equals("3")) {
	                enMenu = false;
	            } else if (opcion.equals("1")) {
	                if (empleado.getTurnos().isEmpty()) {
	                    System.out.println("No tienes turnos asignados.");
	                } else {
	                    for (int i = 0; i < empleado.getTurnos().size(); i++) {
	                        Turno t = empleado.getTurnos().get(i);
	                        System.out.println((i + 1) + ". " + t.getDiaSemana() + " " + t.getHoraInicio() + " - " + t.getHoraSalida() + " (" + t.getFecha() + ")");
	                    }
	                }
	            } else if (opcion.equals("2")) {
	                if (empleado.getTurnos().isEmpty()) {
	                    System.out.println("No tienes turnos asignados para cambiar.");
	                } else {
	                    for (int i = 0; i < empleado.getTurnos().size(); i++) {
	                        Turno t = empleado.getTurnos().get(i);
	                        System.out.println(i + ". " + t.getDiaSemana() + " " + t.getHoraInicio() + " - " + t.getHoraSalida());
	                    }
	                    System.out.print("Seleccione el indice del turno a cambiar: ");
	                    int idx = 0;
	                    try {
	                        idx = Integer.parseInt(scanner.nextLine());
	                    } catch (NumberFormatException e) {
	                        System.out.println("Indice invalido");
	                        continue;
	                    }
	                    if (idx < 0 || idx >= empleado.getTurnos().size()) {
	                        System.out.println("Indice invalido");
	                    } else {
	                        Turno turnoActual = empleado.getTurnos().get(idx);
	                        System.out.print("Nueva hora de inicio (HH:MM): ");
	                        String nHoraI = scanner.nextLine();
	                        System.out.print("Nueva hora de salida (HH:MM): ");
	                        String nHoraF = scanner.nextLine();
	                        Turno nuevoTurno = new Turno(turnoActual.getDiaSemana(), nHoraI, nHoraF, turnoActual.getFecha());
	                        empleado.solicitarCambioTurnoGeneral(turnoActual, nuevoTurno);
	                        System.out.println("Solicitud enviada al administrador.");
	                    }
	                }
	            } else {
	                System.out.println("Opcion invalida");
	            }
	        }
	    }
	}

