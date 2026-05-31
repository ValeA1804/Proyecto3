package InterfazGrafica.Empleado;

import javax.swing.*;
import java.util.ArrayList;
import Proyecto.Empleado;
import Proyecto.Mesero;
import Proyecto.Cocinero;
import Proyecto.Cafeteria;
import Proyecto.Mesa;
import Proyecto.Menu;
import Proyecto.Producto;
import Proyecto.Turno;
import Proyecto.InventarioJuegoVenta;
import Proyecto.InventarioJuegoPrestamo;
import Proyecto.CuentaUsuario;
import Proyecto.Enum.Rol;
import Proyecto.Enum.TipoEmpleado;
import Proyecto.Enum.TipoProducto;
import Proyecto.Enum.Categoria;

public class PruebaInterfazEmpleado {
    
    public static void main(String[] args) {
        
        // ========== CREAR TURNOS PARA EL EMPLEADO ==========
        ArrayList<Turno> turnos = new ArrayList<>();
        turnos.add(new Turno("Lunes", "08:00", "16:00", "2024-01-01"));
        turnos.add(new Turno("Martes", "08:00", "16:00", "2024-01-02"));
        turnos.add(new Turno("Miercoles", "08:00", "16:00", "2024-01-03"));
        turnos.add(new Turno("Jueves", "08:00", "16:00", "2024-01-04"));
        turnos.add(new Turno("Viernes", "08:00", "16:00", "2024-01-05"));
        
        // ========== CREAR EMPLEADO (MESERO) ==========
        Mesero empleado = new Mesero(
            "Carlos Mesero", 
            100, 
            "mesero", 
            "123", 
            Rol.EMPLEADO,
            turnos,
            TipoEmpleado.MESERO,
            false,
            new ArrayList<>(),
            new ArrayList<>(),
            null,
            new ArrayList<>()
        );
        
        System.out.println("=== EMPLEADO CREADO ===");
        System.out.println("Nombre: " + empleado.getNombre());
        System.out.println("Usuario: " + empleado.getUsuario());
        System.out.println("Contrasena: " + empleado.getContraseña());
        System.out.println("Tipo: " + empleado.getTipoEmpleado());
        System.out.println("Turnos: " + empleado.getTurnos().size());
        
        // ========== CREAR MESAS ==========
        ArrayList<Mesa> mesas = new ArrayList<>();
        mesas.add(new Mesa(1, 4, true));
        mesas.add(new Mesa(2, 4, true));
        mesas.add(new Mesa(3, 6, true));
        mesas.add(new Mesa(4, 2, true));
        mesas.add(new Mesa(5, 4, true));
        
        // Asignar mesas al mesero
        empleado.asignarMesa(mesas.get(0));
        empleado.asignarMesa(mesas.get(1));
        empleado.asignarMesa(mesas.get(2));
        
        System.out.println("Mesas asignadas: " + empleado.getMesasAtendiendo().size());
        
        // ========== CREAR PRODUCTOS DE COMIDA ==========
        ArrayList<Producto> productosMenu = new ArrayList<>();
        int id = 1;
        
        ArrayList<String> alergenosVacio = new ArrayList<>();
        productosMenu.add(new Producto(3000, "Cafe Americano", "Cafe negro", false, true, alergenosVacio, TipoProducto.BEBIDA, id++));
        productosMenu.add(new Producto(4000, "Capuchino", "Cafe con leche", false, true, alergenosVacio, TipoProducto.BEBIDA, id++));
        productosMenu.add(new Producto(5000, "Te Verde", "Te relajante", false, true, alergenosVacio, TipoProducto.BEBIDA, id++));
        productosMenu.add(new Producto(6000, "Jugo Natural", "Jugo de frutas", false, false, alergenosVacio, TipoProducto.BEBIDA, id++));
        
        ArrayList<String> gluten = new ArrayList<>();
        gluten.add("gluten");
        productosMenu.add(new Producto(5000, "Croissant", "Hojaldre de mantequilla", false, false, gluten, TipoProducto.PASTELERIA, id++));
        productosMenu.add(new Producto(7000, "Torta Chocolate", "Torta humeda", false, false, gluten, TipoProducto.PASTELERIA, id++));
        
        // ========== CREAR JUEGOS ==========
        ArrayList<Producto> juegosPrestamo = new ArrayList<>();
        ArrayList<Producto> juegosVenta = new ArrayList<>();
        
        CaracteristicasJuego c1 = new CaracteristicasJuego("Catan", 2015, "Kosmos", 4, Categoria.ESTRATEGIA, "Bueno", 10, false);
        Producto j1 = new Producto(50000, "Catan", "Construye colonias", false, false, alergenosVacio, TipoProducto.JUEGOMESAPRESTAMO, id++);
        juegosPrestamo.add(j1);
        
        CaracteristicasJuego c2 = new CaracteristicasJuego("Ajedrez", 1000, "Varios", 2, Categoria.ESTRATEGIA, "Excelente", 6, false);
        Producto j2 = new Producto(30000, "Ajedrez", "Juego de estrategia", false, false, alergenosVacio, TipoProducto.JUEGOMESAPRESTAMO, id++);
        juegosPrestamo.add(j2);
        
        CaracteristicasJuego c3 = new CaracteristicasJuego("Monopoly", 1935, "Hasbro", 6, Categoria.TABLERO, "Nuevo", 8, false);
        Producto j3 = new Producto(60000, "Monopoly", "Compra propiedades", false, false, alergenosVacio, TipoProducto.JUEGOMESAVENTA, id++);
        juegosVenta.add(j3);
        
        CaracteristicasJuego c4 = new CaracteristicasJuego("Risk", 1957, "Hasbro", 6, Categoria.ESTRATEGIA, "Bueno", 12, false);
        Producto j4 = new Producto(55000, "Risk", "Conquista el mundo", false, false, alergenosVacio, TipoProducto.JUEGOMESAVENTA, id++);
        juegosVenta.add(j4);
        
        System.out.println("Productos menu: " + productosMenu.size());
        System.out.println("Juegos prestamo: " + juegosPrestamo.size());
        System.out.println("Juegos venta: " + juegosVenta.size());
        
        // ========== CREAR INVENTARIOS ==========
        InventarioJuegoVenta inventarioVenta = new InventarioJuegoVenta(juegosVenta);
        InventarioJuegoPrestamo inventarioPrestamo = new InventarioJuegoPrestamo(juegosPrestamo);
        
        // ========== CREAR MENU ==========
        Menu menu = new Menu(productosMenu, inventarioPrestamo);
        
        // ========== CREAR CAFETERIA ==========
        Cafeteria cafeteria = new Cafeteria("Bogota", "Calle 123", mesas, new ArrayList<>(), 50, new ArrayList<>(), menu);
        
        // ========== LISTA DE CUENTAS (para intercambio de turnos) ==========
        ArrayList<CuentaUsuario> cuentas = new ArrayList<>();
        cuentas.add(empleado);
        
        // Crear otro empleado para probar intercambio
        ArrayList<Turno> turnos2 = new ArrayList<>();
        turnos2.add(new Turno("Lunes", "14:00", "22:00", "2024-01-01"));
        turnos2.add(new Turno("Martes", "14:00", "22:00", "2024-01-02"));
        
        Cocinero otroEmpleado = new Cocinero(
            "Laura Cocinera", 
            101, 
            "cocinera", 
            "123", 
            Rol.EMPLEADO,
            turnos2,
            TipoEmpleado.COCINERO,
            false,
            new ArrayList<>()
        );
        cuentas.add(otroEmpleado);
        
        System.out.println("\n=== OTRO EMPLEADO CREADO PARA INTERCAMBIO ===");
        System.out.println("Nombre: " + otroEmpleado.getNombre());
        System.out.println("Turnos: " + otroEmpleado.getTurnos().size());
        
        // ========== EJECUTAR INTERFAZ ==========
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                InterfazEmpleado interfaz = new InterfazEmpleado(
                    empleado, cafeteria, inventarioVenta, inventarioPrestamo, menu, cuentas
                );
                interfaz.setVisible(true);
            }
        });
    }
}

// Clase auxiliar para crear caracteristicas de juegos
class CaracteristicasJuego {
    private String nombre;
    private int anioPublicacion;
    private String empresaMatriz;
    private int numeroJugadores;
    private Categoria categoria;
    private String estado;
    private int restriccionEdad;
    private boolean dificil;
    
    public CaracteristicasJuego(String nombre, int anioPublicacion, String empresaMatriz, 
            int numeroJugadores, Categoria categoria, String estado, int restriccionEdad, boolean dificil) {
        this.nombre = nombre;
        this.anioPublicacion = anioPublicacion;
        this.empresaMatriz = empresaMatriz;
        this.numeroJugadores = numeroJugadores;
        this.categoria = categoria;
        this.estado = estado;
        this.restriccionEdad = restriccionEdad;
        this.dificil = dificil;
    }
    
    public String getNombre() { return nombre; }
    public int getAnioPublicacion() { return anioPublicacion; }
    public String getEmpresaMatriz() { return empresaMatriz; }
    public int getNumeroJugadores() { return numeroJugadores; }
    public Categoria getCategoria() { return categoria; }
    public String getEstado() { return estado; }
    public int getRestriccionEdad() { return restriccionEdad; }
    public boolean isDificil() { return dificil; }
}