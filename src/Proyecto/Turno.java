package Proyecto;

public class Turno {
	private String diaSemana; 
	private String horaInicio; 
	private String horaSalida;
	private String fecha ;	
	//Constructor
	public Turno(String diaSemana, String horaInicio, String horaSalida, String fecha) {
		super();
		this.diaSemana = diaSemana;
		this.horaInicio = horaInicio;
		this.horaSalida = horaSalida;
		this.fecha = fecha;
	}
	

	//Getter and Setters




	public String getDiaSemana() {
		return diaSemana;
	}


	public void setDiaSemana(String diaSemana) {
		this.diaSemana = diaSemana;
	}


	public String getHoraInicio() {
		return horaInicio;
	}


	public String getHoraSalida() {
		return horaSalida;
	}

	public String getFecha() {
		return fecha;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public void setHoraSalida(String horaSalida) {
		this.horaSalida = horaSalida;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	
}
