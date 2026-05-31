package InterfazGrafica.Empleado;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import Proyecto.Empleado;
import Proyecto.Turno;

public class PanelCambioGeneral extends JPanel {
    
    private Empleado empleado;
    private JComboBox<String> cmbTurnos;
    private JTextField txtNuevaHoraInicio;
    private JTextField txtNuevaHoraFin;
    private JTextArea txtResultado;
    private JButton btnSolicitar;
    private ArrayList<Turno> turnosLista;
    
    public PanelCambioGeneral(Empleado empleado) {
        this.empleado = empleado;
        this.turnosLista = new ArrayList<>();
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(25, 35, 60));
        initComponents();
        cargarTurnos();
    }
    
    private void initComponents() {
        JLabel titulo = new JLabel("Solicitar Cambio General de Turno");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
        
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(new Color(25, 35, 60));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel lblTurno = new JLabel("Turno a cambiar:");
        lblTurno.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFormulario.add(lblTurno, gbc);
        gbc.gridx = 1;
        cmbTurnos = new JComboBox<>();
        cmbTurnos.setPreferredSize(new Dimension(250, 25));
        panelFormulario.add(cmbTurnos, gbc);
        
        JLabel lblNuevaHoraInicio = new JLabel("Nueva hora inicio:");
        lblNuevaHoraInicio.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormulario.add(lblNuevaHoraInicio, gbc);
        gbc.gridx = 1;
        txtNuevaHoraInicio = new JTextField("09:00", 10);
        panelFormulario.add(txtNuevaHoraInicio, gbc);
        
        JLabel lblNuevaHoraFin = new JLabel("Nueva hora fin:");
        lblNuevaHoraFin.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelFormulario.add(lblNuevaHoraFin, gbc);
        gbc.gridx = 1;
        txtNuevaHoraFin = new JTextField("17:00", 10);
        panelFormulario.add(txtNuevaHoraFin, gbc);
        
        btnSolicitar = new JButton("Solicitar Cambio");
        btnSolicitar.setBackground(new Color(50, 200, 120));
        btnSolicitar.setForeground(Color.WHITE);
        btnSolicitar.setFocusPainted(false);
        btnSolicitar.setBorderPainted(false);
        btnSolicitar.setOpaque(true);
        btnSolicitar.setFont(new Font("Segoe UI", Font.BOLD, 11));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panelFormulario.add(btnSolicitar, gbc);
        
        txtResultado = new JTextArea();
        txtResultado.setEditable(false);
        txtResultado.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtResultado.setBackground(new Color(35, 45, 70));
        txtResultado.setForeground(Color.WHITE);
        txtResultado.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        JScrollPane scrollResultado = new JScrollPane(txtResultado);
        scrollResultado.setBorder(BorderFactory.createEmptyBorder());
        scrollResultado.getViewport().setBackground(new Color(35, 45, 70));
        scrollResultado.setPreferredSize(new Dimension(400, 100));
        
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBackground(new Color(25, 35, 60));
        panelCentral.add(panelFormulario, BorderLayout.NORTH);
        panelCentral.add(scrollResultado, BorderLayout.CENTER);
        
        btnSolicitar.addActionListener(e -> solicitarCambio());
        
        add(titulo, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
    }
    
    private void cargarTurnos() {
        cmbTurnos.removeAllItems();
        turnosLista.clear();
        ArrayList<Turno> turnos = empleado.getTurnos();
        
        if (turnos == null || turnos.isEmpty()) {
            cmbTurnos.addItem("No hay turnos disponibles");
            btnSolicitar.setEnabled(false);
        } else {
            int indice = 0;
            while (indice < turnos.size()) {
                Turno t = turnos.get(indice);
                turnosLista.add(t);
                cmbTurnos.addItem(t.getDiaSemana() + " - " + t.getHoraInicio() + " a " + t.getHoraSalida());
                indice = indice + 1;
            }
            btnSolicitar.setEnabled(true);
        }
    }
    
    private void solicitarCambio() {
        if (turnosLista.isEmpty()) {
            txtResultado.setText("No hay turnos para cambiar");
            return;
        }
        
        int idx = cmbTurnos.getSelectedIndex();
        if (idx < 0 || idx >= turnosLista.size()) {
            txtResultado.setText("Seleccione un turno valido");
            return;
        }
        
        Turno turnoActual = turnosLista.get(idx);
        String nuevaHoraInicio = txtNuevaHoraInicio.getText();
        String nuevaHoraFin = txtNuevaHoraFin.getText();
        
        Turno nuevoTurno = new Turno(turnoActual.getDiaSemana(), nuevaHoraInicio, nuevaHoraFin, turnoActual.getFecha());
        
        empleado.solicitarCambioTurnoGeneral(turnoActual, nuevoTurno);
        
        txtResultado.setText("Solicitud de cambio enviada al administrador.\n\n" +
            "Turno original: " + turnoActual.getDiaSemana() + " " + turnoActual.getHoraInicio() + "-" + turnoActual.getHoraSalida() + "\n" +
            "Turno solicitado: " + nuevoTurno.getDiaSemana() + " " + nuevaHoraInicio + "-" + nuevaHoraFin);
    }
}