package Proyecto;

import java.util.ArrayList;

import Proyecto.Enum.TipoEmpleado;

public class Cafeteria {

	private String ciudad;
	private String direccion;
	private ArrayList<Mesa> mesas;
	private ArrayList<Empleado> empleados;
	private int capacidadMaxima; 
    private ArrayList<Reserva> reservas;
    private Menu menu; 



	// Constructor
	public Cafeteria(String ciudad, String direccion, ArrayList<Mesa> mesas, ArrayList<Empleado> empleados, int capacidadMaxima, ArrayList<Reserva> reservas, Menu menu) {
		super();
		this.ciudad = ciudad;
		this.direccion = direccion;
		this.mesas = mesas;
		this.empleados = empleados;
        this.capacidadMaxima = capacidadMaxima;
        this.reservas = reservas;
        this.menu = menu;

	}


	// Getters
	public String getCiudad() {
		return ciudad;
	}

	
	public String getDireccion() {
		return direccion;
	}

	public ArrayList<Mesa> getMesas() {
		return mesas;
	}

	public int getCapacidadMaxima() {
        return capacidadMaxima;
    }
	
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public void setMesas(ArrayList<Mesa> mesas) {
		this.mesas = mesas;
	}

	public void setEmpleados(ArrayList<Empleado> empleados) {
		this.empleados = empleados;
	}
	
	public ArrayList<Empleado> getEmpleados() {
		return empleados;
	}

	public ArrayList<Reserva> getReservas() {
		return reservas;
	}

	public void setCapacidadMaxima(int capacidadMaxima) {
		this.capacidadMaxima = capacidadMaxima;
	}

	public void setReservas(ArrayList<Reserva> reservas) {
		this.reservas = reservas;
	}


	// Métodos para agregar o quitar mesas por si acaso
	public void agregarMesa(Mesa mesa) {
		mesas.add(mesa);
	}

	public void eliminarMesa(Mesa mesa) {
		mesas.remove(mesa);
	}

	public void puedeAbrir() {
	        int contadorCocineros = 0;
	        int contadorMeseros = 0;
	        
	        for (Empleado e : empleados) {
	            if (e.getTipoEmpleado() == TipoEmpleado.COCINERO) {
	                contadorCocineros++;
	            } else if (e.getTipoEmpleado() == TipoEmpleado.MESERO) {
	                contadorMeseros++;
	            }
	        }
	        
	        boolean puedeAbrir = false;
	        if (contadorCocineros >= 1) {
	            if (contadorMeseros >= 2) {
	                puedeAbrir = true;
	            }         
	        if (!puedeAbrir) {
	            System.out.println("La cafetería NO puede abrir");
	            
	        } else {
	            System.out.println("La cafetería PUEDE abrir.");
	        }
	        	       
	    }
	}
	
	public ArrayList<Producto> getProductos() {
	    return this.menu.getProductos();
	}
	
	@SuppressWarnings("unused")
	private int calcularPersonasEnMesas() {
	    int total = 0;
	    for (Reserva r : reservas) {
	        total += r.getNumeroTotalPersonas();
	    }
	    return total;
	}


	public Menu getMenu() {
		return menu;
	}


	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	}