package Proyecto;
import java.awt.Menu;
import java.util.ArrayList;

import Proyecto.Interfaces.IPedidoMesero;

import Proyecto.Enum.Rol;
import Proyecto.Enum.TipoEmpleado;
import Proyecto.Enum.TipoProducto;



public class Mesero extends Empleado implements IPedidoMesero{
    private ArrayList<Producto> juegosQueExplica; // Juegos que tiene asignados cuando son difíciles
    private ArrayList<Mesa> mesasAtendiendo;  // Mesas que este mesero está atendiendo
    private Mesa mesaActual;
	//Constructor

    public Mesero(String nombre, int id, String usuario, String contraseña, Rol rol, ArrayList<Turno> turnos,
			TipoEmpleado tipoEmpleado, boolean enTurno, ArrayList<Producto> juegosPrestados,
			ArrayList<Producto> juegosQueExplica, Mesa mesaActual, ArrayList<Mesa> mesasAtendiendo) {
		super(nombre, id, usuario, contraseña, Rol.EMPLEADO, turnos, TipoEmpleado.MESERO, enTurno);
		this.juegosQueExplica = juegosQueExplica;
		this.mesasAtendiendo = new ArrayList<>();
		this.mesaActual = null;
	}

	
	//Getters and Setters
	public ArrayList<Producto> getJuegosQueExplica() {
		return juegosQueExplica;
	}

	public void setJuegosPrestamo(ArrayList<Producto> juegosPrestamo) {
		this.juegosQueExplica = juegosPrestamo;
	}
	
	public Mesa getMesaActual() {
		return mesaActual;
	}


	public void setMesaActual(Mesa mesaActual) {
		this.mesaActual = mesaActual;
	}

	public void setJuegosQueExplica(ArrayList<Producto> juegosQueExplica) {
		this.juegosQueExplica = juegosQueExplica;
	}


	public void setMesasAtendiendo(ArrayList<Mesa> mesasAtendiendo) {
		this.mesasAtendiendo = mesasAtendiendo;
	}


	public void agregarJuegoQueExplica(Producto juego) {
        juegosQueExplica.add(juego);
    }

    public boolean sabeExplicar(Producto juego) {
        return juegosQueExplica.contains(juego);
    }

    
    //Métodos
    public ArrayList<Mesa> getMesasAtendiendo() { return mesasAtendiendo; }
    
    public void asignarMesa(Mesa mesa) {
        if (!mesasAtendiendo.contains(mesa)) {
            mesasAtendiendo.add(mesa);
            System.out.println("Mesa #" + mesa.getNumeroMesa() + " asignada a mesero " + this.getNombre());
        }
    }
    
    public void seleccionarMesa(Mesa mesa) {
        if (mesasAtendiendo.contains(mesa)) {
            this.mesaActual = mesa;
            System.out.println("Atendiendo mesa #" + mesa.getNumeroMesa());
        } else {
            System.out.println("Esta mesa no está asignada a ti.");
        }
    }
    
    private boolean verificarDisponibilidadProducto(Producto producto) {
    	// Verificar si hay stock disponible
        if (producto.getCantidadDisponible() <= 0) {
            System.out.println("Producto sin stock disponible.");
            return false;
        }
        
        // Verificar si es un juego de préstamo (requiere mesa y otras restricciones)
        if (producto.getTipoProducto() == TipoProducto.JUEGOMESAPRESTAMO) {
            // Verificar que haya mesa actual
            if (mesaActual == null) {
                System.out.println("No hay mesa seleccionada para prestar el juego.");
                return false;
            }
            
            // Verificar que el juego no esté ya prestado en esta mesa
            if (mesaActual.getJuegosEnMesa().contains(producto)) {
                System.out.println("El juego ya está en esta mesa.");
                return false;
            }
            
            // Verificar límite de juegos por mesa (máximo 2)
            if (mesaActual.getJuegosEnMesa().size() >= 2) {
                System.out.println("La mesa ya tiene 2 juegos. Máximo permitido.");
                return false;
            }
        }
        
        return true;
	}
    

	@Override
	public void rechazarSugerencia(SugerenciaPlatillo sugerencia) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public ArrayList<SugerenciaPlatillo> listarSugerenciasPendientes() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean prestarJuego(Producto juego, CuentaUsuario usuario) {
		return false;
		// TODO Auto-generated method stub
		
	}


	@Override
	public void devolverJuego(Producto juego) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void construirTurnos(ArrayList<Empleado> empleados, String fecha) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void asignarTurno(Empleado empleado, Turno turno) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void modificarTurno(Turno turno, String nuevaHoraInicio, String nuevaHoraFin) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public ArrayList<Turno> obtenerTurnos(Empleado empleado) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void aprobarSolicitud(SolicitudTurno solicitud) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void rechazarSolicitud(SolicitudTurno solicitud) {
		// TODO Auto-generated method stub
		
	}

	//IMPLEMENTACIÓN PEDIDO

	@Override
	public void crearPedido(Cliente cliente, Mesa mesa) {
		if (!mesasAtendiendo.contains(mesa)) {
	        System.out.println("Mesa no asignada a ti.");
	        return;
	    }
	    if (!mesa.isDisponible()) {
	        System.out.println("Mesa no disponible.");
	        return;
	    }
	    if (mesa.getPedido() != null) {
	        System.out.println("La mesa ya tiene un pedido activo.");
	        return;
	    }
	    
	    // EL ID SE GENERA AUTOMÁTICAMENTE AQUÍ
	    Pedido nuevoPedido = new Pedido(this, cliente, mesa);
	    
	    mesa.setPedido(nuevoPedido);
	    mesa.setDisponible(false);
	    cliente.setPedidoActivo(true);
	    cliente.setMesaAsignada(mesa);
	    this.mesaActual = mesa;
	    
	    System.out.println("Pedido #" + nuevoPedido.getIdPedido() + " creado para cliente: " + cliente.getNombre());
	}


	@Override
	public void agregarProducto(Producto producto) {
		if (mesaActual == null || mesaActual.getPedido() == null) {
            System.out.println("No hay pedido activo.");
            return;
        }
        
        Cliente cliente = mesaActual.getPedido().getCliente();
        
        if (!cliente.tieneSolicitudPendiente(producto)) {
            System.out.println("El cliente no ha solicitado este producto.");
            return;
        }
        
        // Verificar disponibilidad real
        if (!verificarDisponibilidadProducto(producto)) {
            System.out.println("Producto no disponible (sin stock).");
            return;
        }
        
        mesaActual.getPedido().agregarProducto(producto);
        cliente.confirmarSolicitud(producto); // quita de pendientes
        System.out.println("Producto confirmado y agregado");
		
	}


	@Override
	public void eliminarProducto(Producto producto) {
		if (mesaActual == null || mesaActual.getPedido() == null) {
            System.out.println("No hay pedido activo.");
            return;
        }
        
        if (mesaActual.getPedido().getProductos().remove(producto)) {
            System.out.println("Producto eliminado del pedido: " + producto.getNombreProducto());
        } else {
            System.out.println("Producto no encontrado en el pedido.");
        }
		
	}


	@Override
	public ArrayList<Producto> verPedido() {
		if (mesaActual != null && mesaActual.getPedido() != null) {
	        return mesaActual.getPedido().getProductos();
	    }
	    return new ArrayList<>();
	}


	@Override
	public ArrayList<Producto> verSolicitudesPendientes() {
		if (mesaActual == null || mesaActual.getPedido() == null) {
	        return new ArrayList<>(); //Para el test no de error
	    }
	    Cliente cliente = mesaActual.getPedido().getCliente();
	    return cliente.getSolicitudesPendientes();
	}


	@Override
	public void aprobarSugerencia(SugerenciaPlatillo sugerencia, Proyecto.Menu menu) {
		// TODO Auto-generated method stub
		
	}




}
