package Proyecto;
import java.util.ArrayList;

import Proyecto.Enum.Rol;
import Proyecto.Enum.TipoEmpleado;

public class Cocinero extends Empleado {

	public Cocinero(String nombre, int id, String usuario, String contraseña, Rol rol, ArrayList<Turno> turnos,
			TipoEmpleado tipoEmpleado, boolean enTurno, ArrayList<Producto> juegosPrestados) {
		super(nombre, id, usuario, contraseña, Rol.EMPLEADO, turnos, TipoEmpleado.COCINERO, enTurno);
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



	@Override
	public void aprobarSugerencia(SugerenciaPlatillo sugerencia, Proyecto.Menu menu) {
		// TODO Auto-generated method stub
		
	}


}