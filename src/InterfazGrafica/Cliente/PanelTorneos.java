package InterfazGrafica.Cliente;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import Proyecto.Cliente;
import Proyecto.Torneo;

public class PanelTorneos extends JPanel {
    
    private Cliente cliente;
    private ArrayList<Torneo> torneosDisponibles;
    private JComboBox<String> cmbTorneos;
    private JButton btnInscribir;
    private JButton btnCancelar;
    private JButton btnConsultar;
    private JTextArea txtInfo;
    
    public PanelTorneos(Cliente cliente, ArrayList<Torneo> torneos) {
        this.cliente = cliente;
        this.torneosDisponibles = torneos;
        setLayout(new BorderLayout(0, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        initComponents();
        cargarTorneosEnCombo();
    }
    
    private void initComponents() {
        JLabel titulo = new JLabel("Torneos");
        titulo.setFont(new Font("Dialog", Font.BOLD, 14));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Panel superior con botones ARRIBA
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        btnInscribir = new JButton("Inscribirse");
        btnCancelar = new JButton("Cancelar");
        btnConsultar = new JButton("Mis Torneos");
        
        Font btnFont = new Font("Dialog", Font.PLAIN, 11);
        btnInscribir.setFont(btnFont);
        btnCancelar.setFont(btnFont);
        btnConsultar.setFont(btnFont);
        
        panelBotones.add(btnInscribir);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnConsultar);
        
        // Selector de torneo DEBAJO de los botones
        cmbTorneos = new JComboBox<>();
        cmbTorneos.setFont(new Font("Dialog", Font.PLAIN, 11));
        cmbTorneos.setMaximumSize(new Dimension(400, 25));
        
        JPanel panelSelector = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelSelector.add(cmbTorneos);
        
        // Panel norte con todo (titulo + botones + selector)
        JPanel panelNorte = new JPanel();
        panelNorte.setLayout(new BoxLayout(panelNorte, BoxLayout.Y_AXIS));
        panelNorte.add(titulo);
        panelNorte.add(Box.createRigidArea(new Dimension(0, 5)));
        panelNorte.add(panelBotones);
        panelNorte.add(Box.createRigidArea(new Dimension(0, 5)));
        panelNorte.add(panelSelector);
        
        // Area de informacion - mas grande y mas cerca
        txtInfo = new JTextArea();
        txtInfo.setEditable(false);
        txtInfo.setFont(new Font("Dialog", Font.PLAIN, 12));
        JScrollPane scrollInfo = new JScrollPane(txtInfo);
        scrollInfo.setBorder(BorderFactory.createTitledBorder("Informacion"));
        
        add(panelNorte, BorderLayout.NORTH);
        add(scrollInfo, BorderLayout.CENTER);
    }
    
    private void cargarTorneosEnCombo() {
        cmbTorneos.removeAllItems();
        if (torneosDisponibles == null || torneosDisponibles.isEmpty()) {
            cmbTorneos.addItem("No hay torneos disponibles");
            btnInscribir.setEnabled(false);
            btnCancelar.setEnabled(false);
        } else {
            for (Torneo t : torneosDisponibles) {
                cmbTorneos.addItem(t.getNombre() + " - " + t.getFecha());
            }
            btnInscribir.setEnabled(true);
            btnCancelar.setEnabled(true);
        }
    }
    
    public JButton getBtnInscribir() { return btnInscribir; }
    public JButton getBtnCancelar() { return btnCancelar; }
    public JButton getBtnConsultar() { return btnConsultar; }
    
    public Torneo getTorneoSeleccionado() {
        if (torneosDisponibles == null || torneosDisponibles.isEmpty()) return null;
        int idx = cmbTorneos.getSelectedIndex();
        if (idx >= 0 && idx < torneosDisponibles.size()) {
            return torneosDisponibles.get(idx);
        }
        return null;
    }
    
    public void mostrarInfo(String texto) { txtInfo.setText(texto); }
}