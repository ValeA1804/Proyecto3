package Proyecto;

import java.util.*;

import Proyecto.Enum.Estado;
import Proyecto.Enum.Rol;
import Proyecto.Enum.TipoPlatillo;
import Proyecto.Enum.TipoProducto;
import Proyecto.Interfaces.InventarioManager;
import Proyecto.Interfaces.ISugerenciaPlatillo;
import Proyecto.Interfaces.ISolicitudTurno;
import Proyecto.Interfaces.ITurno;


public class Administrador extends CuentaUsuario implements InventarioManager, ISugerenciaPlatillo, ISolicitudTurno, ITurno{

    private InventarioJuegoVenta inventarioVenta;
    private InventarioJuegoPrestamo inventarioPrestamo;
    private ArrayList<HistorialPrestamo> historialPrestamoJuegos;
	private Scanner scanner;

	//Constructor
    public Administrador(String nombre, int id, String usuario, String contraseña, Rol rol,
			InventarioJuegoVenta inventarioVenta, InventarioJuegoPrestamo inventarioPrestamo,
			ArrayList<HistorialPrestamo> historialPrestamoJuegos) {
		super(nombre, id, usuario, contraseña, rol);
		this.inventarioVenta = inventarioVenta;
		this.inventarioPrestamo = inventarioPrestamo;
		this.historialPrestamoJuegos = historialPrestamoJuegos;
	}
    
    public Administrador(InventarioJuegoVenta inventarioVenta, 
            InventarioJuegoPrestamo inventarioPrestamo,
            ArrayList<HistorialPrestamo> historialPrestamoJuegos) {
		super("Administrador", 999, "admin", "admin123", Rol.ADMINISTRADOR);
		this.inventarioVenta = inventarioVenta;
		this.inventarioPrestamo = inventarioPrestamo;
		this.historialPrestamoJuegos = historialPrestamoJuegos;
}
	//Getters and Setters
    
    public InventarioJuegoVenta getInventarioVenta() {
		return inventarioVenta;
	}

	public InventarioJuegoPrestamo getInventarioPrestamo() {
		return inventarioPrestamo;
	}
	
	public ArrayList<HistorialPrestamo> getHistorialPrestamoJuegos() {
		return historialPrestamoJuegos;
	}

	public void setInventarioVenta(InventarioJuegoVenta inventarioVenta) {
		this.inventarioVenta = inventarioVenta;
	}

	public void setInventarioPrestamo(InventarioJuegoPrestamo inventarioPrestamo) {
		this.inventarioPrestamo = inventarioPrestamo;
	}
	
	public void setHistorialPrestamoJuegos(ArrayList<HistorialPrestamo> historialPrestamoJuegos) {
		this.historialPrestamoJuegos = historialPrestamoJuegos;
	}
	//Métodos

	@Override
    public void consultarHistorialPrestamos() {
        if (historialPrestamoJuegos.isEmpty()) {
            System.out.println("No hay préstamos registrados en el historial.");
        }
        
        for (int i = 0; i < historialPrestamoJuegos.size(); i++) {
            HistorialPrestamo historialprestamo = historialPrestamoJuegos.get(i);
            System.out.println("Juego: " + historialprestamo.getProducto().getNombreProducto());
            System.out.println("Fecha préstamo: " + historialprestamo.getFechaPrestamo());
            System.out.println("Estado al prestar: " + historialprestamo.getEstadoAlPrestar());
            
            if (historialprestamo.getFechaDevolucion() != null) { //PUEDE QUE AÚN NO LO HAYAN DEVUELTO AL HACER CONSULTA
                System.out.println("Fecha devolución: " + historialprestamo.getFechaDevolucion());
                System.out.println("Estado al devolver: " + historialprestamo.getEstadoAlDevolver());
            } else {
                System.out.println("Estado: ACTUALMENTE PRESTADO");
            }
            System.out.println("Veces prestado: " + historialprestamo.getCantidadVecesPrestado());
            
            if (historialprestamo.getEmpleado() != null) {
                System.out.println("Préstamo realizado por empleado: " + historialprestamo.getEmpleado().getNombre());
            }
        }
    }
    
 

    
    //IMPLEMENTACIÓN SUGERENCIA PLATILLO
	@Override
	public void enviarSugerencia(SugerenciaPlatillo sugerencia) {
		// El administrador no envía sugerencias, las recibe
		
	}

	@Override
	public void rechazarSugerencia(SugerenciaPlatillo sugerencia) {
		if (sugerencia.getEstado() == Estado.PENDIENTE) {
            sugerencia.rechazar();
        }
		
	}

	@Override
	public ArrayList<SugerenciaPlatillo> listarSugerenciasPendientes() {
		ArrayList<SugerenciaPlatillo> pendientes = new ArrayList<>();
        return pendientes;
	}

	
	//IMPLEMENTACIÓN INVENTARIO MANAGER
	@Override
	public void agregarProducto(Producto producto) {
		if (producto.getTipoProducto() == TipoProducto.JUEGOMESAVENTA) {
            inventarioVenta.getJuegosVenta().add(producto);
        } else if (producto.getTipoProducto() == TipoProducto.JUEGOMESAPRESTAMO) {
            inventarioPrestamo.getProductos().add(producto);
        }
		
	}

	@Override
	public void eliminarProducto(Producto producto) {
		inventarioVenta.getJuegosVenta().remove(producto);
        inventarioPrestamo.getProductos().remove(producto);
		
	}

	@Override
	public Producto buscarProducto(String nombre) {
		for (Producto p : inventarioVenta.getJuegosVenta()) {
            if (p.getNombreProducto().equalsIgnoreCase(nombre)) return p;
        }
        for (Producto p : inventarioPrestamo.getProductos()) {
            if (p.getNombreProducto().equalsIgnoreCase(nombre)) return p;
        }
        return null;
	}

	@Override
	public void marcarRobado(Producto juego) {
		if (juego.getTipoProducto() == TipoProducto.JUEGOMESAPRESTAMO) {
            juego.getCaracteristicas().setEstado("robado");
            inventarioPrestamo.getProductos().remove(juego);
        }
		
	}

	@Override
	public ArrayList<Producto> listarProductos() {
		ArrayList<Producto> todos = new ArrayList<>();
        todos.addAll(inventarioVenta.getJuegosVenta());
        todos.addAll(inventarioPrestamo.getProductos());
        return todos;
	}

	@Override
	public void moverAVenta(Producto juego) throws Exception {
		if (this.inventarioPrestamo.getProductos().contains(juego) && !this.inventarioVenta.getJuegosVenta().contains(juego)) {
	        if (juego.getCaracteristicas().getEstado().equals("dañado")) {
	            throw new Exception("El juego tiene daños, debe ser reparado antes de ponerse a la venta.");
	        } else {
	            inventarioVenta.getJuegosVenta().add(juego);
	            inventarioPrestamo.getProductos().remove(juego);
	        }
	    } else {
	        throw new IllegalArgumentException("El juego no está en préstamo o ya está en venta.");
	    }
		
	}

	@Override
	public void moverAPrestamo(Producto juego) {
		if (!this.inventarioPrestamo.getProductos().contains(juego) && this.inventarioVenta.getJuegosVenta().contains(juego)) {
	        inventarioPrestamo.getProductos().add(juego);
	        inventarioVenta.getJuegosVenta().remove(juego);
	    }
		
	}
	
	@Override
    public void repararJuego(Producto juego) {
        juego.getCaracteristicas().setEstado("reparado");
    }
	
	//IMPLEMENTACIONES SOLICITUD TURNO
	
	@Override
    public void construirTurnos(ArrayList<Empleado> empleados, String fecha) {
        scanner = new Scanner(System.in);
        for (Empleado e : empleados) {
            System.out.println("Empleado: " + e.getNombre());
            System.out.print("Hora inicio: ");
            String horaI = scanner.nextLine();
            System.out.print("Hora salida: ");
            String horaF = scanner.nextLine();
            System.out.print("Día semana: ");
            String dia = scanner.nextLine();
            e.getTurnos().add(new Turno(dia, horaI, horaF, fecha));
        }
    }

	@Override
	public void asignarTurno(Empleado empleado, Turno turno) {
		empleado.getTurnos().add(turno);
		
	}

	@Override
	public ArrayList<Turno> obtenerTurnos(Empleado empleado) {
		return empleado.getTurnos();
	}

	@Override
	public void aprobarSolicitud(SolicitudTurno solicitud) {
		if (solicitud.getEstado() == Estado.PENDIENTE) {
            solicitud.aprobar();
        }
		
	}

	@Override
	public void rechazarSolicitud(SolicitudTurno solicitud) {
		if (solicitud.getEstado() == Estado.PENDIENTE) {
            solicitud.rechazar();
        }
	}

	
	 @Override
	    public void modificarTurno(Turno turno, String nuevaHoraInicio, String nuevaHoraFin) {
	        turno.setHoraInicio(nuevaHoraInicio);
	        turno.setHoraSalida(nuevaHoraFin);
	    }

	 @Override
	 public void solicitarCambioTurnoGeneral(Turno turnoActual, Turno nuevoTurno) {
		// El administrador no solicita un cambio de turno
		//Solo aprueba o rechaza
			
	 }

	 @Override
	 public void solicitarIntercambioTurno(Empleado miEmpleado, Empleado otroEmpleado, Turno miTurno, Turno suTurno) {
		// El administrador no solicita un intercambio de turno
		//Solo aprueba o rechaza
			
	 }

	 @Override
	 public void sugerirPlatillo(String nombrePlatillo, String descripcion, double precioSugerido, TipoPlatillo tipo,
			boolean alcoholica, boolean caliente, ArrayList<String> alergenos) {
		// El administrador no sugiere plaatillos
		 //Solo aprueba o rechaza
		
	 }



	 @Override
	 public void aprobarSugerencia(SugerenciaPlatillo sugerencia, Menu menu) {
		 if (sugerencia.getEstado() == Estado.PENDIENTE) {
		        sugerencia.aprobar();
		        menu.agregarProductoAlMenu(
		            sugerencia.getNombrePlatillo(),
		            sugerencia.getDescripcion(),
		            sugerencia.getPrecioSugerido(),
		            sugerencia.getTipo(),           
		            sugerencia.isAlcoholica(),
		            sugerencia.isCaliente(),
		            sugerencia.getAlergenos(),
		            sugerencia.getId()                
		        );
		    }
		
	 }
	 
}


