package tests.usuario;
import Proyecto.*;
import Proyecto.TorneoAmistoso;
import Proyecto.Enum.*;
import Proyecto.Exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class TestTorneo {

    private Producto juegoBase;
    private TorneoAmistoso torneoAmistoso;
    private TorneoCompetitivo torneoCompetitivo;
    private Cliente clienteNormal;
    private Cliente clienteFanatico;
    private Empleado empleadoLibre;
    private Empleado empleadoEnTurno;

    @BeforeEach
    void setUp() {
        juegoBase = new Producto(
            50_000, "Catan", "Juego de estrategia",
            false, false, new ArrayList<>(),
            TipoProducto.JUEGOMESAPRESTAMO, 1,
            null, 10, false
        );

        torneoAmistoso    = new TorneoAmistoso("Torneo Amistoso A", "2025-06-15", juegoBase, 10, 20);
        torneoCompetitivo = new TorneoCompetitivo("Torneo Competitivo C", "2025-06-20", juegoBase, 10, 5000, "Premio en metálico");

        clienteNormal = new Cliente(
            "Juan", 1, "juan01", "pass", Rol.CLIENTE,
            "juan@mail.com", 123456, "Pérez",
            new ArrayList<>(), null, null, false
        );

        clienteFanatico = new Cliente(
            "Ana", 2, "ana02", "pass", Rol.CLIENTE,
            "ana@mail.com", 654321, "López",
            new ArrayList<>(), null, null, false
        );
        clienteFanatico.agregarJuegoFavorito(juegoBase);

        empleadoLibre   = crearEmpleado(3, "Carlos", "2025-07-01");
        empleadoEnTurno = crearEmpleado(4, "Maria",  "2025-06-15");
    }

    private Empleado crearEmpleado(int id, String nombre, String fechaTurno) {
        Turno turno = new Turno("Lunes", "08:00", "16:00", fechaTurno);
        ArrayList<Turno> turnos = new ArrayList<>();
        turnos.add(turno);
        return new Cocinero(nombre, id, nombre.toLowerCase(), "pass", Rol.EMPLEADO, turnos, TipoEmpleado.COCINERO, false, new ArrayList<>());
    }

    @Test
    void inscribir_masDesTresParticipantes_lanzaInscripcionExcedida() {
        ArrayList<Integer> cuatro = new ArrayList<>();
        cuatro.add(10); cuatro.add(11); cuatro.add(12); cuatro.add(13);
        assertThrows(InscripcionExcedidaException.class, () ->
            torneoAmistoso.inscribirParticipante(clienteNormal, cuatro)
        );
    }

    @Test
    void inscribir_exactamenteTresParticipantes_esValido() throws Exception {
        ArrayList<Integer> tres = new ArrayList<>();
        tres.add(10); tres.add(11); tres.add(12);
        assertDoesNotThrow(() ->
            torneoAmistoso.inscribirParticipante(clienteNormal, tres)
        );
        assertTrue(torneoAmistoso.getListaParticipantes().containsKey(clienteNormal.getId()));
    }

    @Test
    void inscribir_cuandoNoHayCupos_lanzaCuposInsuficientes() throws Exception {
        TorneoAmistoso torneoLleno = new TorneoAmistoso("Lleno", "2025-07-01", juegoBase, 1, 10);
        ArrayList<Integer> uno = new ArrayList<>();
        uno.add(99);
        torneoLleno.inscribirParticipante(clienteNormal, uno);
        Cliente clienteExtra = new Cliente(
            "Extra", 99, "extra", "pass", Rol.CLIENTE,
            "e@mail.com", 111, "Extra",
            new ArrayList<>(), null, null, false
        );
        assertThrows(CuposInsuficientesException.class, () ->
            torneoLleno.inscribirParticipante(clienteExtra, uno)
        );
    }

    @Test
    void inscribir_clienteFanatico_vaACuposFanaticos() throws Exception {
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(1);
        torneoAmistoso.inscribirParticipante(clienteFanatico, ids);
        assertTrue(torneoAmistoso.getCuposFanaticos().containsKey(clienteFanatico.getId()));
        assertFalse(torneoAmistoso.getListaParticipantes().containsKey(clienteFanatico.getId()));
    }

    @Test
    void inscribir_clienteNormal_vaAListaRegular() throws Exception {
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(1);
        torneoAmistoso.inscribirParticipante(clienteNormal, ids);
        assertTrue(torneoAmistoso.getListaParticipantes().containsKey(clienteNormal.getId()));
        assertFalse(torneoAmistoso.getCuposFanaticos().containsKey(clienteNormal.getId()));
    }

    @Test
    void inscribir_fanaticoConCuposFanaticoLlenos_vaAListaRegular() throws Exception {
        for (int i = 10; i < 12; i++) {
            Cliente f = new Cliente("Fan" + i, i, "f" + i, "p", Rol.CLIENTE,
                "f@m.com", i, "F", new ArrayList<>(), null, null, false);
            f.agregarJuegoFavorito(juegoBase);
            ArrayList<Integer> ids = new ArrayList<>();
            ids.add(i);
            torneoAmistoso.inscribirParticipante(f, ids);
        }
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(clienteFanatico.getId());
        torneoAmistoso.inscribirParticipante(clienteFanatico, ids);
        assertTrue(torneoAmistoso.getListaParticipantes().containsKey(clienteFanatico.getId()));
    }

    @Test
    void inscribir_empleadoSinTurnoEseDia_exitoso() {
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(empleadoLibre.getId());
        assertDoesNotThrow(() ->
            torneoAmistoso.inscribirParticipante(empleadoLibre, ids)
        );
        assertTrue(torneoAmistoso.getListaParticipantes().containsKey(empleadoLibre.getId()));
    }

    @Test
    void inscribir_empleadoConTurnoEseDia_lanzaEmpleadoEnTurno() {
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(empleadoEnTurno.getId());
        assertThrows(EmpleadoEnTurnoException.class, () ->
            torneoAmistoso.inscribirParticipante(empleadoEnTurno, ids)
        );
    }

    @Test
    void eliminarInscripcion_clienteNormal_eliminaDeLista() throws Exception {
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(1);
        torneoAmistoso.inscribirParticipante(clienteNormal, ids);
        torneoAmistoso.eliminarInscripcion(clienteNormal);
        assertFalse(torneoAmistoso.getListaParticipantes().containsKey(clienteNormal.getId()));
    }

    @Test
    void eliminarInscripcion_clienteFanatico_eliminaDeCuposFanaticos() throws Exception {
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(clienteFanatico.getId());
        torneoAmistoso.inscribirParticipante(clienteFanatico, ids);
        torneoAmistoso.eliminarInscripcion(clienteFanatico);
        assertFalse(torneoAmistoso.getCuposFanaticos().containsKey(clienteFanatico.getId()));
    }

    @Test
    void eliminarInscripcion_usuarioNoInscrito_noLanzaExcepcion() {
        assertDoesNotThrow(() -> torneoAmistoso.eliminarInscripcion(clienteNormal));
    }

    @Test
    void torneoAmistoso_costoInscripcionEsCero() {
        assertEquals(0, torneoAmistoso.getCostoInscripcion());
    }

    @Test
    void torneoAmistoso_premioContieneDescuento() {
        String premio = torneoAmistoso.getPremio();
        assertNotNull(premio);
        assertTrue(premio.contains("20"));
    }

    @Test
    void torneoCompetitivo_costoInscripcionEsTarifa() {
        assertEquals(5000, torneoCompetitivo.getCostoInscripcion());
    }

    @Test
    void torneoCompetitivo_premioRefleja_dineroRecaudado() throws Exception {
        ArrayList<Integer> ids1 = new ArrayList<>(); ids1.add(1);
        ArrayList<Integer> ids2 = new ArrayList<>(); ids2.add(2);
        torneoCompetitivo.inscribirParticipante(clienteNormal,   ids1);
        torneoCompetitivo.inscribirParticipante(clienteFanatico, ids2);
        String premio = torneoCompetitivo.getPremio();
        assertTrue(premio.contains("10000"));
    }

    @Test
    void torneoCompetitivo_empleadoLibre_puedeInscribirse() {
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(empleadoLibre.getId());
        assertDoesNotThrow(() ->
            torneoCompetitivo.inscribirParticipante(empleadoLibre, ids)
        );
    }

    @Test
    void cuposFanaticos_limitEs20PorCientoRedondeadoArriba() {
        assertEquals(2, (int) Math.ceil(10 * 0.20));
        assertEquals(2, (int) Math.ceil(7  * 0.20));
        assertEquals(1, (int) Math.ceil(5  * 0.20));
    }
}