package InterfazGrafica.Cliente;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import Proyecto.Cliente;
import Proyecto.Cafeteria;
import Proyecto.InventarioJuegoVenta;
import Proyecto.InventarioJuegoPrestamo;
import Proyecto.Torneo;

public class VentanaCliente extends JPanel {
    
	private CardLayout cardLayout;
    private PanelReservas panelReservas;
    private PanelPedidos panelPedidos;
    private PanelMiCuenta panelMiCuenta;
    private PanelCompras panelCompras;
    private PanelTorneos panelTorneos;
    
    public VentanaCliente(Cliente cliente, Cafeteria cafeteria, 
            InventarioJuegoVenta inventarioVenta,
            InventarioJuegoPrestamo inventarioPrestamo,
            ArrayList<Torneo> torneos) {
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panelReservas = new PanelReservas(cliente, cafeteria);
        panelPedidos = new PanelPedidos(cliente, inventarioPrestamo, cafeteria);
        panelMiCuenta = new PanelMiCuenta(cliente);
        panelCompras = new PanelCompras(cliente, inventarioVenta);
        panelTorneos = new PanelTorneos(cliente, torneos);
        
        add(panelReservas, "RESERVAS");
        add(panelPedidos, "PEDIDOS");
        add(panelMiCuenta, "MI_CUENTA");
        add(panelCompras, "COMPRAS");
        add(panelTorneos, "TORNEOS");
    }
    
    public void mostrarPanel(String nombre) {
        cardLayout.show(this, nombre);
    }
    
    public PanelReservas getPanelReservas() {
        return panelReservas;
    }
    
    public PanelPedidos getPanelPedidos() {
        return panelPedidos;
    }
    
    public PanelMiCuenta getPanelMiCuenta() {
        return panelMiCuenta;
    }
    
    public PanelCompras getPanelCompras() {
        return panelCompras;
    }
    
    public PanelTorneos getPanelTorneos() {
        return panelTorneos;
    }
    public JButton getBtnCerrarSesion() {
        return panelMiCuenta.getBtnCerrarSesion();
    }
}
