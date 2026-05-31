package Proyecto;

import java.time.LocalDate;
import java.util.ArrayList;

import Proyecto.Enum.Estado;
import Proyecto.Enum.TipoPlatillo;

public class SugerenciaPlatillo {
	
	private static int contadorId = 0; //Contador para poder manejar los id de las solicitudes y poder acceder a estar si las quiero conrutar
	private int id;
	private String nombrePlatillo;
	private String descripcion;
	private double precioSugerido;
	private TipoPlatillo tipo;
	private boolean alcoholica;
	private boolean caliente;
	private ArrayList<String> alergenos;
	private String fecha;
	private Estado estado;
	  
	//Constructor
	public SugerenciaPlatillo(String nombrePlatillo, String descripcion, double precioSugerido,
			TipoPlatillo tipo, boolean alcoholica, boolean caliente, ArrayList<String> alergenos) {
		super();
		this.id = ++contadorId;
		this.nombrePlatillo = nombrePlatillo;
		this.descripcion = null; //Puede no tener una descripción necesaria 
		this.precioSugerido = precioSugerido;
		this.tipo = tipo;
		this.alcoholica = alcoholica;
		this.caliente = caliente;
		this.alergenos = alergenos;
		this.fecha = LocalDate.now().toString(); //Fecha Actual y la convierto a String
		this.estado = Estado.PENDIENTE;
	  }
	  
	  //Getters and Setters

	  public int getId() {
		  return id;
	  }

	  public String getNombrePlatillo() {
		  return nombrePlatillo;
	  }

	  public String getDescripcion() {
		  return descripcion;
	  }

	  public double getPrecioSugerido() {
		  return precioSugerido;
	  }

	  public TipoPlatillo getTipo() {
		  return tipo;
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

	  public String getFecha() {
		  return fecha;
	  }

	  public Estado getEstado() {
		  return estado;
	  }

	  public void setId(int id) {
		  this.id = id;
	  }

	  public void setNombrePlatillo(String nombrePlatillo) {
		  this.nombrePlatillo = nombrePlatillo;
	  }

	  public void setDescripcion(String descripcion) {
		  this.descripcion = descripcion;
	  }

	  public void setPrecioSugerido(double precioSugerido) {
		  this.precioSugerido = precioSugerido;
	  }

	  public void setTipo(TipoPlatillo tipo) {
		  this.tipo = tipo;
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

	  public void setFecha(String fecha) {
		  this.fecha = fecha;
	  }

	  public void setEstado(Estado estado) {
		  this.estado = estado;
	  }
	  
	  //Métodos que usará el administrador
	  
	  public void aprobar() {
	        this.estado = Estado.APROBADO;
	        System.out.println("Sugerencia APROBADA");
	    }
	  
	  public void rechazar() {
	        this.estado = Estado.RECHAZADO;
	        System.out.println("Sugerencia RECHAZADA");
	    }
	  
	  
	  
	

}
