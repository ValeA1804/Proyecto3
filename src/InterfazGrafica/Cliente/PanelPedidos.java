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
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        initComponents();
    }
    
    private void initComponents() {
        JPanel panelSuperior = new JPanel(new FlowLayout());
        
        btnPedirPrestado = new JButton("Pedir Prestado");
        
        panelSuperior.add(btnPedirPrestado);
        
        txtEstado = new JTextArea();
        txtEstado.setEditable(false);
        
        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(txtEstado), BorderLayout.CENTER);
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
        
        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JButton btnActivarPedido = new JButton("Activar Pedido");
        JButton btnSolicitarProducto = new JButton("Solicitar Producto");
        JButton btnCancelarSolicitud = new JButton("Cancelar Solicitud");
        
        panelBotones.add(btnActivarPedido);
        panelBotones.add(btnSolicitarProducto);
        panelBotones.add(btnCancelarSolicitud);
        
        JTextArea txtDetallePedido = new JTextArea();
        txtDetallePedido.setEditable(false);
        
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnCerrar = new JButton("Cerrar");
        JPanel panelInferior = new JPanel(new FlowLayout());
        panelInferior.add(btnActualizar);
        panelInferior.add(btnCerrar);
        
        dialog.add(panelBotones, BorderLayout.WEST);
        dialog.add(new JScrollPane(txtDetallePedido), BorderLayout.CENTER);
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
    
    private void mostrarDialogoSolicitarProducto(JDialog parent, Runnable actualizarTexto) {
        JDialog dialog = new JDialog(parent, "Solicitar Producto", true);
        dialog.setSize(450, 400);
        dialog.setLocationRelativeTo(parent);
        dialog.setLayout(new BorderLayout());
        
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> listaProductos = new JList<>(listModel);
        
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
        
        dialog.add(new JScrollPane(listaProductos), BorderLayout.CENTER);
        dialog.add(btnSolicitar, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    
    private void mostrarDialogoCancelarSolicitud(JDialog parent, Runnable actualizarTexto) {
        JDialog dialog = new JDialog(parent, "Cancelar Solicitud", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(parent);
        dialog.setLayout(new BorderLayout());
        
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> listaSolicitudes = new JList<>(listModel);
        
        for (int i = 0; i < cliente.getSolicitudesPendientes().size(); i++) {
            Producto p = cliente.getSolicitudesPendientes().get(i);
            listModel.addElement(p.getNombreProducto());
        }
        
        JButton btnCancelar = new JButton("Cancelar Solicitud");
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
        
        dialog.add(new JScrollPane(listaSolicitudes), BorderLayout.CENTER);
        dialog.add(btnCancelar, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
}