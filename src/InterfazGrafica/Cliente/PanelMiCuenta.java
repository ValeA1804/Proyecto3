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
    
    public PanelMiCuenta(Cliente cliente) {
        this.cliente = cliente;
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        initComponents();
    }
    
    private void initComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Fila 0 - Nombre
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        lblNombreValor = new JLabel(cliente.getNombre() + " " + cliente.getApellido());
        add(lblNombreValor, gbc);
        
        // Fila 1 - Email
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        lblEmailValor = new JLabel(cliente.getEmail());
        add(lblEmailValor, gbc);
        
        // Fila 2 - Cedula
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Cedula:"), gbc);
        gbc.gridx = 1;
        lblCedulaValor = new JLabel(String.valueOf(cliente.getCedula()));
        add(lblCedulaValor, gbc);
        
        // Fila 3 - Rol
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Rol:"), gbc);
        gbc.gridx = 1;
        lblRolValor = new JLabel(cliente.getRol().toString());
        add(lblRolValor, gbc);
        
        // Fila 4 - Puntos
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Puntos Fidelidad:"), gbc);
        gbc.gridx = 1;
        lblPuntosValor = new JLabel(String.valueOf(cliente.getPuntosFidelidad()));
        add(lblPuntosValor, gbc);
    }
    
    public void actualizarDatos() {
        lblNombreValor.setText(cliente.getNombre() + " " + cliente.getApellido());
        lblEmailValor.setText(cliente.getEmail());
        lblCedulaValor.setText(String.valueOf(cliente.getCedula()));
        lblPuntosValor.setText(String.valueOf(cliente.getPuntosFidelidad()));
    }
}