package InterfazGrafica.Cliente;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import Proyecto.Cliente;
import Proyecto.Torneo;
import Proyecto.TorneoCompetitivo;
import Proyecto.TorneoAmistoso;

public class PanelTorneos extends JPanel {
    
    private Cliente cliente;
    private ArrayList<Torneo> torneosDisponibles;
    private JComboBox<String> cmbTorneos;
    private JButton btnInscribir;
    private JButton btnCancelar;
    private JButton btnConsultar;
    private JTextArea txtInfo;
    private JTextArea txtTorneoSeleccionado;
    
    public PanelTorneos(Cliente cliente, ArrayList<Torneo> torneos) {
        this.cliente = cliente;
        this.torneosDisponibles = torneos;
        setLayout(new BorderLayout(0, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(25, 35, 60));
        initComponents();
        cargarTorneosEnCombo();
    }
    
    private void initComponents() {
        JLabel titulo = new JLabel("Torneos");
        titulo.setFont(new Font("Dialog", Font.BOLD, 14));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelBotones.setBackground(new Color(25, 35, 60));
        btnInscribir = new JButton("Inscribirse");
        btnCancelar = new JButton("Cancelar Inscripcion");
        btnConsultar = new JButton("Mis Torneos");
        
        Font btnFont = new Font("Dialog", Font.PLAIN, 11);
        btnInscribir.setFont(btnFont);
        btnCancelar.setFont(btnFont);
        btnConsultar.setFont(btnFont);
        
        btnInscribir.setBackground(new Color(50, 200, 120));
        btnInscribir.setForeground(Color.WHITE);
        btnCancelar.setBackground(new Color(220, 70, 70));
        btnCancelar.setForeground(Color.WHITE);
        btnConsultar.setBackground(new Color(80, 140, 255));
        btnConsultar.setForeground(Color.WHITE);
        
        btnInscribir.setFocusPainted(false);
        btnCancelar.setFocusPainted(false);
        btnConsultar.setFocusPainted(false);
        btnInscribir.setBorderPainted(false);
        btnCancelar.setBorderPainted(false);
        btnConsultar.setBorderPainted(false);
        btnInscribir.setOpaque(true);
        btnCancelar.setOpaque(true);
        btnConsultar.setOpaque(true);
        
        panelBotones.add(btnInscribir);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnConsultar);
        
        cmbTorneos = new JComboBox<>();
        cmbTorneos.setFont(new Font("Dialog", Font.PLAIN, 11));
        cmbTorneos.setForeground(Color.BLACK);
        cmbTorneos.setBackground(Color.WHITE);
        cmbTorneos.addActionListener(e -> mostrarTorneoSeleccionado());
        
        JPanel panelSelector = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelSelector.setBackground(new Color(25, 35, 60));
        panelSelector.add(cmbTorneos);
        
        txtTorneoSeleccionado = new JTextArea();
        txtTorneoSeleccionado.setEditable(false);
        txtTorneoSeleccionado.setFont(new Font("Dialog", Font.PLAIN, 11));
        txtTorneoSeleccionado.setBackground(new Color(35, 45, 70));
        txtTorneoSeleccionado.setForeground(Color.WHITE);
        txtTorneoSeleccionado.setRows(5);
        txtTorneoSeleccionado.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 100, 120)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        JScrollPane scrollSeleccionado = new JScrollPane(txtTorneoSeleccionado);
        scrollSeleccionado.setBorder(BorderFactory.createEmptyBorder());
        scrollSeleccionado.setPreferredSize(new Dimension(400, 100));
        scrollSeleccionado.getViewport().setBackground(new Color(35, 45, 70));
        
        JLabel lblTorneoSeleccionado = new JLabel("Torneo seleccionado");
        lblTorneoSeleccionado.setFont(new Font("Dialog", Font.BOLD, 11));
        lblTorneoSeleccionado.setForeground(Color.WHITE);
        lblTorneoSeleccionado.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        
        JPanel panelSeleccionado = new JPanel(new BorderLayout());
        panelSeleccionado.setBackground(new Color(25, 35, 60));
        panelSeleccionado.add(lblTorneoSeleccionado, BorderLayout.NORTH);
        panelSeleccionado.add(scrollSeleccionado, BorderLayout.CENTER);
        
        JPanel panelNorte = new JPanel();
        panelNorte.setLayout(new BoxLayout(panelNorte, BoxLayout.Y_AXIS));
        panelNorte.setBackground(new Color(25, 35, 60));
        panelNorte.add(titulo);
        panelNorte.add(Box.createRigidArea(new Dimension(0, 5)));
        panelNorte.add(panelSelector);
        panelNorte.add(Box.createRigidArea(new Dimension(0, 5)));
        panelNorte.add(panelSeleccionado);
        panelNorte.add(Box.createRigidArea(new Dimension(0, 5)));
        panelNorte.add(panelBotones);
        
        txtInfo = new JTextArea();
        txtInfo.setEditable(false);
        txtInfo.setFont(new Font("Dialog", Font.PLAIN, 12));
        txtInfo.setBackground(new Color(35, 45, 70));
        txtInfo.setForeground(Color.WHITE);
        txtInfo.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        JScrollPane scrollInfo = new JScrollPane(txtInfo);
        scrollInfo.setBorder(BorderFactory.createEmptyBorder());
        scrollInfo.setPreferredSize(new Dimension(400, 150));
        scrollInfo.getViewport().setBackground(new Color(35, 45, 70));
        
        JLabel lblInformacion = new JLabel("Informacion");
        lblInformacion.setFont(new Font("Dialog", Font.BOLD, 11));
        lblInformacion.setForeground(Color.WHITE);
        lblInformacion.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        
        JPanel panelInfo = new JPanel(new BorderLayout());
        panelInfo.setBackground(new Color(25, 35, 60));
        panelInfo.add(lblInformacion, BorderLayout.NORTH);
        panelInfo.add(scrollInfo, BorderLayout.CENTER);
        
        add(panelNorte, BorderLayout.NORTH);
        add(panelInfo, BorderLayout.CENTER);
    }
    
    private void cargarTorneosEnCombo() {
        cmbTorneos.removeAllItems();
        boolean hayTorneos = (torneosDisponibles != null && !torneosDisponibles.isEmpty());
        
        if (!hayTorneos) {
            cmbTorneos.addItem("No hay torneos disponibles");
            btnInscribir.setEnabled(false);
            btnCancelar.setEnabled(false);
            txtTorneoSeleccionado.setText("");
        } else {
            int indice = 0;
            while (indice < torneosDisponibles.size()) {
                Torneo t = torneosDisponibles.get(indice);
                cmbTorneos.addItem(t.getNombre() + " - " + t.getFecha());
                indice = indice + 1;
            }
            btnInscribir.setEnabled(true);
            btnCancelar.setEnabled(true);
            mostrarTorneoSeleccionado();
        }
    }
    
    private void mostrarTorneoSeleccionado() {
        Torneo t = getTorneoSeleccionado();
        if (t != null) {
            String premio = "";
            if (t instanceof TorneoCompetitivo) {
                TorneoCompetitivo tc = (TorneoCompetitivo) t;
                premio = tc.getPremio();
            } else if (t instanceof TorneoAmistoso) {
                TorneoAmistoso ta = (TorneoAmistoso) t;
                premio = ta.getPremio();
            }
            
            txtTorneoSeleccionado.setText(
                "Nombre: " + t.getNombre() + "\n" +
                "Fecha: " + t.getFecha() + "\n" +
                "Cupos: " + t.getTotalCupos() + "\n" +
                "Premio: " + premio + "\n" +
                "Costo inscripcion: $" + t.getCostoInscripcion()
            );
        } else {
            txtTorneoSeleccionado.setText("");
        }
    }
    
    public JButton getBtnInscribir() { return btnInscribir; }
    public JButton getBtnCancelar() { return btnCancelar; }
    public JButton getBtnConsultar() { return btnConsultar; }
    
    public Torneo getTorneoSeleccionado() {
        boolean hayTorneos = (torneosDisponibles != null && !torneosDisponibles.isEmpty());
        if (!hayTorneos) return null;
        int idx = cmbTorneos.getSelectedIndex();
        if (idx >= 0 && idx < torneosDisponibles.size()) {
            return torneosDisponibles.get(idx);
        }
        return null;
    }
    
    public void mostrarInfo(String texto) { 
        txtInfo.setText(texto); 
    }
}