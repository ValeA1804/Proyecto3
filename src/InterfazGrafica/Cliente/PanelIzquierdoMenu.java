package InterfazGrafica.Cliente;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.util.ArrayList;
import Proyecto.Producto;
import Proyecto.Enum.TipoProducto;

public class PanelIzquierdoMenu extends JPanel {
    
    private ArrayList<Producto> productos;
    private JPanel panelComida;
    private JPanel panelJuegos;
    
    public PanelIzquierdoMenu(ArrayList<Producto> productos) {
        this.productos = productos;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(280, 500));
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(80, 100, 120), 1),
            "Menu y Catalogo",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12),
            Color.WHITE
        ));
        setBackground(new Color(25, 35, 60));
        initComponents();
    }
    
    private void initComponents() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBackground(new Color(25, 35, 60));
        
        panelComida = new JPanel();
        panelComida.setLayout(new BoxLayout(panelComida, BoxLayout.Y_AXIS));
        panelComida.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(80, 100, 120)),
            "Comida",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 11),
            Color.WHITE
        ));
        panelComida.setBackground(new Color(25, 35, 60));
        panelComida.setAlignmentX(LEFT_ALIGNMENT);
        
        if (productos != null) {
            for (Producto p : productos) {
                if (p.getTipoProducto() == TipoProducto.BEBIDA || 
                    p.getTipoProducto() == TipoProducto.PASTELERIA) {
                    JPanel item = new JPanel(new BorderLayout());
                    item.setMaximumSize(new Dimension(260, 22));
                    item.setBackground(new Color(25, 35, 60));
                    JLabel lblNombre = new JLabel(p.getNombreProducto());
                    JLabel lblPrecio = new JLabel("$" + p.getPrecio());
                    lblNombre.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                    lblNombre.setForeground(Color.WHITE);
                    lblPrecio.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                    lblPrecio.setForeground(new Color(50, 200, 120));
                    item.add(lblNombre, BorderLayout.WEST);
                    item.add(lblPrecio, BorderLayout.EAST);
                    panelComida.add(item);
                }
            }
        }
        
        if (panelComida.getComponentCount() == 0) {
            JLabel lblVacio = new JLabel("No hay productos disponibles");
            lblVacio.setFont(new Font("Segoe UI", Font.ITALIC, 11));
            lblVacio.setForeground(Color.LIGHT_GRAY);
            panelComida.add(lblVacio);
        }
        
        panelJuegos = new JPanel();
        panelJuegos.setLayout(new BoxLayout(panelJuegos, BoxLayout.Y_AXIS));
        panelJuegos.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(80, 100, 120)),
            "Juegos",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 11),
            Color.WHITE
        ));
        panelJuegos.setBackground(new Color(25, 35, 60));
        panelJuegos.setAlignmentX(LEFT_ALIGNMENT);
        
        panelPrincipal.add(panelComida);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
        panelPrincipal.add(panelJuegos);
        
        JScrollPane scroll = new JScrollPane(panelPrincipal);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(new Color(25, 35, 60));
        add(scroll, BorderLayout.CENTER);
    }
    
    public void actualizarJuegos(ArrayList<Producto> juegos) {
        panelJuegos.removeAll();
        if (juegos == null || juegos.isEmpty()) {
            JLabel lblVacio = new JLabel("No hay juegos disponibles");
            lblVacio.setFont(new Font("Segoe UI", Font.ITALIC, 11));
            lblVacio.setForeground(Color.LIGHT_GRAY);
            panelJuegos.add(lblVacio);
        } else {
            for (Producto p : juegos) {
                if (p.getCaracteristicas() != null) {
                    JPanel item = new JPanel();
                    item.setLayout(new BoxLayout(item, BoxLayout.Y_AXIS));
                    item.setMaximumSize(new Dimension(260, 65));
                    item.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(60, 80, 100)),
                        BorderFactory.createEmptyBorder(4, 5, 4, 5)
                    ));
                    item.setBackground(new Color(30, 40, 65));
                    
                    JLabel lblNombre = new JLabel("Nombre: " + p.getNombreProducto());
                    JLabel lblTipo = new JLabel("Tipo: " + p.getCaracteristicas().getCategoria());
                    JLabel lblRestriccion = new JLabel("Edad: " + p.getCaracteristicas().getRestriccionEdad() + "+");
                    JLabel lblJugadores = new JLabel("Jugadores: " + p.getCaracteristicas().getNumeroJugadores());
                    
                    Font smallFont = new Font("Segoe UI", Font.PLAIN, 10);
                    lblNombre.setFont(smallFont);
                    lblTipo.setFont(smallFont);
                    lblRestriccion.setFont(smallFont);
                    lblJugadores.setFont(smallFont);
                    lblNombre.setForeground(Color.WHITE);
                    lblTipo.setForeground(Color.WHITE);
                    lblRestriccion.setForeground(Color.WHITE);
                    lblJugadores.setForeground(Color.WHITE);
                    
                    item.add(lblNombre);
                    item.add(lblTipo);
                    item.add(lblRestriccion);
                    item.add(lblJugadores);
                    panelJuegos.add(item);
                    panelJuegos.add(Box.createRigidArea(new Dimension(0, 3)));
                }
            }
        }
        panelJuegos.revalidate();
        panelJuegos.repaint();
    }
}