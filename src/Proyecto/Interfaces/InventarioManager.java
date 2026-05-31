package Proyecto.Interfaces;
import Proyecto.Producto;
import java.util.ArrayList;


public interface InventarioManager {
	void agregarProducto(Producto producto);
    void eliminarProducto(Producto producto);
    Producto buscarProducto(String nombre);
    void repararJuego(Producto juego);
    void marcarRobado(Producto juego);
    ArrayList<Producto> listarProductos();
    void moverAVenta(Producto juego) throws Exception;
    void moverAPrestamo(Producto juego);
    void consultarHistorialPrestamos();
}
