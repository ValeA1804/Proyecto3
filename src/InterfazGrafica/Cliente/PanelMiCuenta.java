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
        initComponents();
    }
    
    private void initComponents() {
        JPanel panelDatos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        int fila = 0;
        
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelDatos.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        lblNombreValor = new JLabel(cliente.getNombre() + " " + cliente.getApellido());
        panelDatos.add(lblNombreValor, gbc);
        
        fila++;
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelDatos.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        lblEmailValor = new JLabel(cliente.getEmail());
        panelDatos.add(lblEmailValor, gbc);
        
        fila++;
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelDatos.add(new JLabel("Cedula:"), gbc);
        gbc.gridx = 1;
        lblCedulaValor = new JLabel(String.valueOf(cliente.getCedula()));
        panelDatos.add(lblCedulaValor, gbc);
        
        fila++;
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelDatos.add(new JLabel("Rol:"), gbc);
        gbc.gridx = 1;
        lblRolValor = new JLabel(cliente.getRol().toString());
        panelDatos.add(lblRolValor, gbc);
        
        fila++;
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelDatos.add(new JLabel("Puntos Fidelidad:"), gbc);
        gbc.gridx = 1;
        lblPuntosValor = new JLabel(String.valueOf(cliente.getPuntosFidelidad()));
        panelDatos.add(lblPuntosValor, gbc);
        
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnCerrarSesion = new JButton("Cerrar Sesion");
        btnCerrarSesion.setBackground(new Color(220, 80, 70));
        btnCerrarSesion.setForeground(Color.RED);
        btnCerrarSesion.setFocusPainted(false);
        btnCerrarSesion.setPreferredSize(new Dimension(150, 40));
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