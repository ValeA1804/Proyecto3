package InterfazGrafica.Empleado;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import Proyecto.Empleado;
import Proyecto.Turno;
import Proyecto.CuentaUsuario;

public class PanelIntercambio extends JPanel {
    
    private Empleado empleado;
    private ArrayList<CuentaUsuario> cuentas;
    private JComboBox<String> cmbOtroEmpleado;
    private JComboBox<String> cmbMiTurno;
    private JComboBox<String> cmbSuTurno;
    private JTextArea txtResultado;
    private JButton btnSolicitar;
    private ArrayList<Turno> misTurnosLista;
    private ArrayList<Turno> susTurnosLista;
    private Empleado otroEmpleadoSeleccionado;
    
    public PanelIntercambio(Empleado empleado, ArrayList<CuentaUsuario> cuentas) {
        this.empleado = empleado;
        this.cuentas = cuentas;
        this.misTurnosLista = new ArrayList<>();
        this.susTurnosLista = new ArrayList<>();
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(25, 35, 60));
        initComponents();
        cargarOtrosEmpleados();
    }
    
    private void initComponents() {
        JLabel titulo = new JLabel("Solicitar Intercambio de Turno");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
        
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(new Color(25, 35, 60));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel lblOtroEmpleado = new JLabel("Empleado para intercambiar:");
        lblOtroEmpleado.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFormulario.add(lblOtroEmpleado, gbc);
        gbc.gridx = 1;
        cmbOtroEmpleado = new JComboBox<>();
        cmbOtroEmpleado.setPreferredSize(new Dimension(250, 25));
        panelFormulario.add(cmbOtroEmpleado, gbc);
        
        JLabel lblMiTurno = new JLabel("Mi turno a intercambiar:");
        lblMiTurno.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormulario.add(lblMiTurno, gbc);
        gbc.gridx = 1;
        cmbMiTurno = new JComboBox<>();
        cmbMiTurno.setPreferredSize(new Dimension(250, 25));
        panelFormulario.add(cmbMiTurno, gbc);
        
        JLabel lblSuTurno = new JLabel("Turno del otro empleado:");
        lblSuTurno.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelFormulario.add(lblSuTurno, gbc);
        gbc.gridx = 1;
        cmbSuTurno = new JComboBox<>();
        cmbSuTurno.setPreferredSize(new Dimension(250, 25));
        panelFormulario.add(cmbSuTurno, gbc);
        
        btnSolicitar = new JButton("Solicitar Intercambio");
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
        scrollResultado.setPreferredSize(new Dimension(400, 120));
        
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBackground(new Color(25, 35, 60));
        panelCentral.add(panelFormulario, BorderLayout.NORTH);
        panelCentral.add(scrollResultado, BorderLayout.CENTER);
        
        cmbOtroEmpleado.addActionListener(e -> cargarTurnosOtroEmpleado());
        
        btnSolicitar.addActionListener(e -> solicitarIntercambio());
        
        add(titulo, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
    }
    
    private void cargarOtrosEmpleados() {
        cmbOtroEmpleado.removeAllItems();
        int indice = 0;
        while (indice < cuentas.size()) {
            CuentaUsuario cuenta = cuentas.get(indice);
            if (cuenta instanceof Empleado && cuenta.getId() != empleado.getId()) {
                Empleado e = (Empleado) cuenta;
                cmbOtroEmpleado.addItem(e.getId() + " - " + e.getNombre() + " (" + e.getTipoEmpleado() + ")");
            }
            indice = indice + 1;
        }
        if (cmbOtroEmpleado.getItemCount() == 0) {
            cmbOtroEmpleado.addItem("No hay otros empleados");
            btnSolicitar.setEnabled(false);
        }
    }
    
    private void cargarTurnosOtroEmpleado() {
        if (cmbOtroEmpleado.getItemCount() == 0) return;
        
        String seleccion = (String) cmbOtroEmpleado.getSelectedItem();
        if (seleccion == null || seleccion.equals("No hay otros empleados")) return;
        
        int id = Integer.parseInt(seleccion.split(" - ")[0]);
        otroEmpleadoSeleccionado = null;
        
        int indice = 0;
        while (indice < cuentas.size() && otroEmpleadoSeleccionado == null) {
            CuentaUsuario cuenta = cuentas.get(indice);
            if (cuenta instanceof Empleado && cuenta.getId() == id) {
                otroEmpleadoSeleccionado = (Empleado) cuenta;
            }
            indice = indice + 1;
        }
        
        cargarMisTurnos();
        cargarSusTurnos();
    }
    
    private void cargarMisTurnos() {
        cmbMiTurno.removeAllItems();
        misTurnosLista.clear();
        ArrayList<Turno> turnos = empleado.getTurnos();
        
        if (turnos == null || turnos.isEmpty()) {
            cmbMiTurno.addItem("No tienes turnos");
        } else {
            int indice = 0;
            while (indice < turnos.size()) {
                Turno t = turnos.get(indice);
                misTurnosLista.add(t);
                cmbMiTurno.addItem(t.getDiaSemana() + " - " + t.getHoraInicio() + " a " + t.getHoraSalida());
                indice = indice + 1;
            }
        }
    }
    
    private void cargarSusTurnos() {
        cmbSuTurno.removeAllItems();
        susTurnosLista.clear();
        
        if (otroEmpleadoSeleccionado == null) return;
        
        ArrayList<Turno> turnos = otroEmpleadoSeleccionado.getTurnos();
        
        if (turnos == null || turnos.isEmpty()) {
            cmbSuTurno.addItem("El empleado no tiene turnos");
        } else {
            int indice = 0;
            while (indice < turnos.size()) {
                Turno t = turnos.get(indice);
                susTurnosLista.add(t);
                cmbSuTurno.addItem(t.getDiaSemana() + " - " + t.getHoraInicio() + " a " + t.getHoraSalida());
                indice = indice + 1;
            }
        }
    }
    
    private void solicitarIntercambio() {
        if (otroEmpleadoSeleccionado == null) {
            txtResultado.setText("Seleccione un empleado primero");
            return;
        }
        
        int idxMiTurno = cmbMiTurno.getSelectedIndex();
        int idxSuTurno = cmbSuTurno.getSelectedIndex();
        
        if (idxMiTurno < 0 || idxMiTurno >= misTurnosLista.size()) {
            txtResultado.setText("Seleccione su turno");
            return;
        }
        
        if (idxSuTurno < 0 || idxSuTurno >= susTurnosLista.size()) {
            txtResultado.setText("Seleccione el turno del otro empleado");
            return;
        }
        
        Turno miTurno = misTurnosLista.get(idxMiTurno);
        Turno suTurno = susTurnosLista.get(idxSuTurno);
        
        empleado.solicitarIntercambioTurno(empleado, otroEmpleadoSeleccionado, miTurno, suTurno);
        
        txtResultado.setText("Solicitud de intercambio enviada al administrador.\n\n" +
            "Tu turno: " + miTurno.getDiaSemana() + " " + miTurno.getHoraInicio() + "-" + miTurno.getHoraSalida() + "\n" +
            "Turno de " + otroEmpleadoSeleccionado.getNombre() + ": " + suTurno.getDiaSemana() + " " + suTurno.getHoraInicio() + "-" + suTurno.getHoraSalida());
    }
}