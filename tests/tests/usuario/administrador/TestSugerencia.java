package tests.usuario.administrador;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import Proyecto.Administrador;
import Proyecto.HistorialPrestamo;
import Proyecto.InventarioJuegoPrestamo;
import Proyecto.InventarioJuegoVenta;
import Proyecto.Menu;
import Proyecto.SugerenciaPlatillo;
import Proyecto.Enum.Estado;
import Proyecto.Enum.TipoPlatillo;

import java.util.ArrayList;

class TestSugerencia {

	private Administrador admin;
    private InventarioJuegoVenta inventarioVenta;
    private InventarioJuegoPrestamo inventarioPrestamo;
    private ArrayList<HistorialPrestamo> historial;
    private SugerenciaPlatillo sugerencia;
    private ArrayList<SugerenciaPlatillo> sugerenciasCreadas;
    private Menu menu; 
    
    @BeforeEach
    void setUp() {
        inventarioVenta = new InventarioJuegoVenta(new ArrayList<>());
        inventarioPrestamo = new InventarioJuegoPrestamo(new ArrayList<>());
        historial = new ArrayList<>();
        admin = new Administrador(inventarioVenta, inventarioPrestamo, historial);
        sugerenciasCreadas = new ArrayList<>();
        
        sugerencia = new SugerenciaPlatillo("Brownie", "Con chocolate y arequipe", 8000, 
                                             TipoPlatillo.PASTELERIA, false, false, new ArrayList<>());
        sugerenciasCreadas.add(sugerencia);
    }
    
    @AfterEach
    void limpiarSugerenciasCreadas() {
        for (SugerenciaPlatillo sugerencia : sugerenciasCreadas) {
        	sugerencia = null;
        }
        sugerenciasCreadas.clear();
        admin = null;
        inventarioVenta = null;
        inventarioPrestamo = null;
        historial = null;
        sugerencia = null;
    }
    
    @Test
    void testAprobarSugerenciaPendiente() {
        assertEquals(Estado.PENDIENTE, sugerencia.getEstado());
        
        admin.aprobarSugerencia(sugerencia, menu);
        
        assertEquals(Estado.APROBADO, sugerencia.getEstado());
    }
    
    
    @Test
    void testRechazarSugerenciaPendiente() {
        assertEquals(Estado.PENDIENTE, sugerencia.getEstado());
        
        admin.rechazarSugerencia(sugerencia);
        
        assertEquals(Estado.RECHAZADO, sugerencia.getEstado());
    }
    
    
    @Test
    void testListarSugerenciasPendientesRetornaListaVacia() {
        ArrayList<SugerenciaPlatillo> pendientes = admin.listarSugerenciasPendientes();
        
        assertNotNull(pendientes);
        assertTrue(pendientes.isEmpty());
    }
}
