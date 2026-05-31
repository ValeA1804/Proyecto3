package Proyecto.Interfaces;
import java.util.ArrayList;
import Proyecto.Turno;
import Proyecto.Empleado;
import Proyecto.SolicitudTurno;

public interface ISolicitudTurno {
	void construirTurnos(ArrayList<Empleado> empleados, String fecha);
    void asignarTurno(Empleado empleado, Turno turno);
    void modificarTurno(Turno turno, String nuevaHoraInicio, String nuevaHoraFin);
    ArrayList<Turno> obtenerTurnos(Empleado empleado);
    void aprobarSolicitud(SolicitudTurno solicitud);
    void rechazarSolicitud(SolicitudTurno solicitud);
    void solicitarCambioTurnoGeneral(Turno turnoActual, Turno nuevoTurno);
    void solicitarIntercambioTurno(Empleado miEmpleado, Empleado otroEmpleado, Turno miTurno, Turno suTurno);
}
