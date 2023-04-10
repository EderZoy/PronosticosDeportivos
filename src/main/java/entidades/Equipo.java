package entidades;

public class Equipo {
    private String nombre;
    private String descripcion;

    //CONSTRUCTOR SIN PARAMETROS
    public Equipo() {

    }

    //CONSTRUCTOR CON PARAMETROS
    public Equipo(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    //GETER Y SETTERS
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


}

