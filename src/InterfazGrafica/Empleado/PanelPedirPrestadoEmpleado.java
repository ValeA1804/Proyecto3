package InterfazGrafica.Empleado;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import Proyecto.Empleado;
import Proyecto.Producto;
import Proyecto.InventarioJuegoPrestamo;

public class PanelPedirPrestadoEmpleado extends JPanel {
    
    private Empleado empleado;
    private InventarioJuegoPrestamo inventarioPrestamo;
    private DefaultListModel<String> listModel;
    private JList<String> listaJuegos;
    private JButton btnPedir;
    private JTextArea txtResultado;
    
    public PanelPedirPrestadoEmpleado(Empleado empleado, InventarioJuegoPrestamo inventarioPrestamo) {
        this.empleado = empleado;
        this.inventarioPrestamo = inventarioPrestamo;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(25, 35, 60));
        initComponents();
        actualizarListaJuegos();
    }
    
    private void initComponents() {
        JLabel titulo = new JLabel("Pedir Juego Prestado");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
        
        listModel = new DefaultListModel<>();
        listaJuegos = new JList<>(listModel);
        listaJuegos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaJuegos.setBackground(new Color(35, 45, 70));
        listaJuegos.setForeground(Color.WHITE);
        listaJuegos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        listaJuegos.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JScrollPane scrollJuegos = new JScrollPane(listaJuegos);
        scrollJuegos.setPreferredSize(new Dimension(350, 250));
        scrollJuegos.setBorder(BorderFactory.createEmptyBorder());
        scrollJuegos.getViewport().setBackground(new Color(35, 45, 70));
        
        JLabel lblJuegosTitulo = new JLabel("Juegos Disponibles para Prestamo");
        lblJuegosTitulo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblJuegosTitulo.setForeground(Color.WHITE);
        
        JPanel panelLista = new JPanel(new BorderLayout());
        panelLista.setBackground(new Color(25, 35, 60));
        panelLista.add(lblJuegosTitulo, BorderLayout.NORTH);
        panelLista.add(scrollJuegos, BorderLayout.CENTER);
        
        btnPedir = new JButton("Pedir Prestado");
        btnPedir.setBackground(new Color(255, 160, 50));
        btnPedir.setForeground(Color.WHITE);
        btnPedir.setFocusPainted(false);
        btnPedir.setBorderPainted(false);
        btnPedir.setOpaque(true);
        btnPedir.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnPedir.addActionListener(e -> pedirPrestado());
        
        txtResultado = new JTextArea();
        txtResultado.setEditable(false);
        txtResultado.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        txtResultado.setBackground(new Color(35, 45, 70));
        txtResultado.setForeground(Color.WHITE);
        txtResultado.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        JScrollPane scrollResultado = new JScrollPane(txtResultado);
        scrollResultado.setBorder(BorderFactory.createEmptyBorder());
        scrollResultado.getViewport().setBackground(new Color(35, 45, 70));
        scrollResultado.setPreferredSize(new Dimension(350, 100));
        
        JLabel lblEstadoTitulo = new JLabel("Estado");
        lblEstadoTitulo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblEstadoTitulo.setForeground(Color.WHITE);
        
        JPanel panelEstado = new JPanel(new BorderLayout());
        panelEstado.setBackground(new Color(25, 35, 60));
        panelEstado.add(lblEstadoTitulo, BorderLayout.NORTH);
        panelEstado.add(scrollResultado, BorderLayout.CENTER);
        
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(new Color(25, 35, 60));
        panelBotones.add(btnPedir);
        
        JPanel panelCentral = new JPanel(new GridLayout(1, 2, 10, 0));
        panelCentral.setBackground(new Color(25, 35, 60));
        panelCentral.add(panelLista);
        panelCentral.add(panelEstado);
        
        add(titulo, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }
    
    private void actualizarListaJuegos() {
        listModel.clear();
        if (inventarioPrestamo != null && inventarioPrestamo.getProductos() != null) {
            int indice = 0;
            while (indice < inventarioPrestamo.getProductos().size()) {
                Producto p = inventarioPrestamo.getProductos().get(indice);
                if (p.getCantidadDisponible() > 0) {
                    listModel.addElement(p.getNombreProducto() + " - $" + p.getPrecio() + " (Disponibles: " + p.getCantidadDisponible() + ")");
                }
                indice = indice + 1;
            }
        }
        if (listModel.isEmpty()) {
            listModel.addElement("No hay juegos disponibles para prestamo");
            btnPedir.setEnabled(false);
        } else {
            btnPedir.setEnabled(true);
        }
    }
    
    private void pedirPrestado() {
        String seleccion = listaJuegos.getSelectedValue();
        if (seleccion == null || seleccion.equals("No hay juegos disponibles para prestamo")) {
            txtResultado.setText("Seleccione un juego primero");
            return;
        }
        
        String nombreJuego = seleccion.split(" - ")[0];
        Producto juegoSeleccionado = null;
        
        int indice = 0;
        while (indice < inventarioPrestamo.getProductos().size() && juegoSeleccionado == null) {
            Producto p = inventarioPrestamo.getProductos().get(indice);
            if (p.getNombreProducto().equals(nombreJuego)) {
                juegoSeleccionado = p;
            }
            indice = indice + 1;
        }
        
        if (juegoSeleccionado != null) {
            boolean exito = empleado.prestarJuego(juegoSeleccionado, empleado);
            if (exito) {
                actualizarListaJuegos();
                txtResultado.setText("Prestamo exitoso!\n" +
                    "Juego: " + juegoSeleccionado.getNombreProducto() + "\n" +
                    "Stock restante: " + juegoSeleccionado.getCantidadDisponible());
            } else {
                txtResultado.setText("Error: No se pudo realizar el prestamo.\n" +
                    "Verifica que el juego este disponible.");
            }
        } else {
            txtResultado.setText("Error: Juego no encontrado");
        }
    }
}