package InterfazGrafica.Cliente;

import javax.swing.*;
import java.awt.*;
import Proyecto.Cliente;
import Proyecto.Producto;
import Proyecto.InventarioJuegoPrestamo;
import Proyecto.Cafeteria;

public class PanelPedidos extends JPanel {
    
    private Cliente cliente;
    private InventarioJuegoPrestamo inventarioPrestamo;
    private Cafeteria cafeteria;
    private JButton btnPedirPrestado;
    private JTextArea txtEstado;
    
    public PanelPedidos(Cliente cliente, InventarioJuegoPrestamo inventarioPrestamo, Cafeteria cafeteria) {
        this.cliente = cliente;
        this.inventarioPrestamo = inventarioPrestamo;
        this.cafeteria = cafeteria;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(25, 35, 60));
        initComponents();
    }
    
    private void initComponents() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(25, 35, 60));
        
        JLabel titulo = new JLabel("Gestion de Pedidos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
        
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(25, 35, 60));
        panelSuperior.add(titulo, BorderLayout.CENTER);
        
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setBackground(new Color(25, 35, 60));
        btnPedirPrestado = new JButton("Pedir Prestado");
        btnPedirPrestado.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnPedirPrestado.setPreferredSize(new Dimension(130, 32));
        btnPedirPrestado.setBackground(new Color(255, 160, 50));
        btnPedirPrestado.setForeground(Color.WHITE);
        btnPedirPrestado.setFocusPainted(false);
        btnPedirPrestado.setBorderPainted(false);
        btnPedirPrestado.setOpaque(true);
        btnPedirPrestado.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPedirPrestado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPedirPrestado.setBackground(new Color(255, 180, 70));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPedirPrestado.setBackground(new Color(255, 160, 50));
            }
        });
        panelBoton.add(btnPedirPrestado);
        
        panelSuperior.add(panelBoton, BorderLayout.SOUTH);
        
        txtEstado = new JTextArea();
        txtEstado.setEditable(false);
        txtEstado.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtEstado.setBackground(new Color(35, 45, 70));
        txtEstado.setForeground(Color.WHITE);
        txtEstado.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        JScrollPane scrollEstado = new JScrollPane(txtEstado);
        scrollEstado.setBorder(BorderFactory.createEmptyBorder());
        scrollEstado.getViewport().setBackground(new Color(35, 45, 70));
        scrollEstado.setPreferredSize(new Dimension(400, 200));
        
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(scrollEstado, BorderLayout.CENTER);
        
        add(panelPrincipal, BorderLayout.CENTER);
    }
    
    public JButton getBtnPedirPrestado() {
        return btnPedirPrestado;
    }
    
    public void mostrarEstado(String texto) {
        txtEstado.setText(texto);
    }
    
    public void mostrarDialogoPedido() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Gestion de Pedido", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(new Color(25, 35, 60));
        
        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelBotones.setBackground(new Color(25, 35, 60));
        
        JButton btnActivarPedido = crearBotonDialogo("Activar Pedido", new Color(50, 200, 120));
        JButton btnSolicitarProducto = crearBotonDialogo("Solicitar Producto", new Color(50, 200, 120));
        JButton btnCancelarSolicitud = crearBotonDialogo("Cancelar Solicitud", new Color(220, 70, 70));
        
        panelBotones.add(btnActivarPedido);
        panelBotones.add(btnSolicitarProducto);
        panelBotones.add(btnCancelarSolicitud);
        
        JTextArea txtDetallePedido = new JTextArea();
        txtDetallePedido.setEditable(false);
        txtDetallePedido.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtDetallePedido.setBackground(new Color(35, 45, 70));
        txtDetallePedido.setForeground(Color.WHITE);
        txtDetallePedido.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        JScrollPane scrollDetalle = new JScrollPane(txtDetallePedido);
        scrollDetalle.setBorder(BorderFactory.createEmptyBorder());
        scrollDetalle.getViewport().setBackground(new Color(35, 45, 70));
        
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnCerrar = new JButton("Cerrar");
        btnActualizar.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnActualizar.setBackground(new Color(50, 200, 120));
        btnActualizar.setForeground(Color.WHITE);
        btnCerrar.setBackground(new Color(220, 70, 70));
        btnCerrar.setForeground(Color.WHITE);
        btnActualizar.setFocusPainted(false);
        btnCerrar.setFocusPainted(false);
        btnActualizar.setBorderPainted(false);
        btnCerrar.setBorderPainted(false);
        btnActualizar.setOpaque(true);
        btnCerrar.setOpaque(true);
        
        JPanel panelInferior = new JPanel(new FlowLayout());
        panelInferior.setBackground(new Color(25, 35, 60));
        panelInferior.add(btnActualizar);
        panelInferior.add(btnCerrar);
        
        dialog.add(panelBotones, BorderLayout.WEST);
        dialog.add(scrollDetalle, BorderLayout.CENTER);
        dialog.add(panelInferior, BorderLayout.SOUTH);
        
        Runnable actualizarTexto = () -> {
            txtDetallePedido.setText("");
            txtDetallePedido.append("Estado del pedido:\n");
            txtDetallePedido.append("Pedido activo: ");
            if (cliente.isPedidoActivo()) {
                txtDetallePedido.append("SI\n");
            } else {
                txtDetallePedido.append("NO\n");
            }
            txtDetallePedido.append("Mesa asignada: ");
            if (cliente.getMesaAsignada() != null) {
                txtDetallePedido.append(String.valueOf(cliente.getMesaAsignada().getNumeroMesa()) + "\n");
            } else {
                txtDetallePedido.append("NINGUNA\n");
            }
            txtDetallePedido.append("\nSolicitudes pendientes: " + cliente.getSolicitudesPendientes().size() + "\n");
            for (int i = 0; i < cliente.getSolicitudesPendientes().size(); i++) {
                Producto p = cliente.getSolicitudesPendientes().get(i);
                txtDetallePedido.append("  - " + p.getNombreProducto() + "\n");
            }
        };
        
        btnActivarPedido.addActionListener(e -> {
            if (cliente.getMesaAsignada() == null) {
                JOptionPane.showMessageDialog(dialog, "No tienes mesa asignada. Un mesero debe asignarte una mesa primero.");
                return;
            }
            cliente.setPedidoActivo(true);
            actualizarTexto.run();
            mostrarEstado("Pedido activado para " + cliente.getNombre());
        });
        
        btnSolicitarProducto.addActionListener(e -> {
            if (!cliente.isPedidoActivo()) {
                JOptionPane.showMessageDialog(dialog, "Primero debe activar el pedido");
                return;
            }
            mostrarDialogoSolicitarProducto(dialog, actualizarTexto);
        });
        
        btnCancelarSolicitud.addActionListener(e -> {
            if (cliente.getSolicitudesPendientes().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "No hay solicitudes pendientes para cancelar");
                return;
            }
            mostrarDialogoCancelarSolicitud(dialog, actualizarTexto);
        });
        
        btnActualizar.addActionListener(e -> {
            actualizarTexto.run();
        });
        
        btnCerrar.addActionListener(e -> {
            dialog.dispose();
        });
        
        actualizarTexto.run();
        dialog.setVisible(true);
    }
    
    private JButton crearBotonDialogo(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(color);
            }
        });
        return btn;
    }
    
    private void mostrarDialogoSolicitarProducto(JDialog parent, Runnable actualizarTexto) {
        JDialog dialog = new JDialog(parent, "Solicitar Producto", true);
        dialog.setSize(450, 400);
        dialog.setLocationRelativeTo(parent);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(new Color(25, 35, 60));
        
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> listaProductos = new JList<>(listModel);
        listaProductos.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        listaProductos.setBackground(new Color(35, 45, 70));
        listaProductos.setForeground(Color.WHITE);
        listaProductos.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        listModel.addElement("--- PRODUCTOS DE CONSUMO (MENU) ---");
        
        if (cafeteria != null && cafeteria.getProductos() != null) {
            for (int i = 0; i < cafeteria.getProductos().size(); i++) {
                Producto p = cafeteria.getProductos().get(i);
                String info = p.getNombreProducto() + " - $" + p.getPrecio();
                if (p.isAlcoholica()) {
                    info = info + " [ALCOHOL]";
                }
                if (p.isCaliente()) {
                    info = info + " [CALIENTE]";
                }
                listModel.addElement(info);
            }
        }
        
        listModel.addElement("--- JUEGOS EN PRESTAMO ---");
        
        if (inventarioPrestamo != null && inventarioPrestamo.getProductos() != null) {
            for (int i = 0; i < inventarioPrestamo.getProductos().size(); i++) {
                Producto p = inventarioPrestamo.getProductos().get(i);
                if (p.getCantidadDisponible() > 0) {
                    listModel.addElement(p.getNombreProducto() + " - $" + p.getPrecio() + " [DISPONIBLE: " + p.getCantidadDisponible() + "]");
                }
            }
        }
        
        JButton btnSolicitar = new JButton("Solicitar");
        btnSolicitar.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnSolicitar.setBackground(new Color(50, 200, 120));
        btnSolicitar.setForeground(Color.WHITE);
        btnSolicitar.setFocusPainted(false);
        btnSolicitar.setBorderPainted(false);
        btnSolicitar.setOpaque(true);
        
        btnSolicitar.addActionListener(e -> {
            String seleccion = listaProductos.getSelectedValue();
            if (seleccion == null) {
                JOptionPane.showMessageDialog(dialog, "Seleccione un producto");
                return;
            }
            if (seleccion.startsWith("---")) {
                JOptionPane.showMessageDialog(dialog, "Seleccione un producto valido");
                return;
            }
            
            String nombreProducto = seleccion.split(" - ")[0];
            Producto productoSeleccionado = null;
            
            if (cafeteria != null && cafeteria.getProductos() != null) {
                for (int i = 0; i < cafeteria.getProductos().size(); i++) {
                    Producto p = cafeteria.getProductos().get(i);
                    if (p.getNombreProducto().equals(nombreProducto)) {
                        productoSeleccionado = p;
                    }
                }
            }
            
            if (productoSeleccionado == null && inventarioPrestamo != null && inventarioPrestamo.getProductos() != null) {
                for (int i = 0; i < inventarioPrestamo.getProductos().size(); i++) {
                    Producto p = inventarioPrestamo.getProductos().get(i);
                    if (p.getNombreProducto().equals(nombreProducto)) {
                        productoSeleccionado = p;
                    }
                }
            }
            
            if (productoSeleccionado != null) {
                cliente.solicitarProducto(productoSeleccionado);
                actualizarTexto.run();
                mostrarEstado("Solicitado: " + productoSeleccionado.getNombreProducto());
                JOptionPane.showMessageDialog(dialog, "Solicitud enviada: " + productoSeleccionado.getNombreProducto());
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "No se encontro el producto");
            }
        });
        
        JScrollPane scrollLista = new JScrollPane(listaProductos);
        scrollLista.setBorder(BorderFactory.createEmptyBorder());
        scrollLista.getViewport().setBackground(new Color(35, 45, 70));
        
        dialog.add(scrollLista, BorderLayout.CENTER);
        dialog.add(btnSolicitar, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    
    private void mostrarDialogoCancelarSolicitud(JDialog parent, Runnable actualizarTexto) {
        JDialog dialog = new JDialog(parent, "Cancelar Solicitud", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(parent);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(new Color(25, 35, 60));
        
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> listaSolicitudes = new JList<>(listModel);
        listaSolicitudes.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        listaSolicitudes.setBackground(new Color(35, 45, 70));
        listaSolicitudes.setForeground(Color.WHITE);
        listaSolicitudes.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        for (int i = 0; i < cliente.getSolicitudesPendientes().size(); i++) {
            Producto p = cliente.getSolicitudesPendientes().get(i);
            listModel.addElement(p.getNombreProducto());
        }
        
        JButton btnCancelar = new JButton("Cancelar Solicitud");
        btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnCancelar.setBackground(new Color(220, 70, 70));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setBorderPainted(false);
        btnCancelar.setOpaque(true);
        
        btnCancelar.addActionListener(e -> {
            String seleccion = listaSolicitudes.getSelectedValue();
            if (seleccion == null) {
                JOptionPane.showMessageDialog(dialog, "Seleccione una solicitud");
                return;
            }
            
            Producto productoCancelar = null;
            for (int i = 0; i < cliente.getSolicitudesPendientes().size(); i++) {
                Producto p = cliente.getSolicitudesPendientes().get(i);
                if (p.getNombreProducto().equals(seleccion)) {
                    productoCancelar = p;
                }
            }
            
            if (productoCancelar != null) {
                cliente.cancelarSolicitud(productoCancelar);
                actualizarTexto.run();
                mostrarEstado("Cancelado: " + productoCancelar.getNombreProducto());
                JOptionPane.showMessageDialog(dialog, "Solicitud cancelada");
                dialog.dispose();
            }
        });
        
        JScrollPane scrollLista = new JScrollPane(listaSolicitudes);
        scrollLista.setBorder(BorderFactory.createEmptyBorder());
        scrollLista.getViewport().setBackground(new Color(35, 45, 70));
        
        dialog.add(scrollLista, BorderLayout.CENTER);
        dialog.add(btnCancelar, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
}