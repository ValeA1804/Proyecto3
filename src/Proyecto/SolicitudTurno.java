package Proyecto;
import java.time.LocalDate;

import Proyecto.Enum.Estado;
import Proyecto.Enum.TipoSolicitud;



public class SolicitudTurno {
	
	private static int contadorId = 0; //Contador para poder manejar los id de las solicitudes y poder acceder a estar si las quiero conrutar
	private int id;
	private Empleado empleadoSolicitante;
	private Empleado otroEmpleado;  // null si es cambio general
	private Turno turnoActual;
	private Turno turnoDeseado;
	private TipoSolicitud tipoSolicitud;  // CAMBIO_GENERAL o INTERCAMBIO
	private String fecha;
	private Estado estado;
	 
	//Constructor para el intercambio donde tiene los dos empleados
	public SolicitudTurno(Empleado empleadoSolicitante, Empleado otroEmpleado, Turno turnoActual, Turno turnoDeseado) {
		super();
		this.id = ++contadorId;
		this.empleadoSolicitante = empleadoSolicitante;
		this.otroEmpleado = otroEmpleado;
		this.turnoActual = turnoActual;
		this.turnoDeseado = turnoDeseado;
		this.tipoSolicitud = TipoSolicitud.INTERCAMBIO;
		this.fecha = LocalDate.now().toString(); //Fecha Actual y la convierto a String
		this.estado = Estado.PENDIENTE;
	 }

	 //Constructor para el cambio general de turno donde no necesita el otro empleado
	 public SolicitudTurno(Empleado empleadoSolicitante, Turno turnoActual, Turno turnoDeseado) {
		super();
		this.id = ++contadorId;
		this.empleadoSolicitante = empleadoSolicitante;
		this.turnoActual = turnoActual;
		this.turnoDeseado = turnoDeseado;
		this.tipoSolicitud = TipoSolicitud.CAMBIO;
		this.fecha = LocalDate.now().toString(); //Fecha Actual y la convierto a String
		this.estado = Estado.PENDIENTE;
	 }

	 //Setters and Getters
	 public int getId() {
		 return id;
	 }

	 public Empleado getEmpleadoSolicitante() {
		 return empleadoSolicitante;
	 }

	 public Empleado getOtroEmpleado() {
		 return otroEmpleado;
	 }

	 public Turno getTurnoActual() {
		 return turnoActual;
	 }

	 public Turno getTurnoDeseado() {
		 return turnoDeseado;
	 }

	 public TipoSolicitud getTipoSolicitud() {
		 return tipoSolicitud;
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

	 public void setEmpleadoSolicitante(Empleado empleadoSolicitante) {
		 this.empleadoSolicitante = empleadoSolicitante;
	 }

	 public void setOtroEmpleado(Empleado otroEmpleado) {
		 this.otroEmpleado = otroEmpleado;
	 }

	 public void setTurnoActual(Turno turnoActual) {
		 this.turnoActual = turnoActual;
	 }

	 public void setTurnoDeseado(Turno turnoDeseado) {
		 this.turnoDeseado = turnoDeseado;
	 }

	 public void setTipoSolicitud(TipoSolicitud tipoSolicitud) {
		 this.tipoSolicitud = tipoSolicitud;
	 }

	 public void setFecha(String fecha) {
		 this.fecha = fecha;
	 }

	 public void setEstado(Estado estado) {
		 this.estado = estado;
	 }
	 
	 //Métodos que usará el Administrador
	 public void aprobar() {
	        this.estado = Estado.APROBADO;
	        System.out.println("Solicitud de turno APROBADA");
	    }
	 
	 public void rechazar() {
	        this.estado = Estado.RECHAZADO;

	        System.out.println("Solicitud de turno RECHAZADA");
	    }

}
