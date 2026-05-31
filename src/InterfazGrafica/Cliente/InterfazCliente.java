package InterfazGrafica.Cliente;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import Proyecto.Cliente;
import Proyecto.Compra;
import Proyecto.Cafeteria;
import Proyecto.Producto;
import Proyecto.Torneo;
import Proyecto.InventarioJuegoVenta;
import Proyecto.InventarioJuegoPrestamo;

public class InterfazCliente extends JFrame {
    
	private Cliente cliente;
    private PanelSuperior panelSuperior;
    private PanelIzquierdoMenu panelIzquierdo;
    private PanelBotones panelBotones;
    private VentanaCliente panelCentral;
    private InventarioJuegoVenta inventarioVenta;
    private InventarioJuegoPrestamo inventarioPrestamo;
    private ArrayList<Torneo> torneos;

    
    public InterfazCliente(Cliente cliente, ArrayList<Producto> productos,
            Cafeteria cafeteria,
            InventarioJuegoVenta inventarioVenta,
            InventarioJuegoPrestamo inventarioPrestamo,
            ArrayList<Torneo> torneos) {  

			this.cliente = cliente;
			this.inventarioVenta = inventarioVenta;
			this.inventarioPrestamo = inventarioPrestamo;
			this.torneos = torneos;
			        
        setTitle("Board Game Cafe - Cliente: " + cliente.getNombre());
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout());
        
        panelSuperior = new PanelSuperior(cliente);
        panelIzquierdo = new PanelIzquierdoMenu(productos);
        panelBotones = new PanelBotones();        
        panelIzquierdo.actualizarJuegos(inventarioPrestamo.getProductos());
        panelCentral = new VentanaCliente(cliente, cafeteria, inventarioVenta, inventarioPrestamo, torneos);
        
        configurarListeners();
        
        add(panelSuperior, BorderLayout.NORTH);
        add(panelIzquierdo, BorderLayout.WEST);
        add(panelBotones, BorderLayout.EAST);
        add(panelCentral, BorderLayout.CENTER);
    }
    
    private void configurarListeners() {
        panelBotones.setListenerReservas(e -> panelCentral.mostrarPanel("RESERVAS"));
        panelBotones.setListenerPedidos(e -> panelCentral.mostrarPanel("PEDIDOS"));
        panelBotones.setListenerMiCuenta(e -> panelCentral.mostrarPanel("MI_CUENTA"));
        panelBotones.setListenerCompras(e -> panelCentral.mostrarPanel("COMPRAS"));
        panelBotones.setListenerTorneos(e -> panelCentral.mostrarPanel("TORNEOS"));
        
        panelCentral.getPanelReservas().getBtnCrear().addActionListener(e -> 
            panelCentral.getPanelReservas().mostrarDialogoCrearReserva()
        );
        
        panelCentral.getPanelReservas().getBtnCancelar().addActionListener(e -> {
            panelCentral.getPanelReservas().mostrarResultados("Reserva cancelada");
        });
        
        panelCentral.getPanelReservas().getBtnConsultar().addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            for (Proyecto.Reserva r : cliente.getReservas()) {
                sb.append("ID: ").append(r.getId())
                  .append(" - Fecha: ").append(r.getFecha())
                  .append(" - Activa: ").append(r.isActiva()).append("\n");
            }
            panelCentral.getPanelReservas().mostrarResultados(sb.toString());
        });
        
        // CODIGO CORREGIDO PARA PEDIDOS
        panelCentral.getPanelPedidos().getBtnPedirPrestado().addActionListener(e -> 
            panelCentral.getPanelPedidos().mostrarDialogoPedido()
        );
        
        panelCentral.getPanelCompras().getBtnComprar().addActionListener(e -> {
            String nombreJuego = panelCentral.getPanelCompras().getJuegoSeleccionado();
            if (nombreJuego != null) {
                Producto juegoAComprar = null;
                ArrayList<Producto> juegos = inventarioVenta.getJuegosVenta();
                
                for (int i = 0; i < juegos.size(); i++) {
                    if (juegos.get(i).getNombreProducto().equals(nombreJuego)) {
                        juegoAComprar = juegos.get(i);
                        break;
                    }
                }
                
                if (juegoAComprar != null) {
                    ArrayList<Producto> productosCompra = new ArrayList<>();
                    productosCompra.add(juegoAComprar);
                    
                    Compra compra = cliente.realizarCompra(productosCompra, cliente, 0, null);
                    
                    if (compra != null && compra.getEstado().equals("COMPLETADA")) {
                        inventarioVenta.getJuegosVenta().remove(juegoAComprar);
                        panelCentral.getPanelCompras().eliminarJuegoDeLista(nombreJuego);
                        panelCentral.getPanelCompras().mostrarResultado(
                            "Compra exitosa!\n" +
                            "Producto: " + juegoAComprar.getNombreProducto() + "\n" +
                            "Precio: $" + juegoAComprar.getPrecio() + "\n" +
                            "Total pagado: $" + compra.getTotal() + "\n" +
                            "Puntos acumulados: " + compra.getPuntosFidelidad()
                        );
                        if (panelCentral.getPanelMiCuenta() != null) {
                            panelCentral.getPanelMiCuenta().actualizarDatos();
                        }
                    } else {
                        panelCentral.getPanelCompras().mostrarResultado("Error: No se pudo completar la compra");
                    }
                } else {
                    panelCentral.getPanelCompras().mostrarResultado("Error: Juego no encontrado");
                }
            } else {
                panelCentral.getPanelCompras().mostrarResultado("Seleccione un juego primero");
            }
        });
        
        panelCentral.getPanelTorneos().getBtnInscribir().addActionListener(e -> {
            Torneo torneo = panelCentral.getPanelTorneos().getTorneoSeleccionado();
            if (torneo != null) {
                try {
                    ArrayList<Integer> ids = new ArrayList<>();
                    ids.add(cliente.getId());
                    torneo.inscribirParticipante(cliente, ids);
                    cliente.inscribirEnTorneo(torneo);
                    panelCentral.getPanelTorneos().mostrarInfo("Inscrito a: " + torneo.getNombre());
                } catch (Exception ex) {
                    panelCentral.getPanelTorneos().mostrarInfo("Error: " + ex.getMessage());
                }
            } else {
                panelCentral.getPanelTorneos().mostrarInfo("Seleccione un torneo");
            }
        });

        panelCentral.getPanelTorneos().getBtnCancelar().addActionListener(e -> {
            Torneo torneo = panelCentral.getPanelTorneos().getTorneoSeleccionado();
            if (torneo != null) {
                if (cliente.estaInscritoEnTorneo(torneo)) {
                    torneo.eliminarInscripcion(cliente);
                    cliente.cancelarInscripcionTorneo(torneo);
                    panelCentral.getPanelTorneos().mostrarInfo("Cancelada inscripcion a: " + torneo.getNombre());
                } else {
                    panelCentral.getPanelTorneos().mostrarInfo("No estas inscrito en este torneo");
                }
            } else {
                panelCentral.getPanelTorneos().mostrarInfo("Seleccione un torneo");
            }
        });

        panelCentral.getPanelTorneos().getBtnConsultar().addActionListener(e -> {
            ArrayList<Torneo> misTorneos = cliente.getTorneosInscritos();
            if (misTorneos.isEmpty()) {
                panelCentral.getPanelTorneos().mostrarInfo("No estas inscrito en ningun torneo");
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Torneos inscritos:\n\n");
                for (int i = 0; i < misTorneos.size(); i++) {
                    Torneo t = misTorneos.get(i);
                    sb.append((i+1) + ". " + t.getNombre() + "\n");
                    sb.append("   Fecha: " + t.getFecha() + "\n");
                    sb.append("   Premio: " + t.getPremio() + "\n\n");
                }
                panelCentral.getPanelTorneos().mostrarInfo(sb.toString());
            }
        });
        
        
        panelCentral.getBtnCerrarSesion().addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Desea cerrar sesion?", 
                "Cerrar Sesion", 
                JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                this.dispose();
            }
        });
    }
    
}