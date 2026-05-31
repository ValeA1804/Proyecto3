package InterfazGrafica.Cliente;


import javax.swing.*;
import java.awt.*;
import Proyecto.Cliente;
import Proyecto.Producto;
import Proyecto.InventarioJuegoPrestamo;

public class PanelPedidos extends JPanel {
    
    private Cliente cliente;
    private InventarioJuegoPrestamo inventarioPrestamo;
    private JButton btnCrearPedido;
    private JButton btnCancelarPedido;
    private JButton btnPedirPrestado;
    private JTextArea txtEstado;
    
    public PanelPedidos(Cliente cliente, InventarioJuegoPrestamo inventarioPrestamo) {
        this.cliente = cliente;
        this.inventarioPrestamo = inventarioPrestamo;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        initComponents();
    }
    
    private void initComponents() {
        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));
        
        btnCrearPedido = new JButton("Crear Pedido");
        btnCancelarPedido = new JButton("Cancelar Pedido");
        btnPedirPrestado = new JButton("Pedir Juego Prestado");
        
        panelBotones.add(btnCrearPedido);
        panelBotones.add(btnCancelarPedido);
        panelBotones.add(btnPedirPrestado);
        
        txtEstado = new JTextArea();
        txtEstado.setEditable(false);
        
        add(panelBotones, BorderLayout.NORTH);
        add(new JScrollPane(txtEstado), BorderLayout.CENTER);
    }
    
    public JButton getBtnCrearPedido() {
        return btnCrearPedido;
    }
    
    public JButton getBtnCancelarPedido() {
        return btnCancelarPedido;
    }
    
    public JButton getBtnPedirPrestado() {
        return btnPedirPrestado;
    }
    
    public void mostrarEstado(String texto) {
        txtEstado.setText(texto);
    }
    
    public void mostrarDialogoPedirJuego() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Pedir Juego Prestado", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> listaJuegos = new JList<>(listModel);
        
        for (Producto p : inventarioPrestamo.getProductos()) {
            if (p.getCantidadDisponible() > 0) {
                listModel.addElement(p.getNombreProducto() + " - Disponibles: " + p.getCantidadDisponible());
            }
        }
        
        JButton btnSeleccionar = new JButton("Solicitar");
        btnSeleccionar.addActionListener(e -> {
            String seleccion = listaJuegos.getSelectedValue();
            if (seleccion != null) {
                dialog.dispose();
            }
        });
        
        dialog.setLayout(new BorderLayout());
        dialog.add(new JScrollPane(listaJuegos), BorderLayout.CENTER);
        dialog.add(btnSeleccionar, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
}