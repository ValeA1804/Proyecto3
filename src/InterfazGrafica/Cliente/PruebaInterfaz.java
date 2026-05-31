package InterfazGrafica.Cliente;

import javax.swing.*;
import java.util.ArrayList;
import Proyecto.Cliente;
import Proyecto.Cafeteria;
import Proyecto.Mesa;
import Proyecto.Producto;
import Proyecto.Torneo;
import Proyecto.InventarioJuegoVenta;
import Proyecto.InventarioJuegoPrestamo;
import Proyecto.PersistenciaManager;
import Proyecto.Enum.Rol;
import Proyecto.Enum.TipoProducto;

public class PruebaInterfaz {
    
    public static void main(String[] args) {
        // CARGAR DATOS REALES DESDE CSV
        ArrayList<Proyecto.CuentaUsuario> cuentas = PersistenciaManager.cargarCuentas();
        ArrayList<Producto> productos = PersistenciaManager.cargarProductos();
        ArrayList<Torneo> torneos = new ArrayList<>();
        
        // Buscar un cliente de las cuentas cargadas SIN usar break
        Cliente clienteEncontrado = null;
        int indice = 0;
        while (indice < cuentas.size() && clienteEncontrado == null) {
            Proyecto.CuentaUsuario cuenta = cuentas.get(indice);
            if (cuenta.getRol() == Rol.CLIENTE) {
                clienteEncontrado = (Cliente) cuenta;
            }
            indice++;
        }
        
        // Si no hay cliente, crear uno de prueba
        Cliente cliente;
        if (clienteEncontrado != null) {
            cliente = clienteEncontrado;
            System.out.println("Cliente encontrado: " + cliente.getNombre());
        } else {
            System.out.println("No se encontro cliente en CSV, creando uno de prueba");
            cliente = new Cliente(
                "Cliente Prueba", 999, "cliente", "123", Rol.CLIENTE,
                "cliente@mail.com", 12345678, "Apellido",
                new ArrayList<>(), null, null, false
            );
        }
        
        // Crear cafeteria con datos
        ArrayList<Mesa> mesas = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            mesas.add(new Mesa(i, 4 + (i % 3), true));
        }
        Cafeteria cafeteria = new Cafeteria("Bogota", "Calle 123", mesas, new ArrayList<>(), 50, new ArrayList<>(), null);
        
        // Crear inventarios y llenar con productos reales
        InventarioJuegoVenta inventarioVenta = new InventarioJuegoVenta(new ArrayList<>());
        InventarioJuegoPrestamo inventarioPrestamo = new InventarioJuegoPrestamo(new ArrayList<>());
        
        for (Producto p : productos) {
            if (p.getTipoProducto() == TipoProducto.JUEGOMESAVENTA) {
                inventarioVenta.getJuegosVenta().add(p);
            } else if (p.getTipoProducto() == TipoProducto.JUEGOMESAPRESTAMO) {
                inventarioPrestamo.getProductos().add(p);
            }
        }
        
        // Ejecutar interfaz
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                InterfazCliente interfaz = new InterfazCliente(
                    cliente, productos, cafeteria, 
                    inventarioVenta, inventarioPrestamo, torneos
                );
                interfaz.setVisible(true);
            }
        });
    }
}