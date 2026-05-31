package InterfazGrafica.Empleado;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import Proyecto.Empleado;
import Proyecto.Mesero;
import Proyecto.Cocinero;
import Proyecto.Turno;
import Proyecto.Producto;
import Proyecto.Cafeteria;
import Proyecto.InventarioJuegoVenta;
import Proyecto.InventarioJuegoPrestamo;
import Proyecto.CuentaUsuario;
import Proyecto.Compra;
import Proyecto.Menu;

public class InterfazEmpleado extends JFrame {
    
    private Empleado empleado;
    private PanelSuperiorEmpleado panelSuperior;
    private PanelIzquierdoTurnos panelIzquierdo;
    private PanelBotonesEmpleado panelBotones;
    private VentanaEmpleado panelCentral;
    private Cafeteria cafeteria;
    private InventarioJuegoVenta inventarioVenta;
    private InventarioJuegoPrestamo inventarioPrestamo;
    private Menu menu;
    private ArrayList<CuentaUsuario> cuentas;
    
    public InterfazEmpleado(Empleado empleado, Cafeteria cafeteria,
            InventarioJuegoVenta inventarioVenta,
            InventarioJuegoPrestamo inventarioPrestamo,
            Menu menu, ArrayList<CuentaUsuario> cuentas) {
        
        this.empleado = empleado;
        this.cafeteria = cafeteria;
        this.inventarioVenta = inventarioVenta;
        this.inventarioPrestamo = inventarioPrestamo;
        this.menu = menu;
        this.cuentas = cuentas;
        
        setTitle("Board Game Cafe - Empleado: " + empleado.getNombre());
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout());
        
        panelSuperior = new PanelSuperiorEmpleado(empleado);
        panelIzquierdo = new PanelIzquierdoTurnos(empleado);
        panelBotones = new PanelBotonesEmpleado();
        panelCentral = new VentanaEmpleado(empleado, cafeteria, inventarioVenta, inventarioPrestamo, menu, cuentas);
        
        configurarListeners();
        
        add(panelSuperior, BorderLayout.NORTH);
        add(panelIzquierdo, BorderLayout.WEST);
        add(panelBotones, BorderLayout.EAST);
        add(panelCentral, BorderLayout.CENTER);
    }
    
    private void configurarListeners() {
        panelBotones.setListenerMisTurnos(e -> panelCentral.mostrarPanel("MIS_TURNOS"));
        panelBotones.setListenerCambioGeneral(e -> panelCentral.mostrarPanel("CAMBIO_GENERAL"));
        panelBotones.setListenerIntercambio(e -> panelCentral.mostrarPanel("INTERCAMBIO"));
        panelBotones.setListenerMesas(e -> panelCentral.mostrarPanel("MESAS"));
        panelBotones.setListenerComprar(e -> panelCentral.mostrarPanel("COMPRAR"));
        panelBotones.setListenerPedirPrestado(e -> panelCentral.mostrarPanel("PEDIR_PRESTADO"));
        
        panelCentral.getBtnCerrarSesion().addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Desea cerrar sesion?", 
                "Cerrar Sesion", 
                JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                this.dispose();
            }
        });
    }
}