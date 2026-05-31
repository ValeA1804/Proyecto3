package tests.usuario.administrador;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import Proyecto.Administrador;
import Proyecto.Cocinero;
import Proyecto.Empleado;
import Proyecto.HistorialPrestamo;
import Proyecto.InventarioJuegoPrestamo;
import Proyecto.InventarioJuegoVenta;
import Proyecto.Mesero;
import Proyecto.Turno;
import Proyecto.Enum.*;


class TestTurnos {

	private Administrador admin;
    private InventarioJuegoVenta inventarioVenta;
    private InventarioJuegoPrestamo inventarioPrestamo;
    private ArrayList<HistorialPrestamo> historial;
    private Empleado empleado1;
    private Empleado empleado2;
    private Turno turno;
    private ArrayList<Turno> turnosCreados;
    private ArrayList<Empleado> empleadosCreados;
    
    @BeforeEach
    void setUp() {
        inventarioVenta = new InventarioJuegoVenta(new ArrayList<>());
        inventarioPrestamo = new InventarioJuegoPrestamo(new ArrayList<>());
        historial = new ArrayList<>();
        admin = new Administrador(inventarioVenta, inventarioPrestamo, historial);
        turnosCreados = new ArrayList<>();
        empleadosCreados = new ArrayList<>();
        
        empleado1 = new Mesero("Juan", 10, "juan", "123", Rol.EMPLEADO,
                                new ArrayList<>(), TipoEmpleado.MESERO, false,
                                new ArrayList<>(), new ArrayList<>(), null, new ArrayList<>());
        
        empleado2 = new Cocinero("Maria", 11, "maria", "123", Rol.EMPLEADO,
                                  new ArrayList<>(), TipoEmpleado.COCINERO, false, new ArrayList<>());
        
        empleadosCreados.add(empleado1);
        empleadosCreados.add(empleado2);
        
        turno = new Turno("Lunes", "09:00", "17:00", "2024-01-01");
        turnosCreados.add(turno);
    }
    
    @AfterEach
    void limpiarTurnosCreados() {
        for (Empleado e : empleadosCreados) {
            if (e != null && e.getTurnos() != null) {
                e.getTurnos().clear();
            }
        }
        for (Turno turno : turnosCreados) {
            turno = null;
        }
        turnosCreados.clear();
        empleadosCreados.clear();
        admin = null;
        empleado1 = null;
        empleado2 = null;
        turno = null;
    }
    
    @Test
    void testAsignarTurno() {
        assertTrue(empleado1.getTurnos().isEmpty());
        
        admin.asignarTurno(empleado1, turno);
        
        assertEquals(1, empleado1.getTurnos().size());
        assertEquals(turno, empleado1.getTurnos().get(0));
    }
    
    @Test
    void testObtenerTurnos() {
        empleado1.getTurnos().add(turno);
        
        ArrayList<Turno> turnos = admin.obtenerTurnos(empleado1);
        
        assertEquals(1, turnos.size());
        assertEquals(turno, turnos.get(0));
    }
    
    @Test
    void testModificarTurno() {
        assertEquals("09:00", turno.getHoraInicio());
        assertEquals("17:00", turno.getHoraSalida());
        
        admin.modificarTurno(turno, "10:00", "18:00");
        
        assertEquals("10:00", turno.getHoraInicio());
        assertEquals("18:00", turno.getHoraSalida());
    }
}
