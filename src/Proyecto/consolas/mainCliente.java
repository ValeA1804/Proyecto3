package Proyecto.consolas;


import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import Proyecto.Administrador;
import Proyecto.Cliente;
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
 
public class mainCliente {
	
	    static Scanner scanner = new Scanner(System.in);
	    static InventarioJuegoVenta inventarioVenta = new InventarioJuegoVenta(new ArrayList<>());
	    static InventarioJuegoPrestamo inventarioPrestamo = new InventarioJuegoPrestamo(new ArrayList<>());

	    public static void main(String[] args) {
	        System.out.println(new File("Data").getAbsolutePath());
	        new File("Data").mkdirs();

	        ArrayList<CuentaUsuario> cuentas = PersistenciaManager.cargarCuentas();
	        ArrayList<Producto> productos = PersistenciaManager.cargarProductos();

	        for (Producto p : productos) {
	            if (p.getTipoProducto() == TipoProducto.JUEGOMESAVENTA) {
	                inventarioVenta.getJuegosVenta().add(p);
	            } else if (p.getTipoProducto() == TipoProducto.JUEGOMESAPRESTAMO) {
	                inventarioPrestamo.getProductos().add(p);
	            }
	        }

	        boolean ejecutando = true;
	        while (ejecutando) {
	            System.out.println("\n=== BOARD GAME CAFE - CLIENTE ===");
	            System.out.println("1. Iniciar sesion");
	            System.out.println("2. Crear cuenta nueva");
	            System.out.println("3. Salir");
	            System.out.print("Opcion: ");
	            String opcion = scanner.nextLine();

	            if (opcion.equals("3")) {
	                System.out.println("Hasta luego.");
	                ejecutando = false;
	            } else if (opcion.equals("1")) {
	                System.out.print("Usuario: ");
	                String usuario = scanner.nextLine();
	                System.out.print("Contrasena: ");
	                String contrasena = scanner.nextLine();

	                CuentaUsuario sesion = null;
	                for (CuentaUsuario cuenta : cuentas) {
	                    if (cuenta.getUsuario().equals(usuario) && cuenta.getContraseña().equals(contrasena)
	                            && cuenta.getRol() == Rol.CLIENTE) {
	                        sesion = cuenta;
	                        break;
	                    }
	                }

	                if (sesion == null) {
	                    System.out.println("Error: Usuario o contrasena incorrectos, o no es cliente.");
	                } else {
	                    System.out.println("Bienvenido " + sesion.getNombre());
	                    menuCliente((Cliente) sesion, cuentas, productos);
	                }
	            } else if (opcion.equals("2")) {
	                crearCuentaNueva(cuentas);
	                PersistenciaManager.guardarCuentas(cuentas);
	                PersistenciaManager.guardarClientes(cuentas);
	            } else {
	                System.out.println("Opcion invalida");
	            }
	        }
	        scanner.close();
	    }

	    static void crearCuentaNueva(ArrayList<CuentaUsuario> cuentas) {
	        System.out.println("\n=== CREAR NUEVA CUENTA ===");
	        System.out.print("Nombre completo: ");
	        String nombre = scanner.nextLine();
	        System.out.print("Apellido: ");
	        String apellido = scanner.nextLine();
	        System.out.print("Cedula: ");
	        int cedula = 0;
	        try {
	            cedula = Integer.parseInt(scanner.nextLine());
	        } catch (NumberFormatException e) {
	            System.out.println("Cedula invalida. Usando 0 por defecto.");
	        }
	        System.out.print("Email: ");
	        String email = scanner.nextLine();
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

	        Cliente nuevoCliente = new Cliente(nombre, nuevoId, usuario, contrasena,
	                Rol.CLIENTE, email, cedula, apellido, new ArrayList<>(), null, null, false);

	        cuentas.add(nuevoCliente);
	        System.out.println("\n=== CUENTA CREADA EXITOSAMENTE ===");
	        System.out.println("Usuario: " + usuario);
	        System.out.println("Nombre: " + nombre + " " + apellido);
	    }

	    static void menuCliente(Cliente cliente, ArrayList<CuentaUsuario> cuentas, ArrayList<Producto> productos) {
	        boolean enMenu = true;
	        while (enMenu) {
	            System.out.println("\n=== MENU CLIENTE ===");
	            System.out.println("1. Pedir juego prestado");
	            System.out.println("2. Ver juegos favoritos");
	            System.out.println("3. Agregar juego a favoritos");
	            System.out.println("4. Solicitar producto (comida/bebida)");
	            System.out.println("5. Ver mis solicitudes pendientes");
	            System.out.println("6. Cerrar sesion");
	            System.out.print("Opcion: ");
	            String opcion = scanner.nextLine();

	            if (opcion.equals("6")) {
	                enMenu = false;
	            } else if (opcion.equals("1")) {
	                if (inventarioPrestamo.getProductos().isEmpty()) {
	                    System.out.println("No hay juegos disponibles para prestamo.");
	                } else {
	                    for (Producto p : inventarioPrestamo.getProductos()) {
	                        String estado = p.getCaracteristicas() != null ? p.getCaracteristicas().getEstado() : "Nuevo";
	                        System.out.println("  - " + p.getNombreProducto() + " | Disponibles: " + p.getCantidadDisponible() + " | Estado: " + estado);
	                    }
	                    System.out.print("Nombre del juego: ");
	                    String nombre = scanner.nextLine();
	                    Producto juegoSeleccionado = null;
	                    for (Producto p : inventarioPrestamo.getProductos()) {
	                        if (p.getNombreProducto().equalsIgnoreCase(nombre)) {
	                            juegoSeleccionado = p;
	                            break;
	                        }
	                    }
	                    if (juegoSeleccionado != null) {
	                        cliente.prestarJuego(juegoSeleccionado, cliente);
	                    } else {
	                        System.out.println("Juego no encontrado.");
	                    }
	                }
	            } else if (opcion.equals("2")) {
	                if (cliente.getJuegosFavoritos().isEmpty()) {
	                    System.out.println("No tienes juegos favoritos.");
	                } else {
	                    for (Producto p : cliente.getJuegosFavoritos()) {
	                        System.out.println("  - " + p.getNombreProducto());
	                    }
	                }
	            } else if (opcion.equals("3")) {
	                System.out.print("Nombre del juego a agregar a favoritos: ");
	                String nombreFav = scanner.nextLine();
	                Producto juegoFav = null;
	                for (Producto p : inventarioPrestamo.getProductos()) {
	                    if (p.getNombreProducto().equalsIgnoreCase(nombreFav)) {
	                        juegoFav = p;
	                        break;
	                    }
	                }
	                if (juegoFav == null) {
	                    for (Producto p : inventarioVenta.getJuegosVenta()) {
	                        if (p.getNombreProducto().equalsIgnoreCase(nombreFav)) {
	                            juegoFav = p;
	                            break;
	                        }
	                    }
	                }
	                if (juegoFav != null) {
	                    cliente.agregarJuegoFavorito(juegoFav);
	                    System.out.println("Juego agregado a favoritos.");
	                } else {
	                    System.out.println("Juego no encontrado.");
	                }
	            } else if (opcion.equals("4")) {
	                if (productos.isEmpty()) {
	                    System.out.println("No hay productos disponibles.");
	                } else {
	                    for (Producto p : productos) {
	                        if (p.getTipoProducto() != TipoProducto.JUEGOMESAPRESTAMO &&
	                                p.getTipoProducto() != TipoProducto.JUEGOMESAVENTA) {
	                            System.out.println("  - " + p.getNombreProducto() + " | $" + p.getPrecio());
	                        }
	                    }
	                    System.out.print("Nombre del producto: ");
	                    String nombreProd = scanner.nextLine();
	                    Producto productoSeleccionado = null;
	                    for (Producto p : productos) {
	                        if (p.getNombreProducto().equalsIgnoreCase(nombreProd)) {
	                            productoSeleccionado = p;
	                            break;
	                        }
	                    }
	                    if (productoSeleccionado != null) {
	                        cliente.solicitarProducto(productoSeleccionado);
	                    } else {
	                        System.out.println("Producto no encontrado.");
	                    }
	                }
	            } else if (opcion.equals("5")) {
	                ArrayList<Producto> pendientes = cliente.getSolicitudesPendientes();
	                if (pendientes.isEmpty()) {
	                    System.out.println("No tienes solicitudes pendientes.");
	                } else {
	                    for (Producto p : pendientes) {
	                        System.out.println("  - " + p.getNombreProducto());
	                    }
	                    System.out.println("Esperando confirmacion del mesero...");
	                }
	            } else {
	                System.out.println("Opcion invalida");
	            }
	        }
	    }
	
}
