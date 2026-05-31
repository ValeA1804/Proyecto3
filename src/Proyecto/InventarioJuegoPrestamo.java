package Proyecto;

import java.util.ArrayList;

import Proyecto.Enum.TipoProducto;

public class InventarioJuegoPrestamo {

    private ArrayList<Producto> juegosPrestamo;

    //Constructor
	public InventarioJuegoPrestamo(ArrayList<Producto> productos) {
		super();
		this.juegosPrestamo = productos;
	}
	
	//Getters and Setters

	public ArrayList<Producto> getProductos() {
		return juegosPrestamo;
	}

	public void setProductos(ArrayList<Producto> productos) {
		this.juegosPrestamo = productos;
	}
	
	//Métodos
	
	public void agregarJuego(Producto producto) {
        if (producto.getTipoProducto() == TipoProducto.JUEGOMESAPRESTAMO) {
            juegosPrestamo.add(producto);
        } else {
            System.out.println("Error: Solo juegos de préstamo");
        }
	}
	public void eliminarJuego(Producto producto) {
	    if (producto.getTipoProducto() == TipoProducto.JUEGOMESAPRESTAMO) {
	        if (juegosPrestamo.remove(producto)) {
	            System.out.println("Juego eliminado correctamente");
	        } else {
	            System.out.println("Error: El juego no existe en el inventario");
	        }
	    }	   
	}
}