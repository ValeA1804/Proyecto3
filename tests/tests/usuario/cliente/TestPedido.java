package tests.usuario.cliente;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.util.ArrayList;

import Proyecto.Cliente;
import Proyecto.Mesa;
import Proyecto.Mesero;
import Proyecto.Pedido;
import Proyecto.Producto;
import Proyecto.Reserva;
import Proyecto.Turno;
import Proyecto.Enum.*;

class TestPedido {

	private Cliente cliente;
    private Mesero mesero;
    private Mesa mesa;
    private Pedido pedido;
    private Producto bebida;
    private Producto pastel;
    private Producto bebidaAlcoholica;
    private Producto bebidaCaliente;
    private Reserva reserva;
    private ArrayList<Producto> productosCreados;
    private ArrayList<Reserva> reservasCreadas;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("Juan Perez", 1, "juan", "123", Rol.CLIENTE,
                              "juan@mail.com", 12345678, "Perez",
                              new ArrayList<>(), null, null, false);

        mesa = new Mesa(1, 4, true);

        reserva = new Reserva(3, 0, 0, 100, "18:00", "2024-12-25", mesa, true);
        cliente.getReservas().add(reserva);
        cliente.setMiReserva(reserva);
        reservasCreadas = new ArrayList<>();
        reservasCreadas.add(reserva);

        ArrayList<Turno> turnos = new ArrayList<>();
        mesero = new Mesero("Pedro Mesero", 10, "pedro", "123", Rol.EMPLEADO,
                            turnos, TipoEmpleado.MESERO, false,
                            new ArrayList<>(), new ArrayList<>(), null, new ArrayList<>());
        
        mesero.asignarMesa(mesa);

        bebida = new Producto(5000, "Coca Cola", "Bebida gaseosa", false, false,
                              new ArrayList<>(), TipoProducto.BEBIDA, 1);
        bebida.setCantidadDisponible(1);
        
        pastel = new Producto(8000, "Pastel de Chocolate", "Pastel de chocolate con mani y arequipe", false, false,
                              new ArrayList<>(), TipoProducto.PASTELERIA, 2);
        
        bebidaAlcoholica = new Producto(15000, "Cerveza", "Bebida alcohólica", true, false,
                                        new ArrayList<>(), TipoProducto.BEBIDA, 3);
        
        bebidaCaliente = new Producto(7000, "Café", "Bebida caliente", false, true,
                                      new ArrayList<>(), TipoProducto.BEBIDA, 4);

        productosCreados = new ArrayList<>();
        productosCreados.add(bebida);
        productosCreados.add(pastel);
        productosCreados.add(bebidaAlcoholica);
        productosCreados.add(bebidaCaliente);
    }

    @AfterEach
    void limpiarPedidoYReserva() {
        if (mesa != null) {
            mesa.setPedido(null);
            mesa.setDisponible(true);
        }
        if (cliente != null) {
            cliente.setPedidoActivo(false);
            cliente.setMesaAsignada(null);
            if (cliente.getSolicitudesPendientes() != null) {
                cliente.getSolicitudesPendientes().clear();
            }
            if (cliente.getReservas() != null) {
                cliente.getReservas().clear();
            }
            cliente.setMiReserva(null);
        }
        if (mesero != null && mesero.getMesasAtendiendo() != null) {
            mesero.getMesasAtendiendo().clear();
        }
        reservasCreadas.clear();
        productosCreados.clear();
        pedido = null;
        cliente = null;
        mesero = null;
        mesa = null;
        reserva = null;
    }

    // TESTS DE CREAR PEDIDO (MESERO) 

    @Test
    void testCrearPedido() {
        assertTrue(mesa.isDisponible());
        assertNull(mesa.getPedido());
        assertFalse(cliente.isPedidoActivo());

        mesero.crearPedido(cliente, mesa);

        assertNotNull(mesa.getPedido());
        assertFalse(mesa.isDisponible());
        assertTrue(cliente.isPedidoActivo());
        assertEquals(mesa, cliente.getMesaAsignada());
        assertEquals(mesero, mesa.getPedido().getMesero());
        assertEquals(cliente, mesa.getPedido().getCliente());
        
        pedido = mesa.getPedido();
    }

    @Test
    void testCrearPedidoMesaNoAsignadaAlMesero() {
        Mesa otraMesa = new Mesa(99, 4, true);
        
        mesero.crearPedido(cliente, otraMesa);
        
        assertNull(otraMesa.getPedido());
        assertTrue(otraMesa.isDisponible());
        assertFalse(cliente.isPedidoActivo());
    }

    @Test
    void testCrearPedidoMesaNoDisponible() {
        mesa.setDisponible(false);
        
        mesero.crearPedido(cliente, mesa);
        
        assertNull(mesa.getPedido());
        assertFalse(cliente.isPedidoActivo());
    }

    @Test
    void testCrearPedidoMesaYaConPedido() {
        mesero.crearPedido(cliente, mesa);
        pedido = mesa.getPedido();
        
        Cliente otroCliente = new Cliente("Maria", 2, "maria", "123", Rol.CLIENTE,
                                          "maria@mail.com", 87654321, "Lopez",
                                          new ArrayList<>(), null, null, false);
        
        mesero.crearPedido(otroCliente, mesa);
        
        assertEquals(pedido, mesa.getPedido());
    }

    // TESTS DE SOLICITAR PRODUCTO (CLIENTE) 

    @Test
    void testSolicitarProductoConPedidoActivo() {
        mesero.crearPedido(cliente, mesa);
        pedido = mesa.getPedido();
        
        int tamanioInicial = cliente.getSolicitudesPendientes().size();
        
        cliente.solicitarProducto(bebida);
        
        assertEquals(tamanioInicial + 1, cliente.getSolicitudesPendientes().size());
        assertTrue(cliente.getSolicitudesPendientes().contains(bebida));
    }

    @Test
    void testSolicitarProductoSinPedidoActivo() {
        int tamanioInicial = cliente.getSolicitudesPendientes().size();
        
        cliente.solicitarProducto(bebida);
        
        assertEquals(tamanioInicial, cliente.getSolicitudesPendientes().size());
        assertFalse(cliente.getSolicitudesPendientes().contains(bebida));
    }

    @Test
    void testSolicitarProductoSinMesaAsignada() {
        mesero.crearPedido(cliente, mesa);
        cliente.setMesaAsignada(null);
        
        cliente.solicitarProducto(bebida);
        
        assertFalse(cliente.getSolicitudesPendientes().contains(bebida));
    }

    @Test
    void testSolicitarProductoConRestriccionAlcoholMenores() {

    	Reserva reservaConMenor = new Reserva(3, 1, 0, 101, "18:00", "2024-12-25", mesa, true);
        cliente.getReservas().add(reservaConMenor);
        cliente.setMiReserva(reservaConMenor);
        reservasCreadas.add(reservaConMenor);
        
        mesero.crearPedido(cliente, mesa);
        
        cliente.solicitarProducto(bebidaAlcoholica);
        
        assertFalse(cliente.getSolicitudesPendientes().contains(bebidaAlcoholica));
    }

    @Test
    void testSolicitarProductoPasteleriaConAlergenos() {

    	ArrayList<String> alergenos = new ArrayList<>();
        alergenos.add("nueces");
        alergenos.add("gluten");
        pastel.setAlergenos(alergenos);
        
        mesero.crearPedido(cliente, mesa);
        
        cliente.solicitarProducto(pastel);
        
        assertTrue(cliente.getSolicitudesPendientes().contains(pastel));
    }

    //  TESTS DE AGREGAR PRODUCTO (MESERO) 

    @Test
    void testAgregarProductoConfirmado() {
        mesero.crearPedido(cliente, mesa);
        pedido = mesa.getPedido();
        
        cliente.solicitarProducto(bebida);
        assertTrue(cliente.getSolicitudesPendientes().contains(bebida));
        
        mesero.setMesaActual(mesa);
        mesero.agregarProducto(bebida);
        
        assertTrue(pedido.getProductos().contains(bebida));
        assertFalse(cliente.getSolicitudesPendientes().contains(bebida));
    }




    // TESTS DE CANCELAR SOLICITUD (CLIENTE) 

    @Test
    void testCancelarSolicitudPendiente() {
        mesero.crearPedido(cliente, mesa);
        
        cliente.solicitarProducto(bebida);
        assertTrue(cliente.getSolicitudesPendientes().contains(bebida));
        
        cliente.cancelarSolicitud(bebida);
        
        assertFalse(cliente.getSolicitudesPendientes().contains(bebida));
    }

    @Test
    void testCancelarSolicitudNoExistente() {
        mesero.crearPedido(cliente, mesa);
        
        cliente.cancelarSolicitud(bebida);
        
        assertFalse(cliente.getSolicitudesPendientes().contains(bebida));
    }

    //  TESTS DE ELIMINAR PRODUCTO (MESERO) 

    @Test
    void testEliminarProductoDelPedido() {
        mesero.crearPedido(cliente, mesa);
        pedido = mesa.getPedido();
        
        cliente.solicitarProducto(bebida);
                mesero.agregarProducto(bebida);
        
        assertTrue(pedido.getProductos().contains(bebida));
        
        mesero.eliminarProducto(bebida);
        
        assertFalse(pedido.getProductos().contains(bebida));
    }

    @Test
    void testEliminarProductoNoExistente() {
        mesero.crearPedido(cliente, mesa);
        pedido = mesa.getPedido();
        mesero.setMesaActual(mesa);
        
        int tamanioInicial = pedido.getProductos().size();
        
        mesero.eliminarProducto(bebida);
        
        assertEquals(tamanioInicial, pedido.getProductos().size());
    }

    //  TESTS DE VER PEDIDO 

    @Test
    void testVerPedido() {
    	mesero.crearPedido(cliente, mesa);        
        
        cliente.solicitarProducto(bebida);
        cliente.solicitarProducto(pastel);
        
        mesero.setMesaActual(mesa);  
        
        mesero.agregarProducto(bebida);
        mesero.agregarProducto(pastel);
        
        ArrayList<Producto> productosPedido = mesero.verPedido();
        
        assertEquals(2, productosPedido.size());
        assertTrue(productosPedido.contains(bebida));
        assertTrue(productosPedido.contains(pastel));}

    

    //  TESTS DE VER SOLICITUDES PENDIENTES (MESERO) 

    @Test
    void testVerSolicitudesPendientes() {
        mesero.crearPedido(cliente, mesa);
        
        cliente.solicitarProducto(bebida);
        cliente.solicitarProducto(pastel);
        
        mesero.setMesaActual(mesa);
        ArrayList<Producto> pendientes = mesero.verSolicitudesPendientes();
        
        assertEquals(2, pendientes.size());
        assertTrue(pendientes.contains(bebida));
        assertTrue(pendientes.contains(pastel));
    }

 
}
