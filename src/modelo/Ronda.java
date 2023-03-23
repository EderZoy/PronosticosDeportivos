package modelo;

public class Ronda {
	private String nro;
	private Partido[] partidos; //Array que almacena los partidos de esa ronda
	private int cantPartidos; //para recorrer esos partidos
	
	public Ronda() { }
    //CONSTRUCTOR CON PARAMETROS
	public Ronda(String nro, Partido[] partidos, int cantPartidos) {
		this.nro = nro;
		this.partidos = partidos;
		this.cantPartidos = cantPartidos;
	}

	//GETTER Y SETTER
	public String getNro() {
		return nro;
	}
	
	public void setNro(String nro) {
		this.nro = nro;
	}
	
	public Partido[] getPartidos() {
		return partidos;
	}
	
	public void setPartidos(Partido[] partidos) {
		this.partidos = partidos;
	}
	
	public int getCantPartidos() {
		return cantPartidos;
	}
	public void setCantPartidos(int cantPartidos) {
		this.cantPartidos = cantPartidos;
	}

}
