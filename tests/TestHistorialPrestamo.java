import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import Proyecto.Administrador;
import Proyecto.Caracteristicas;
import Proyecto.Empleado;
import Proyecto.HistorialPrestamo;
import Proyecto.InventarioJuegoPrestamo;
import Proyecto.InventarioJuegoVenta;
import Proyecto.Mesero;
import Proyecto.Producto;
import Proyecto.Enum.*;

class TestHistorialPrestamo {

	private Administrador admin;
    private InventarioJuegoVenta inventarioVenta;
    private InventarioJuegoPrestamo inventarioPrestamo;
    private ArrayList<HistorialPrestamo> historial;
    private Producto juego;
    private Empleado empleado;
    private HistorialPrestamo historialCliente;
    private HistorialPrestamo historialEmpleado;
    private HistorialPrestamo historialDevuelto;
    private ArrayList<HistorialPrestamo> historialesCreados;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        inventarioVenta = new InventarioJuegoVenta(new ArrayList<>());
        inventarioPrestamo = new InventarioJuegoPrestamo(new ArrayList<>());
        historial = new ArrayList<>();
        admin = new Administrador(inventarioVenta, inventarioPrestamo, historial);
        historialesCreados = new ArrayList<>();

        Caracteristicas carac = new Caracteristicas(Categoria.TABLERO, 1);
        carac.setEstado("bueno");
        carac.setNombre("Catan");
        juego = new Producto(50000, "Catan", "Juego", false, false,
                             new ArrayList<>(), TipoProducto.JUEGOMESAPRESTAMO, 1, carac, 5, false);

        empleado = new Mesero("Pedro Mesero", 10, "pedro", "123", Rol.EMPLEADO,
                              new ArrayList<>(), TipoEmpleado.MESERO, false,
                              new ArrayList<>(), new ArrayList<>(), null, new ArrayList<>());

        historialCliente = new HistorialPrestamo("2024-01-01", null, "bueno", null, 0, juego);
        
        historialEmpleado = new HistorialPrestamo("2024-01-05", null, "bueno", null, 0, empleado, juego);
        
        historialDevuelto = new HistorialPrestamo("2024-01-10", "2024-01-12", "bueno", "bueno", 1, juego);

        historial.add(historialCliente);
        historial.add(historialEmpleado);
        historial.add(historialDevuelto);
        
        historialesCreados.add(historialCliente);
        historialesCreados.add(historialEmpleado);
        historialesCreados.add(historialDevuelto);

        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void limpiarHistorial() {
        historial.clear();
        for (HistorialPrestamo historial : historialesCreados) {
        	historial = null;
        }
        historialesCreados.clear();
        admin = null;
        juego = null;
        empleado = null;
        System.setOut(originalOut);
        outContent.reset();
    }

    //  TESTS CONSULTAR HISTORIAL 

    @Test
    void testConsultarHistorial() {
        admin.consultarHistorialPrestamos();
        
        String output = outContent.toString();
        
        assertTrue(output.contains("Juego: Catan"));
        assertTrue(output.contains("Fecha préstamo: 2024-01-01"));
        assertTrue(output.contains("Fecha préstamo: 2024-01-05"));
        assertTrue(output.contains("Fecha préstamo: 2024-01-10"));
    }

    @Test
    void testConsultarHistorialVacio() {
        historial.clear();
        
        admin.consultarHistorialPrestamos();
        
        String output = outContent.toString();
        assertTrue(output.contains("No hay préstamos registrados en el historial."));
    }

    @Test
    void testConsultarHistorialMuestraEstadoPrestado() {
        admin.consultarHistorialPrestamos();
        
        String output = outContent.toString();
        assertTrue(output.contains("ACTUALMENTE PRESTADO"));
    }

    @Test
    void testConsultarHistorialMuestraFechaDevolucionCuandoExiste() {
        admin.consultarHistorialPrestamos();
        
        String output = outContent.toString();
        assertTrue(output.contains("Fecha devolución: 2024-01-12"));
    }

    @Test
    void testConsultarHistorialMuestraEstadoAlDevolver() {
        admin.consultarHistorialPrestamos();
        
        String output = outContent.toString();
        assertTrue(output.contains("Estado al devolver: bueno"));
    }

    @Test
    void testConsultarHistorialMuestraVecesPrestado() {
        admin.consultarHistorialPrestamos();
        
        String output = outContent.toString();
        assertTrue(output.contains("Veces prestado:"));
    }

    @Test
    void testConsultarHistorialMuestraEmpleadoCuandoCorresponde() {
        admin.consultarHistorialPrestamos();
        
        String output = outContent.toString();
        assertTrue(output.contains("Préstamo realizado por empleado: Pedro Mesero"));
    }

    @Test
    void testConsultarHistorialNoMuestraEmpleadoEnPrestamoCliente() {
        admin.consultarHistorialPrestamos();
        
        String output = outContent.toString();
        int count = output.split("Préstamo realizado por empleado:").length - 1;
        assertEquals(1, count);
    }

    // TESTS CONSULTAR HISTORIAL CON MÚLTIPLES PRÉSTAMOS 

    @Test
    void testConsultarHistorialConMultiplesPrestamosMismoJuego() {
        HistorialPrestamo historialExtra = new HistorialPrestamo("2024-02-01", "2024-02-03", "bueno", "regular", 2, juego);
        historial.add(historialExtra);
        historialesCreados.add(historialExtra);
        
        admin.consultarHistorialPrestamos();
        
        String output = outContent.toString();
        assertTrue(output.contains("Fecha préstamo: 2024-02-01"));
        assertTrue(output.contains("Estado al devolver: regular"));
    }

    @Test
    void testConsultarHistorialConJuegoDiferente() {
        Caracteristicas carac2 = new Caracteristicas(Categoria.CARTAS, 2);
        carac2.setEstado("bueno");
        carac2.setNombre("Uno");
        Producto juego2 = new Producto(30000, "Uno", "Juego", false, false,
                                        new ArrayList<>(), TipoProducto.JUEGOMESAPRESTAMO, 2, carac2, 10, false);
        
        HistorialPrestamo historialOtroJuego = new HistorialPrestamo("2024-03-01", null, "bueno", null, 0, juego2);
        historial.add(historialOtroJuego);
        historialesCreados.add(historialOtroJuego);
        
        admin.consultarHistorialPrestamos();
        
        String output = outContent.toString();
        assertTrue(output.contains("Juego: Uno"));
        assertTrue(output.contains("Juego: Catan"));
    }

    //  TESTS DE REGISTRO DE PRÉSTAMO (HISTORIAL) 
    @Test
    void testRegistrarPrestamoCliente() {
        HistorialPrestamo nuevoHistorial = new HistorialPrestamo();
        
        nuevoHistorial.registrarPrestamo("bueno", juego);
        
        assertEquals("bueno", nuevoHistorial.getEstadoAlPrestar());
        assertNotNull(nuevoHistorial.getFechaPrestamo());
        assertNull(nuevoHistorial.getFechaDevolucion());
        assertNull(nuevoHistorial.getEmpleado());
        assertEquals(juego, nuevoHistorial.getProducto());
    }

    @Test
    void testRegistrarPrestamoEmpleado() {
        HistorialPrestamo nuevoHistorial = new HistorialPrestamo();
        
        nuevoHistorial.registrarPrestamo("bueno", juego, empleado);
        
        assertEquals("bueno", nuevoHistorial.getEstadoAlPrestar());
        assertNotNull(nuevoHistorial.getFechaPrestamo());
        assertNull(nuevoHistorial.getFechaDevolucion());
        assertEquals(empleado, nuevoHistorial.getEmpleado());
        assertEquals(juego, nuevoHistorial.getProducto());
    }

    @Test
    void testRegistrarDevolucion() {
        HistorialPrestamo nuevoHistorial = new HistorialPrestamo();
        nuevoHistorial.registrarPrestamo("bueno", juego);
        
        nuevoHistorial.registrarDevolucion("2024-12-31", "dañado");
        
        assertEquals("2024-12-31", nuevoHistorial.getFechaDevolucion());
        assertEquals("dañado", nuevoHistorial.getEstadoAlDevolver());
        assertEquals(1, nuevoHistorial.getCantidadVecesPrestado());
    }

    @Test
    void testAcumularCantidadPrestado() {
        HistorialPrestamo nuevoHistorial = new HistorialPrestamo();
        assertEquals(0, nuevoHistorial.getCantidadVecesPrestado());
        
        nuevoHistorial.acumularCantidadPrestado();
        assertEquals(1, nuevoHistorial.getCantidadVecesPrestado());
        
        nuevoHistorial.acumularCantidadPrestado();
        assertEquals(2, nuevoHistorial.getCantidadVecesPrestado());
    }

   
    @Test
    void testConstructorHistorialCliente() {
        HistorialPrestamo nuevo = new HistorialPrestamo("2024-05-01", "2024-05-02", "bueno", "bueno", 3, juego);
        
        assertEquals("2024-05-01", nuevo.getFechaPrestamo());
        assertEquals("2024-05-02", nuevo.getFechaDevolucion());
        assertEquals("bueno", nuevo.getEstadoAlPrestar());
        assertEquals("bueno", nuevo.getEstadoAlDevolver());
        assertEquals(3, nuevo.getCantidadVecesPrestado());
        assertEquals(juego, nuevo.getProducto());
        assertNull(nuevo.getEmpleado());
    }

    @Test
    void testConstructorHistorialEmpleado() {
        HistorialPrestamo nuevo = new HistorialPrestamo("2024-05-01", "2024-05-02", "bueno", "bueno", 3, empleado, juego);
        
        assertEquals("2024-05-01", nuevo.getFechaPrestamo());
        assertEquals("2024-05-02", nuevo.getFechaDevolucion());
        assertEquals("bueno", nuevo.getEstadoAlPrestar());
        assertEquals("bueno", nuevo.getEstadoAlDevolver());
        assertEquals(3, nuevo.getCantidadVecesPrestado());
        assertEquals(empleado, nuevo.getEmpleado());
        assertEquals(juego, nuevo.getProducto());
    }

}
