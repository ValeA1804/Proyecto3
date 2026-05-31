package Proyecto;

import java.io.File;
import java.util.*;

import Proyecto.Enum.*;
import Proyecto.Exception.CuposInsuficientesException;
import Proyecto.Exception.EmpleadoEnTurnoException;
import Proyecto.Exception.InscripcionExcedidaException;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static InventarioJuegoVenta inventarioVentaGlobal = new InventarioJuegoVenta(new ArrayList<>());
    static InventarioJuegoPrestamo inventarioPrestamoGlobal = new InventarioJuegoPrestamo(new ArrayList<>());
    static Cafeteria cafeteriaGlobal = null;

    public static void main(String[] args) throws Exception {
        System.out.println(new File("Data").getAbsolutePath());
        new File("Data").mkdirs();

        ArrayList<CuentaUsuario> cuentas = PersistenciaManager.cargarCuentas();
        ArrayList<Producto> productos = PersistenciaManager.cargarProductos();
        ArrayList<Torneo> torneos = new ArrayList<>();
        
        // DATOS INICIALES 
        datosInicialesAdministrador(cuentas);
        datosInicialesEmpleado(cuentas);
        datosInicialesCliente(cuentas);
        datosInicialesJuegos(productos);
        datosInicialesComida(productos);
        datosInicialesTorneos(torneos);
        
        // Recargar productos despues de datos iniciales
        productos = PersistenciaManager.cargarProductos();
        if (productos.isEmpty()) {
            datosInicialesJuegos(productos);
            datosInicialesComida(productos);
        }

        ArrayList<Producto> productosActualizados = PersistenciaManager.cargarProductos();
        if (!productosActualizados.isEmpty()) {
            productos = productosActualizados;
        }

        // Llenar inventarios globales
        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);
            if (p.getTipoProducto() == TipoProducto.JUEGOMESAVENTA) {
                inventarioVentaGlobal.getJuegosVenta().add(p);
            } else if (p.getTipoProducto() == TipoProducto.JUEGOMESAPRESTAMO) {
                inventarioPrestamoGlobal.getProductos().add(p);
            }
        }

        // Crear menu
        Menu menu = new Menu(new ArrayList<>(), inventarioPrestamoGlobal);
        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);
            if (p.getTipoProducto() == TipoProducto.BEBIDA || p.getTipoProducto() == TipoProducto.PASTELERIA) {
                menu.getMenuProductos().add(p);
            }
        }
        
        PersistenciaManager.guardarTodo(cuentas, productos, menu);
        menu = PersistenciaManager.cargarMenu(productos);
        
        // Crear mesas y cafeteria
        ArrayList<Mesa> mesas = datosInicialesMesas();
        cafeteriaGlobal = new Cafeteria("Bogota", "Calle Principal 123", mesas, new ArrayList<>(), 50, new ArrayList<>(), menu);
        
        System.out.println("\n========================================");
        System.out.println("SISTEMA INICIALIZADO CORRECTAMENTE");
        System.out.println("========================================");
        System.out.println("Mesas disponibles: " + mesas.size());
        System.out.println("Productos en menu: " + menu.getMenuProductos().size());
        System.out.println("Juegos en prestamo: " + inventarioPrestamoGlobal.getProductos().size());
        System.out.println("Juegos en venta: " + inventarioVentaGlobal.getJuegosVenta().size());
        System.out.println("Torneos disponibles: " + torneos.size());
        System.out.println("========================================\n");
        System.out.println("\n=== CREDENCIALES DISPONIBLES ===");
        for (int i = 0; i < cuentas.size(); i++) {
            CuentaUsuario c = cuentas.get(i);
            System.out.println(c.getUsuario() + " / " + c.getContraseña() + " - " + c.getRol());
        }
        boolean ejecutando = true;
        while (ejecutando) {
            System.out.println("\n=== BIENVENIDO AL BOARD GAME CAFE ===");
            System.out.println("1. Iniciar sesion");
            System.out.println("2. Crear cuenta nueva");
            System.out.println("3. Salir");
            System.out.print("Opcion: ");
            String opcion = scanner.nextLine();

            if (opcion.equals("3")) {
                PersistenciaManager.guardarTodo(cuentas, productos, menu);
                System.out.println("Hasta luego.");
                ejecutando = false;
            } else if (opcion.equals("1")) {
                System.out.print("Usuario: ");
                String usuario = scanner.nextLine();
                System.out.print("Contrasena: ");
                String contrasena = scanner.nextLine();

                CuentaUsuario sesion = null;
                for (int i = 0; i < cuentas.size(); i++) {
                    CuentaUsuario cuenta = cuentas.get(i);
                    if (cuenta.getUsuario().equals(usuario) && cuenta.getContraseña().equals(contrasena)) {
                        sesion = cuenta;
                    }
                }

                if (sesion == null) {
                    System.out.println("Error: Usuario o contrasena incorrectos");
                } else {
                    System.out.println("Sesion iniciada como: " + sesion.getNombre());
                    System.out.println("Rol: " + sesion.getRol());
                    if (sesion.getRol() == Rol.ADMINISTRADOR) {
                        menuAdministrador((Administrador) sesion, cuentas, productos, torneos);
                    } else if (sesion.getRol() == Rol.EMPLEADO) {
                        menuEmpleado((Empleado) sesion, cuentas);
                    } else if (sesion.getRol() == Rol.CLIENTE) {
                        menuCliente((Cliente) sesion, cuentas, productos, torneos);
                    }
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
    
    // DATOS INICIALES 
    
    private static void datosInicialesAdministrador(ArrayList<CuentaUsuario> cuentas) {
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
            System.out.println("[DATOS INICIALES] Administrador creado: admin / admin123");
        }
    }
    
    private static void datosInicialesEmpleado(ArrayList<CuentaUsuario> cuentas) {
        boolean existeMesero = false;
        boolean existeCocinero = false;
        for (int i = 0; i < cuentas.size(); i++) {
            if (cuentas.get(i) instanceof Empleado) {
                Empleado e = (Empleado) cuentas.get(i);
                if (e.getTipoEmpleado() == TipoEmpleado.MESERO) {
                    existeMesero = true;
                } else if (e.getTipoEmpleado() == TipoEmpleado.COCINERO) {
                    existeCocinero = true;
                }
            }
        }
        
        // TURNOS PARA EMPLEADOS
        ArrayList<Turno> turnosMesero = new ArrayList<>();
        turnosMesero.add(new Turno("Lunes", "08:00", "16:00", "2024-01-01"));
        turnosMesero.add(new Turno("Martes", "08:00", "16:00", "2024-01-02"));
        turnosMesero.add(new Turno("Miercoles", "08:00", "16:00", "2024-01-03"));
        turnosMesero.add(new Turno("Jueves", "08:00", "16:00", "2024-01-04"));
        turnosMesero.add(new Turno("Viernes", "08:00", "16:00", "2024-01-05"));
        
        ArrayList<Turno> turnosCocinero = new ArrayList<>();
        turnosCocinero.add(new Turno("Lunes", "14:00", "22:00", "2024-01-01"));
        turnosCocinero.add(new Turno("Martes", "14:00", "22:00", "2024-01-02"));
        turnosCocinero.add(new Turno("Miercoles", "14:00", "22:00", "2024-01-03"));
        turnosCocinero.add(new Turno("Jueves", "14:00", "22:00", "2024-01-04"));
        turnosCocinero.add(new Turno("Viernes", "14:00", "22:00", "2024-01-05"));
        
        if (!existeMesero) {
            Mesero mesero = new Mesero(
                "Mesero Principal", 100, "mesero", "123", Rol.EMPLEADO,
                turnosMesero, TipoEmpleado.MESERO, false,
                new ArrayList<>(), new ArrayList<>(), null, new ArrayList<>()
            );
            cuentas.add(mesero);
            System.out.println("[DATOS INICIALES] Mesero creado: mesero / 123 - Turnos: Lunes a Viernes 8am-4pm");
        } else {
            for (int i = 0; i < cuentas.size(); i++) {
                if (cuentas.get(i) instanceof Mesero) {
                    Mesero m = (Mesero) cuentas.get(i);
                    if (m.getTurnos().isEmpty()) {
                        for (int j = 0; j < turnosMesero.size(); j++) {
                            m.getTurnos().add(turnosMesero.get(j));
                        }
                        System.out.println("[DATOS INICIALES] Turnos asignados al mesero existente");
                    }
                }
            }
        }
        
        if (!existeCocinero) {
            Cocinero cocinero = new Cocinero(
                "Cocinero Principal", 101, "cocinero", "123", Rol.EMPLEADO,
                turnosCocinero, TipoEmpleado.COCINERO, false, new ArrayList<>()
            );
            cuentas.add(cocinero);
            System.out.println("[DATOS INICIALES] Cocinero creado: cocinero / 123 - Turnos: Lunes a Viernes 2pm-10pm");
        } else {
            for (int i = 0; i < cuentas.size(); i++) {
                if (cuentas.get(i) instanceof Cocinero) {
                    Cocinero c = (Cocinero) cuentas.get(i);
                    if (c.getTurnos().isEmpty()) {
                        for (int j = 0; j < turnosCocinero.size(); j++) {
                            c.getTurnos().add(turnosCocinero.get(j));
                        }
                        System.out.println("[DATOS INICIALES] Turnos asignados al cocinero existente");
                    }
                }
            }
        }
    }
    
    private static void datosInicialesCliente(ArrayList<CuentaUsuario> cuentas) {
        boolean existeCliente = false;
        for (int i = 0; i < cuentas.size(); i++) {
            if (cuentas.get(i).getRol() == Rol.CLIENTE) {
                existeCliente = true;
            }
        }
        
        if (!existeCliente) {
            Cliente cliente1 = new Cliente(
                "Juan", 200, "juan", "123", Rol.CLIENTE,
                "juan@mail.com", 12345678, "Perez",
                new ArrayList<>(), null, null, false
            );
            cuentas.add(cliente1);
            
            Cliente cliente2 = new Cliente(
                "Maria", 201, "maria", "123", Rol.CLIENTE,
                "maria@mail.com", 87654321, "Gomez",
                new ArrayList<>(), null, null, false
            );
            cuentas.add(cliente2);
            
            System.out.println("[DATOS INICIALES] Clientes creados: juan/123, maria/123");
        }
    }
    
    private static void datosInicialesJuegos(ArrayList<Producto> productos) {
        boolean hayJuegos = false;
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getTipoProducto() == TipoProducto.JUEGOMESAPRESTAMO ||
                productos.get(i).getTipoProducto() == TipoProducto.JUEGOMESAVENTA) {
                hayJuegos = true;
            }
        }
        
        if (!hayJuegos) {
            int id = 100;
            
            Caracteristicas c1 = new Caracteristicas(Categoria.ESTRATEGIA, id);
            c1.setNombre("Catan");
            c1.setNumeroJugadores(4);
            c1.setRestriccionEdad(10);
            c1.setEstado("Bueno");
            Producto j1 = new Producto(50000, "Catan", "Construye colonias", false, false, new ArrayList<>(), TipoProducto.JUEGOMESAPRESTAMO, id, c1, 3, false);
            j1.setCantidadDisponible(3);
            productos.add(j1);
            id++;
            
            Caracteristicas c2 = new Caracteristicas(Categoria.ESTRATEGIA, id);
            c2.setNombre("Ajedrez");
            c2.setNumeroJugadores(2);
            c2.setRestriccionEdad(6);
            c2.setEstado("Excelente");
            Producto j2 = new Producto(30000, "Ajedrez", "Juego de estrategia", false, false, new ArrayList<>(), TipoProducto.JUEGOMESAPRESTAMO, id, c2, 5, false);
            j2.setCantidadDisponible(5);
            productos.add(j2);
            id++;
            
            Caracteristicas c3 = new Caracteristicas(Categoria.CARTAS, id);
            c3.setNombre("Dixit");
            c3.setNumeroJugadores(6);
            c3.setRestriccionEdad(8);
            c3.setEstado("Bueno");
            Producto j3 = new Producto(45000, "Dixit", "Juego de imaginacion", false, false, new ArrayList<>(), TipoProducto.JUEGOMESAPRESTAMO, id, c3, 4, false);
            j3.setCantidadDisponible(4);
            productos.add(j3);
            id++;
            
            Caracteristicas c4 = new Caracteristicas(Categoria.ACCION, id);
            c4.setNombre("Jenga");
            c4.setNumeroJugadores(8);
            c4.setRestriccionEdad(5);
            c4.setEstado("Regular");
            Producto j4 = new Producto(25000, "Jenga", "Torre de bloques", false, false, new ArrayList<>(), TipoProducto.JUEGOMESAPRESTAMO, id, c4, 3, false);
            j4.setCantidadDisponible(3);
            productos.add(j4);
            id++;
            
            Caracteristicas c5 = new Caracteristicas(Categoria.TABLERO, id);
            c5.setNombre("Ticket to Ride");
            c5.setNumeroJugadores(5);
            c5.setRestriccionEdad(8);
            c5.setEstado("Excelente");
            Producto j5 = new Producto(65000, "Ticket to Ride", "Construye rutas", false, false, new ArrayList<>(), TipoProducto.JUEGOMESAPRESTAMO, id, c5, 2, false);
            j5.setCantidadDisponible(2);
            productos.add(j5);
            id++;
            
            Caracteristicas c6 = new Caracteristicas(Categoria.TABLERO, id);
            c6.setNombre("Monopoly");
            c6.setNumeroJugadores(6);
            c6.setRestriccionEdad(8);
            c6.setEstado("Nuevo");
            Producto j6 = new Producto(60000, "Monopoly", "Compra propiedades", false, false, new ArrayList<>(), TipoProducto.JUEGOMESAVENTA, id, c6, 2, false);
            j6.setCantidadDisponible(2);
            productos.add(j6);
            
            System.out.println("[DATOS INICIALES] Creados 6 juegos de mesa");
            PersistenciaManager.guardarProductos(productos);
            PersistenciaManager.guardarCaracteristicas(productos);
        }
    }
    
    private static void datosInicialesComida(ArrayList<Producto> productos) {
        boolean hayComida = false;
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getTipoProducto() == TipoProducto.BEBIDA || 
                productos.get(i).getTipoProducto() == TipoProducto.PASTELERIA) {
                hayComida = true;
            }
        }
        
        if (!hayComida) {
            int id = 1;
            ArrayList<String> alergenosVacio = new ArrayList<>();
            
            productos.add(new Producto(3000, "Cafe Americano", "Cafe negro", false, true, alergenosVacio, TipoProducto.BEBIDA, id++));
            productos.add(new Producto(4000, "Capuchino", "Cafe con leche", false, true, alergenosVacio, TipoProducto.BEBIDA, id++));
            productos.add(new Producto(5000, "Te Verde", "Te relajante", false, true, alergenosVacio, TipoProducto.BEBIDA, id++));
            productos.add(new Producto(6000, "Jugo Natural", "Jugo de frutas", false, false, alergenosVacio, TipoProducto.BEBIDA, id++));
            productos.add(new Producto(8000, "Cerveza", "Cerveza artesanal", true, false, alergenosVacio, TipoProducto.BEBIDA, id++));
            
            ArrayList<String> gluten = new ArrayList<>();
            gluten.add("gluten");
            ArrayList<String> lacteos = new ArrayList<>();
            lacteos.add("lacteos");
            
            productos.add(new Producto(5000, "Croissant", "Hojaldre de mantequilla", false, false, gluten, TipoProducto.PASTELERIA, id++));
            productos.add(new Producto(7000, "Torta Chocolate", "Torta humeda", false, false, lacteos, TipoProducto.PASTELERIA, id++));
            productos.add(new Producto(6000, "Galletas", "Galletas de avena", false, false, gluten, TipoProducto.PASTELERIA, id++));
            
            System.out.println("[DATOS INICIALES] Creados 8 productos de comida");
            PersistenciaManager.guardarProductos(productos);
        }
    }
    
    private static void datosInicialesTorneos(ArrayList<Torneo> torneos) {
        if (torneos.isEmpty()) {
            TorneoCompetitivo t1 = new TorneoCompetitivo("Competitivo - Catan", "2024-12-15", null, 16, 5000, "Trofeo + $200000");
            TorneoAmistoso t2 = new TorneoAmistoso("Amistoso - D&D", "2024-12-20", null, 8, 15);
            TorneoCompetitivo t3 = new TorneoCompetitivo("Competitivo - Ajedrez", "2024-12-25", null, 16, 3000, "Medalla + $100000");
            
            torneos.add(t1);
            torneos.add(t2);
            torneos.add(t3);
            
            System.out.println("[DATOS INICIALES] Creados 3 torneos");
        }
    }
    
    private static ArrayList<Mesa> datosInicialesMesas() {
        ArrayList<Mesa> mesas = new ArrayList<>();
        mesas.add(new Mesa(1, 2, true));
        mesas.add(new Mesa(2, 4, true));
        mesas.add(new Mesa(3, 4, true));
        mesas.add(new Mesa(4, 6, true));
        mesas.add(new Mesa(5, 2, true));
        mesas.add(new Mesa(6, 4, true));
        mesas.add(new Mesa(7, 6, true));
        mesas.add(new Mesa(8, 8, true));
        System.out.println("[DATOS INICIALES] Creadas 8 mesas");
        return mesas;
    }
    
    static void crearCuentaNueva(ArrayList<CuentaUsuario> cuentas) {
        System.out.println("\n=== CREAR NUEVA CUENTA CLIENTE ===");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Cedula: ");
        String cedulaStr = scanner.nextLine();
        int cedula = 0;
        if (cedulaStr.matches("\\d+")) {
            cedula = Integer.parseInt(cedulaStr);
        }
        System.out.print("Email: ");
        String email = scanner.nextLine();
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
        
        Cliente nuevo = new Cliente(nombre, maxId + 1, usuario, contrasena, Rol.CLIENTE, email, cedula, apellido, new ArrayList<>(), null, null, false);
        cuentas.add(nuevo);
        System.out.println("Cuenta creada exitosamente");
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
            Mesero m = new Mesero(nombre, maxId + 1, usuario, contrasena, Rol.EMPLEADO, new ArrayList<>(), TipoEmpleado.MESERO, false, new ArrayList<>(), new ArrayList<>(), null, new ArrayList<>());
            cuentas.add(m);
            System.out.println("Mesero creado");
        } else {
            Cocinero c = new Cocinero(nombre, maxId + 1, usuario, contrasena, Rol.EMPLEADO, new ArrayList<>(), TipoEmpleado.COCINERO, false, new ArrayList<>());
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
        System.out.print("Fecha (YYYY-MM-DD): ");
        String fecha = scanner.nextLine();
        System.out.print("Cupos totales: ");
        String cuposStr = scanner.nextLine();
        int cupos = 0;
        if (cuposStr.matches("\\d+")) {
            cupos = Integer.parseInt(cuposStr);
        }
        
        if (tipo.equals("1")) {
            System.out.print("Tarifa de inscripcion: ");
            String tarifaStr = scanner.nextLine();
            int tarifa = 0;
            if (tarifaStr.matches("\\d+")) {
                tarifa = Integer.parseInt(tarifaStr);
            }
            System.out.print("Premio: ");
            String premio = scanner.nextLine();
            TorneoCompetitivo t = new TorneoCompetitivo(nombre, fecha, null, cupos, tarifa, premio);
            torneos.add(t);
            System.out.println("Torneo Competitivo creado");
        } else if (tipo.equals("2")) {
            System.out.print("Bono descuento (%): ");
            String bonoStr = scanner.nextLine();
            int bono = 0;
            if (bonoStr.matches("\\d+")) {
                bono = Integer.parseInt(bonoStr);
            }
            TorneoAmistoso t = new TorneoAmistoso(nombre, fecha, null, cupos, bono);
            torneos.add(t);
            System.out.println("Torneo Amistoso creado");
        }
    }
    
    //  MENU ADMINISTRADOR 
    
    static void menuAdministrador(Administrador admin, ArrayList<CuentaUsuario> cuentas, ArrayList<Producto> productos, ArrayList<Torneo> torneos) throws Exception {
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
                for (int i = 0; i < torneos.size(); i++) {
                    Torneo t = torneos.get(i);
                    System.out.println((i+1) + ". " + t.getNombre() + " | " + t.getFecha() + " | " + t.getPremio());
                }
            } else if (opcion.equals("14")) {
                crearTorneo(torneos);
            } else {
                System.out.println("Opcion invalida");
            }
        }
    }
    
    // GESTIONAR SOLICITUDES TURNO 
    
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
            System.out.println("No hay solicitudes pendientes");
            return;
        }
        
        System.out.println("\n=== SOLICITUDES DE TURNO ===");
        for (int i = 0; i < todas.size(); i++) {
            SolicitudTurno s = todas.get(i);
            String tipoInfo = "";
            if (s.getTipoSolicitud() == TipoSolicitud.CAMBIO) {
                tipoInfo = "CAMBIO GENERAL";
            } else {
                tipoInfo = "INTERCAMBIO con " + s.getOtroEmpleado().getNombre();
            }
            System.out.println((i+1) + ". " + s.getEmpleadoSolicitante().getNombre() + " | " + tipoInfo + " | Estado: " + s.getEstado());
        }
        
        System.out.print("Seleccione numero (0 salir): ");
        String input = scanner.nextLine();
        boolean esNumero = true;
        for (int j = 0; j < input.length(); j++) {
            if (!Character.isDigit(input.charAt(j))) {
                esNumero = false;
            }
        }
        
        if (esNumero) {
            int idx = Integer.parseInt(input);
            if (idx > 0 && idx <= todas.size()) {
                SolicitudTurno seleccionada = todas.get(idx - 1);
                
                System.out.println("1. Aprobar");
                System.out.println("2. Rechazar");
                System.out.print("Opcion: ");
                String accion = scanner.nextLine();
                
                if (accion.equals("1")) {
                    if (seleccionada.getTipoSolicitud() == TipoSolicitud.CAMBIO) {
                        Empleado empleado = seleccionada.getEmpleadoSolicitante();
                        Turno turnoActual = seleccionada.getTurnoActual();
                        Turno turnoNuevo = seleccionada.getTurnoDeseado();
                        
                        ArrayList<Turno> turnosEmpleado = empleado.getTurnos();
                        for (int i = 0; i < turnosEmpleado.size(); i++) {
                            if (turnosEmpleado.get(i).equals(turnoActual)) {
                                turnosEmpleado.set(i, turnoNuevo);
                            }
                        }
                        admin.aprobarSolicitud(seleccionada);
                        System.out.println("Solicitud de CAMBIO GENERAL aprobada");
                    } else {
                        Empleado emp1 = seleccionada.getEmpleadoSolicitante();
                        Empleado emp2 = seleccionada.getOtroEmpleado();
                        Turno turnoEmp1 = seleccionada.getTurnoActual();
                        Turno turnoEmp2 = seleccionada.getTurnoDeseado();
                        
                        ArrayList<Turno> turnosEmp1 = emp1.getTurnos();
                        ArrayList<Turno> turnosEmp2 = emp2.getTurnos();
                        
                        for (int i = 0; i < turnosEmp1.size(); i++) {
                            if (turnosEmp1.get(i).equals(turnoEmp1)) {
                                turnosEmp1.set(i, turnoEmp2);
                            }
                        }
                        for (int i = 0; i < turnosEmp2.size(); i++) {
                            if (turnosEmp2.get(i).equals(turnoEmp2)) {
                                turnosEmp2.set(i, turnoEmp1);
                            }
                        }
                        
                        admin.aprobarSolicitud(seleccionada);
                        System.out.println("Solicitud de INTERCAMBIO aprobada");
                    }
                } else if (accion.equals("2")) {
                    admin.rechazarSolicitud(seleccionada);
                    System.out.println("Solicitud rechazada");
                }
            }
        }
    }
    
    // MENU EMPLEADO 
    
    static void menuEmpleado(Empleado empleado, ArrayList<CuentaUsuario> cuentas) {
        boolean enMenu = true;
        while (enMenu) {
            System.out.println("\n=== MENU EMPLEADO ===");
            System.out.println("1. Ver mis turnos");
            System.out.println("2. Solicitar cambio general de turno");
            System.out.println("3. Solicitar intercambio de turno");
            System.out.println("4. Ver mesas que atiendo (Mesero)");
            System.out.println("5. Comprar producto");
            System.out.println("6. Pedir juego prestado");
            System.out.println("7. Salir");
            System.out.print("Opcion: ");
            String opcion = scanner.nextLine();

            if (opcion.equals("7")) {
                enMenu = false;
            } else if (opcion.equals("1")) {
                ArrayList<Turno> turnos = empleado.getTurnos();
                if (turnos.isEmpty()) {
                    System.out.println("No tienes turnos");
                } else {
                    for (int i = 0; i < turnos.size(); i++) {
                        Turno t = turnos.get(i);
                        System.out.println((i+1) + ". " + t.getDiaSemana() + " " + t.getHoraInicio() + "-" + t.getHoraSalida());
                    }
                }
            } else if (opcion.equals("2")) {
                ArrayList<Turno> turnos = empleado.getTurnos();
                if (turnos.isEmpty()) {
                    System.out.println("No tienes turnos");
                } else {
                    for (int i = 0; i < turnos.size(); i++) {
                        Turno t = turnos.get(i);
                        System.out.println(i + ". " + t.getDiaSemana() + " " + t.getHoraInicio() + "-" + t.getHoraSalida());
                    }
                    System.out.print("Seleccione turno: ");
                    String input = scanner.nextLine();
                    int idx = -1;
                    boolean esNumero = true;
                    for (int j = 0; j < input.length(); j++) {
                        if (!Character.isDigit(input.charAt(j))) esNumero = false;
                    }
                    if (esNumero) idx = Integer.parseInt(input);
                    
                    if (idx >= 0 && idx < turnos.size()) {
                        Turno actual = turnos.get(idx);
                        System.out.print("Nueva hora inicio: ");
                        String nuevaHoraI = scanner.nextLine();
                        System.out.print("Nueva hora fin: ");
                        String nuevaHoraF = scanner.nextLine();
                        Turno nuevo = new Turno(actual.getDiaSemana(), nuevaHoraI, nuevaHoraF, actual.getFecha());
                        empleado.solicitarCambioTurnoGeneral(actual, nuevo);
                        System.out.println("Solicitud enviada");
                    }
                }
            } else if (opcion.equals("3")) {
                System.out.print("Usuario del otro empleado: ");
                String otroUser = scanner.nextLine();
                Empleado otro = null;
                for (int i = 0; i < cuentas.size(); i++) {
                    if (cuentas.get(i) instanceof Empleado && cuentas.get(i).getUsuario().equals(otroUser)) {
                        otro = (Empleado) cuentas.get(i);
                    }
                }
                if (otro == null) {
                    System.out.println("Empleado no encontrado");
                } else {
                    System.out.println("\nTUS TURNOS:");
                    ArrayList<Turno> misTurnos = empleado.getTurnos();
                    for (int i = 0; i < misTurnos.size(); i++) {
                        Turno t = misTurnos.get(i);
                        System.out.println(i + ". " + t.getDiaSemana() + " " + t.getHoraInicio() + "-" + t.getHoraSalida());
                    }
                    System.out.print("Selecciona tu turno: ");
                    String input1 = scanner.nextLine();
                    int idx1 = -1;
                    boolean esNum1 = true;
                    for (int j = 0; j < input1.length(); j++) {
                        if (!Character.isDigit(input1.charAt(j))) esNum1 = false;
                    }
                    if (esNum1) idx1 = Integer.parseInt(input1);
                    
                    System.out.println("\nTURNOS DE " + otro.getNombre() + ":");
                    ArrayList<Turno> susTurnos = otro.getTurnos();
                    for (int i = 0; i < susTurnos.size(); i++) {
                        Turno t = susTurnos.get(i);
                        System.out.println(i + ". " + t.getDiaSemana() + " " + t.getHoraInicio() + "-" + t.getHoraSalida());
                    }
                    System.out.print("Selecciona su turno: ");
                    String input2 = scanner.nextLine();
                    int idx2 = -1;
                    boolean esNum2 = true;
                    for (int j = 0; j < input2.length(); j++) {
                        if (!Character.isDigit(input2.charAt(j))) esNum2 = false;
                    }
                    if (esNum2) idx2 = Integer.parseInt(input2);
                    
                    if (idx1 >= 0 && idx1 < misTurnos.size() && idx2 >= 0 && idx2 < susTurnos.size()) {
                        empleado.solicitarIntercambioTurno(empleado, otro, misTurnos.get(idx1), susTurnos.get(idx2));
                        System.out.println("Solicitud de intercambio enviada");
                    }
                }
            } else if (opcion.equals("4")) {
                if (empleado instanceof Mesero) {
                    Mesero mesero = (Mesero) empleado;
                    ArrayList<Mesa> mesasAtendiendo = mesero.getMesasAtendiendo();
                    if (mesasAtendiendo.isEmpty()) {
                        System.out.println("No estas atendiendo ninguna mesa");
                    } else {
                        System.out.println("\n=== MESAS QUE ATIENDES ===");
                        for (int i = 0; i < mesasAtendiendo.size(); i++) {
                            Mesa m = mesasAtendiendo.get(i);
                            System.out.println("Mesa #" + m.getNumeroMesa() + " | Capacidad: " + m.getCapacidad() + " | Disponible: " + m.isDisponible());
                        }
                    }
                } else {
                    System.out.println("Opcion solo para meseros");
                }
            } else if (opcion.equals("5")) {
                System.out.println("\n=== PRODUCTOS DISPONIBLES ===");
                for (int i = 0; i < productosComida.size(); i++) {
                    Producto p = productosComida.get(i);
                    System.out.println("- " + p.getNombreProducto() + " | $" + p.getPrecio());
                }
                System.out.print("Nombre producto a comprar: ");
                String nombre = scanner.nextLine();
                Producto seleccionado = null;
                for (int i = 0; i < productosComida.size(); i++) {
                    if (productosComida.get(i).getNombreProducto().equalsIgnoreCase(nombre)) {
                        seleccionado = productosComida.get(i);
                    }
                }
                if (seleccionado != null) {
                    ArrayList<Producto> lista = new ArrayList<>();
                    lista.add(seleccionado);
                    empleado.realizarCompra(lista, empleado, 0, null);
                    System.out.println("Compra realizada");
                } else {
                    System.out.println("Producto no encontrado");
                }
            } else if (opcion.equals("6")) {
                if (inventarioPrestamoGlobal.getProductos().isEmpty()) {
                    System.out.println("No hay juegos disponibles");
                } else {
                    for (int i = 0; i < inventarioPrestamoGlobal.getProductos().size(); i++) {
                        Producto p = inventarioPrestamoGlobal.getProductos().get(i);
                        System.out.println("- " + p.getNombreProducto() + " | Disponibles: " + p.getCantidadDisponible());
                    }
                    System.out.print("Nombre juego a pedir prestado: ");
                    String nombre = scanner.nextLine();
                    Producto seleccionado = null;
                    for (int i = 0; i < inventarioPrestamoGlobal.getProductos().size(); i++) {
                        if (inventarioPrestamoGlobal.getProductos().get(i).getNombreProducto().equalsIgnoreCase(nombre)) {
                            seleccionado = inventarioPrestamoGlobal.getProductos().get(i);
                        }
                    }
                    if (seleccionado != null) {
                        empleado.prestarJuego(seleccionado, empleado);
                        System.out.println("Juego prestado");
                    } else {
                        System.out.println("Juego no encontrado");
                    }
                }
            } else {
                System.out.println("Opcion invalida");
            }
        }
    }
    
    static ArrayList<Producto> productosComida = new ArrayList<>();
    
    // ========== MENU CLIENTE ==========
    
    static void menuCliente(Cliente cliente, ArrayList<CuentaUsuario> cuentas, ArrayList<Producto> productos, ArrayList<Torneo> torneos) throws EmpleadoEnTurnoException, CuposInsuficientesException, InscripcionExcedidaException {
        boolean enMenu = true;
        
        // Cargar productos de comida
        productosComida.clear();
        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);
            if (p.getTipoProducto() != TipoProducto.JUEGOMESAPRESTAMO && p.getTipoProducto() != TipoProducto.JUEGOMESAVENTA) {
                productosComida.add(p);
            }
        }
        
        while (enMenu) {
            System.out.println("\n=== MENU CLIENTE ===");
            System.out.println("1. Pedir juego prestado");
            System.out.println("2. Ver favoritos");
            System.out.println("3. Agregar favorito");
            System.out.println("4. Solicitar producto");
            System.out.println("5. Ver solicitudes pendientes");
            System.out.println("6. Comprar juego");
            System.out.println("7. Torneos");
            System.out.println("8. Reservas");
            System.out.println("9. Salir");
            System.out.print("Opcion: ");
            String opcion = scanner.nextLine();

            if (opcion.equals("9")) {
                enMenu = false;
            } else if (opcion.equals("1")) {
                if (inventarioPrestamoGlobal.getProductos().isEmpty()) {
                    System.out.println("No hay juegos");
                } else {
                    for (int i = 0; i < inventarioPrestamoGlobal.getProductos().size(); i++) {
                        Producto p = inventarioPrestamoGlobal.getProductos().get(i);
                        System.out.println("- " + p.getNombreProducto() + " | Disponibles: " + p.getCantidadDisponible());
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
                    } else {
                        System.out.println("No encontrado");
                    }
                }
            } else if (opcion.equals("2")) {
                ArrayList<Producto> favs = cliente.getJuegosFavoritos();
                if (favs.isEmpty()) {
                    System.out.println("No tienes favoritos");
                } else {
                    for (int i = 0; i < favs.size(); i++) {
                        System.out.println("- " + favs.get(i).getNombreProducto());
                    }
                }
            } else if (opcion.equals("3")) {
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
                } else {
                    System.out.println("No encontrado");
                }
            } else if (opcion.equals("4")) {
                for (int i = 0; i < productosComida.size(); i++) {
                    Producto p = productosComida.get(i);
                    System.out.println("- " + p.getNombreProducto() + " | $" + p.getPrecio());
                }
                System.out.print("Nombre producto: ");
                String nombre = scanner.nextLine();
                Producto seleccionado = null;
                for (int i = 0; i < productosComida.size(); i++) {
                    if (productosComida.get(i).getNombreProducto().equalsIgnoreCase(nombre)) {
                        seleccionado = productosComida.get(i);
                    }
                }
                if (seleccionado != null) {
                    cliente.solicitarProducto(seleccionado);
                } else {
                    System.out.println("No encontrado");
                }
            } else if (opcion.equals("5")) {
                ArrayList<Producto> pendientes = cliente.getSolicitudesPendientes();
                if (pendientes.isEmpty()) {
                    System.out.println("No hay solicitudes");
                } else {
                    for (int i = 0; i < pendientes.size(); i++) {
                        System.out.println("- " + pendientes.get(i).getNombreProducto());
                    }
                }
            } else if (opcion.equals("6")) {
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
                    } else {
                        System.out.println("No encontrado");
                    }
                }
            } else if (opcion.equals("7")) {
                menuTorneosCliente(cliente, torneos);
            } else if (opcion.equals("8")) {
                menuReservasCliente(cliente);
            } else {
                System.out.println("Opcion invalida");
            }
        }
    }
    
    static void menuReservasCliente(Cliente cliente) {
        boolean enMenu = true;
        while (enMenu) {
            System.out.println("\n=== MIS RESERVAS ===");
            System.out.println("1. Crear reserva");
            System.out.println("2. Cancelar reserva");
            System.out.println("3. Consultar reservas");
            System.out.println("4. Volver");
            System.out.print("Opcion: ");
            String opcion = scanner.nextLine();
            
            if (opcion.equals("4")) {
                enMenu = false;
            } else if (opcion.equals("1")) {
                if (cafeteriaGlobal == null) {
                    System.out.println("Cafeteria no disponible");
                } else {
                    System.out.println("\n=== MESAS DISPONIBLES ===");
                    ArrayList<Mesa> mesasDisponibles = new ArrayList<>();
                    for (int i = 0; i < cafeteriaGlobal.getMesas().size(); i++) {
                        Mesa m = cafeteriaGlobal.getMesas().get(i);
                        if (m.isDisponible()) {
                            System.out.println("Mesa #" + m.getNumeroMesa() + " | Capacidad: " + m.getCapacidad());
                            mesasDisponibles.add(m);
                        }
                    }
                    
                    if (mesasDisponibles.isEmpty()) {
                        System.out.println("No hay mesas disponibles");
                    } else {
                        System.out.print("Numero de mesa: ");
                        String numMesaStr = scanner.nextLine();
                        int numMesa = 0;
                        if (numMesaStr.matches("\\d+")) {
                            numMesa = Integer.parseInt(numMesaStr);
                        }
                        
                        Mesa mesaSeleccionada = null;
                        for (int i = 0; i < mesasDisponibles.size(); i++) {
                            if (mesasDisponibles.get(i).getNumeroMesa() == numMesa) {
                                mesaSeleccionada = mesasDisponibles.get(i);
                            }
                        }
                        
                        if (mesaSeleccionada == null) {
                            System.out.println("Mesa no disponible");
                        } else {
                            System.out.print("Numero de personas: ");
                            String personasStr = scanner.nextLine();
                            int personas = 1;
                            if (personasStr.matches("\\d+")) {
                                personas = Integer.parseInt(personasStr);
                            }
                            System.out.print("Menores de edad: ");
                            String menoresStr = scanner.nextLine();
                            int menores = 0;
                            if (menoresStr.matches("\\d+")) {
                                menores = Integer.parseInt(menoresStr);
                            }
                            System.out.print("Menores de 5 anos: ");
                            String menores5Str = scanner.nextLine();
                            int menores5 = 0;
                            if (menores5Str.matches("\\d+")) {
                                menores5 = Integer.parseInt(menores5Str);
                            }
                            System.out.print("Hora (HH:MM): ");
                            String hora = scanner.nextLine();
                            System.out.print("Fecha (YYYY-MM-DD): ");
                            String fecha = scanner.nextLine();
                            
                            int idReserva = cliente.getReservas().size() + 1;
                            Reserva nueva = cliente.crearReserva(cafeteriaGlobal, personas, menores, menores5, idReserva, hora, "22:00", fecha, mesaSeleccionada);
                            if (nueva != null) {
                                System.out.println("Reserva creada exitosamente");
                            } else {
                                System.out.println("Error al crear reserva");
                            }
                        }
                    }
                }
            } else if (opcion.equals("2")) {
                if (cliente.getMiReserva() != null && cliente.getMiReserva().isActiva()) {
                    cliente.cancelarReserva(cliente.getMiReserva());
                    System.out.println("Reserva cancelada");
                } else {
                    System.out.println("No tienes una reserva activa");
                }
            } else if (opcion.equals("3")) {
                ArrayList<Reserva> reservas = cliente.getReservas();
                if (reservas.isEmpty()) {
                    System.out.println("No tienes reservas");
                } else {
                    System.out.println("\n=== MIS RESERVAS ===");
                    for (int i = 0; i < reservas.size(); i++) {
                        Reserva r = reservas.get(i);
                        System.out.println("Reserva #" + (i+1));
                        System.out.println("  ID: " + r.getId());
                        System.out.println("  Fecha: " + r.getFecha());
                        System.out.println("  Hora: " + r.getHoraInicio());
                        System.out.println("  Personas: " + r.getNumeroTotalPersonas());
                        System.out.println("  Mesa: #" + r.getMesa().getNumeroMesa());
                        System.out.println("  Activa: " + (r.isActiva() ? "SI" : "NO"));
                        System.out.println("---");
                    }
                }
            } else {
                System.out.println("Opcion invalida");
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
                    System.out.println((i+1) + ". " + t.getNombre() + " | Fecha: " + t.getFecha() + " | Premio: " + t.getPremio());
                }
            } else if (opcion.equals("2")) {
                System.out.print("Seleccione torneo: ");
                String input = scanner.nextLine();
                int idx = -1;
                boolean esNum = true;
                for (int j = 0; j < input.length(); j++) {
                    if (!Character.isDigit(input.charAt(j))) esNum = false;
                }
                if (esNum) idx = Integer.parseInt(input);
                if (idx > 0 && idx <= torneos.size()) {
                    Torneo t = torneos.get(idx - 1);
                    ArrayList<Integer> ids = new ArrayList<>();
                    ids.add(cliente.getId());
                    t.inscribirParticipante(cliente, ids);
                    cliente.inscribirEnTorneo(t);
                    System.out.println("Inscrito a: " + t.getNombre());
                }
            } else if (opcion.equals("3")) {
                System.out.print("Seleccione torneo: ");
                String input = scanner.nextLine();
                int idx = -1;
                boolean esNum = true;
                for (int j = 0; j < input.length(); j++) {
                    if (!Character.isDigit(input.charAt(j))) esNum = false;
                }
                if (esNum) idx = Integer.parseInt(input);
                if (idx > 0 && idx <= torneos.size()) {
                    Torneo t = torneos.get(idx - 1);
                    t.eliminarInscripcion(cliente);
                    cliente.cancelarInscripcionTorneo(t);
                    System.out.println("Inscripcion cancelada");
                }
            } else if (opcion.equals("4")) {
                ArrayList<Torneo> misTorneos = cliente.getTorneosInscritos();
                if (misTorneos.isEmpty()) {
                    System.out.println("No estas inscrito en ningun torneo");
                } else {
                    for (int i = 0; i < misTorneos.size(); i++) {
                        System.out.println("- " + misTorneos.get(i).getNombre());
                    }
                }
            }
        }
    }
}