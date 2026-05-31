package Proyecto;

import java.time.LocalDate;

public class HistorialPrestamo {
	private String fechaPrestamo; 
	private String fechaDevolucion;
	private String estadoAlPrestar;
	private String estadoAlDevolver;
	private int cantidadVecesPrestado;
	private Empleado empleado; 
	private Producto producto;
	
	//Constructor si un cliente hizo el prestamo
	public HistorialPrestamo(String fechaPrestamo, String fechaDevolucion, String estadoAlPrestar,
			String estadoAlDevolver, int cantidadVecesPrestado, Producto producto) {
		super();
		this.fechaPrestamo = fechaPrestamo;
		this.fechaDevolucion = fechaDevolucion;
		this.estadoAlPrestar = estadoAlPrestar;
		this.estadoAlDevolver = estadoAlDevolver;
		this.cantidadVecesPrestado = cantidadVecesPrestado;
		this.producto = producto;
	}
	
	//Constructor si un empleado hizo el prestamo
	public HistorialPrestamo(String fechaPrestamo, String fechaDevolucion, String estadoAlPrestar,
			String estadoAlDevolver, int cantidadVecesPrestado, Empleado empleado, Producto producto) {
		super();
		this.fechaPrestamo = fechaPrestamo;
		this.fechaDevolucion = fechaDevolucion;
		this.estadoAlPrestar = estadoAlPrestar;
		this.estadoAlDevolver = estadoAlDevolver;
		this.cantidadVecesPrestado = cantidadVecesPrestado;
		this.empleado = empleado;
		this.producto = producto;

	}
	
	
	//Constructor vacío porque cuando hay un nuevo juego en el inventario, aún no tiene histoail 
	public HistorialPrestamo() {
		super();
		this.fechaPrestamo = null;
		this.fechaDevolucion = null;
		this.estadoAlPrestar = null;
		this.estadoAlDevolver = null;
		this.cantidadVecesPrestado = 0;
		this.producto = null;
	}

	//Getters and setters

	public String getFechaPrestamo() {
		return fechaPrestamo;
	}

	public String getFechaDevolucion() {
		return fechaDevolucion;
	}

	public String getEstadoAlPrestar() {
		return estadoAlPrestar;
	}

	public String getEstadoAlDevolver() {
		return estadoAlDevolver;
	}

	public int getCantidadVecesPrestado() {
		return cantidadVecesPrestado;
	}

	public void setFechaPrestamo(String fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}

	public void setFechaDevolucion(String fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}

	public void setEstadoAlPrestar(String estadoAlPrestar) {
		this.estadoAlPrestar = estadoAlPrestar;
	}

	public void setEstadoAlDevolver(String estadoAlDevolver) {
		this.estadoAlDevolver = estadoAlDevolver;
	}

	public void setCantidadVecesPrestado(int cantidadVecesPrestado) {
		this.cantidadVecesPrestado = cantidadVecesPrestado;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	//Métodos
	
	//Registrar Prestamo cuando es un empleado
	public void registrarPrestamo(String estadoActual, Producto producto, Empleado empleado) {
        this.fechaPrestamo = LocalDate.now().toString(); //Fecha Actual y la convierto a String
        this.estadoAlPrestar = estadoActual;
        this.producto = producto;
        this.empleado = empleado;
        this.fechaDevolucion = null;
        this.estadoAlDevolver = null;
        System.out.println("Préstamo registrado");
    }
	
	//Registrar Prestamo cuando es un cliente
	public void registrarPrestamo(String estadoActual, Producto producto) {
        this.fechaPrestamo = LocalDate.now().toString(); //Fecha Actual y la convierto a String
        this.estadoAlPrestar = estadoActual;
        this.producto = producto;
        this.fechaDevolucion = null;
        this.estadoAlDevolver = null;
        System.out.println("Préstamo registrado EXITOSAMENTE");
        }
	
	public void registrarDevolucion(String fecha, String estadoAlDevolver) {
        this.fechaDevolucion = fecha;
        this.estadoAlDevolver = estadoAlDevolver;
        this.cantidadVecesPrestado++;
        System.out.println("Devolución registrada EXITOSAMENTE");
        }
	
	public void acumularCantidadPrestado() {
        this.cantidadVecesPrestado++;
    }
}
