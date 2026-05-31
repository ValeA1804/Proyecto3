package Proyecto;

import java.time.LocalDate;
import java.util.ArrayList;

import Proyecto.Enum.Rol;
import Proyecto.Enum.TipoEmpleado;
import Proyecto.Enum.TipoPlatillo;
import Proyecto.Interfaces.IPrestamo;
import Proyecto.Interfaces.ISugerenciaPlatillo;
import Proyecto.Interfaces.ISolicitudTurno;
import Proyecto.Interfaces.IVenta;
import Proyecto.Interfaces.Comprar;


public abstract class Empleado extends CuentaUsuario implements ISugerenciaPlatillo, IPrestamo, ISolicitudTurno, IVenta, Comprar{
	
	private ArrayList<Turno> turnos;
	private TipoEmpleado tipoEmpleado;
	private ArrayList<SugerenciaPlatillo> sugerenciasPlatillos;
	private ArrayList<SolicitudTurno> solicitudesTurno;
	protected boolean enTurno;
	protected ArrayList<Producto> juegosPrestados;  // Juegos que tiene actualmente prestados



	//Constructor 
	public Empleado(String nombre, int id, String usuario, String contraseña, Rol rol, ArrayList<Turno> turnos,
			TipoEmpleado tipoEmpleado, boolean enTurno) {
		super(nombre, id, usuario, contraseña, Rol.EMPLEADO);
		this.turnos = turnos;
		this.tipoEmpleado = tipoEmpleado;
		this.sugerenciasPlatillos = new ArrayList<>(); 
		this.solicitudesTurno = new ArrayList<>();
		this.enTurno = enTurno;
		this.juegosPrestados = new ArrayList<>(); 
	}



	//Getters and Setters

	public ArrayList<Turno> getTurnos() {
		return turnos;
	}

	public TipoEmpleado getTipoEmpleado() {
		return tipoEmpleado;
	}
	
	public ArrayList<SugerenciaPlatillo> getSugerenciaPlatillos() {
		return sugerenciasPlatillos;
	}

	public ArrayList<SolicitudTurno> getSolicitudTurno() {
		return solicitudesTurno;
	}

	public void setTurnos(ArrayList<Turno> turnos) {
		this.turnos = turnos;
	}

	public void setTipoEmpleado(TipoEmpleado tipoEmpleado) {
		this.tipoEmpleado = tipoEmpleado;
	}
	
	public void setSugerenciaPlatillos(ArrayList<SugerenciaPlatillo> sugerenciaPlatillos) {
		this.sugerenciasPlatillos = sugerenciaPlatillos;
	}



	public void setSolicitudTurno(ArrayList<SolicitudTurno> solicitudTurno) {
		this.solicitudesTurno = solicitudTurno;
	}
	
	
	
	 @Override
	 public void solicitarCambioTurnoGeneral(Turno turnoActual, Turno nuevoTurno) {
	        
	        if (!this.turnos.contains(turnoActual)) {
	            System.out.println("Error");
	            System.out.println("No tienes ese turno. Revisa que tengas ese turno asignado");

	        }
	        else {
	        	
	        	SolicitudTurno nuevaSolicitud = new SolicitudTurno(this, turnoActual, nuevoTurno);
	            solicitudesTurno.add(nuevaSolicitud);
		        System.out.println("Solicitud de cambio general. Cambiar " + turnoActual + " por " + nuevoTurno);
	            System.out.println("Solicitud exitosa");

	        }
	        

	    }
	 @Override
	 public void solicitarIntercambioTurno(Empleado miEmpleado, Empleado otroEmpleado, Turno miTurno, Turno suTurno) {
	       
	        if (!this.turnos.contains(miTurno)) {
	        	System.out.println("Error");
	            System.out.println("No tienes ese turno. Revisa que tengas ese turno asignado");	         
	        }
	        
	        
	        if (!otroEmpleado.getTurnos().contains(suTurno)) {
	        	System.out.println("Error");
	            System.out.println("No tienes ese turno. Revisa que tengas ese turno asignado");	
	        }
	        
	        
	        SolicitudTurno nuevaSolicitud = new SolicitudTurno(this, otroEmpleado, miTurno, suTurno);
	        solicitudesTurno.add(nuevaSolicitud);
	        System.out.println("Solicitud de intercambio: " + miTurno + " - " + suTurno);
	        System.out.println("Entre: " + miEmpleado.getNombre() + " - " + otroEmpleado.getNombre());
            System.out.println("Solicitud exitosa");


	        
	        
	    }
	 
	 
	
	 @Override
	    public boolean verificarDisponibilidad(Producto juego) {
	        return juego.getCantidadDisponible() > 0;
	    }
	    
	    @Override
	    public boolean verificarRestricciones(Producto juego, Cafeteria cafeteria) {
	        
	        if (enTurno) {
	            System.out.println("No puedes pedir prestado mientras estás en turno.");
	            return false;
	        }
	        
	        
	        for (Mesa mesa : cafeteria.getMesas()) {
	            if (!mesa.isDisponible()) {
	                System.out.println("Hay clientes en la cafetería. No puedes pedir prestado.");
	                return false;
	            }
	        }
	        
	        return true;
	    }
	    @Override
		public boolean prestarJuego(Producto juego, CuentaUsuario usuario) {
	    	 if (!verificarDisponibilidad(juego)) {
		            System.out.println("Juego no disponible.");
		            return false;
		        }
		        
		        juego.setCantidadDisponible(juego.getCantidadDisponible() - 1);
		        juegosPrestados.add(juego);
		        
		        System.out.println("Préstamo exitoso");
		        return true;
		        
	    }
			

		@Override
		public void devolverJuego(Producto juego) {
			if (!juegosPrestados.contains(juego)) {
	            System.out.println("No tienes este juego prestado.");
	            return;
	        }
	        
	        juego.setCantidadDisponible(juego.getCantidadDisponible() + 1);
	        juegosPrestados.remove(juego);
	        
	        
	        HistorialPrestamo historial = new HistorialPrestamo();
	        historial.registrarDevolucion(java.time.LocalDate.now().toString(), 
	                                       juego.getCaracteristicas().getEstado());
	        
	        System.out.println("Juego devuelto");
			
		}

	    
	  
	    @Override
	    public void sugerirPlatillo(String nombrePlatillo, String descripcion, double precioSugerido, TipoPlatillo tipo, 
	            boolean alcoholica, boolean caliente, ArrayList<String> alergenos) {
			 	SugerenciaPlatillo nuevaSugerencia = new SugerenciaPlatillo(nombrePlatillo, descripcion, precioSugerido, tipo, 
			 			alcoholica, caliente, alergenos);
			 	sugerenciasPlatillos.add(nuevaSugerencia);
		        System.out.println("Sugerencia exitosa");

		 }
	    @Override
	    public void enviarSugerencia(SugerenciaPlatillo sugerencia) {
	        sugerenciasPlatillos.add(sugerencia);
	        System.out.println("Sugerencia enviada.");
	    }
	    
	    
	    @Override
	    public void rechazarSugerencia(SugerenciaPlatillo sugerencia) {
	        // Solo administrador puede rechazar
	    }
	    
	    
	 // Implementación IVenta 

	    @Override
	    public Compra realizarCompra(ArrayList<Producto> productos, CuentaUsuario usuario, double propina, String codigoDescuento) {
	        if (productos == null || productos.isEmpty()) {
	            System.out.println("No hay productos para comprar.");
	            return null;
	        }
	        
	        // Empleado siempre tiene 20% de descuento
	        Compra compra = new Compra("PENDIENTE", Compra.getDESCUENTOEMPLEADO(), null, 0,
	                                    Compra.getIVA(), propina, LocalDate.now().toString());
	        compra.setProductos(productos);
	        compra.finalizarCompra(this, propina);
	        
	        return compra;
	    }

	    @Override
	    public double calcularSubtotal(ArrayList<Producto> productos) {
	        Compra temp = new Compra("TEMP", 0, null, 0, Compra.getIVA(), 0, LocalDate.now().toString());
	        temp.setProductos(productos);
	        return temp.getSubtotal();
	    }

	    @Override
	    public double calcularDescuento(double subtotal, CuentaUsuario usuario, String codigoDescuento) {
	        return subtotal * Compra.getDESCUENTOEMPLEADO();
	    }

	    @Override
	    public double calcularImpuestos(double subtotal, ArrayList<Producto> productos) {
	        Compra temp = new Compra("TEMP", 0, null, 0, Compra.getIVA(), 0, LocalDate.now().toString());
	        temp.setProductos(productos);
	        return temp.getImpuestos();
	    }

	    @Override
	    public double calcularTotal(double subtotal, double descuento, double impuestos, double propina) {
	        return subtotal - descuento + impuestos + propina;
	    }

	    // Método para empleado comprar juego
	    @Override
	    public void comprarJuego(Producto juego, InventarioJuegoVenta inventario) {
	        if (juego == null || !inventario.getJuegosVenta().contains(juego)) {
	            System.out.println("Juego no disponible para venta.");
	            return;
	        }
	        
	        ArrayList<Producto> productos = new ArrayList<>();
	        productos.add(juego);
	        
	        Compra compra = realizarCompra(productos, this, 0, null);
	        if (compra != null) {
	            inventario.getJuegosVenta().remove(juego);
	            System.out.println("Venta exitosa.");
	        }
	    }
}
