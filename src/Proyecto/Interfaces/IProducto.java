package Proyecto.Interfaces;
import java.util.ArrayList;
import Proyecto.Producto;

public interface IProducto {
	void agregarProducto(Producto producto);
    void eliminarProducto(Producto producto);
    Producto buscarProducto(String nombre);
    ArrayList<Producto> listarProductos();
}
