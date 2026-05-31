package InterfazGrafica.Empleado;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import Proyecto.Empleado;
import Proyecto.Cafeteria;
import Proyecto.InventarioJuegoVenta;
import Proyecto.InventarioJuegoPrestamo;
import Proyecto.CuentaUsuario;
import Proyecto.Menu;

public class VentanaEmpleado extends JPanel {
    
    private CardLayout cardLayout;
    private PanelMisTurnos panelMisTurnos;
    private PanelCambioGeneral panelCambioGeneral;
    private PanelIntercambio panelIntercambio;
    private PanelMesasEmpleado panelMesas;
    private PanelComprarEmpleado panelComprar;
    private PanelPedirPrestadoEmpleado panelPedirPrestado;
    private JButton btnCerrarSesion;
    
    public VentanaEmpleado(Empleado empleado, Cafeteria cafeteria,
            InventarioJuegoVenta inventarioVenta,
            InventarioJuegoPrestamo inventarioPrestamo,
            Menu menu, ArrayList<CuentaUsuario> cuentas) {
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(25, 35, 60));
        
        panelMisTurnos = new PanelMisTurnos(empleado);
        panelCambioGeneral = new PanelCambioGeneral(empleado);
        panelIntercambio = new PanelIntercambio(empleado, cuentas);
        panelMesas = new PanelMesasEmpleado(empleado, cafeteria);
        panelComprar = new PanelComprarEmpleado(empleado, menu, inventarioVenta);
        panelPedirPrestado = new PanelPedirPrestadoEmpleado(empleado, inventarioPrestamo);
        
        btnCerrarSesion = new JButton("Cerrar Sesion");
        btnCerrarSesion.setBackground(new Color(220, 70, 70));
        btnCerrarSesion.setForeground(Color.WHITE);
        btnCerrarSesion.setFocusPainted(false);
        btnCerrarSesion.setBorderPainted(false);
        btnCerrarSesion.setOpaque(true);
        btnCerrarSesion.setPreferredSize(new Dimension(150, 35));
        btnCerrarSesion.setFont(new Font("Segoe UI", Font.BOLD, 11));
        
        add(panelMisTurnos, "MIS_TURNOS");
        add(panelCambioGeneral, "CAMBIO_GENERAL");
        add(panelIntercambio, "INTERCAMBIO");
        add(panelMesas, "MESAS");
        add(panelComprar, "COMPRAR");
        add(panelPedirPrestado, "PEDIR_PRESTADO");
    }
    
    public void mostrarPanel(String nombre) {
        cardLayout.show(this, nombre);
    }
    
    public JButton getBtnCerrarSesion() {
        return btnCerrarSesion;
    }
}