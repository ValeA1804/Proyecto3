package Proyecto.consolas;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import Proyecto.Administrador;
import Proyecto.Cocinero;
import Proyecto.CuentaUsuario;
import Proyecto.Empleado;
import Proyecto.InventarioJuegoPrestamo;
import Proyecto.InventarioJuegoVenta;
import Proyecto.Menu;
import Proyecto.Mesero;
import Proyecto.PersistenciaManager;
import Proyecto.Producto;
import Proyecto.Turno;
import Proyecto.Enum.Rol;
import Proyecto.Enum.TipoEmpleado;
import Proyecto.Enum.TipoProducto;

public class mainAdministrador {
	
	    static Scanner scanner = new Scanner(System.in);
	    static InventarioJuegoVenta inventarioVenta = new InventarioJuegoVenta(new ArrayList<>());
	    static InventarioJuegoPrestamo inventarioPrestamo = new InventarioJuegoPrestamo(new ArrayList<>());

	    public static void main(String[] args) {
	        System.out.println(new File("Data").getAbsolutePath());
	        new File("Data").mkdirs();

	        ArrayList<CuentaUsuario> cuentas = PersistenciaManager.cargarCuentas();
	        ArrayList<Producto> productos = PersistenciaManager.cargarProductos();
	        Menu menu = PersistenciaManager.cargarMenu(productos);

	        for (Producto p : productos) {
	            if (p.getTipoProducto() == TipoProducto.JUEGOMESAVENTA) {
	                inventarioVenta.getJuegosVenta().add(p);
	            } else if (p.getTipoProducto() == TipoProducto.JUEGOMESAPRESTAMO) {
	                inventarioPrestamo.getProductos().add(p);
	            }
	        }

	        boolean ejecutando = true;
	        while (ejecutando) {
	            System.out.println("\n=== BOARD GAME CAFE - ADMINISTRADOR ===");
	            System.out.print("Usuario: ");
	            String usuario = scanner.nextLine();
	            System.out.print("Contrasena: ");
	            String contrasena = scanner.nextLine();

	            CuentaUsuario sesion = null;
	            for (CuentaUsuario cuenta : cuentas) {
	                if (cuenta.getUsuario().equals(usuario) && cuenta.getContraseña().equals(contrasena)
	                        && cuenta.getRol() == Rol.ADMINISTRADOR) {
	                    sesion = cuenta;
	                    break;
	                }
	            }

	            if (sesion == null) {
	                System.out.println("Error: Usuario o contrasena incorrectos, o no es administrador.");
	            } else {
	                System.out.println("Bienvenido " + sesion.getNombre());
	                menuAdministrador((Administrador) sesion, cuentas, productos);
	                ejecutando = false;
	            }
	        }
	        scanner.close();
	    }

	    static void menuAdministrador(Administrador admin, ArrayList<CuentaUsuario> cuentas, ArrayList<Producto> productos) {
	        boolean enMenu = true;
	        while (enMenu) {
	            System.out.println("\n=== MENU ADMINISTRADOR ===");
	            System.out.println("1. Construir turnos");
	            System.out.println("2. Ver turnos de un empleado");
	            System.out.println("3. Mover juego a venta");
	            System.out.println("4. Mover juego a prestamo");
	            System.out.println("5. Reparar juego");
	            System.out.println("6. Marcar juego robado");
	            System.out.println("7. Crear cuenta de empleado");
	            System.out.println("8. Ver todas las cuentas");
	            System.out.println("9. Cerrar sesion");
	            System.out.print("Opcion: ");
	            String opcion = scanner.nextLine();

	            if (opcion.equals("9")) {
	                PersistenciaManager.guardarTodo(cuentas, productos, null);
	                enMenu = false;
	            } else if (opcion.equals("1")) {
	                ArrayList<Empleado> empleados = new ArrayList<>();
	                for (CuentaUsuario c : cuentas) {
	                    if (c instanceof Empleado) empleados.add((Empleado) c);
	                }
	                if (empleados.isEmpty()) {
	                    System.out.println("No hay empleados registrados.");
	                } else {
	                    System.out.print("Fecha (DD/MM/AAAA): ");
	                    String fecha = scanner.nextLine();
	                    admin.construirTurnos(empleados, fecha);
	                    PersistenciaManager.guardarTurnos(cuentas);
	                }
	            } else if (opcion.equals("2")) {
	                System.out.print("Usuario del empleado: ");
	                String u = scanner.nextLine();
	                boolean encontrado = false;
	                for (CuentaUsuario c : cuentas) {
	                    if (c instanceof Empleado && c.getUsuario().equals(u)) {
	                        ArrayList<Turno> turnos = admin.obtenerTurnos((Empleado) c);
	                        if (turnos.isEmpty()) {
	                            System.out.println("El empleado no tiene turnos asignados.");
	                        } else {
	                            for (Turno t : turnos) {
	                                System.out.println(t.getDiaSemana() + " " + t.getHoraInicio() + " - " + t.getHoraSalida() + " (" + t.getFecha() + ")");
	                            }
	                        }
	                        encontrado = true;
	                        break;
	                    }
	                }
	                if (!encontrado) System.out.println("Empleado no encontrado.");
	            } else if (opcion.equals("3")) {
	                System.out.print("Nombre del juego: ");
	                String nombreVenta = scanner.nextLine();
	                Producto juegoVenta = null;
	                for (Producto p : inventarioPrestamo.getProductos()) {
	                    if (p.getNombreProducto().equalsIgnoreCase(nombreVenta)) {
	                        juegoVenta = p;
	                        break;
	                    }
	                }
	                if (juegoVenta != null) {
	                    try {
	                        admin.moverAVenta(juegoVenta);
	                        System.out.println("Juego movido a venta.");
	                    } catch (Exception e) {
	                        System.out.println(e.getMessage());
	                    }
	                } else {
	                    System.out.println("Juego no encontrado en prestamo.");
	                }
	            } else if (opcion.equals("4")) {
	                System.out.print("Nombre del juego: ");
	                String nombrePrestamo = scanner.nextLine();
	                boolean encontrado = false;
	                for (Producto p : inventarioVenta.getJuegosVenta()) {
	                    if (p.getNombreProducto().equalsIgnoreCase(nombrePrestamo)) {
	                        admin.moverAPrestamo(p);
	                        System.out.println("Juego movido a prestamo.");
	                        encontrado = true;
	                        break;
	                    }
	                }
	                if (!encontrado) System.out.println("Juego no encontrado en venta.");
	            } else if (opcion.equals("5")) {
	                System.out.print("Nombre del juego: ");
	                String nombreReparar = scanner.nextLine();
	                boolean reparado = false;
	                for (Producto p : inventarioPrestamo.getProductos()) {
	                    if (p.getNombreProducto().equalsIgnoreCase(nombreReparar)) {
	                        admin.repararJuego(p);
	                        System.out.println("Juego reparado. Estado: " + p.getCaracteristicas().getEstado());
	                        reparado = true;
	                        break;
	                    }
	                }
	                if (!reparado) System.out.println("Juego no encontrado en prestamo.");
	            } else if (opcion.equals("6")) {
	                System.out.print("Nombre del juego: ");
	                String nombreRobado = scanner.nextLine();
	                boolean robado = false;
	                for (Producto p : inventarioPrestamo.getProductos()) {
	                    if (p.getNombreProducto().equalsIgnoreCase(nombreRobado)) {
	                        admin.marcarRobado(p);
	                        System.out.println("Juego marcado como robado.");
	                        robado = true;
	                        break;
	                    }
	                }
	                if (!robado) System.out.println("Juego no encontrado en prestamo.");
	            } else if (opcion.equals("7")) {
	                crearCuentaEmpleado(admin, cuentas);
	                PersistenciaManager.guardarEmpleados(cuentas);
	                PersistenciaManager.guardarCuentas(cuentas);
	            } else if (opcion.equals("8")) {
	                System.out.println("\n=== LISTA DE CUENTAS ===");
	                for (CuentaUsuario c : cuentas) {
	                    String tipoInfo = c instanceof Empleado ? " - " + ((Empleado) c).getTipoEmpleado() : "";
	                    System.out.println(c.getId() + " | " + c.getUsuario() + " | " + c.getNombre() + " | " + c.getRol() + tipoInfo);
	                }
	            } else {
	                System.out.println("Opcion invalida");
	            }
	        }
	    }

	    static void crearCuentaEmpleado(Administrador admin, ArrayList<CuentaUsuario> cuentas) {
	        System.out.println("\n=== CREAR CUENTA DE EMPLEADO ===");
	        System.out.println("1. Mesero");
	        System.out.println("2. Cocinero");
	        System.out.println("3. Cancelar");
	        System.out.print("Opcion: ");
	        String tipo = scanner.nextLine();

	        if (tipo.equals("3")) {
	            System.out.println("Creacion cancelada.");
	            return;
	        }

	        if (!tipo.equals("1") && !tipo.equals("2")) {
	            System.out.println("Opcion invalida");
	            return;
	        }

	        System.out.print("Nombre completo: ");
	        String nombre = scanner.nextLine();
	        System.out.print("Usuario: ");
	        String usuario = scanner.nextLine();

	        for (CuentaUsuario cuenta : cuentas) {
	            if (cuenta.getUsuario().equals(usuario)) {
	                System.out.println("Error: El usuario ya existe.");
	                return;
	            }
	        }

	        System.out.print("Contrasena: ");
	        String contrasena = scanner.nextLine();

	        int maxId = 0;
	        for (CuentaUsuario cuenta : cuentas) {
	            if (cuenta.getId() > maxId) maxId = cuenta.getId();
	        }
	        int nuevoId = maxId + 1;

	        Empleado nuevoEmpleado;
	        if (tipo.equals("1")) {
	            nuevoEmpleado = new Mesero(nombre, nuevoId, usuario, contrasena, Rol.EMPLEADO,
	                    new ArrayList<>(), TipoEmpleado.MESERO, false,
	                    new ArrayList<>(), new ArrayList<>(), null, new ArrayList<>());
	            System.out.println("=== MESERO CREADO EXITOSAMENTE ===");
	        } else {
	            nuevoEmpleado = new Cocinero(nombre, nuevoId, usuario, contrasena, Rol.EMPLEADO,
	                    new ArrayList<>(), TipoEmpleado.COCINERO, false, new ArrayList<>());
	            System.out.println("=== COCINERO CREADO EXITOSAMENTE ===");
	        }

	        cuentas.add(nuevoEmpleado);
	        System.out.println("Usuario: " + usuario + " | Nombre: " + nombre);
	    }
	
	}



