package Proyecto;
import java.util.ArrayList;

import Proyecto.Enum.Categoria;

public class Mesa {
	private int numeroMesa;
	private int capacidad;
	private boolean disponible;
	private Pedido pedido;
	private ArrayList<Producto> juegosEnMesa;
    private boolean tieneBebidaCaliente;
	
	//Constructor
	public Mesa(int numeroMesa, int capacidad, boolean disponible) {
		super();
		this.numeroMesa = numeroMesa;
		this.capacidad = capacidad;
		this.disponible = disponible;
		this.pedido = null; //Se inicializan todas  las mesas vacías, por ende no hay pedidos
		this.tieneBebidaCaliente = false;
		this.juegosEnMesa = new ArrayList<>();

	}

	
	//Getters and Setters
	public int getNumeroMesa() {
		return numeroMesa;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setNumeroMesa(int numeroMesa) {
		this.numeroMesa = numeroMesa;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}


	public ArrayList<Producto> getJuegosEnMesa() {
		return juegosEnMesa;
	}


	public void setJuegosEnMesa(ArrayList<Producto> juegosEnMesa) {
		this.juegosEnMesa = juegosEnMesa;
	}


	public boolean isTieneBebidaCaliente() {
		return tieneBebidaCaliente;
	}


	public void setTieneBebidaCaliente(boolean tieneBebidaCaliente) {
		this.tieneBebidaCaliente = tieneBebidaCaliente;
	}
	
	
	//Métodos
	  
    public boolean tieneJuegoAccion() {
        for (Producto j : juegosEnMesa) {
            if (j.getCaracteristicas().getCategoria() == Categoria.ACCION) {
                return true;
            }
        }
        return false;
    }
    
    public void agregarJuego(Producto juego) {
        if (juego.getCaracteristicas().getCategoria() == Categoria.ACCION) {
            if (tieneBebidaCaliente) {
                System.out.println("No se puede agregar juego de ACCIÓN con bebida caliente");
                return;
            }
        }
        juegosEnMesa.add(juego);
    }
}
