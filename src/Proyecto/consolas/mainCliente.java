package Proyecto.consolas;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import Proyecto.Cliente;
import Proyecto.CuentaUsuario;
import Proyecto.InventarioJuegoPrestamo;
import Proyecto.InventarioJuegoVenta;
import Proyecto.PersistenciaManager;
import Proyecto.Producto;
import Proyecto.Torneo;
import Proyecto.TorneoCompetitivo;
import Proyecto.TorneoAmistoso;
import Proyecto.Enum.*;
import Proyecto.Exception.CuposInsuficientesException;
import Proyecto.Exception.EmpleadoEnTurnoException;
import Proyecto.Exception.InscripcionExcedidaException;

public class mainCliente {
    
    static Scanner scanner = new Scanner(System.in);
    static InventarioJuegoVenta inventarioVentaGlobal = new InventarioJuegoVenta(new ArrayList<>());
    static InventarioJuegoPrestamo inventarioPrestamoGlobal = new InventarioJuegoPrestamo(new ArrayList<>());

    public static void main(String[] args) throws EmpleadoEnTurnoException, CuposInsuficientesException, InscripcionExcedidaException {
        System.out.println(new File("Data").getAbsolutePath());
        new File("Data").mkdirs();

        ArrayList<CuentaUsuario> cuentas = PersistenciaManager.cargarCuentas();
        ArrayList<Producto> productos = PersistenciaManager.cargarProductos();
        ArrayList<Torneo> torneos = new ArrayList<>();
        
        datosIniciales(cuentas, productos, torneos);
        
        // Llenar inventarios
        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);
            if (p.getTipoProducto() == TipoProducto.JUEGOMESAVENTA) {
                inventarioVentaGlobal.getJuegosVenta().add(p);
            } else if (p.getTipoProducto() == TipoProducto.JUEGOMESAPRESTAMO) {
                inventarioPrestamoGlobal.getProductos().add(p);
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
                for (int i = 0; i < cuentas.size(); i++) {
                    if (cuentas.get(i).getUsuario().equals(usuario) && 
                        cuentas.get(i).getContraseña().equals(contrasena) &&
                        cuentas.get(i).getRol() == Rol.CLIENTE) {
                        sesion = cuentas.get(i);
                    }
                }

                if (sesion == null) {
                    System.out.println("Error: Usuario o contrasena incorrectos");
                } else {
                    System.out.println("Bienvenido " + sesion.getNombre());
                    menuCliente((Cliente) sesion, cuentas, productos, torneos);
                }
            } else if (opcion.equals("2")) {
                crearCuentaNueva(cuentas);
            } else {
                System.out.println("Opcion invalida");
            }
        }
        scanner.close();
    }
    
    private static void datosIniciales(ArrayList<CuentaUsuario> cuentas,
                                        ArrayList<Producto> productos,
                                        ArrayList<Torneo> torneos) {
        
        // Clientes
        boolean existeCliente = false;
        for (int i = 0; i < cuentas.size(); i++) {
            if (cuentas.get(i).getRol() == Rol.CLIENTE) {
                existeCliente = true;
            }
        }
        if (!existeCliente) {
            Cliente c1 = new Cliente("Juan", 200, "juan", "123", Rol.CLIENTE,
                                     "juan@mail.com", 12345678, "Perez",
                                     new ArrayList<>(), null, null, false);
            Cliente c2 = new Cliente("Maria", 201, "maria", "123", Rol.CLIENTE,
                                     "maria@mail.com", 87654321, "Gomez",
                                     new ArrayList<>(), null, null, false);
            cuentas.add(c1);
            cuentas.add(c2);
            System.out.println("Clientes creados: juan/123, maria/123");
        }
        
        // Productos comida
        boolean hayComida = false;
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getTipoProducto() == TipoProducto.BEBIDA ||
                productos.get(i).getTipoProducto() == TipoProducto.PASTELERIA) {
                hayComida = true;
            }
        }
        if (!hayComida) {
            int id = 1;
            productos.add(new Producto(3000, "Cafe Americano", "Cafe negro", false, true, new ArrayList<>(), TipoProducto.BEBIDA, id++));
            productos.add(new Producto(4000, "Capuchino", "Cafe con leche", false, true, new ArrayList<>(), TipoProducto.BEBIDA, id++));
            productos.add(new Producto(5000, "Te Verde", "Te relajante", false, true, new ArrayList<>(), TipoProducto.BEBIDA, id++));
            productos.add(new Producto(6000, "Jugo Natural", "Jugo de frutas", false, false, new ArrayList<>(), TipoProducto.BEBIDA, id++));
            productos.add(new Producto(5000, "Croissant", "Hojaldre", false, false, new ArrayList<>(), TipoProducto.PASTELERIA, id++));
            productos.add(new Producto(7000, "Torta Chocolate", "Torta humeda", false, false, new ArrayList<>(), TipoProducto.PASTELERIA, id++));
        }
        
        // Torneos
        if (torneos.isEmpty()) {
            torneos.add(new TorneoCompetitivo("Competitivo - Catan", "2024-12-15", null, 16, 5000, "Trofeo"));
            torneos.add(new TorneoAmistoso("Amistoso - D&D", "2024-12-20", null, 8, 10));
        }
    }
    
    static void crearCuentaNueva(ArrayList<CuentaUsuario> cuentas) {
        System.out.println("\n=== CREAR CUENTA NUEVA ===");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Cedula: ");
        String cedulaStr = scanner.nextLine();
        int cedula = 0;
        if (cedulaStr.matches("\\d+")) cedula = Integer.parseInt(cedulaStr);
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Usuario: ");
        String usuario = scanner.nextLine();
        
        boolean existe = false;
        for (int i = 0; i < cuentas.size(); i++) {
            if (cuentas.get(i).getUsuario().equals(usuario)) existe = true;
        }
        if (existe) {
            System.out.println("Usuario ya existe");
            return;
        }
        
        System.out.print("Contrasena: ");
        String contrasena = scanner.nextLine();
        
        int maxId = 0;
        for (int i = 0; i < cuentas.size(); i++) {
            if (cuentas.get(i).getId() > maxId) maxId = cuentas.get(i).getId();
        }
        
        Cliente nuevo = new Cliente(nombre, maxId + 1, usuario, contrasena, Rol.CLIENTE,
                                    email, cedula, apellido, new ArrayList<>(), null, null, false);
        cuentas.add(nuevo);
        System.out.println("Cuenta creada");
    }
    
    static void menuCliente(Cliente cliente, ArrayList<CuentaUsuario> cuentas,
                            ArrayList<Producto> productos, ArrayList<Torneo> torneos) throws EmpleadoEnTurnoException, CuposInsuficientesException, InscripcionExcedidaException {
        boolean enMenu = true;
        while (enMenu) {
            System.out.println("\n=== MENU CLIENTE ===");
            System.out.println("1. Ver menu de comida");
            System.out.println("2. Ver catalogo de juegos");
            System.out.println("3. Pedir juego prestado");
            System.out.println("4. Ver juegos favoritos");
            System.out.println("5. Agregar juego a favoritos");
            System.out.println("6. Solicitar producto");
            System.out.println("7. Comprar juego");
            System.out.println("8. Torneos");
            System.out.println("9. Salir");
            System.out.print("Opcion: ");
            String opcion = scanner.nextLine();
            
            if (opcion.equals("9")) {
                enMenu = false;
            } else if (opcion.equals("1")) {
                System.out.println("\n=== MENU DE COMIDA ===");
                for (int i = 0; i < productos.size(); i++) {
                    Producto p = productos.get(i);
                    if (p.getTipoProducto() == TipoProducto.BEBIDA || p.getTipoProducto() == TipoProducto.PASTELERIA) {
                        System.out.println("- " + p.getNombreProducto() + " | $" + p.getPrecio());
                        if (p.isAlcoholica()) System.out.println("  (Contiene alcohol)");
                        if (p.isCaliente()) System.out.println("  (Bebida caliente)");
                        if (!p.getAlergenos().isEmpty()) System.out.println("  Alergenos: " + p.getAlergenos());
                    }
                }
            } else if (opcion.equals("2")) {
                System.out.println("\n=== CATALOGO DE JUEGOS ===");
                for (int i = 0; i < inventarioPrestamoGlobal.getProductos().size(); i++) {
                    Producto p = inventarioPrestamoGlobal.getProductos().get(i);
                    System.out.println("- " + p.getNombreProducto() + " | $" + p.getPrecio());
                    if (p.getCaracteristicas() != null) {
                        System.out.println("  Jugadores: " + p.getCaracteristicas().getNumeroJugadores());
                        System.out.println("  Edad: " + p.getCaracteristicas().getRestriccionEdad() + "+");
                        System.out.println("  Categoria: " + p.getCaracteristicas().getCategoria());
                    }
                }
            } else if (opcion.equals("3")) {
                if (inventarioPrestamoGlobal.getProductos().isEmpty()) {
                    System.out.println("No hay juegos");
                } else {
                    for (int i = 0; i < inventarioPrestamoGlobal.getProductos().size(); i++) {
                        Producto p = inventarioPrestamoGlobal.getProductos().get(i);
                        System.out.println("- " + p.getNombreProducto() + " | Stock: " + p.getCantidadDisponible());
                    }
                    System.out.print("Nombre juego: ");
                    String nombre = scanner.nextLine();
                    Producto seleccionado = null;
                    for (int i = 0; i < inventarioPrestamoGlobal.getProductos().size(); i++) {
                        if (inventarioPrestamoGlobal.getProductos().get(i).getNombreProducto().equalsIgnoreCase(nombre)) {
                            seleccionado = inventarioPrestamoGlobal.getProductos().get(i);
                        }
                    }
                    if (seleccionado != null) {
                        cliente.prestarJuego(seleccionado, cliente);
                    }
                }
            } else if (opcion.equals("4")) {
                ArrayList<Producto> favs = cliente.getJuegosFavoritos();
                if (favs.isEmpty()) {
                    System.out.println("No tienes favoritos");
                } else {
                    for (int i = 0; i < favs.size(); i++) {
                        System.out.println("- " + favs.get(i).getNombreProducto());
                    }
                }
            } else if (opcion.equals("5")) {
                System.out.print("Nombre juego: ");
                String nombre = scanner.nextLine();
                Producto encontrado = null;
                for (int i = 0; i < inventarioPrestamoGlobal.getProductos().size(); i++) {
                    if (inventarioPrestamoGlobal.getProductos().get(i).getNombreProducto().equalsIgnoreCase(nombre)) {
                        encontrado = inventarioPrestamoGlobal.getProductos().get(i);
                    }
                }
                if (encontrado == null) {
                    for (int i = 0; i < inventarioVentaGlobal.getJuegosVenta().size(); i++) {
                        if (inventarioVentaGlobal.getJuegosVenta().get(i).getNombreProducto().equalsIgnoreCase(nombre)) {
                            encontrado = inventarioVentaGlobal.getJuegosVenta().get(i);
                        }
                    }
                }
                if (encontrado != null) {
                    cliente.agregarJuegoFavorito(encontrado);
                    System.out.println("Agregado a favoritos");
                }
            } else if (opcion.equals("6")) {
                System.out.println("\n=== SOLICITAR PRODUCTO ===");
                for (int i = 0; i < productos.size(); i++) {
                    Producto p = productos.get(i);
                    if (p.getTipoProducto() == TipoProducto.BEBIDA || p.getTipoProducto() == TipoProducto.PASTELERIA) {
                        System.out.println("- " + p.getNombreProducto() + " | $" + p.getPrecio());
                    }
                }
                System.out.print("Nombre producto: ");
                String nombre = scanner.nextLine();
                Producto seleccionado = null;
                for (int i = 0; i < productos.size(); i++) {
                    if (productos.get(i).getNombreProducto().equalsIgnoreCase(nombre)) {
                        seleccionado = productos.get(i);
                    }
                }
                if (seleccionado != null) {
                    cliente.solicitarProducto(seleccionado);
                }
            } else if (opcion.equals("7")) {
                if (inventarioVentaGlobal.getJuegosVenta().isEmpty()) {
                    System.out.println("No hay juegos en venta");
                } else {
                    for (int i = 0; i < inventarioVentaGlobal.getJuegosVenta().size(); i++) {
                        Producto p = inventarioVentaGlobal.getJuegosVenta().get(i);
                        System.out.println("- " + p.getNombreProducto() + " | $" + p.getPrecio());
                    }
                    System.out.print("Nombre juego: ");
                    String nombre = scanner.nextLine();
                    Producto seleccionado = null;
                    for (int i = 0; i < inventarioVentaGlobal.getJuegosVenta().size(); i++) {
                        if (inventarioVentaGlobal.getJuegosVenta().get(i).getNombreProducto().equalsIgnoreCase(nombre)) {
                            seleccionado = inventarioVentaGlobal.getJuegosVenta().get(i);
                        }
                    }
                    if (seleccionado != null) {
                        cliente.comprarJuego(seleccionado, inventarioVentaGlobal);
                    }
                }
            } else if (opcion.equals("8")) {
                menuTorneosCliente(cliente, torneos);
            }
        }
    }
    
    static void menuTorneosCliente(Cliente cliente, ArrayList<Torneo> torneos) throws EmpleadoEnTurnoException, CuposInsuficientesException, InscripcionExcedidaException {
        boolean enMenu = true;
        while (enMenu) {
            System.out.println("\n=== TORNEOS ===");
            System.out.println("1. Ver torneos");
            System.out.println("2. Inscribirse");
            System.out.println("3. Cancelar inscripcion");
            System.out.println("4. Mis torneos");
            System.out.println("5. Volver");
            System.out.print("Opcion: ");
            String opcion = scanner.nextLine();
            
            if (opcion.equals("5")) {
                enMenu = false;
            } else if (opcion.equals("1")) {
                for (int i = 0; i < torneos.size(); i++) {
                    Torneo t = torneos.get(i);
                    System.out.println((i+1) + ". " + t.getNombre() + " | Fecha: " + t.getFecha());
                }
            } else if (opcion.equals("2")) {
                System.out.print("Seleccione torneo: ");
                String input = scanner.nextLine();
                int idx = -1;
                if (input.matches("\\d+")) idx = Integer.parseInt(input);
                if (idx > 0 && idx <= torneos.size()) {
                    Torneo t = torneos.get(idx - 1);
                    ArrayList<Integer> ids = new ArrayList<>();
                    ids.add(cliente.getId());
                    t.inscribirParticipante(cliente, ids);
                    cliente.inscribirEnTorneo(t);
                    System.out.println("Inscrito");
                }
            } else if (opcion.equals("3")) {
                System.out.print("Seleccione torneo: ");
                String input = scanner.nextLine();
                int idx = -1;
                if (input.matches("\\d+")) idx = Integer.parseInt(input);
                if (idx > 0 && idx <= torneos.size()) {
                    Torneo t = torneos.get(idx - 1);
                    t.eliminarInscripcion(cliente);
                    cliente.cancelarInscripcionTorneo(t);
                    System.out.println("Cancelado");
                }
            } else if (opcion.equals("4")) {
                ArrayList<Torneo> misTorneos = cliente.getTorneosInscritos();
                if (misTorneos.isEmpty()) {
                    System.out.println("No estas inscrito");
                } else {
                    for (int i = 0; i < misTorneos.size(); i++) {
                        System.out.println("- " + misTorneos.get(i).getNombre());
                    }
                }
            }
        }
    }
}