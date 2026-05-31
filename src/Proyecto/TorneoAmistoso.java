package Proyecto;

public class TorneoAmistoso extends Torneo{
	
	private int bonoDescuento;

	public TorneoAmistoso(String nombre, String fecha, Producto juegoAsociado, int totalCupos, int bonoDescuento) {
		super(nombre, fecha, juegoAsociado, totalCupos);
		
		this.bonoDescuento = bonoDescuento;
	}


	@Override
	public
	int getCostoInscripcion() {
		return 0;
	}

	@Override
	public
	String getPremio() {
		String mensaje = "Tarjeta de descuento de" + bonoDescuento + "%";
		return mensaje;
	} 
	
	
}
