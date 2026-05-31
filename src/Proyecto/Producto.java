package Proyecto;
import java.util.ArrayList;

import Proyecto.Enum.TipoProducto;


public class Producto {
	private double precio; 
	private String nombreProducto;
	private String descripcion; 
	private boolean alcoholica;
	private boolean caliente; 
	private ArrayList<String> alergenos;
	private TipoProducto tipoProducto;
	private int identificador;
	private Caracteristicas caracteristicas;
    private int stock;
    private int cantidadDisponible; 
    private boolean requiereMesa;
	
 // Constructor con características para JUEGOMESAPRESTAMO y JUEGOMESAVENTA
    public Producto(double precio, String nombreProducto, String descripcion, 
                    boolean alcoholica, boolean caliente, ArrayList<String> alergenos,
                    TipoProducto tipoProducto, int identificador, 
                    Caracteristicas caracteristicas, int stock, boolean requiereMesa) {
        this(precio, nombreProducto, descripcion, alcoholica, caliente, 
             alergenos, tipoProducto, identificador);
        this.caracteristicas = caracteristicas; //Solo los juegos tienen estas caracteristicas
        this.stock = stock;
        this.cantidadDisponible = stock;  // Inicialmente todos disponibles
        this.requiereMesa = false;

    }

    // Constructor sin características para BEBIDA, PASTELERIA
    public Producto(double precio, String nombreProducto, String descripcion, 
                    boolean alcoholica, boolean caliente, ArrayList<String> alergenos,
                    TipoProducto tipoProducto, int identificador) {
        this.precio = precio;
        this.nombreProducto = nombreProducto;
        this.descripcion = descripcion;
        this.alcoholica = alcoholica;
        this.caliente = caliente;
        this.alergenos = alergenos;
        this.tipoProducto = tipoProducto;
        this.identificador = identificador;
        this.caracteristicas = null; //Solo los juegos tienen caracteristicas
        this.stock = 1;  // Por defecto, bebidas y pastelería tienen 1 unidad
        this.cantidadDisponible = 0; 
     }

	//Getters and Setters
	
	public double getPrecio() {
		return precio;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public boolean isAlcoholica() {
		return alcoholica;
	}

	public boolean isCaliente() {
		return caliente;
	}

	public ArrayList<String> getAlergenos() {
		return alergenos;
	}

	public TipoProducto getTipoProducto() {
		return tipoProducto;
	}

	public int getIdentificador() {
		return identificador;
	}

	public Caracteristicas getCaracteristicas() {
		return caracteristicas;
	}

	public int getStock() {
		return stock;
	}

	public int getCantidadDisponible() {
		return cantidadDisponible;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setAlcoholica(boolean alcoholica) {
		this.alcoholica = alcoholica;
	}

	public void setCaliente(boolean caliente) {
		this.caliente = caliente;
	}

	public void setAlergenos(ArrayList<String> alergenos) {
		this.alergenos = alergenos;
	}

	public void setTipoProducto(TipoProducto tipoProducto) {
		this.tipoProducto = tipoProducto;
	}

	public void setIdentificador(int identificador) {
		this.identificador = identificador;
	}

	public void setCaracteristicas(Caracteristicas caracteristicas) {
		this.caracteristicas = caracteristicas;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public void setCantidadDisponible(int cantidadDisponible) {
		this.cantidadDisponible = cantidadDisponible;
	}


	
	
	//Métodos
	// Verificar si el producto necesita mesa (juegos de préstamo)
    public boolean requiereMesa() {
        return this.tipoProducto == TipoProducto.JUEGOMESAPRESTAMO;
    }
    
    // Verificar si es para consumo en el lugar
    public boolean esConsumible() {
        return this.tipoProducto == TipoProducto.BEBIDA || 
               this.tipoProducto == TipoProducto.PASTELERIA;
    }

	public boolean isRequiereMesa() {
		return requiereMesa;
	}

	public void setRequiereMesa(boolean requiereMesa) {
		this.requiereMesa = requiereMesa;
	}
	
}
