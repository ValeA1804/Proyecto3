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
        initComponents();
        actualizarListaJuegos();
    }
    
    private void initComponents() {
        JLabel titulo = new JLabel("Comprar Juegos");
        titulo.setFont(new Font("Dialog", Font.BOLD, 14));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        listModel = new DefaultListModel<>();
        listaJuegos = new JList<>(listModel);
        listaJuegos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollJuegos = new JScrollPane(listaJuegos);
        scrollJuegos.setPreferredSize(new Dimension(300, 200));
        scrollJuegos.setBorder(BorderFactory.createTitledBorder("Juegos Disponibles"));
        
        btnComprar = new JButton("Comprar Juego Seleccionado");
        
        txtResultado = new JTextArea();
        txtResultado.setEditable(false);
        txtResultado.setFont(new Font("Dialog", Font.PLAIN, 11));
        JScrollPane scrollResultado = new JScrollPane(txtResultado);
        scrollResultado.setPreferredSize(new Dimension(300, 80));
        scrollResultado.setBorder(BorderFactory.createTitledBorder("Estado"));
        
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnComprar);
        
        add(titulo, BorderLayout.NORTH);
        add(scrollJuegos, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
        add(scrollResultado, BorderLayout.EAST);
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