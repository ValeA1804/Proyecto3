package InterfazGrafica.Cliente;

import javax.swing.*;
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
        setPreferredSize(new Dimension(260, 500));
        setBorder(BorderFactory.createTitledBorder("Menu y Catalogo"));
        initComponents();
    }
    
    private void initComponents() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        
        // Comida
        panelComida = new JPanel();
        panelComida.setLayout(new BoxLayout(panelComida, BoxLayout.Y_AXIS));
        panelComida.setBorder(BorderFactory.createTitledBorder("Comida"));
        panelComida.setAlignmentX(LEFT_ALIGNMENT);
        
        if (productos != null) {
            for (Producto p : productos) {
                if (p.getTipoProducto() == TipoProducto.BEBIDA || 
                    p.getTipoProducto() == TipoProducto.PASTELERIA) {
                    JPanel item = new JPanel(new BorderLayout());
                    item.setMaximumSize(new Dimension(240, 22));
                    JLabel lblNombre = new JLabel(p.getNombreProducto());
                    JLabel lblPrecio = new JLabel("$" + p.getPrecio());
                    lblNombre.setFont(new Font("Dialog", Font.PLAIN, 11));
                    lblPrecio.setFont(new Font("Dialog", Font.PLAIN, 11));
                    item.add(lblNombre, BorderLayout.WEST);
                    item.add(lblPrecio, BorderLayout.EAST);
                    panelComida.add(item);
                }
            }
        }
        
        if (panelComida.getComponentCount() == 0) {
            JLabel lblVacio = new JLabel("No hay productos disponibles");
            lblVacio.setFont(new Font("Dialog", Font.ITALIC, 11));
            panelComida.add(lblVacio);
        }
        
        // Juegos
        panelJuegos = new JPanel();
        panelJuegos.setLayout(new BoxLayout(panelJuegos, BoxLayout.Y_AXIS));
        panelJuegos.setBorder(BorderFactory.createTitledBorder("Juegos"));
        panelJuegos.setAlignmentX(LEFT_ALIGNMENT);
        
        panelPrincipal.add(panelComida);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 5)));
        panelPrincipal.add(panelJuegos);
        
        JScrollPane scroll = new JScrollPane(panelPrincipal);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll, BorderLayout.CENTER);
    }
    
    public void actualizarJuegos(ArrayList<Producto> juegos) {
        panelJuegos.removeAll();
        if (juegos == null || juegos.isEmpty()) {
            JLabel lblVacio = new JLabel("No hay juegos disponibles");
            lblVacio.setFont(new Font("Dialog", Font.ITALIC, 11));
            panelJuegos.add(lblVacio);
        } else {
            for (Producto p : juegos) {
                if (p.getCaracteristicas() != null) {
                    JPanel item = new JPanel();
                    item.setLayout(new BoxLayout(item, BoxLayout.Y_AXIS));
                    item.setMaximumSize(new Dimension(240, 60));
                    item.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
                    
                    JLabel lblNombre = new JLabel("Nombre: " + p.getNombreProducto());
                    JLabel lblTipo = new JLabel("Tipo: " + p.getCaracteristicas().getCategoria());
                    JLabel lblRestriccion = new JLabel("Edad: " + p.getCaracteristicas().getRestriccionEdad() + "+");
                    JLabel lblJugadores = new JLabel("Jugadores: " + p.getCaracteristicas().getNumeroJugadores());
                    
                    Font smallFont = new Font("Dialog", Font.PLAIN, 10);
                    lblNombre.setFont(smallFont);
                    lblTipo.setFont(smallFont);
                    lblRestriccion.setFont(smallFont);
                    lblJugadores.setFont(smallFont);
                    
                    item.add(lblNombre);
                    item.add(lblTipo);
                    item.add(lblRestriccion);
                    item.add(lblJugadores);
                    panelJuegos.add(item);
                    panelJuegos.add(Box.createRigidArea(new Dimension(0, 2)));
                }
            }
        }
        panelJuegos.revalidate();
        panelJuegos.repaint();
    }
}