package tests.usuario.administrador;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import Proyecto.Administrador;
import Proyecto.Empleado;
import Proyecto.HistorialPrestamo;
import Proyecto.InventarioJuegoPrestamo;
import Proyecto.InventarioJuegoVenta;
import Proyecto.Mesero;
import Proyecto.SolicitudTurno;
import Proyecto.Turno;
import Proyecto.Enum.*;


class TestsSolicitud {

	private Administrador admin;
    private InventarioJuegoVenta inventarioVenta;
    private InventarioJuegoPrestamo inventarioPrestamo;
    private ArrayList<HistorialPrestamo> historial;
    private Empleado empleado;
    private Turno turnoActual;
    private Turno turnoDeseado;
    private SolicitudTurno solicitud;
    private ArrayList<SolicitudTurno> solicitudesCreadas;
    
    @BeforeEach
    void setUp() {
        inventarioVenta = new InventarioJuegoVenta(new ArrayList<>());
        inventarioPrestamo = new InventarioJuegoPrestamo(new ArrayList<>());
        historial = new ArrayList<>();
        admin = new Administrador(inventarioVenta, inventarioPrestamo, historial);
        solicitudesCreadas = new ArrayList<>();
        
        empleado = new Mesero("Juan", 10, "juan", "123", Rol.EMPLEADO,
                              new ArrayList<>(), TipoEmpleado.MESERO, false,
                              new ArrayList<>(), new ArrayList<>(), null, new ArrayList<>());
        
        turnoActual = new Turno("Lunes", "09:00", "17:00", "2024-01-01");
        turnoDeseado = new Turno("Martes", "10:00", "18:00", "2024-01-02");
        solicitud = new SolicitudTurno(empleado, turnoActual, turnoDeseado);
        solicitudesCreadas.add(solicitud);
    }
    
    @AfterEach
    void limpiarSolicitudesCreadas() {
        if (empleado != null && empleado.getTurnos() != null) {
            empleado.getTurnos().clear();
        }
        for (SolicitudTurno solicitud : solicitudesCreadas) {
        	solicitud = null;
        }
        solicitudesCreadas.clear();
        admin = null;
        empleado = null;
        turnoActual = null;
        turnoDeseado = null;
        solicitud = null;
    }
    
    @Test
    void testAprobarSolicitudTurnoPendiente() {
        assertEquals(Estado.PENDIENTE, solicitud.getEstado());
        
        admin.aprobarSolicitud(solicitud);
        
        assertEquals(Estado.APROBADO, solicitud.getEstado());
    }
    
    
    @Test
    void testRechazarSolicitudTurnoPendiente() {
        assertEquals(Estado.PENDIENTE, solicitud.getEstado());
        
        admin.rechazarSolicitud(solicitud);
        
        assertEquals(Estado.RECHAZADO, solicitud.getEstado());
    }
    

}
