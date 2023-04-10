package entidades;

public class Partido {
    private Equipo equipo1;
    private Equipo equipo2;
    private int golesEquipo1;
    private int golesEquipo2;

    public Partido() { }

    public Partido(Equipo equipo1, Equipo equipo2, int golesEquipo1, int golesEquipo2) {
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
        this.golesEquipo1 = golesEquipo1;
        this.golesEquipo2 = golesEquipo2;
    }

    public ResultadoEnum resultado(Equipo equipo) {  //EL METODO NOS DEVUELVE UN OBJETO DEL TIPO RESULTADO ENUM
        //Para asignar el dato enum debemos plantear las tres posibilidades
        //Empate
        //Que gane el equipo 1 y pierda el 2
        //Que gane el equipo 2 y pierda el 1

        //empate
        if(golesEquipo1 == golesEquipo2) {
            return ResultadoEnum.EMPATE;
        }

        //EQUIPO 1
        if(equipo.equals(equipo1)) {
            if(golesEquipo1 > golesEquipo2) {
                return ResultadoEnum.GANADOR;
            } else {
                return ResultadoEnum.PERDEDOR;
            }
        }

        //EQUIPO 2
        if(equipo.equals(equipo2)) {
            if(golesEquipo2 > golesEquipo1) {
                return ResultadoEnum.GANADOR;
            } else {
                return ResultadoEnum.PERDEDOR;
            }
        }

        return null; //Debe estar para que no tire error

    }

    //GETTERS Y SETTERS
    public Equipo getEquipo1() {
        return equipo1;
    }

    public void setEquipo1(Equipo equipo1) {
        this.equipo1 = equipo1;
    }

    public Equipo getEquipo2() {
        return equipo2;
    }

    public void setEquipo2(Equipo equipo2) {
        this.equipo2 = equipo2;
    }

    public int getGolesEquipo1() {
        return golesEquipo1;
    }

    public void setGolesEquipo1(int golesEquipo1) {
        this.golesEquipo1 = golesEquipo1;
    }

    public int getGolesEquipo2() {
        return golesEquipo2;
    }

    public void setGolesEquipo2(int golesEquipo2) {
        this.golesEquipo2 = golesEquipo2;
    }



}

