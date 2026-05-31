package tests.usuario.administrador;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import Proyecto.Administrador;
import Proyecto.Caracteristicas;
import Proyecto.HistorialPrestamo;
import Proyecto.InventarioJuegoPrestamo;
import Proyecto.InventarioJuegoVenta;
import Proyecto.Producto;
import Proyecto.Enum.*;


class TestGestionarProductoInventario {

	private Administrador admin;
    private InventarioJuegoVenta inventarioVenta;
    private InventarioJuegoPrestamo inventarioPrestamo;
    private ArrayList<HistorialPrestamo> historial;
    private Producto juegoVenta;
    private Producto juegoPrestamo;
    private Producto juegoPrestamoDanado;
    private Producto juegoPrestamoRobado;
    private ArrayList<Producto> productosCreados;
    
    @BeforeEach
    void setUp() {
        inventarioVenta = new InventarioJuegoVenta(new ArrayList<>());
        inventarioPrestamo = new InventarioJuegoPrestamo(new ArrayList<>());
        historial = new ArrayList<>();
        admin = new Administrador(inventarioVenta, inventarioPrestamo, historial);
        productosCreados = new ArrayList<>();
        
        Caracteristicas carac1 = new Caracteristicas(Categoria.TABLERO, 1);
        carac1.setEstado("bueno");
        carac1.setNombre("Catan");
        
        Caracteristicas carac2 = new Caracteristicas(Categoria.ACCION, 2);
        carac2.setEstado("dañado");
        carac2.setNombre("Jenga");
        
        Caracteristicas carac3 = new Caracteristicas(Categoria.CARTAS, 3);
        carac3.setEstado("bueno");
        carac3.setNombre("Uno");
        
        Caracteristicas carac4 = new Caracteristicas(Categoria.TABLERO, 4);
        carac4.setEstado("bueno");
        carac4.setNombre("Risk");
        
        juegoVenta = new Producto(50000, "Catan", "Juego de estrategia", false, false, 
                                   new ArrayList<>(), TipoProducto.JUEGOMESAVENTA, 1, carac1, 5, false);
        
        juegoPrestamo = new Producto(30000, "Uno", "Juego de cartas", false, false,
                                      new ArrayList<>(), TipoProducto.JUEGOMESAPRESTAMO, 3, carac3, 10, false);
        
        juegoPrestamoDanado = new Producto(20000, "Jenga", "Torre de bloques", false, false,
                                            new ArrayList<>(), TipoProducto.JUEGOMESAPRESTAMO, 2, carac2, 3, false);
        
        juegoPrestamoRobado = new Producto(40000, "Risk", "Juego de guerra", false, false,
                                            new ArrayList<>(), TipoProducto.JUEGOMESAPRESTAMO, 4, carac4, 5, false);
        
        inventarioVenta.getJuegosVenta().add(juegoVenta);
        inventarioPrestamo.getProductos().add(juegoPrestamo);
        inventarioPrestamo.getProductos().add(juegoPrestamoDanado);
        inventarioPrestamo.getProductos().add(juegoPrestamoRobado);
        
        productosCreados.add(juegoVenta);
        productosCreados.add(juegoPrestamo);
        productosCreados.add(juegoPrestamoDanado);
        productosCreados.add(juegoPrestamoRobado);
    }
    
    @AfterEach
    void limpiarProductosCreados() {
        for (Producto p : productosCreados) {
            inventarioVenta.getJuegosVenta().remove(p);
            inventarioPrestamo.getProductos().remove(p);
        }
        productosCreados.clear();
        admin = null;
        inventarioVenta = null;
        inventarioPrestamo = null;
        historial = null;
        juegoVenta = null;
        juegoPrestamo = null;
        juegoPrestamoDanado = null;
        juegoPrestamoRobado = null;
    }
    
    @Test
    void testAgregarProductoVenta() {
        Caracteristicas carac = new Caracteristicas(Categoria.TABLERO, 99);
        carac.setNombre("NuevoJuego");
        Producto nuevoJuego = new Producto(60000, "NuevoJuego", "Descripción", false, false,
                                            new ArrayList<>(), TipoProducto.JUEGOMESAVENTA, 99, carac, 4, false);
        productosCreados.add(nuevoJuego);
        
        int tamanioInicial = inventarioVenta.getJuegosVenta().size();
        
        admin.agregarProducto(nuevoJuego);
        
        assertEquals(tamanioInicial + 1, inventarioVenta.getJuegosVenta().size());
        assertTrue(inventarioVenta.getJuegosVenta().contains(nuevoJuego));
    }
    
    @Test
    void testAgregarProductoPrestamo() {
        Caracteristicas carac = new Caracteristicas(Categoria.CARTAS, 100);
        carac.setNombre("NuevoJuegoPrestamo");
        Producto nuevoJuego = new Producto(40000, "NuevoJuegoPrestamo", "Descripción", false, false,
                                            new ArrayList<>(), TipoProducto.JUEGOMESAPRESTAMO, 100, carac, 6, false);
        productosCreados.add(nuevoJuego);
        
        int tamanioInicial = inventarioPrestamo.getProductos().size();
        
        admin.agregarProducto(nuevoJuego);
        
        assertEquals(tamanioInicial + 1, inventarioPrestamo.getProductos().size());
        assertTrue(inventarioPrestamo.getProductos().contains(nuevoJuego));
    }
    
    @Test
    void testEliminarProducto() {
        assertTrue(inventarioVenta.getJuegosVenta().contains(juegoVenta));
        assertTrue(inventarioPrestamo.getProductos().contains(juegoPrestamo));
        
        admin.eliminarProducto(juegoVenta);
        admin.eliminarProducto(juegoPrestamo);
        
        assertFalse(inventarioVenta.getJuegosVenta().contains(juegoVenta));
        assertFalse(inventarioPrestamo.getProductos().contains(juegoPrestamo));
    }
    
    @Test
    void testBuscarProductoPorNombre() {
        Producto encontrado = admin.buscarProducto("catan");
        
        assertNotNull(encontrado);
        assertEquals("Catan", encontrado.getNombreProducto());
    }
    
    
    @Test
    void testMarcarRobado() {
        assertTrue(inventarioPrestamo.getProductos().contains(juegoPrestamoRobado));
        
        admin.marcarRobado(juegoPrestamoRobado);
        
        assertEquals("robado", juegoPrestamoRobado.getCaracteristicas().getEstado());
        assertFalse(inventarioPrestamo.getProductos().contains(juegoPrestamoRobado));
    }
    

    
    @Test
    void testListarProductos() {
        ArrayList<Producto> todos = admin.listarProductos();
        
        assertEquals(4, todos.size());
        assertTrue(todos.contains(juegoVenta));
        assertTrue(todos.contains(juegoPrestamo));
        assertTrue(todos.contains(juegoPrestamoDanado));
        assertTrue(todos.contains(juegoPrestamoRobado));
    }
    
    @Test
    void testMoverAVentaExitoso() throws Exception {
        assertTrue(inventarioPrestamo.getProductos().contains(juegoPrestamo));
        assertFalse(inventarioVenta.getJuegosVenta().contains(juegoPrestamo));
        
        admin.moverAVenta(juegoPrestamo);
        
        assertFalse(inventarioPrestamo.getProductos().contains(juegoPrestamo));
        assertTrue(inventarioVenta.getJuegosVenta().contains(juegoPrestamo));
    }
    
    @Test
    void testMoverAVentaJuegoDanadoLanzaException() {
        assertTrue(inventarioPrestamo.getProductos().contains(juegoPrestamoDanado));
        
        Exception exception = assertThrows(Exception.class, () -> {
            admin.moverAVenta(juegoPrestamoDanado);
        });
        
        assertEquals("El juego tiene daños, debe ser reparado antes de ponerse a la venta.", 
                     exception.getMessage());
    }
    
    @Test
    void testRepararJuego() {
        assertEquals("dañado", juegoPrestamoDanado.getCaracteristicas().getEstado());
        
        admin.repararJuego(juegoPrestamoDanado);
        
        assertEquals("reparado", juegoPrestamoDanado.getCaracteristicas().getEstado());
    }
    
    @Test
    void testMoverAPrestamo() {
        assertTrue(inventarioVenta.getJuegosVenta().contains(juegoVenta));
        assertFalse(inventarioPrestamo.getProductos().contains(juegoVenta));
        
        admin.moverAPrestamo(juegoVenta);
        
        assertFalse(inventarioVenta.getJuegosVenta().contains(juegoVenta));
        assertTrue(inventarioPrestamo.getProductos().contains(juegoVenta));
    }

}
