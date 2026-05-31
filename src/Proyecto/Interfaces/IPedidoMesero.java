package Proyecto.Interfaces;
import Proyecto.Producto;
import Proyecto.Cliente;
import Proyecto.Mesa;
import java.util.ArrayList;


public interface IPedidoMesero {
	void crearPedido(Cliente cliente, Mesa mesa);       
    void agregarProducto(Producto producto);            
    void eliminarProducto(Producto producto);            
    ArrayList<Producto> verPedido();                     
    ArrayList<Producto> verSolicitudesPendientes();      
}
