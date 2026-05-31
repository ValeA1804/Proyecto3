package Proyecto;
import java.util.*;
import Proyecto.Exception.CuposInsuficientesException;
import Proyecto.Exception.EmpleadoEnTurnoException;
import Proyecto.Exception.InscripcionExcedidaException;

public abstract class Torneo {
	
	private String nombre;
	private String fecha;
	private Producto juegoAsociado;
	private int totalCupos;
	private HashMap<Integer, ArrayList<Integer>> listaParticipantes;
	private HashMap<Integer, ArrayList<Integer>> cuposFanaticos;
	
	public abstract int getCostoInscripcion();
	
	public abstract String getPremio();
	
	
	
	public Torneo(String nombre, String fecha, Producto juegoAsociado, int totalCupos) {
		
		this.nombre = nombre;
		
		this.fecha = fecha;
		
		this.juegoAsociado = juegoAsociado;
		
		this.totalCupos = totalCupos;
		
		this.listaParticipantes = new HashMap<>();
		
		this.cuposFanaticos = new HashMap<>();
	}

	public void inscribirParticipante(CuentaUsuario usuario, ArrayList<Integer> idParticipantes)
	        throws EmpleadoEnTurnoException, CuposInsuficientesException, InscripcionExcedidaException {

	    if (idParticipantes.size() > 3) {
	        throw new InscripcionExcedidaException("Número de inscripciones excedido");
	    }

	    if (this.cuposFanaticos.size() + this.listaParticipantes.size() + 1 > this.totalCupos) {
	        throw new CuposInsuficientesException("El torneo no tiene más cupos disponibles");
	    }

	    if (usuario instanceof Cliente) {
	        Cliente cliente = (Cliente) usuario;
	        int limiteFanaticos = (int) Math.ceil(this.totalCupos * 0.20);

	        if (cliente.getJuegosFavoritos().contains(this.juegoAsociado)) {
	            if (this.cuposFanaticos.size() < limiteFanaticos) {
	                this.cuposFanaticos.put(cliente.getId(), idParticipantes);
	            } else {
	                this.listaParticipantes.put(cliente.getId(), idParticipantes);
	                System.out.println("Cupos fanáticos llenos. Inscrito en lista regular.");
	            }
	        } else {
	            this.listaParticipantes.put(cliente.getId(), idParticipantes);
	        }

	    } else if (usuario instanceof Empleado) {
	        Empleado empleado = (Empleado) usuario;

	        for (Turno turno : empleado.getTurnos()) {
	            if (turno.getFecha().equals(this.fecha)) {
	                throw new EmpleadoEnTurnoException("El empleado tiene turno ese día");
	            }
	        }

	        this.listaParticipantes.put(empleado.getId(), idParticipantes);
	    }
	}
	
	public void eliminarInscripcion(CuentaUsuario usuario) {
		Integer idUsuario = usuario.getId();
		this.cuposFanaticos.remove(idUsuario);
		this.listaParticipantes.remove(idUsuario);
	}

	public String getNombre() {
		return nombre;
	}

	public String getFecha() {
		return fecha;
	}

	public Producto getJuegoAsociado() {
		return juegoAsociado;
	}

	public int getTotalCupos() {
		return totalCupos;
	}

	public void setTotalCupos(int totalCupos) {
		this.totalCupos = totalCupos;
	}

	public HashMap<Integer, ArrayList<Integer>> getListaParticipantes() {
		return listaParticipantes;
	}

	public HashMap<Integer, ArrayList<Integer>> getCuposFanaticos() {
		return cuposFanaticos;
	}
		
	
}
