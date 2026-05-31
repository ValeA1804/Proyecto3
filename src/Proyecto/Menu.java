package Proyecto;
import java.util.ArrayList;

import Proyecto.Enum.TipoPlatillo;
import Proyecto.Enum.TipoProducto;

public class Menu {
	private ArrayList<Producto> menuProductos;
	private InventarioJuegoPrestamo inventarioJuegos;
	
	
	public Menu(ArrayList<Producto> menuProductos, InventarioJuegoPrestamo inventarioJuegos) {
		super();
		this.menuProductos = menuProductos;
		this.inventarioJuegos = inventarioJuegos;
	}
	public ArrayList<Producto> getMenuProductos() {
		return menuProductos;
	}
	
	
	public InventarioJuegoPrestamo getInventarioJuegos() {
		return inventarioJuegos;
	}
	public void setMenuProductos(ArrayList<Producto> menuProductos) {
		this.menuProductos = menuProductos;
	}
	public void setInventarioJuegos(InventarioJuegoPrestamo inventarioJuegos) {
		this.inventarioJuegos = inventarioJuegos;
	}
	
	
	public void agregarProductoAlMenu(String nombre, String descripcion, double precio, 
	        TipoPlatillo tipoPlatillo, boolean alcoholica, boolean caliente, ArrayList<String> alergenos, 
	        int identificador) {
		
		    TipoProducto tipoProducto;
		    if (tipoPlatillo == TipoPlatillo.BEBIDA) {
		        tipoProducto = TipoProducto.BEBIDA;
		    } else if (tipoPlatillo == TipoPlatillo.PASTELERIA) {
		        tipoProducto = TipoProducto.PASTELERIA;
		    } else {
		        System.out.println("Tipo de platillo no válido");
		        return;
		    }
		    
		    Producto nuevoProducto = new Producto(precio, nombre, descripcion, alcoholica, 
		                                          caliente, alergenos, tipoProducto, identificador);
		    menuProductos.add(nuevoProducto);
	}
}
