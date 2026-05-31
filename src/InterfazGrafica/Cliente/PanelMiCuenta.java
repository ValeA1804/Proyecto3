package InterfazGrafica.Cliente;

import javax.swing.*;
import java.awt.*;
import Proyecto.Cliente;

public class PanelMiCuenta extends JPanel {
    
    private Cliente cliente;
    private JLabel lblNombreValor;
    private JLabel lblEmailValor;
    private JLabel lblCedulaValor;
    private JLabel lblRolValor;
    private JLabel lblPuntosValor;
    private JButton btnCerrarSesion;
    
    public PanelMiCuenta(Cliente cliente) {
        this.cliente = cliente;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(new Color(25, 35, 60));
        initComponents();
    }
    
    private void initComponents() {
        JPanel panelDatos = new JPanel(new GridBagLayout());
        panelDatos.setBackground(new Color(25, 35, 60));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        int fila = 0;
        
        JLabel lblNombreTitulo = new JLabel("Nombre:");
        lblNombreTitulo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblNombreTitulo.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelDatos.add(lblNombreTitulo, gbc);
        gbc.gridx = 1;
        lblNombreValor = new JLabel(cliente.getNombre() + " " + cliente.getApellido());
        lblNombreValor.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblNombreValor.setForeground(Color.WHITE);
        panelDatos.add(lblNombreValor, gbc);
        
        fila++;
        JLabel lblEmailTitulo = new JLabel("Email:");
        lblEmailTitulo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblEmailTitulo.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelDatos.add(lblEmailTitulo, gbc);
        gbc.gridx = 1;
        lblEmailValor = new JLabel(cliente.getEmail());
        lblEmailValor.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblEmailValor.setForeground(Color.WHITE);
        panelDatos.add(lblEmailValor, gbc);
        
        fila++;
        JLabel lblCedulaTitulo = new JLabel("Cedula:");
        lblCedulaTitulo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblCedulaTitulo.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelDatos.add(lblCedulaTitulo, gbc);
        gbc.gridx = 1;
        lblCedulaValor = new JLabel(String.valueOf(cliente.getCedula()));
        lblCedulaValor.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblCedulaValor.setForeground(Color.WHITE);
        panelDatos.add(lblCedulaValor, gbc);
        
        fila++;
        JLabel lblRolTitulo = new JLabel("Rol:");
        lblRolTitulo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblRolTitulo.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelDatos.add(lblRolTitulo, gbc);
        gbc.gridx = 1;
        lblRolValor = new JLabel(cliente.getRol().toString());
        lblRolValor.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblRolValor.setForeground(Color.WHITE);
        panelDatos.add(lblRolValor, gbc);
        
        fila++;
        JLabel lblPuntosTitulo = new JLabel("Puntos Fidelidad:");
        lblPuntosTitulo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblPuntosTitulo.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelDatos.add(lblPuntosTitulo, gbc);
        gbc.gridx = 1;
        lblPuntosValor = new JLabel(String.valueOf(cliente.getPuntosFidelidad()));
        lblPuntosValor.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblPuntosValor.setForeground(new Color(50, 200, 120));
        panelDatos.add(lblPuntosValor, gbc);
        
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setBackground(new Color(25, 35, 60));
        btnCerrarSesion = new JButton("Cerrar Sesion");
        btnCerrarSesion.setBackground(new Color(220, 70, 70));
        btnCerrarSesion.setForeground(Color.WHITE);
        btnCerrarSesion.setFocusPainted(false);
        btnCerrarSesion.setBorderPainted(false);
        btnCerrarSesion.setOpaque(true);
        btnCerrarSesion.setPreferredSize(new Dimension(150, 40));
        btnCerrarSesion.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCerrarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCerrarSesion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCerrarSesion.setBackground(new Color(240, 90, 90));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCerrarSesion.setBackground(new Color(220, 70, 70));
            }
        });
        panelBoton.add(btnCerrarSesion);
        
        add(panelDatos, BorderLayout.CENTER);
        add(panelBoton, BorderLayout.SOUTH);
    }
    
    public void actualizarDatos() {
        lblNombreValor.setText(cliente.getNombre() + " " + cliente.getApellido());
        lblEmailValor.setText(cliente.getEmail());
        lblCedulaValor.setText(String.valueOf(cliente.getCedula()));
        lblPuntosValor.setText(String.valueOf(cliente.getPuntosFidelidad()));
    }
    
    public JButton getBtnCerrarSesion() {
        return btnCerrarSesion;
    }
}