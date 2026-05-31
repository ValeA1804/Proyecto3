package InterfazGrafica.Empleado;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import Proyecto.Empleado;
import Proyecto.Producto;
import Proyecto.Menu;
import Proyecto.InventarioJuegoVenta;
import Proyecto.Compra;

public class PanelComprarEmpleado extends JPanel {
    
    private Empleado empleado;
    private Menu menu;
    private InventarioJuegoVenta inventarioVenta;
    private DefaultListModel<String> listModelMenu;
    private DefaultListModel<String> listModelVenta;
    private JList<String> listaMenu;
    private JList<String> listaVenta;
    private JTextArea txtResultado;
    private JButton btnComprar;
    
    public PanelComprarEmpleado(Empleado empleado, Menu menu, InventarioJuegoVenta inventarioVenta) {
        this.empleado = empleado;
        this.menu = menu;
        this.inventarioVenta = inventarioVenta;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(25, 35, 60));
        initComponents();
        cargarProductos();
    }
    
    private void initComponents() {
        JLabel titulo = new JLabel("Comprar Producto");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        listModelMenu = new DefaultListModel<>();
        listaMenu = new JList<>(listModelMenu);
        listaMenu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaMenu.setBackground(new Color(35, 45, 70));
        listaMenu.setForeground(Color.WHITE);
        listaMenu.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        listaMenu.setFixedCellHeight(35);
        listaMenu.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        JScrollPane scrollMenu = new JScrollPane(listaMenu);
        scrollMenu.setBorder(BorderFactory.createLineBorder(new Color(80, 100, 120)));
        scrollMenu.getViewport().setBackground(new Color(35, 45, 70));
        
        JLabel lblMenuTitulo = new JLabel("Productos del Menu");
        lblMenuTitulo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblMenuTitulo.setForeground(Color.WHITE);
        lblMenuTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblMenuTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        
        JPanel panelMenu = new JPanel(new BorderLayout());
        panelMenu.setBackground(new Color(25, 35, 60));
        panelMenu.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelMenu.add(lblMenuTitulo, BorderLayout.NORTH);
        panelMenu.add(scrollMenu, BorderLayout.CENTER);
        
        listModelVenta = new DefaultListModel<>();
        listaVenta = new JList<>(listModelVenta);
        listaVenta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaVenta.setBackground(new Color(35, 45, 70));
        listaVenta.setForeground(Color.WHITE);
        listaVenta.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        listaVenta.setFixedCellHeight(35);
        listaVenta.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        JScrollPane scrollVenta = new JScrollPane(listaVenta);
        scrollVenta.setBorder(BorderFactory.createLineBorder(new Color(80, 100, 120)));
        scrollVenta.getViewport().setBackground(new Color(35, 45, 70));
        
        JLabel lblVentaTitulo = new JLabel("Juegos en Venta");
        lblVentaTitulo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblVentaTitulo.setForeground(Color.WHITE);
        lblVentaTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblVentaTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        
        JPanel panelVenta = new JPanel(new BorderLayout());
        panelVenta.setBackground(new Color(25, 35, 60));
        panelVenta.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelVenta.add(lblVentaTitulo, BorderLayout.NORTH);
        panelVenta.add(scrollVenta, BorderLayout.CENTER);
        
        JPanel panelListas = new JPanel(new GridLayout(1, 2, 20, 0));
        panelListas.setBackground(new Color(25, 35, 60));
        panelListas.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panelListas.add(panelMenu);
        panelListas.add(panelVenta);
        
        btnComprar = new JButton("COMPRAR PRODUCTO SELECCIONADO");
        btnComprar.setBackground(new Color(50, 200, 120));
        btnComprar.setForeground(Color.WHITE);
        btnComprar.setFocusPainted(false);
        btnComprar.setBorderPainted(false);
        btnComprar.setOpaque(true);
        btnComprar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnComprar.setPreferredSize(new Dimension(400, 45));
        btnComprar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnComprar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnComprar.setBackground(new Color(70, 220, 140));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnComprar.setBackground(new Color(50, 200, 120));
            }
        });
        btnComprar.addActionListener(e -> comprarProducto());
        
        txtResultado = new JTextArea();
        txtResultado.setEditable(false);
        txtResultado.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtResultado.setBackground(new Color(35, 45, 70));
        txtResultado.setForeground(Color.WHITE);
        txtResultado.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        txtResultado.setRows(5);
        
        JScrollPane scrollResultado = new JScrollPane(txtResultado);
        scrollResultado.setBorder(BorderFactory.createLineBorder(new Color(80, 100, 120)));
        scrollResultado.getViewport().setBackground(new Color(35, 45, 70));
        
        JLabel lblEstadoTitulo = new JLabel("Estado de la Compra");
        lblEstadoTitulo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblEstadoTitulo.setForeground(Color.WHITE);
        lblEstadoTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblEstadoTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        
        JPanel panelEstado = new JPanel(new BorderLayout());
        panelEstado.setBackground(new Color(25, 35, 60));
        panelEstado.setBorder(BorderFactory.createEmptyBorder(10, 10, 15, 10));
        panelEstado.add(lblEstadoTitulo, BorderLayout.NORTH);
        panelEstado.add(scrollResultado, BorderLayout.CENTER);
        
        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.setBackground(new Color(25, 35, 60));
        panelSur.add(btnComprar, BorderLayout.CENTER);
        
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBackground(new Color(25, 35, 60));
        panelCentral.add(panelListas, BorderLayout.CENTER);
        panelCentral.add(panelEstado, BorderLayout.SOUTH);
        
        add(titulo, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
        add(panelSur, BorderLayout.SOUTH);
    }
    
    private void cargarProductos() {
        listModelMenu.clear();
        if (menu != null && menu.getMenuProductos() != null) {
            int indice = 0;
            while (indice < menu.getMenuProductos().size()) {
                Producto p = menu.getMenuProductos().get(indice);
                listModelMenu.addElement(p.getNombreProducto() + "  -  $" + p.getPrecio());
                indice = indice + 1;
            }
        }
        
        listModelVenta.clear();
        if (inventarioVenta != null && inventarioVenta.getJuegosVenta() != null) {
            int indice = 0;
            while (indice < inventarioVenta.getJuegosVenta().size()) {
                Producto p = inventarioVenta.getJuegosVenta().get(indice);
                listModelVenta.addElement(p.getNombreProducto() + "  -  $" + p.getPrecio());
                indice = indice + 1;
            }
        }
        
        if (listModelMenu.isEmpty()) {
            listModelMenu.addElement("No hay productos en el menu");
        }
        
        if (listModelVenta.isEmpty()) {
            listModelVenta.addElement("No hay juegos en venta");
        }
    }
    
    private void comprarProducto() {
        String seleccionMenu = listaMenu.getSelectedValue();
        String seleccionVenta = listaVenta.getSelectedValue();
        Producto productoSeleccionado = null;
        String tipoProducto = "";
        
        if (seleccionMenu != null && !seleccionMenu.equals("No hay productos en el menu")) {
            String nombreProducto = seleccionMenu.split(" - ")[0].trim();
            int indice = 0;
            while (indice < menu.getMenuProductos().size() && productoSeleccionado == null) {
                Producto p = menu.getMenuProductos().get(indice);
                if (p.getNombreProducto().equals(nombreProducto)) {
                    productoSeleccionado = p;
                    tipoProducto = "del menu";
                }
                indice = indice + 1;
            }
        }
        
        if (productoSeleccionado == null && seleccionVenta != null && !seleccionVenta.equals("No hay juegos en venta")) {
            String nombreProducto = seleccionVenta.split(" - ")[0].trim();
            int indice = 0;
            while (indice < inventarioVenta.getJuegosVenta().size() && productoSeleccionado == null) {
                Producto p = inventarioVenta.getJuegosVenta().get(indice);
                if (p.getNombreProducto().equals(nombreProducto)) {
                    productoSeleccionado = p;
                    tipoProducto = "juego";
                }
                indice = indice + 1;
            }
        }
        
        if (productoSeleccionado == null) {
            txtResultado.setText("ERROR: Seleccione un producto primero");
            return;
        }
        
        ArrayList<Producto> productosCompra = new ArrayList<>();
        productosCompra.add(productoSeleccionado);
        
        Compra compra = empleado.realizarCompra(productosCompra, empleado, 0, null);
        
        if (compra != null && compra.getEstado().equals("COMPLETADA")) {
            if (tipoProducto.equals("juego")) {
                inventarioVenta.getJuegosVenta().remove(productoSeleccionado);
                cargarProductos();
            }
            
            double descuento = productoSeleccionado.getPrecio() * 0.2;
            double totalFinal = productoSeleccionado.getPrecio() - descuento;
            
            txtResultado.setText(
                "COMPRA EXITOSA!\n" +
                "--------------------------------------------------\n" +
                "Producto: " + productoSeleccionado.getNombreProducto() + "\n" +
                "Precio original: $" + productoSeleccionado.getPrecio() + "\n" +
                "Descuento empleado (20%): -$" + String.format("%.2f", descuento) + "\n" +
                "Total pagado: $" + String.format("%.2f", totalFinal) + "\n" +
                "--------------------------------------------------\n" +
                "Gracias por tu compra!"
            );
        } else {
            txtResultado.setText("ERROR: No se pudo completar la compra");
        }
    }
}