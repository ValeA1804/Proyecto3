package InterfazGrafica.Empleado;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanelBotonesEmpleado extends JPanel {
    
    private JButton btnMisTurnos;
    private JButton btnCambioGeneral;
    private JButton btnIntercambio;
    private JButton btnMesas;
    private JButton btnComprar;
    private JButton btnPedirPrestado;
    
    public PanelBotonesEmpleado() {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(130, 500));
        setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        setBackground(new Color(25, 35, 60));
        initComponents();
    }
    
    private void initComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);
        
        btnMisTurnos = crearBoton("Mis Turnos", new Color(80, 140, 255));
        btnCambioGeneral = crearBoton("Cambio General", new Color(255, 160, 50));
        btnIntercambio = crearBoton("Intercambio", new Color(50, 200, 120));
        btnMesas = crearBoton("Ver Mesas", new Color(80, 140, 255));
        btnComprar = crearBoton("Comprar", new Color(50, 200, 120));
        btnPedirPrestado = crearBoton("Pedir Prestado", new Color(255, 160, 50));
        
        gbc.gridy = 0;
        add(Box.createVerticalGlue(), gbc);
        
        gbc.gridy = 1;
        add(btnMisTurnos, gbc);
        gbc.gridy = 2;
        add(btnCambioGeneral, gbc);
        gbc.gridy = 3;
        add(btnIntercambio, gbc);
        gbc.gridy = 4;
        add(btnMesas, gbc);
        gbc.gridy = 5;
        add(btnComprar, gbc);
        gbc.gridy = 6;
        add(btnPedirPrestado, gbc);
        
        gbc.gridy = 7;
        add(Box.createVerticalGlue(), gbc);
    }
    
    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setPreferredSize(new Dimension(110, 35));
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
    
    public void setListenerMisTurnos(ActionListener listener) { btnMisTurnos.addActionListener(listener); }
    public void setListenerCambioGeneral(ActionListener listener) { btnCambioGeneral.addActionListener(listener); }
    public void setListenerIntercambio(ActionListener listener) { btnIntercambio.addActionListener(listener); }
    public void setListenerMesas(ActionListener listener) { btnMesas.addActionListener(listener); }
    public void setListenerComprar(ActionListener listener) { btnComprar.addActionListener(listener); }
    public void setListenerPedirPrestado(ActionListener listener) { btnPedirPrestado.addActionListener(listener); }
}