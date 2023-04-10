package entidades;

public class Participante {
    private String nombre;
    private Pronostico[] pronosticos;

    public Participante() { } //Constructor sin parametros

    public Participante(String nombre, Pronostico[] pronostico) {
        this.nombre = nombre;
        this.pronosticos = pronostico;
    }

    public Participante(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Pronostico[] getPronosticos() {
        return pronosticos;
    }

    public void setPronosticos(Pronostico[] pronosticos) {
        this.pronosticos = pronosticos;
    }

    //METODO PARA OBTENER EL PUNTAJE DEL JUGADOR
    public int puntaje() {
        int totalPuntos = 0;
        for(Pronostico pronostico : pronosticos) {
            totalPuntos+= pronostico.puntos();
            //System.out.println(pronostico.puntos());
        }
        return totalPuntos;
    }
}
