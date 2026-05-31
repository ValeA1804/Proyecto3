package InterfazGrafica.Empleado;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import Proyecto.Empleado;
import Proyecto.Turno;

public class PanelMisTurnos extends JPanel {
    
    private Empleado empleado;
    private JTextArea txtTurnos;
    
    public PanelMisTurnos(Empleado empleado) {
        this.empleado = empleado;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(25, 35, 60));
        initComponents();
        cargarTurnos();
    }
    
    private void initComponents() {
        JLabel titulo = new JLabel("Mis Turnos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
        
        txtTurnos = new JTextArea();
        txtTurnos.setEditable(false);
        txtTurnos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtTurnos.setBackground(new Color(35, 45, 70));
        txtTurnos.setForeground(Color.WHITE);
        txtTurnos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollTurnos = new JScrollPane(txtTurnos);
        scrollTurnos.setBorder(BorderFactory.createEmptyBorder());
        scrollTurnos.getViewport().setBackground(new Color(35, 45, 70));
        
        add(titulo, BorderLayout.NORTH);
        add(scrollTurnos, BorderLayout.CENTER);
    }
    
    private void cargarTurnos() {
        StringBuilder sb = new StringBuilder();
        ArrayList<Turno> turnos = empleado.getTurnos();
        
        if (turnos == null || turnos.isEmpty()) {
            sb.append("No tienes turnos asignados.\n");
        } else {
            int indice = 0;
            while (indice < turnos.size()) {
                Turno t = turnos.get(indice);
                sb.append("Turno ").append(indice + 1).append(":\n");
                sb.append("  Dia: ").append(t.getDiaSemana()).append("\n");
                sb.append("  Horario: ").append(t.getHoraInicio()).append(" - ").append(t.getHoraSalida()).append("\n");
                sb.append("  Fecha: ").append(t.getFecha()).append("\n\n");
                indice = indice + 1;
            }
        }
        txtTurnos.setText(sb.toString());
    }
}