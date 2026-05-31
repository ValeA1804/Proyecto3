package Proyecto;

import Proyecto.Enum.Rol;

public abstract class CuentaUsuario {
    private String nombre;
    private int id; 
    private String Usuario; 
    private String contraseña;
    private Rol rol;
    
    // Constructor
    public CuentaUsuario(String nombre, int id, String usuario, String contraseña, Rol rol) {
        this.nombre = nombre;
        this.id = id;
        this.Usuario = usuario;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    // Getters 
    public String getNombre() { return nombre; }
    public int getId() { return id; }
    public String getUsuario() { return Usuario; }
    public String getContraseña() { return contraseña; }
    public Rol getRol() { return rol; }

    // Setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setId(int id) { this.id = id; }
    public void setUsuario(String usuario) { Usuario = usuario; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }
    public void setRol(Rol rol) { this.rol = rol; }

}

