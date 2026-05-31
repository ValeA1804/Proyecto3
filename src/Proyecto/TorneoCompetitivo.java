package Proyecto;

public class TorneoCompetitivo  extends Torneo{

	private int tarifa;
	private String premio; 
	
	
	
	public TorneoCompetitivo(String nombre, String fecha, Producto juegoAsociado, int totalCupos, int tarifa,
			String premio) {
		super(nombre, fecha, juegoAsociado, totalCupos);
		this.tarifa = tarifa;
		this.premio = premio;
	}

	
	public int getTarifa() {
		return tarifa;
	} 
	
	@Override
	public String getPremio() {
		int tamanio = this.getCuposFanaticos().size() + this.getListaParticipantes().size();
		String mensaje = "El presupuesto destinado al premio es:" + (tamanio*tarifa);
		return mensaje; 
	}


	@Override
	public
	int getCostoInscripcion() {
		return tarifa;
	}

	
	
}
