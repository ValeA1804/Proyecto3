package Proyecto.Interfaces;
import Proyecto.Mesa;
import Proyecto.Reserva;
import Proyecto.Cafeteria;

public interface IReserva {
	Reserva crearReserva(Cafeteria cafeteria, int numeroTotalPersonas, 
            int cantidadMenoresEdad, int cantidadMenoresCincoAnios, 
            int id, String horaInicio, String horaFin, String fecha, Mesa mesa);    void cancelarReserva(Reserva reserva);
    boolean verificarDisponibilidad(Mesa mesa, String fecha, String hora);

}
