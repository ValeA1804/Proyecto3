package InterfazGrafica.Cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanelBotones extends JPanel {
    
    private JButton btnReservas;
    private JButton btnPedidos;
    private JButton btnMiCuenta;
    private JButton btnCompras;
    private JButton btnTorneos;
    
    public PanelBotones() {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(120, 500));
        setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        setBackground(new Color(25, 35, 60));
        initComponents();
    }
    
    private void initComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);
        
        btnReservas = crearBoton("Reservas", new Color(255, 160, 50));
        btnPedidos = crearBoton("Pedidos", new Color(50, 200, 120));
        btnMiCuenta = crearBoton("Mi Cuenta", new Color(80, 140, 255));
        btnCompras = crearBoton("Compras", new Color(255, 160, 50));
        btnTorneos = crearBoton("Torneos", new Color(50, 200, 120));
        
        gbc.gridy = 0;
        add(Box.createVerticalGlue(), gbc);
        
        gbc.gridy = 1;
        add(btnReservas, gbc);
        gbc.gridy = 2;
        add(btnPedidos, gbc);
        gbc.gridy = 3;
        add(btnMiCuenta, gbc);
        gbc.gridy = 4;
        add(btnCompras, gbc);
        gbc.gridy = 5;
        add(btnTorneos, gbc);
        
        gbc.gridy = 6;
        add(Box.createVerticalGlue(), gbc);
    }
    
    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setPreferredSize(new Dimension(100, 35));
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
    
    public void setListenerReservas(ActionListener listener) {
        btnReservas.addActionListener(listener);
    }
    
    public void setListenerPedidos(ActionListener listener) {
        btnPedidos.addActionListener(listener);
    }
    
    public void setListenerMiCuenta(ActionListener listener) {
        btnMiCuenta.addActionListener(listener);
    }
    
    public void setListenerCompras(ActionListener listener) {
        btnCompras.addActionListener(listener);
    }
    
    public void setListenerTorneos(ActionListener listener) {
        btnTorneos.addActionListener(listener);
    }
}