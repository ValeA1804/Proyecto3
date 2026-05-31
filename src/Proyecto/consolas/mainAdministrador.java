package Proyecto.consolas;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import Proyecto.Administrador;
import Proyecto.Cocinero;
import Proyecto.CuentaUsuario;
import Proyecto.Empleado;
import Proyecto.HistorialPrestamo;
import Proyecto.InventarioJuegoPrestamo;
import Proyecto.InventarioJuegoVenta;
import Proyecto.Menu;
import Proyecto.Mesero;
import Proyecto.PersistenciaManager;
import Proyecto.Producto;
import Proyecto.SolicitudTurno;
import Proyecto.Torneo;
import Proyecto.TorneoCompetitivo;
import Proyecto.TorneoAmistoso;
import Proyecto.Turno;
import Proyecto.Enum.*;

public class mainAdministrador {
    
    static Scanner scanner = new Scanner(System.in);
    static InventarioJuegoVenta inventarioVentaGlobal = new InventarioJuegoVenta(new ArrayList<>());
    static InventarioJuegoPrestamo inventarioPrestamoGlobal = new InventarioJuegoPrestamo(new ArrayList<>());

    public static void main(String[] args) throws Exception {
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
            System.out.println("\n=== BOARD GAME CAFE - ADMINISTRADOR ===");
            System.out.print("Usuario: ");
            String usuario = scanner.nextLine();
            System.out.print("Contrasena: ");
            String contrasena = scanner.nextLine();

            CuentaUsuario sesion = null;
            for (int i = 0; i < cuentas.size(); i++) {
                if (cuentas.get(i).getUsuario().equals(usuario) && 
                    cuentas.get(i).getContraseña().equals(contrasena) &&
                    cuentas.get(i).getRol() == Rol.ADMINISTRADOR) {
                    sesion = cuentas.get(i);
                }
            }

            if (sesion == null) {
                System.out.println("Error: Usuario o contrasena incorrectos, o no es administrador.");
            } else {
                System.out.println("Bienvenido " + sesion.getNombre());
                menuAdministrador((Administrador) sesion, cuentas, productos, torneos);
                ejecutando = false;
            }
        }
        scanner.close();
    }
    
    private static void datosIniciales(ArrayList<CuentaUsuario> cuentas, 
                                        ArrayList<Producto> productos,
                                        ArrayList<Torneo> torneos) {
        
        // Administrador
        boolean existeAdmin = false;
        for (int i = 0; i < cuentas.size(); i++) {
            if (cuentas.get(i).getRol() == Rol.ADMINISTRADOR) {
                existeAdmin = true;
            }
        }
        if (!existeAdmin) {
            InventarioJuegoVenta invVenta = new InventarioJuegoVenta(new ArrayList<>());
            InventarioJuegoPrestamo invPrestamo = new InventarioJuegoPrestamo(new ArrayList<>());
            ArrayList<HistorialPrestamo> historial = new ArrayList<>();
            Administrador admin = new Administrador(invVenta, invPrestamo, historial);
            cuentas.add(admin);
            System.out.println("Administrador creado: admin / admin123");
        }
        
        // Torneos
        if (torneos.isEmpty()) {
            TorneoCompetitivo t1 = new TorneoCompetitivo("Competitivo - Catan", "2024-12-15", null, 16, 5000, "Trofeo");
            TorneoAmistoso t2 = new TorneoAmistoso("Amistoso - D&D", "2024-12-20", null, 8, 10);
            torneos.add(t1);
            torneos.add(t2);
        }
    }
    
    static void menuAdministrador(Administrador admin, ArrayList<CuentaUsuario> cuentas, 
                                   ArrayList<Producto> productos, ArrayList<Torneo> torneos) throws Exception {
        boolean enMenu = true;
        while (enMenu) {
            System.out.println("\n=== MENU ADMINISTRADOR ===");
            System.out.println("1. Construir turnos");
            System.out.println("2. Ver turnos de empleado");
            System.out.println("3. Mover juego a venta");
            System.out.println("4. Mover juego a prestamo");
            System.out.println("5. Reparar juego");
            System.out.println("6. Marcar juego robado");
            System.out.println("7. Crear cuenta empleado");
            System.out.println("8. Ver cuentas");
            System.out.println("9. Historial prestamos");
            System.out.println("10. Inventario prestamo");
            System.out.println("11. Inventario venta");
            System.out.println("12. Gestionar solicitudes turno");
            System.out.println("13. Ver torneos");
            System.out.println("14. Crear torneo");
            System.out.println("15. Salir");
            System.out.print("Opcion: ");
            String opcion = scanner.nextLine();

            if (opcion.equals("15")) {
                enMenu = false;
            } else if (opcion.equals("1")) {
                ArrayList<Empleado> empleados = new ArrayList<>();
                for (int i = 0; i < cuentas.size(); i++) {
                    if (cuentas.get(i) instanceof Empleado) {
                        empleados.add((Empleado) cuentas.get(i));
                    }
                }
                if (empleados.isEmpty()) {
                    System.out.println("No hay empleados");
                } else {
                    System.out.print("Fecha: ");
                    String fecha = scanner.nextLine();
                    admin.construirTurnos(empleados, fecha);
                }
            } else if (opcion.equals("2")) {
                System.out.print("Usuario: ");
                String u = scanner.nextLine();
                boolean encontrado = false;
                for (int i = 0; i < cuentas.size(); i++) {
                    if (cuentas.get(i) instanceof Empleado && cuentas.get(i).getUsuario().equals(u)) {
                        ArrayList<Turno> turnosEmp = admin.obtenerTurnos((Empleado) cuentas.get(i));
                        for (int j = 0; j < turnosEmp.size(); j++) {
                            Turno t = turnosEmp.get(j);
                            System.out.println(t.getDiaSemana() + " " + t.getHoraInicio() + "-" + t.getHoraSalida());
                        }
                        encontrado = true;
                    }
                }
                if (!encontrado) System.out.println("No encontrado");
            } else if (opcion.equals("3")) {
                System.out.print("Nombre juego: ");
                String nombre = scanner.nextLine();
                Producto juego = null;
                for (int i = 0; i < inventarioPrestamoGlobal.getProductos().size(); i++) {
                    if (inventarioPrestamoGlobal.getProductos().get(i).getNombreProducto().equalsIgnoreCase(nombre)) {
                        juego = inventarioPrestamoGlobal.getProductos().get(i);
                    }
                }
                if (juego != null) {
                    admin.moverAVenta(juego);
                    System.out.println("Movido a venta");
                } else {
                    System.out.println("No encontrado");
                }
            } else if (opcion.equals("4")) {
                System.out.print("Nombre juego: ");
                String nombre = scanner.nextLine();
                Producto juego = null;
                for (int i = 0; i < inventarioVentaGlobal.getJuegosVenta().size(); i++) {
                    if (inventarioVentaGlobal.getJuegosVenta().get(i).getNombreProducto().equalsIgnoreCase(nombre)) {
                        juego = inventarioVentaGlobal.getJuegosVenta().get(i);
                    }
                }
                if (juego != null) {
                    admin.moverAPrestamo(juego);
                    System.out.println("Movido a prestamo");
                } else {
                    System.out.println("No encontrado");
                }
            } else if (opcion.equals("5")) {
                System.out.print("Nombre juego: ");
                String nombre = scanner.nextLine();
                boolean encontrado = false;
                for (int i = 0; i < inventarioPrestamoGlobal.getProductos().size(); i++) {
                    Producto p = inventarioPrestamoGlobal.getProductos().get(i);
                    if (p.getNombreProducto().equalsIgnoreCase(nombre)) {
                        admin.repararJuego(p);
                        System.out.println("Juego reparado");
                        encontrado = true;
                    }
                }
                if (!encontrado) System.out.println("No encontrado");
            } else if (opcion.equals("6")) {
                System.out.print("Nombre juego: ");
                String nombre = scanner.nextLine();
                boolean encontrado = false;
                for (int i = 0; i < inventarioPrestamoGlobal.getProductos().size(); i++) {
                    Producto p = inventarioPrestamoGlobal.getProductos().get(i);
                    if (p.getNombreProducto().equalsIgnoreCase(nombre)) {
                        admin.marcarRobado(p);
                        System.out.println("Juego marcado robado");
                        encontrado = true;
                    }
                }
                if (!encontrado) System.out.println("No encontrado");
            } else if (opcion.equals("7")) {
                crearCuentaEmpleado(admin, cuentas);
            } else if (opcion.equals("8")) {
                System.out.println("\n=== LISTA DE CUENTAS ===");
                for (int i = 0; i < cuentas.size(); i++) {
                    CuentaUsuario c = cuentas.get(i);
                    System.out.println(c.getId() + " | " + c.getUsuario() + " | " + c.getNombre() + " | " + c.getRol());
                }
            } else if (opcion.equals("9")) {
                admin.consultarHistorialPrestamos();
            } else if (opcion.equals("10")) {
                System.out.println("\n=== INVENTARIO PRESTAMO ===");
                for (int i = 0; i < inventarioPrestamoGlobal.getProductos().size(); i++) {
                    Producto p = inventarioPrestamoGlobal.getProductos().get(i);
                    System.out.println("- " + p.getNombreProducto() + " | Stock: " + p.getCantidadDisponible());
                }
            } else if (opcion.equals("11")) {
                System.out.println("\n=== INVENTARIO VENTA ===");
                for (int i = 0; i < inventarioVentaGlobal.getJuegosVenta().size(); i++) {
                    Producto p = inventarioVentaGlobal.getJuegosVenta().get(i);
                    System.out.println("- " + p.getNombreProducto() + " | $" + p.getPrecio());
                }
            } else if (opcion.equals("12")) {
                gestionarSolicitudesTurno(admin, cuentas);
            } else if (opcion.equals("13")) {
                System.out.println("\n=== TORNEOS ===");
                for (int i = 0; i < torneos.size(); i++) {
                    Torneo t = torneos.get(i);
                    System.out.println((i+1) + ". " + t.getNombre() + " | " + t.getFecha());
                }
            } else if (opcion.equals("14")) {
                crearTorneo(torneos);
            } else {
                System.out.println("Opcion invalida");
            }
        }
    }
    
    static void crearCuentaEmpleado(Administrador admin, ArrayList<CuentaUsuario> cuentas) {
        System.out.println("\n=== CREAR CUENTA EMPLEADO ===");
        System.out.println("1. Mesero");
        System.out.println("2. Cocinero");
        System.out.print("Opcion: ");
        String tipo = scanner.nextLine();

        if (!tipo.equals("1") && !tipo.equals("2")) {
            System.out.println("Opcion invalida");
            return;
        }

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Usuario: ");
        String usuario = scanner.nextLine();

        boolean existe = false;
        for (int i = 0; i < cuentas.size(); i++) {
            if (cuentas.get(i).getUsuario().equals(usuario)) {
                existe = true;
            }
        }
        if (existe) {
            System.out.println("Usuario ya existe");
            return;
        }

        System.out.print("Contrasena: ");
        String contrasena = scanner.nextLine();

        int maxId = 0;
        for (int i = 0; i < cuentas.size(); i++) {
            if (cuentas.get(i).getId() > maxId) {
                maxId = cuentas.get(i).getId();
            }
        }

        if (tipo.equals("1")) {
            Mesero m = new Mesero(nombre, maxId + 1, usuario, contrasena, Rol.EMPLEADO, 
                                  new ArrayList<>(), TipoEmpleado.MESERO, false,
                                  new ArrayList<>(), new ArrayList<>(), null, new ArrayList<>());
            cuentas.add(m);
            System.out.println("Mesero creado");
        } else {
            Cocinero c = new Cocinero(nombre, maxId + 1, usuario, contrasena, Rol.EMPLEADO,
                                      new ArrayList<>(), TipoEmpleado.COCINERO, false, new ArrayList<>());
            cuentas.add(c);
            System.out.println("Cocinero creado");
        }
    }
    
    static void crearTorneo(ArrayList<Torneo> torneos) {
        System.out.println("\n=== CREAR TORNEO ===");
        System.out.println("1. Torneo Competitivo");
        System.out.println("2. Torneo Amistoso");
        System.out.print("Tipo: ");
        String tipo = scanner.nextLine();
        
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Fecha: ");
        String fecha = scanner.nextLine();
        System.out.print("Cupos: ");
        String cuposStr = scanner.nextLine();
        int cupos = 0;
        if (cuposStr.matches("\\d+")) {
            cupos = Integer.parseInt(cuposStr);
        }
        
        if (tipo.equals("1")) {
            System.out.print("Tarifa: ");
            String tarifaStr = scanner.nextLine();
            int tarifa = 0;
            if (tarifaStr.matches("\\d+")) {
                tarifa = Integer.parseInt(tarifaStr);
            }
            System.out.print("Premio: ");
            String premio = scanner.nextLine();
            TorneoCompetitivo t = new TorneoCompetitivo(nombre, fecha, null, cupos, tarifa, premio);
            torneos.add(t);
            System.out.println("Torneo competitivo creado");
        } else if (tipo.equals("2")) {
            System.out.print("Bono descuento: ");
            String bonoStr = scanner.nextLine();
            int bono = 0;
            if (bonoStr.matches("\\d+")) {
                bono = Integer.parseInt(bonoStr);
            }
            TorneoAmistoso t = new TorneoAmistoso(nombre, fecha, null, cupos, bono);
            torneos.add(t);
            System.out.println("Torneo amistoso creado");
        }
    }
    
    static void gestionarSolicitudesTurno(Administrador admin, ArrayList<CuentaUsuario> cuentas) {
        ArrayList<SolicitudTurno> todas = new ArrayList<>();
        for (int i = 0; i < cuentas.size(); i++) {
            if (cuentas.get(i) instanceof Empleado) {
                Empleado e = (Empleado) cuentas.get(i);
                for (int j = 0; j < e.getSolicitudTurno().size(); j++) {
                    todas.add(e.getSolicitudTurno().get(j));
                }
            }
        }
        
        if (todas.isEmpty()) {
            System.out.println("No hay solicitudes");
            return;
        }
        
        System.out.println("\n=== SOLICITUDES ===");
        for (int i = 0; i < todas.size(); i++) {
            SolicitudTurno s = todas.get(i);
            System.out.println((i+1) + ". " + s.getEmpleadoSolicitante().getNombre() + " | " + s.getTipoSolicitud());
        }
        
        System.out.print("Seleccione: ");
        String input = scanner.nextLine();
        int idx = -1;
        if (input.matches("\\d+")) idx = Integer.parseInt(input);
        
        if (idx > 0 && idx <= todas.size()) {
            SolicitudTurno s = todas.get(idx - 1);
            System.out.println("1. Aprobar");
            System.out.println("2. Rechazar");
            System.out.print("Opcion: ");
            String accion = scanner.nextLine();
            if (accion.equals("1")) {
                admin.aprobarSolicitud(s);
                System.out.println("Aprobada");
            } else if (accion.equals("2")) {
                admin.rechazarSolicitud(s);
                System.out.println("Rechazada");
            }
        }
    }
}