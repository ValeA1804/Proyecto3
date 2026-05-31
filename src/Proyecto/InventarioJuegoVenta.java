package Proyecto;

import java.util.ArrayList;

import Proyecto.Enum.TipoProducto;

public class InventarioJuegoVenta {
    private ArrayList<Producto> juegosVenta;

    //Constructor
	public InventarioJuegoVenta(ArrayList<Producto> juegosVenta) {
		super();
		this.juegosVenta = juegosVenta;
	}
	
	//Getters and Setters
	public ArrayList<Producto> getJuegosVenta() {
		return juegosVenta;
	}

	public void setJuegosVenta(ArrayList<Producto> juegosVenta) {
		this.juegosVenta = juegosVenta;
	}
    
	//Métodos

	public void agregarJuego(Producto producto) {
        if (producto.getTipoProducto() == TipoProducto.JUEGOMESAVENTA) {
        	juegosVenta.add(producto);
        } else {
            System.out.println("Error: Solo juegos de venta");
        }
	}
	public void eliminarJuego(Producto producto) {
	    if (producto.getTipoProducto() == TipoProducto.JUEGOMESAVENTA) {
	        if (juegosVenta.remove(producto)) {
	            System.out.println("Juego eliminado correctamente");
	        } else {
	            System.out.println("Error: El juego no existe en el inventario");
	        }
	    }	   
	}
    

}
