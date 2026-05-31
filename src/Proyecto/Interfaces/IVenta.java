package Proyecto.Interfaces;
import java.util.ArrayList;
import Proyecto.CuentaUsuario;
import Proyecto.Compra;
import Proyecto.Producto;



public interface IVenta {
	Compra realizarCompra(ArrayList<Producto> productos, CuentaUsuario usuario, double propina, String codigoDescuento);
    double calcularSubtotal(ArrayList<Producto> productos);
    double calcularDescuento(double subtotal, CuentaUsuario usuario, String codigoDescuento);
    double calcularImpuestos(double subtotal, ArrayList<Producto> productos);
    double calcularTotal(double subtotal, double descuento, double impuestos, double propina);
}
