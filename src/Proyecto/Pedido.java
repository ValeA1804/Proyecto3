package Proyecto;

import java.util.ArrayList;
import Proyecto.Enum.TipoProducto;


public class Pedido {
	private static int ultimoId = 0; 
	private int idPedido;
	private Mesero mesero;
	private ArrayList<Producto> productos;
	private boolean cerrado;
	private Cliente cliente;
	private Mesa mesa;


	// Constructor 
	public Pedido(int idPedido, Mesero mesero, ArrayList<Producto> productos, boolean cerrado, Cliente cliente, Mesa mesa) {
		this.idPedido = idPedido;
		this.mesero = mesero;
		this.productos = new ArrayList<>();
		this.setCerrado(false);
		this.setCliente(cliente);
		this.mesa = mesa;
		if (idPedido > ultimoId) {  
			ultimoId = idPedido;
		}
	}
	
	//constructor sin ID 
	public Pedido(Mesero mesero, Cliente cliente, Mesa mesa) {
		this.idPedido = ++ultimoId;
		this.mesero = mesero;
		this.productos = new ArrayList<>();
		this.cerrado = false;
		this.cliente = cliente;
		this.mesa = mesa;
	}

	//getters
	public int getIdPedido() {
		return idPedido;
	}

	public Mesero getMesero() {
		return mesero;
	}

	public ArrayList<Producto> getProductos() {
		return productos;
	}
	
	public boolean isCerrado() {
		return cerrado;
	}

	public void setCerrado(boolean cerrado) {
		this.cerrado = cerrado;
	}
	
	public void setMesa(Mesa mesa) { 
		this.mesa = mesa; 
		}
	
	public Mesa getMesa() { 
		return mesa; 
		}
	
	public void setCliente(Cliente cliente) { 
		this.cliente = cliente; 
		}
	
	public Cliente getCliente() { 
		return cliente; 
		}
	
	public static void setUltimoId(int id) {
		ultimoId = id;
	}
	
	public static int getUltimoId() {
		return ultimoId;
	}

	// Métodos para agregar o quitar productos
	public void agregarProducto(Producto producto) {
        if (!cerrado) {
            productos.add(producto);
        } else {
            System.out.println("El pedido ya se cerró, no se pueden agregar productos.");
        }
    }

    public void eliminarProducto(Producto producto) {
        if (!cerrado) {
            productos.remove(producto);
        }
    }

    public double calcularSubtotal() {
        double suma = 0;
        for (Producto p : productos) {
            suma += p.getPrecio();
        }
        return suma;
    }
    
    public boolean tieneBebidaCaliente() {
        for (Producto p : productos) {
            if (p.isCaliente() && p.getTipoProducto() == TipoProducto.BEBIDA) {
                return true;
            }
        }
        return false;
    }
    
    public void cerrar() {
        this.cerrado = true;
        System.out.println("Pedido #" + idPedido + " cerrado.");
    }
    
  
}