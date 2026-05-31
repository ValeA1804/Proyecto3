package Proyecto.Interfaces;
import Proyecto.Cafeteria;
import Proyecto.CuentaUsuario;
import Proyecto.Producto;
import Proyecto.HistorialPrestamo;


public interface IPrestamo {
	boolean prestarJuego(Producto juego, CuentaUsuario usuario); //CLIENTE o EMPLEADO
    void devolverJuego(Producto juego);
    boolean verificarDisponibilidad(Producto juego);
	boolean verificarRestricciones(Producto juego, Cafeteria cafeteria);
}
