import org.junit.Test;

import static org.junit.Assert.*;
import entidades.*;

public class ContarPuntajeTest {

    @Test
    public void contarPuntaje() {
        //Creamos los participantes
        Participante participante = new Participante();

        //Creamos los equipos
        Equipo argentina = new Equipo("Argentina","Arg");
        Equipo arabia = new Equipo("Arabia Saudita","ArSau");
        Equipo polonia = new Equipo("Polonia","Pol");
        Equipo mexico = new Equipo("Mexico","Mex");

        //Creamos los partidos
        Partido partido1 = new Partido(argentina,arabia,1,2);
        Partido partido2 = new Partido(polonia,mexico,0,0);
        //Vector de partidos para pasarle a la ronda
        Partido[] vectorPartidos = {partido1,partido2};
        //Creamos la ronda
        Ronda ronda = new Ronda("1",vectorPartidos,2);

        //Creamos los pronosticos
        Pronostico pronostico1 = new Pronostico(partido1,argentina,ResultadoEnum.GANADOR);
        Pronostico pronostico2 = new Pronostico(partido2,polonia,ResultadoEnum.EMPATE);

        //Vector de pronosticos para pasarle al participante
        Pronostico[] pronosticos = {pronostico1,pronostico2};
        participante.setPronosticos(pronosticos); //seteamos los pronosticos

        //Hacemos la prueba
        assertEquals(1,participante.puntaje()); //Deberia dar correcto
        assertEquals(2,participante.puntaje()); //Deberia dar incorrecto



    }
}