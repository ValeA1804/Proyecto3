package InterfazGrafica.Cliente;

import javax.swing.*;
import java.awt.*;
import Proyecto.Cliente;

public class PanelSuperior extends JPanel {
    
    private Cliente cliente;

    public PanelSuperior(Cliente cliente) {
        this.cliente = cliente;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 50));
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        setBackground(new Color(20, 30, 50));
        initComponents();
    }

    private void initComponents() {
        JLabel lblTitulo = new JLabel("Board Game Cafe");
        lblTitulo.setFont(new Font("Dialog", Font.BOLD, 16));
        lblTitulo.setForeground(Color.WHITE);
        
        JPanel panelUsuario = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        panelUsuario.setOpaque(false);
        
        JPanel panelInfo = new JPanel(new GridLayout(2, 1, 0, 2));
        panelInfo.setOpaque(false);
        JLabel lblNombre = new JLabel(cliente.getNombre() + " " + cliente.getApellido());
        JLabel lblRol = new JLabel(cliente.getRol().toString());
        lblNombre.setFont(new Font("Dialog", Font.PLAIN, 11));
        lblNombre.setForeground(Color.WHITE);
        lblRol.setFont(new Font("Dialog", Font.PLAIN, 10));
        lblRol.setForeground(new Color(200, 220, 180));
        panelInfo.add(lblNombre);
        panelInfo.add(lblRol);
        
        panelUsuario.add(panelInfo);
        
        add(lblTitulo, BorderLayout.WEST);
        add(panelUsuario, BorderLayout.EAST);
    }
}