package InterfazGrafica.Empleado;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import Proyecto.Empleado;
import Proyecto.Mesero;
import Proyecto.Cafeteria;
import Proyecto.Mesa;

public class PanelMesasEmpleado extends JPanel {
    
    private Empleado empleado;
    private Cafeteria cafeteria;
    private JTextArea txtMesas;
    
    public PanelMesasEmpleado(Empleado empleado, Cafeteria cafeteria) {
        this.empleado = empleado;
        this.cafeteria = cafeteria;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(25, 35, 60));
        initComponents();
        cargarMesas();
    }
    
    private void initComponents() {
        JLabel titulo = new JLabel("Mesas que Atiendo");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
        
        txtMesas = new JTextArea();
        txtMesas.setEditable(false);
        txtMesas.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtMesas.setBackground(new Color(35, 45, 70));
        txtMesas.setForeground(Color.WHITE);
        txtMesas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollMesas = new JScrollPane(txtMesas);
        scrollMesas.setBorder(BorderFactory.createEmptyBorder());
        scrollMesas.getViewport().setBackground(new Color(35, 45, 70));
        
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setBackground(new Color(80, 140, 255));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setFocusPainted(false);
        btnActualizar.setBorderPainted(false);
        btnActualizar.setOpaque(true);
        btnActualizar.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnActualizar.addActionListener(e -> cargarMesas());
        
        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(new Color(25, 35, 60));
        panelBoton.add(btnActualizar);
        
        add(titulo, BorderLayout.NORTH);
        add(scrollMesas, BorderLayout.CENTER);
        add(panelBoton, BorderLayout.SOUTH);
    }
    
    private void cargarMesas() {
        StringBuilder sb = new StringBuilder();
        
        if (!(empleado instanceof Mesero)) {
            sb.append("Solo los meseros pueden ver mesas asignadas.\n");
            sb.append("Tu rol es: ").append(empleado.getTipoEmpleado());
            txtMesas.setText(sb.toString());
            return;
        }
        
        Mesero mesero = (Mesero) empleado;
        ArrayList<Mesa> mesasAtendiendo = mesero.getMesasAtendiendo();
        
        if (mesasAtendiendo == null || mesasAtendiendo.isEmpty()) {
            sb.append("No tienes mesas asignadas actualmente.\n\n");
            sb.append("Un administrador debe asignarte mesas.");
        } else {
            sb.append("Mesas que atiendes:\n\n");
            int indice = 0;
            while (indice < mesasAtendiendo.size()) {
                Mesa m = mesasAtendiendo.get(indice);
                sb.append("Mesa #").append(m.getNumeroMesa()).append("\n");
                sb.append("  Capacidad: ").append(m.getCapacidad()).append(" personas\n");
                sb.append("  Disponible: ").append(m.isDisponible() ? "SI" : "NO").append("\n");
                if (m.getPedido() != null) {
                    sb.append("  Pedido activo: SI (ID: ").append(m.getPedido().getIdPedido()).append(")\n");
                } else {
                    sb.append("  Pedido activo: NO\n");
                }
                sb.append("\n");
                indice = indice + 1;
            }
        }
        txtMesas.setText(sb.toString());
    }
}