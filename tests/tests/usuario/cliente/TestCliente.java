package tests.usuario.cliente;

import Proyecto.*;
import Proyecto.Enum.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestCliente {

    private Cliente cliente;
    private Producto juegoValido;
    private Producto juegoPrestamo;
    private Reserva reservaActiva;
    private Mesa mesa;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("Juan", 1, "juan01", "pass", Rol.CLIENTE,
                "juan@mail.com", 123456, "Pérez", new ArrayList<>(), null, null, false);

        Caracteristicas car = new Caracteristicas(Categoria.ESTRATEGIA, 10);
        car.setNombre("Catan");
        car.setNumeroJugadores(4);
        car.setRestriccionEdad(8);
        car.setEstado("Bueno");

        juegoValido = new Producto(50000, "Catan", "Estrategia", false, false,
                new ArrayList<>(), TipoProducto.JUEGOMESAPRESTAMO, 10, car, 5, false);
        juegoValido.setCantidadDisponible(3);

        juegoPrestamo = new Producto(30000, "Ajedrez", "Clasico", false, false,
                new ArrayList<>(), TipoProducto.JUEGOMESAPRESTAMO, 11, car, 3, false);
        juegoPrestamo.setCantidadDisponible(2);

        mesa = new Mesa(1, 4, true);
        reservaActiva = new Reserva(2, 0, 0, 1, "10:00", "2025-06-15", mesa, true);
        cliente.setMiReserva(reservaActiva);
    }

    @Test
    void getEmail_retornaEmailCorrecto() {
        assertEquals("juan@mail.com", cliente.getEmail());
    }

    @Test
    void getCedula_retornaCedulaCorrecta() {
        assertEquals(123456, cliente.getCedula());
    }

    @Test
    void getApellido_retornaApellidoCorrecto() {
        assertEquals("Pérez", cliente.getApellido());
    }

    @Test
    void puntosFidelidad_iniciaEnCero() {
        assertEquals(0, cliente.getPuntosFidelidad());
    }

    @Test
    void acumularPuntos_aumentaPuntos() {
        cliente.acumularPuntos(100);
        assertEquals(100, cliente.getPuntosFidelidad());
    }

    @Test
    void acumularPuntos_aumentaPuntosGenerados() {
        cliente.acumularPuntos(150);
        assertEquals(150, cliente.getPuntosGenerados());
    }

    @Test
    void redimirPuntos_conSuficientesSaldo_descuentaPuntos() {
        cliente.acumularPuntos(200);
        int redimidos = cliente.redimirPuntos(100);
        assertEquals(100, redimidos);
        assertEquals(100, cliente.getPuntosFidelidad());
    }

    @Test
    void redimirPuntos_sinSuficienteSaldo_retornaCero() {
        cliente.acumularPuntos(50);
        int redimidos = cliente.redimirPuntos(100);
        assertEquals(0, redimidos);
        assertEquals(50, cliente.getPuntosFidelidad());
    }

    @Test
    void consultarPuntos_retornaPuntosFidelidad() {
        cliente.acumularPuntos(300);
        assertEquals(300, cliente.consultarPuntos());
    }

    @Test
    void agregarJuegoFavorito_agregaCorrectamente() {
        cliente.agregarJuegoFavorito(juegoValido);
        assertTrue(cliente.getJuegosFavoritos().contains(juegoValido));
    }

    @Test
    void eliminarJuegoFavorito_eliminaCorrectamente() {
        cliente.agregarJuegoFavorito(juegoValido);
        cliente.eliminarJuegoFavorito(juegoValido);
        assertFalse(cliente.getJuegosFavoritos().contains(juegoValido));
    }

    @Test
    void verificarDisponibilidad_juegoConStock_retornaTrue() {
        assertTrue(cliente.verificarDisponibilidad(juegoValido));
    }

    @Test
    void verificarDisponibilidad_juegoSinStock_retornaFalse() {
        juegoValido.setCantidadDisponible(0);
        assertFalse(cliente.verificarDisponibilidad(juegoValido));
    }

    @Test
    void prestarJuego_sinReserva_retornaFalse() {
        cliente.setMiReserva(null);
        assertFalse(cliente.prestarJuego(juegoValido, cliente));
    }

    @Test
    void prestarJuego_productoNoEsPrestamo_retornaFalse() {
        Producto bebida = new Producto(5000, "Agua", "Bebida", false, false,
                new ArrayList<>(), TipoProducto.BEBIDA, 99);
        assertFalse(cliente.prestarJuego(bebida, cliente));
    }

    @Test
    void prestarJuego_juegoSinStock_retornaFalse() {
        juegoValido.setCantidadDisponible(0);
        assertFalse(cliente.prestarJuego(juegoValido, cliente));
    }

    @Test
    void devolverJuego_juegoNoPrestado_noLanzaExcepcion() {
        assertDoesNotThrow(() -> cliente.devolverJuego(juegoValido));
    }

    @Test
    void tieneSolicitudPendiente_sinSolicitud_retornaFalse() {
        assertFalse(cliente.tieneSolicitudPendiente(juegoValido));
    }

    @Test
    void cancelarSolicitud_productoNoEnPendientes_noLanzaExcepcion() {
        assertDoesNotThrow(() -> cliente.cancelarSolicitud(juegoValido));
    }

    @Test
    void setPedidoActivo_actualizaEstado() {
        cliente.setPedidoActivo(true);
        assertTrue(cliente.isPedidoActivo());
    }

    @Test
    void setMesaAsignada_actualizaMesa() {
        cliente.setMesaAsignada(mesa);
        assertEquals(mesa, cliente.getMesaAsignada());
    }
}