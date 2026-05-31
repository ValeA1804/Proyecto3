package Proyecto;

import java.util.ArrayList;

public class Reserva {

	private int numeroTotalPersonas;
	private String horaInicio;
	private int cantidadMenoresEdad;
	private int cantidadMenoresCincoAnios;
	private String fecha;
	private int id;
	//relaciones entre clases
	private Mesa mesa;
	private boolean activa; 
	private ArrayList<Producto> productosReservados; //Prestamo

	// Constructor
	public Reserva(int numeroTotalPersonas, int cantidadMenoresEdad, int cantidadMenoresCincoAnios,
			int id, String horaInicio, String fecha, Mesa mesa, boolean activa) {

		this.numeroTotalPersonas = numeroTotalPersonas;
		this.cantidadMenoresEdad = cantidadMenoresEdad;
		this.cantidadMenoresCincoAnios = cantidadMenoresCincoAnios;
		this.id = id;
		this.horaInicio = horaInicio;
		this.fecha = fecha;
		this.mesa = mesa;
		this.productosReservados = new ArrayList<>();
		this.activa = true;
	}

	// Getters
	public int getNumeroTotalPersonas() {
		return numeroTotalPersonas;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public int getCantidadMenoresEdad() {
		return cantidadMenoresEdad;
	}


	public int getCantidadMenoresCincoAnios() {
		return cantidadMenoresCincoAnios;
	}

	public String getFecha() {
		return fecha;
	}

	public int getId() {
		return id;
	}

	public Mesa getMesa() {
		return mesa;
	}

	public ArrayList<Producto> getProductosReservados() {
		return productosReservados;
	}

	public void setMesa(Mesa mesa) {
		this.mesa = mesa;
	}

	public void setProductosReservados(ArrayList<Producto> productosReservados) {
		this.productosReservados = productosReservados;
	}

	public boolean isActiva() {
		return activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}
	
	
	//Métodos
	
	public boolean tieneMenoresEdad() {
        return cantidadMenoresEdad > 0;
    }
    
    public boolean tieneMenoresCinco() {
        return cantidadMenoresCincoAnios > 0;
    }
    
    public boolean tieneBebidaCaliente() {
        if (mesa != null && mesa.getPedido() != null) {
            return mesa.getPedido().tieneBebidaCaliente();
        }
        return false;
    }
    
    public boolean tieneJuegoAccion() {
        return mesa != null && mesa.tieneJuegoAccion();
    }


}