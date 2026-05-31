package InterfazGrafica.Cliente;

import javax.swing.*;
import java.awt.*;
import Proyecto.Cliente;
import Proyecto.Producto;
import Proyecto.InventarioJuegoVenta;

public class PanelCompras extends JPanel {
    
    private Cliente cliente;
    private InventarioJuegoVenta inventarioVenta;
    private DefaultListModel<String> listModel;
    private JList<String> listaJuegos;
    private JButton btnComprar;
    private JTextArea txtResultado;
    
    public PanelCompras(Cliente cliente, InventarioJuegoVenta inventarioVenta) {
        this.cliente = cliente;
        this.inventarioVenta = inventarioVenta;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(25, 35, 60));
        initComponents();
        actualizarListaJuegos();
    }
    
    private void initComponents() {
        JLabel titulo = new JLabel("Comprar Juegos");
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
        scrollJuegos.setPreferredSize(new Dimension(300, 200));
        scrollJuegos.setBorder(BorderFactory.createEmptyBorder());
        scrollJuegos.getViewport().setBackground(new Color(35, 45, 70));
        
        JLabel lblJuegosTitulo = new JLabel("Juegos Disponibles");
        lblJuegosTitulo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblJuegosTitulo.setForeground(Color.WHITE);
        lblJuegosTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        
        JPanel panelLista = new JPanel(new BorderLayout());
        panelLista.setBackground(new Color(25, 35, 60));
        panelLista.add(lblJuegosTitulo, BorderLayout.NORTH);
        panelLista.add(scrollJuegos, BorderLayout.CENTER);
        
        btnComprar = new JButton("Comprar Juego Seleccionado");
        btnComprar.setBackground(new Color(50, 200, 120));
        btnComprar.setForeground(Color.WHITE);
        btnComprar.setFocusPainted(false);
        btnComprar.setBorderPainted(false);
        btnComprar.setOpaque(true);
        btnComprar.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnComprar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnComprar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnComprar.setBackground(new Color(70, 220, 140));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnComprar.setBackground(new Color(50, 200, 120));
            }
        });
        
        txtResultado = new JTextArea();
        txtResultado.setEditable(false);
        txtResultado.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        txtResultado.setBackground(new Color(35, 45, 70));
        txtResultado.setForeground(Color.WHITE);
        txtResultado.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        JScrollPane scrollResultado = new JScrollPane(txtResultado);
        scrollResultado.setPreferredSize(new Dimension(300, 80));
        scrollResultado.setBorder(BorderFactory.createEmptyBorder());
        scrollResultado.getViewport().setBackground(new Color(35, 45, 70));
        
        JLabel lblEstadoTitulo = new JLabel("Estado");
        lblEstadoTitulo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblEstadoTitulo.setForeground(Color.WHITE);
        lblEstadoTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        
        JPanel panelEstado = new JPanel(new BorderLayout());
        panelEstado.setBackground(new Color(25, 35, 60));
        panelEstado.add(lblEstadoTitulo, BorderLayout.NORTH);
        panelEstado.add(scrollResultado, BorderLayout.CENTER);
        
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(new Color(25, 35, 60));
        panelBotones.add(btnComprar);
        
        JPanel panelCentral = new JPanel(new GridLayout(1, 2, 10, 0));
        panelCentral.setBackground(new Color(25, 35, 60));
        panelCentral.add(panelLista);
        panelCentral.add(panelEstado);
        
        add(titulo, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }
    
    public void actualizarListaJuegos() {
        listModel.clear();
        for (Producto p : inventarioVenta.getJuegosVenta()) {
            listModel.addElement(p.getNombreProducto() + " - $" + p.getPrecio());
        }
        if (listModel.isEmpty()) {
            listModel.addElement("No hay juegos disponibles para comprar");
        }
    }
    
    public JButton getBtnComprar() {
        return btnComprar;
    }
    
    public String getJuegoSeleccionado() {
        String seleccion = listaJuegos.getSelectedValue();
        if (seleccion != null && !seleccion.equals("No hay juegos disponibles para comprar")) {
            return seleccion.split(" - ")[0];
        }
        return null;
    }
    
    public void eliminarJuegoDeLista(String nombreJuego) {
        for (int i = 0; i < listModel.size(); i++) {
            if (listModel.get(i).startsWith(nombreJuego)) {
                listModel.remove(i);
                break;
            }
        }
        if (listModel.isEmpty()) {
            listModel.addElement("No hay juegos disponibles para comprar");
        }
    }
    
    public void mostrarResultado(String mensaje) {
        txtResultado.setText(mensaje);
    }
}