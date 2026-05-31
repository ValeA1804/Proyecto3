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
        initComponents();
    }
    
    private void initComponents() {
        JLabel titulo = new JLabel("Gestion de Reservas");
        titulo.setFont(new Font("Dialog", Font.BOLD, 14));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        btnCrear = new JButton("Crear Reserva");
        btnCancelar = new JButton("Cancelar Reserva");
        btnConsultar = new JButton("Consultar Reservas");
        
        Font btnFont = new Font("Dialog", Font.PLAIN, 11);
        btnCrear.setFont(btnFont);
        btnCancelar.setFont(btnFont);
        btnConsultar.setFont(btnFont);
        
        panelBotones.add(btnCrear);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnConsultar);
        
        JPanel panelNorte = new JPanel();
        panelNorte.setLayout(new BoxLayout(panelNorte, BoxLayout.Y_AXIS));
        panelNorte.add(titulo);
        panelNorte.add(Box.createRigidArea(new Dimension(0, 5)));
        panelNorte.add(panelBotones);
        
        txtResultados = new JTextArea();
        txtResultados.setEditable(false);
        txtResultados.setFont(new Font("Dialog", Font.PLAIN, 12));
        JScrollPane scrollResultados = new JScrollPane(txtResultados);
        scrollResultados.setBorder(BorderFactory.createTitledBorder("Resultados"));
        scrollResultados.setPreferredSize(new Dimension(400, 250));
        
        add(panelNorte, BorderLayout.NORTH);
        add(scrollResultados, BorderLayout.CENTER);
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
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 8, 4, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("Personas:"), gbc);
        gbc.gridx = 1;
        JSpinner spnPersonas = new JSpinner(new SpinnerNumberModel(1, 1, 20, 1));
        dialog.add(spnPersonas, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("Menores edad:"), gbc);
        gbc.gridx = 1;
        JSpinner spnMenores = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        dialog.add(spnMenores, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(new JLabel("Menores 5 anos:"), gbc);
        gbc.gridx = 1;
        JSpinner spnMenores5 = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        dialog.add(spnMenores5, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        dialog.add(new JLabel("Hora:"), gbc);
        gbc.gridx = 1;
        JTextField txtHora = new JTextField("14:00");
        dialog.add(txtHora, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        dialog.add(new JLabel("Fecha:"), gbc);
        gbc.gridx = 1;
        JTextField txtFecha = new JTextField(java.time.LocalDate.now().toString());
        dialog.add(txtFecha, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5;
        dialog.add(new JLabel("Mesa:"), gbc);
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