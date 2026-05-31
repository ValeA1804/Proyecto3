package Proyecto.Interfaces;
import java.util.ArrayList;
import Proyecto.Turno;
import Proyecto.Empleado;
import Proyecto.SolicitudTurno;


public interface ITurno {
	void asignarTurno(Empleado empleado, Turno turno);
    void modificarTurno(Turno turno, String nuevaHoraInicio, String nuevaHoraFin);
    ArrayList<Turno> obtenerTurnos(Empleado empleado);
    void aprobarSolicitud(SolicitudTurno solicitud);
    void rechazarSolicitud(SolicitudTurno solicitud );
}
