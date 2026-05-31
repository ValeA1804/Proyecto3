package tests.usuario.cliente;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.awt.Menu;
import java.util.ArrayList;

import Proyecto.Cafeteria;
import Proyecto.Cliente;
import Proyecto.Mesa;
import Proyecto.Reserva;
import Proyecto.Enum.*;

class TestReserva {

	private Cliente cliente;
    private Cafeteria cafeteria;
    private Mesa mesa1;
    private Mesa mesa2;
    private ArrayList<Reserva> reservasCreadas;
    private ArrayList<Mesa> mesasCreadas;
    private Proyecto.Menu menu; 

    @BeforeEach
    void setUp() {
        
        mesa1 = new Mesa(1, 4, true);
        mesa2 = new Mesa(2, 6, true);
        mesasCreadas = new ArrayList<>();
        mesasCreadas.add(mesa1);
        mesasCreadas.add(mesa2);

        ArrayList<Mesa> mesas = new ArrayList<>();
        mesas.add(mesa1);
        mesas.add(mesa2);

                cafeteria = new Cafeteria("Bogotá", "Calle 12", mesas, new ArrayList<>(), 20, new ArrayList<>(), menu);

        cliente = new Cliente("Juan Perez", 1, "juan", "123", Rol.CLIENTE,
                              "juan@mail.com", 12345678, "Perez",
                              new ArrayList<>(), null, null, false);

        reservasCreadas = new ArrayList<>();
    }

    @AfterEach
    void limpiarReservasCreadas() {
        for (Mesa m : mesasCreadas) {
            if (m != null) {
                m.setDisponible(true);
            }
        }
        if (cliente != null && cliente.getReservas() != null) {
            cliente.getReservas().clear();
        }
        if (cliente != null) {
            cliente.setMiReserva(null);
        }
        reservasCreadas.clear();
        cliente = null;
        cafeteria = null;
        mesa1 = null;
        mesa2 = null;
    }

    //  TESTS DE VERIFICAR DISPONIBILIDAD 

    @Test
    void testVerificarDisponibilidadMesaLibre() {
        boolean disponible = cliente.verificarDisponibilidad(mesa1, "2024-12-25", "18:00");

        assertTrue(disponible);
    }

    @Test
    void testVerificarDisponibilidadMesaOcupada() {
        mesa1.setDisponible(false);

        boolean disponible = cliente.verificarDisponibilidad(mesa1, "2024-12-25", "18:00");

        assertFalse(disponible);
    }

    @Test
    void testVerificarDisponibilidadClienteYaTieneReserva() {
        Reserva reservaExistente = new Reserva(2, 0, 0, 100, "18:00", "2024-12-25", mesa1, true);
        cliente.getReservas().add(reservaExistente);
        cliente.setMiReserva(reservaExistente);

        boolean disponible = cliente.verificarDisponibilidad(mesa2, "2024-12-26", "19:00");

        assertFalse(disponible);
    }

    
    //  TESTS DE CREAR RESERVA 

    @Test
    void testCrearReserva() {
        int tamanioInicial = cliente.getReservas().size();

        Reserva nuevaReserva = cliente.crearReserva(cafeteria, 3, 0, 0, 1, "19:00", "22:00", "2024-12-25", mesa1);

        assertNotNull(nuevaReserva);
        assertEquals(3, nuevaReserva.getNumeroTotalPersonas());
        assertEquals(mesa1, nuevaReserva.getMesa());
        assertFalse(mesa1.isDisponible());
        assertEquals(tamanioInicial + 1, cliente.getReservas().size());
        assertEquals(nuevaReserva, cliente.getMiReserva());
        reservasCreadas.add(nuevaReserva);
    }

    @Test
    void testCrearReservaMesaNoDisponible() {
        mesa1.setDisponible(false);

        Reserva nuevaReserva = cliente.crearReserva(cafeteria, 2, 0, 0, 2, "18:00", "2024-12-25", null, mesa1);

        assertNull(nuevaReserva);
        assertFalse(mesa1.isDisponible());  
    }

    @Test
    void testCrearReservaCapacidadInsuficiente() {
        Reserva nuevaReserva = cliente.crearReserva(cafeteria, 6, 0, 0, 3, "18:00", "21:00", "2024-12-25", mesa1);

        assertNull(nuevaReserva);
        assertTrue(mesa1.isDisponible());
    }

    @Test
    void testCrearReservaCafeteriaLlena() {
        Mesa mesaGrande = new Mesa(10, 18, true);
        ArrayList<Mesa> mesasTemp = new ArrayList<>();
        mesasTemp.add(mesaGrande);
        cafeteria.setMesas(mesasTemp);
        mesasCreadas.add(mesaGrande);

        Reserva primeraReserva = cliente.crearReserva(cafeteria, 18, 0, 0, 4, "18:00", "21:00", "2024-12-25", mesaGrande);
        reservasCreadas.add(primeraReserva);

        Reserva segundaReserva = cliente.crearReserva(cafeteria, 4, 0, 0, 5, "19:00", "22:00", "2024-12-25", mesa2);

        assertNull(segundaReserva);
    }

    @Test
    void testCrearReservaConMenoresEdad() {
        Reserva nuevaReserva = cliente.crearReserva(cafeteria, 4, 2, 0, 6, "18:00", "21:00", "2024-12-25", mesa2);

        assertNotNull(nuevaReserva);
        assertEquals(2, nuevaReserva.getCantidadMenoresEdad());
        assertEquals(0, nuevaReserva.getCantidadMenoresCincoAnios());
        reservasCreadas.add(nuevaReserva);
    }

    @Test
    void testCrearReservaConMenoresDeCinco() {
        Reserva nuevaReserva = cliente.crearReserva(cafeteria, 4, 0, 2, 7, "18:00", "21:00", "2024-12-25", mesa2);

        assertNotNull(nuevaReserva);
        assertEquals(0, nuevaReserva.getCantidadMenoresEdad());
        assertEquals(2, nuevaReserva.getCantidadMenoresCincoAnios());
        reservasCreadas.add(nuevaReserva);
    }

    // TESTS DE CANCELAR RESERVA 

    @Test
    void testCancelarReserva() {
        Reserva reserva = cliente.crearReserva(cafeteria, 3, 0, 0, 8, "18:00", "21:00", "2024-12-25", mesa1);
        reservasCreadas.add(reserva);
        assertNotNull(reserva);
        assertFalse(mesa1.isDisponible());
        assertNotNull(cliente.getMiReserva());

        cliente.cancelarReserva(reserva);

        assertTrue(mesa1.isDisponible());
        assertNull(cliente.getMiReserva());
        assertFalse(cliente.getReservas().contains(reserva));
    }

}
