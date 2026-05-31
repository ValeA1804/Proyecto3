package Proyecto.Interfaces;

import java.util.ArrayList;

import Proyecto.Cliente;
import Proyecto.CuentaUsuario;
import Proyecto.Producto;

public interface ICompra {
	void aplicarDescuentoPorPuntos(Cliente cliente, int puntosARedimir);
	void finalizarCompra(CuentaUsuario usuario, double propinaElegida);
	double calcularTotal();
	double calcularImpuestos();
	ArrayList<Producto> obtenerProductosCompra();
	void recalcularCompra();
	
}
