package Proyecto.Interfaces;
import Proyecto.Producto;


public interface IPedidoCliente {
	void solicitarProducto(Producto producto);  
    void cancelarSolicitud(Producto producto);   
    double calcularTotal();   
}
