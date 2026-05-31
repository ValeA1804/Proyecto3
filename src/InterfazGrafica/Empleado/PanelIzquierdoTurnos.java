package InterfazGrafica.Empleado;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import Proyecto.Empleado;
import Proyecto.Turno;

public class PanelIzquierdoTurnos extends JPanel {
    
    private Empleado empleado;
    private JPanel panelTurnos;
    
    public PanelIzquierdoTurnos(Empleado empleado) {
        this.empleado = empleado;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(280, 500));
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(80, 100, 120), 1),
            "Mis Turnos",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12),
            Color.WHITE
        ));
        setBackground(new Color(25, 35, 60));
        initComponents();
        actualizarTurnos();
    }
    
    private void initComponents() {
        panelTurnos = new JPanel();
        panelTurnos.setLayout(new BoxLayout(panelTurnos, BoxLayout.Y_AXIS));
        panelTurnos.setBackground(new Color(25, 35, 60));
        
        JScrollPane scroll = new JScrollPane(panelTurnos);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(new Color(25, 35, 60));
        add(scroll, BorderLayout.CENTER);
    }
    
    public void actualizarTurnos() {
        panelTurnos.removeAll();
        ArrayList<Turno> turnos = empleado.getTurnos();
        
        if (turnos == null || turnos.isEmpty()) {
            JLabel lblVacio = new JLabel("No hay turnos asignados");
            lblVacio.setFont(new Font("Segoe UI", Font.ITALIC, 11));
            lblVacio.setForeground(Color.LIGHT_GRAY);
            panelTurnos.add(lblVacio);
        } else {
            int indice = 0;
            while (indice < turnos.size()) {
                Turno t = turnos.get(indice);
                JPanel item = new JPanel();
                item.setLayout(new BoxLayout(item, BoxLayout.Y_AXIS));
                item.setMaximumSize(new Dimension(260, 65));
                item.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(60, 80, 100)),
                    BorderFactory.createEmptyBorder(4, 5, 4, 5)
                ));
                item.setBackground(new Color(30, 40, 65));
                
                JLabel lblDia = new JLabel("Dia: " + t.getDiaSemana());
                JLabel lblHora = new JLabel("Horario: " + t.getHoraInicio() + " - " + t.getHoraSalida());
                JLabel lblFecha = new JLabel("Fecha: " + t.getFecha());
                
                Font smallFont = new Font("Segoe UI", Font.PLAIN, 10);
                lblDia.setFont(smallFont);
                lblHora.setFont(smallFont);
                lblFecha.setFont(smallFont);
                lblDia.setForeground(Color.WHITE);
                lblHora.setForeground(Color.WHITE);
                lblFecha.setForeground(Color.WHITE);
                
                item.add(lblDia);
                item.add(lblHora);
                item.add(lblFecha);
                panelTurnos.add(item);
                panelTurnos.add(Box.createRigidArea(new Dimension(0, 3)));
                indice = indice + 1;
            }
        }
        panelTurnos.revalidate();
        panelTurnos.repaint();
    }
}