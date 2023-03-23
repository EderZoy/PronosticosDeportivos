package modelo;

public class Pronostico {
	private Partido partido;
	private Equipo equipo;
	private ResultadoEnum resultado;
	
	//Constructor sin parametros
	public Pronostico() {}
	
	//Constructur con parametros
	public Pronostico(Partido partido, Equipo equipo, ResultadoEnum resultado) {
		this.partido = partido;
		this.equipo = equipo;
		this.resultado = resultado;
	}

	//Metodo para asignar puntos
	public int puntos() {
		if(partido.resultado(equipo) == resultado) {
			return 1;  //Si el resultado pronosticado del equipo es igual al resultado del equipo suma un punto 
		} else {
			return 0; //Si es diferente no suma nada 
		}
		
	}
	//GETTER Y SETTERS
	public Partido getPartido() {
		return partido;
	}

	public void setPartido(Partido partido) {
		this.partido = partido;
	}

	public Equipo getEquipo() {
		return equipo;
	}

	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}

	public ResultadoEnum getResultado() {
		return resultado;
	}

	public void setResultado(ResultadoEnum resultado) {
		this.resultado = resultado;
	}
	
	
	
	
}
