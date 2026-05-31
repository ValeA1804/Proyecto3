package Proyecto;

import Proyecto.Enum.Categoria;

public class Caracteristicas {
	private String nombre;
	private int anioPublicacion; 
	private String empresaMatriz;
	private int numeroJugadores;
	private Categoria categoria;
	private String estado;
	private int restriccionEdad;
	private int idProducto;
	private boolean dificil;
	
	//Constructor

	public Caracteristicas(Categoria categoria, int idProducto) {
		super();
		this.nombre = null;
		this.anioPublicacion = 0;
		this.empresaMatriz = null;
		this.numeroJugadores = 0;
		this.categoria = categoria;
		this.estado = null;
		this.restriccionEdad = 0;
		this.idProducto = idProducto;
		this.dificil = false;
	}
	//Setters and getters
	public String getNombre() {
		return nombre;
	}

	public int getAnioPublicacion() {
		return anioPublicacion;
	}

	public String getEmpresaMatriz() {
		return empresaMatriz;
	}

	public int getNumeroJugadores() {
		return numeroJugadores;
	}


	public String getEstado() {
		return estado;
	}

	public int getRestriccionEdad() {
		return restriccionEdad;
	}

	public int getIdProducto() {
		return idProducto;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}

	public boolean isDificil() {
		return dificil;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setAnioPublicacion(int anioPublicacion) {
		this.anioPublicacion = anioPublicacion;
	}

	public void setEmpresaMatriz(String empresaMatriz) {
		this.empresaMatriz = empresaMatriz;
	}

	public void setNumeroJugadores(int numeroJugadores) {
		this.numeroJugadores = numeroJugadores;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setRestriccionEdad(int restriccionEdad) {
		this.restriccionEdad = restriccionEdad;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public void setDificil(boolean dificil) {
		this.dificil = dificil;
	}
	
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	

}
