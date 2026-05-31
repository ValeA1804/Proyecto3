package Proyecto.consolas;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import Proyecto.Cocinero;
import Proyecto.CuentaUsuario;
import Proyecto.Empleado;
import Proyecto.Mesero;
import Proyecto.PersistenciaManager;
import Proyecto.Producto;
import Proyecto.Turno;
import Proyecto.Enum.*;

public class mainEmpleado {
    
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Producto> productosComida = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println(new File("Data").getAbsolutePath());
        new File("Data").mkdirs();

        ArrayList<CuentaUsuario> cuentas = PersistenciaManager.cargarCuentas();
        ArrayList<Producto> productos = PersistenciaManager.cargarProductos();
        
        // Cargar productos de comida
        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);
            if (p.getTipoProducto() == TipoProducto.BEBIDA || p.getTipoProducto() == TipoProducto.PASTELERIA) {
                productosComida.add(p);
            }
        }
        
        datosIniciales(cuentas);

        boolean ejecutando = true;
        while (ejecutando) {
            System.out.println("\n=== BOARD GAME CAFE - EMPLEADO ===");
            System.out.print("Usuario: ");
            String usuario = scanner.nextLine();
            System.out.print("Contrasena: ");
            String contrasena = scanner.nextLine();

            CuentaUsuario sesion = null;
            for (int i = 0; i < cuentas.size(); i++) {
                if (cuentas.get(i).getUsuario().equals(usuario) && 
                    cuentas.get(i).getContraseña().equals(contrasena) &&
                    cuentas.get(i).getRol() == Rol.EMPLEADO) {
                    sesion = cuentas.get(i);
                }
            }

            if (sesion == null) {
                System.out.println("Error: Usuario o contrasena incorrectos");
            } else {
                System.out.println("Bienvenido " + sesion.getNombre());
                menuEmpleado((Empleado) sesion, cuentas);
                ejecutando = false;
            }
        }
        scanner.close();
    }
    
    private static void datosIniciales(ArrayList<CuentaUsuario> cuentas) {
        boolean existeMesero = false;
        boolean existeCocinero = false;
        
        for (int i = 0; i < cuentas.size(); i++) {
            if (cuentas.get(i) instanceof Empleado) {
                Empleado e = (Empleado) cuentas.get(i);
                if (e.getTipoEmpleado() == TipoEmpleado.MESERO) existeMesero = true;
                if (e.getTipoEmpleado() == TipoEmpleado.COCINERO) existeCocinero = true;
            }
        }
        
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
            Mesero mesero = new Mesero("Mesero Principal", 100, "mesero", "123", Rol.EMPLEADO,
                                       turnosMesero, TipoEmpleado.MESERO, false,
                                       new ArrayList<>(), new ArrayList<>(), null, new ArrayList<>());
            cuentas.add(mesero);
            System.out.println("Mesero creado: mesero / 123");
        }
        
        if (!existeCocinero) {
            Cocinero cocinero = new Cocinero("Cocinero Principal", 101, "cocinero", "123", Rol.EMPLEADO,
                                             turnosCocinero, TipoEmpleado.COCINERO, false, new ArrayList<>());
            cuentas.add(cocinero);
            System.out.println("Cocinero creado: cocinero / 123");
        }
    }
    
    static void menuEmpleado(Empleado empleado, ArrayList<CuentaUsuario> cuentas) {
        boolean enMenu = true;
        while (enMenu) {
            System.out.println("\n=== MENU EMPLEADO ===");
            System.out.println("1. Ver mis turnos");
            System.out.println("2. Solicitar cambio general de turno");
            System.out.println("3. Solicitar intercambio de turno");
            System.out.println("4. Comprar producto");
            System.out.println("5. Salir");
            System.out.print("Opcion: ");
            String opcion = scanner.nextLine();

            if (opcion.equals("5")) {
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
                    if (input.matches("\\d+")) idx = Integer.parseInt(input);
                    
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
                    if (input1.matches("\\d+")) idx1 = Integer.parseInt(input1);
                    
                    System.out.println("\nTURNOS DE " + otro.getNombre() + ":");
                    ArrayList<Turno> susTurnos = otro.getTurnos();
                    for (int i = 0; i < susTurnos.size(); i++) {
                        Turno t = susTurnos.get(i);
                        System.out.println(i + ". " + t.getDiaSemana() + " " + t.getHoraInicio() + "-" + t.getHoraSalida());
                    }
                    System.out.print("Selecciona su turno: ");
                    String input2 = scanner.nextLine();
                    int idx2 = -1;
                    if (input2.matches("\\d+")) idx2 = Integer.parseInt(input2);
                    
                    if (idx1 >= 0 && idx1 < misTurnos.size() && idx2 >= 0 && idx2 < susTurnos.size()) {
                        empleado.solicitarIntercambioTurno(empleado, otro, misTurnos.get(idx1), susTurnos.get(idx2));
                        System.out.println("Solicitud de intercambio enviada");
                    }
                }
            } else if (opcion.equals("4")) {
                System.out.println("\n=== COMPRAR PRODUCTO ===");
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
                    ArrayList<Producto> lista = new ArrayList<>();
                    lista.add(seleccionado);
                    empleado.realizarCompra(lista, empleado, 0, null);
                    System.out.println("Compra realizada con 20% descuento");
                } else {
                    System.out.println("Producto no encontrado");
                }
            } else {
                System.out.println("Opcion invalida");
            }
        }
    }
}