package InterfazGrafica.Cliente;

import javax.swing.*;
import java.awt.*;
import Proyecto.Cliente;

public class PanelSuperior extends JPanel {
    
private Cliente cliente;
    

public PanelSuperior(Cliente cliente) {
    this.cliente = cliente;
    setLayout(new BorderLayout());
    setPreferredSize(new Dimension(800, 45));
    setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    initComponents();
}

private void initComponents() {
    JLabel lblTitulo = new JLabel("Board Game Cafe");
    lblTitulo.setFont(new Font("Dialog", Font.BOLD, 14));
    
    JPanel panelUsuario = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
    
    JLabel lblImagen = new JLabel("[IMG]");
    lblImagen.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    lblImagen.setPreferredSize(new Dimension(30, 30));
    lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
    lblImagen.setFont(new Font("Dialog", Font.PLAIN, 10));
    
    JPanel panelInfo = new JPanel(new GridLayout(2, 1, 0, 2));
    JLabel lblNombre = new JLabel(cliente.getNombre() + " " + cliente.getApellido());
    JLabel lblRol = new JLabel(cliente.getRol().toString());
    lblNombre.setFont(new Font("Dialog", Font.PLAIN, 10));
    lblRol.setFont(new Font("Dialog", Font.PLAIN, 9));
    panelInfo.add(lblNombre);
    panelInfo.add(lblRol);
    
    panelUsuario.add(lblImagen);
    panelUsuario.add(panelInfo);
    
    add(lblTitulo, BorderLayout.WEST);
    add(panelUsuario, BorderLayout.EAST);
}
}