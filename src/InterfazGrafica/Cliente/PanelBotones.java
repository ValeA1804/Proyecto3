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
        setPreferredSize(new Dimension(100, 500));
        setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        initComponents();
    }
    
    private void initComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(3, 0, 3, 0);
        
        btnReservas = new JButton("Reservas");
        btnPedidos = new JButton("Pedidos");
        btnMiCuenta = new JButton("Mi Cuenta");
        btnCompras = new JButton("Compras");
        btnTorneos = new JButton("Torneos");
        
        Font smallFont = new Font("Dialog", Font.PLAIN, 11);
        btnReservas.setFont(smallFont);
        btnPedidos.setFont(smallFont);
        btnMiCuenta.setFont(smallFont);
        btnCompras.setFont(smallFont);
        btnTorneos.setFont(smallFont);
        
        Dimension btnSize = new Dimension(85, 28);
        btnReservas.setPreferredSize(btnSize);
        btnReservas.setMaximumSize(btnSize);
        btnReservas.setMinimumSize(btnSize);
        
        btnPedidos.setPreferredSize(btnSize);
        btnPedidos.setMaximumSize(btnSize);
        btnPedidos.setMinimumSize(btnSize);
        
        btnMiCuenta.setPreferredSize(btnSize);
        btnMiCuenta.setMaximumSize(btnSize);
        btnMiCuenta.setMinimumSize(btnSize);
        
        btnCompras.setPreferredSize(btnSize);
        btnCompras.setMaximumSize(btnSize);
        btnCompras.setMinimumSize(btnSize);
        
        btnTorneos.setPreferredSize(btnSize);
        btnTorneos.setMaximumSize(btnSize);
        btnTorneos.setMinimumSize(btnSize);
        
        // Centrar verticalmente
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