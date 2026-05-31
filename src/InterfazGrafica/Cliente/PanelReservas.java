package InterfazGrafica.Cliente;

import javax.swing.*;
import java.awt.*;
import Proyecto.Cliente;
import Proyecto.Cafeteria;
import Proyecto.Mesa;
import Proyecto.Reserva;

public class PanelReservas extends JPanel {
    
    private Cliente cliente;
    private Cafeteria cafeteria;
    private JButton btnCrear;
    private JButton btnCancelar;
    private JButton btnConsultar;
    private JTextArea txtResultados;
    
    public PanelReservas(Cliente cliente, Cafeteria cafeteria) {
        this.cliente = cliente;
        this.cafeteria = cafeteria;
        setLayout(new BorderLayout(0, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(25, 35, 60));
        initComponents();
    }
    
    private void initComponents() {
        JLabel titulo = new JLabel("Gestion de Reservas");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        panelBotones.setBackground(new Color(25, 35, 60));
        btnCrear = crearBoton("Crear Reserva", new Color(50, 200, 120));
        btnCancelar = crearBoton("Cancelar Reserva", new Color(220, 70, 70));
        btnConsultar = crearBoton("Consultar Reservas", new Color(80, 140, 255));
        
        panelBotones.add(btnCrear);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnConsultar);
        
        JPanel panelNorte = new JPanel();
        panelNorte.setLayout(new BoxLayout(panelNorte, BoxLayout.Y_AXIS));
        panelNorte.setBackground(new Color(25, 35, 60));
        panelNorte.add(titulo);
        panelNorte.add(Box.createRigidArea(new Dimension(0, 5)));
        panelNorte.add(panelBotones);
        
        txtResultados = new JTextArea();
        txtResultados.setEditable(false);
        txtResultados.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtResultados.setBackground(new Color(35, 45, 70));
        txtResultados.setForeground(Color.WHITE);
        txtResultados.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        JScrollPane scrollResultados = new JScrollPane(txtResultados);
        scrollResultados.setBorder(BorderFactory.createEmptyBorder());
        scrollResultados.getViewport().setBackground(new Color(35, 45, 70));
        scrollResultados.setPreferredSize(new Dimension(400, 250));
        
        add(panelNorte, BorderLayout.NORTH);
        add(scrollResultados, BorderLayout.CENTER);
    }
    
    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setPreferredSize(new Dimension(140, 32));
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
    
    public JButton getBtnCrear() { return btnCrear; }
    public JButton getBtnCancelar() { return btnCancelar; }
    public JButton getBtnConsultar() { return btnConsultar; }
    
    public void mostrarResultados(String texto) { txtResultados.setText(texto); }
    
    public void mostrarDialogoCrearReserva() {
        if (cafeteria == null || cafeteria.getMesas() == null) {
            JOptionPane.showMessageDialog(this, "Error: Cafeteria no disponible");
            return;
        }
        
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Crear Reserva", true);
        dialog.setSize(380, 320);
        dialog.setLayout(new GridBagLayout());
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(new Color(25, 35, 60));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 8, 4, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel lblPersonas = new JLabel("Personas:");
        lblPersonas.setForeground(Color.WHITE);
        lblPersonas.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(lblPersonas, gbc);
        gbc.gridx = 1;
        JSpinner spnPersonas = new JSpinner(new SpinnerNumberModel(1, 1, 20, 1));
        dialog.add(spnPersonas, gbc);
        
        JLabel lblMenores = new JLabel("Menores edad:");
        lblMenores.setForeground(Color.WHITE);
        lblMenores.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(lblMenores, gbc);
        gbc.gridx = 1;
        JSpinner spnMenores = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        dialog.add(spnMenores, gbc);
        
        JLabel lblMenores5 = new JLabel("Menores 5 anos:");
        lblMenores5.setForeground(Color.WHITE);
        lblMenores5.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(lblMenores5, gbc);
        gbc.gridx = 1;
        JSpinner spnMenores5 = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        dialog.add(spnMenores5, gbc);
        
        JLabel lblHora = new JLabel("Hora:");
        lblHora.setForeground(Color.WHITE);
        lblHora.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        gbc.gridx = 0; gbc.gridy = 3;
        dialog.add(lblHora, gbc);
        gbc.gridx = 1;
        JTextField txtHora = new JTextField("14:00");
        dialog.add(txtHora, gbc);
        
        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setForeground(Color.WHITE);
        lblFecha.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        gbc.gridx = 0; gbc.gridy = 4;
        dialog.add(lblFecha, gbc);
        gbc.gridx = 1;
        JTextField txtFecha = new JTextField(java.time.LocalDate.now().toString());
        dialog.add(txtFecha, gbc);
        
        JLabel lblMesa = new JLabel("Mesa:");
        lblMesa.setForeground(Color.WHITE);
        lblMesa.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        gbc.gridx = 0; gbc.gridy = 5;
        dialog.add(lblMesa, gbc);
        gbc.gridx = 1;
        JComboBox<Mesa> cmbMesa = new JComboBox<>();
        for (Mesa m : cafeteria.getMesas()) {
            if (m.isDisponible()) {
                cmbMesa.addItem(m);
            }
        }
        if (cmbMesa.getItemCount() == 0) {
            cmbMesa.addItem(null);
            cmbMesa.setEnabled(false);
        }
        dialog.add(cmbMesa, gbc);
        
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 8, 10, 8);
        JButton btnGuardar = new JButton("Guardar Reserva");
        btnGuardar.setBackground(new Color(50, 200, 120));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setOpaque(true);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 11));
        dialog.add(btnGuardar, gbc);
        
        btnGuardar.addActionListener(e -> {
            Mesa mesa = (Mesa) cmbMesa.getSelectedItem();
            if (mesa == null) {
                mostrarResultados("No hay mesas disponibles");
                dialog.dispose();
                return;
            }
            
            int personas = (int) spnPersonas.getValue();
            int menores = (int) spnMenores.getValue();
            int menores5 = (int) spnMenores5.getValue();
            String hora = txtHora.getText();
            String fecha = txtFecha.getText();
            int id = cliente.getReservas().size() + 1;
            
            Reserva nueva = cliente.crearReserva(cafeteria, personas, menores, menores5, id, hora, "22:00", fecha, mesa);
            
            if (nueva != null) {
                mostrarResultados("Reserva creada!\nMesa: " + mesa.getNumeroMesa() + "\nFecha: " + fecha + "\nHora: " + hora + "\nPersonas: " + personas);
                dialog.dispose();
            } else {
                mostrarResultados("Error: No se pudo crear la reserva");
            }
        });
        
        dialog.setVisible(true);
    }
}