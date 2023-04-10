import entidades.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static java.nio.charset.StandardCharsets.*;
import static java.nio.file.Files.*;
import static java.nio.file.Paths.*;

public class PronosticoApp {
    public static void main(String[] args) {
        //Indicamos las rutas de entrada
        String archivoResultados = "C:\\Users\\Usuario\\IdeaProjects\\TPI_JAVA\\src\\main\\java\\archivos\\resultados.csv";
        String archivoPronosticos = "C:\\Users\\Usuario\\IdeaProjects\\TPI_JAVA\\src\\main\\java\\archivos\\pronostico.csv"; //obtenemos al archivo
        //El gestor va a tener que crear la ronda (con los partidos y equipos) y el pronostico
        //Llamamos a los metodos para cargar la ronda y el pronostico desde el archivo

        //creamos los dos participantes
        Participante[] participantes;
        participantes = new Participante[3]; //Inicializamos con dos participantes
        participantes[0] = new Participante("Eder");
        participantes[1] = new Participante("Joaquin");
        participantes[2] = new Participante("Javier");


        Ronda[] rondas =  cargarRonda(archivoResultados); //creamos la ronda mediante el metodo cargarRonda
        Pronostico[] pronostico = cargarPronostico(archivoPronosticos, rondas, participantes); //Creamos el pronostico mediante el metodo cargarPronostico donde se le pasa la ronda como parametro.

        //Llamamos al metodo para asignar los pronosticos a los participantes
        asignarPronosticos(participantes, pronostico);
        //Mostramos los puntajes del participante obtenidos en la ronda
        System.out.println("El puntaje de los participantes es: ");
        contarPuntaje(pronostico,participantes);
        System.out.println("-------------------------------------------------------------------------------");

        //A modo extra muestro los partidos con sus resultados y el pronostico del participante.
        System.out.println("PARTIDOS DE LA RONDA:");
        mostrarPartidos(participantes,rondas, pronostico);

    }

    //METODO PARA CARGAR LA RONDA
    public static Ronda[] cargarRonda(String archivoResultados) {
        List<String> resultados = new ArrayList<String>(); //La lista que contendra los resultados
        Ronda[] rondas = new Ronda[1]; //por el momento tenemos una ronda

        //Leemos el archivo por ende usamos un Try
        try {
            resultados = readAllLines(get(archivoResultados), ISO_8859_1); //para evitar la excepcion realizamos una codificacion
            resultados.remove(0); //removemos la primer linea que es la que tiene los titulos.

        } catch(IOException e) {
            System.out.println("Error al leer el archivo");
            e.printStackTrace();
        }

        //Ahora debemos recorrer cada resultado, validarlo y crear los objetos

        List <Partido> partidosRonda = new ArrayList<Partido>(); //creamos un array list de partidos para ir agregandolo
        int i=0;
        String nroRonda = "";

        for (String resultado : resultados) { //Leemos cada linea del archivo
            if(validarArchivoResultados(resultado)) { //Llamamos al metodo para validar cada linea
                String[] vectorResultado = resultado.split(";"); //lee por cada separcion ;
                //le asignamos el nombre a la ronda
                if(nroRonda.isEmpty()) { //Si el nro de la ronda esta vacio le asignamos el nro que indica la linea
                    nroRonda = vectorResultado[0];
                }

                //Antes de cargar cada ronda debemos verificar que no haya cambiado el mro de ronda
                //Si el nro de ronda cambio guardamos los datos de esa ronda
                if(!vectorResultado[0].equals(nroRonda) && !nroRonda.isEmpty()) {
                    //Si el nro de la ronda que indica la linea es diferente al nro de ronda actual
                    Ronda ronda = new Ronda();
                    Partido[] partidos = new Partido[partidosRonda.size()];
                    ronda.setNro(nroRonda);
                    ronda.setPartidos(partidosRonda.toArray(partidos)); //le pasamos los partidos del array
                    rondas[i] = ronda; //seteamos al vector de rondas esa ronda.
                    partidosRonda.clear(); //borramos el contenido del array list de partidos
                    nroRonda = vectorResultado[0]; //le asignamos el nro de la ronda siguiente
                    i++; //incrementamos i
                }
                //Si el nro de ronda sigue siendo el mismo seguimos creando equipos y partidos en esa misma ronda
                //Creamos el partido con sus datos (Equipo, goles)
                Equipo equipo1 = new Equipo();
                equipo1.setNombre(vectorResultado[1]); //le pasamos el nombre del equipo mediante el set
                Equipo equipo2 = new Equipo();
                equipo2.setNombre(vectorResultado[4]); //le pasamos el nombre del equipo mediante el set
                Partido partido = new Partido();
                //Seteamos el partido con los datos de la linea del archivo
                partido.setEquipo1(equipo1);
                partido.setEquipo2(equipo2);
                partido.setGolesEquipo1(Integer.valueOf(vectorResultado[2])); //convertimos el gol que esta en string a int
                partido.setGolesEquipo2(Integer.valueOf(vectorResultado[3]));
                //Agregamos el partido al arrayList
                partidosRonda.add(partido);

            }
        }
        Ronda ronda = new Ronda();
        Partido[] partidos = new Partido[partidosRonda.size()];
        ronda.setNro(nroRonda);
        ronda.setPartidos(partidosRonda.toArray(partidos)); //le pasamos los partidos del array
        rondas[i] = ronda; //seteamos al vector de rondas esa ronda

        return rondas; //retorna la ronda
    }

    //METODO PARA CARGAR EL PRONOSTICO
    public static Pronostico[] cargarPronostico(String archivoPronosticos, Ronda[] rondas, Participante[] participantes) {
        List<String> pronosticos = new ArrayList<String>(); //La lista que contendra los pronosticos del archivo

        //Leemos el archivo por ende usamos un Try
        try {
            pronosticos = readAllLines(get(archivoPronosticos), ISO_8859_1); //para evitar la excepcion realizamos una codificacion
            pronosticos.remove(0); //removemos la primer linea que es la que tiene los titulos.
        } catch(IOException e) {
            System.out.println("Error al leer el archivo");
            e.printStackTrace();
        }


        List <Pronostico> pronosticosRonda = new ArrayList <Pronostico>(); //creamos el array list para los pronostico del tamaño de la cantidad de partidos que tiene la ronda
        int i=0;
        //Recorremos cada ronda
        for(Ronda ronda:rondas) {
            //Recorremos el participante
            for(Participante participante:participantes) {
                //obtenemos los partidos de la ronda para recorrerlos
                Partido[] partidosRonda = ronda.getPartidos();
                for(Partido partido:partidosRonda) {
                    //validamos cada linea pronostico llamando al metodo
                    if(validarArchivoPronosticos(pronosticos.get(i))) {
                        Pronostico pronostico = new Pronostico();
                        ResultadoEnum resultadoPronostico; //Creamos una variable de ResultadoEnum

                        //INDICAR EL RESULTADOO
                        //obtenemos la linea que estamos recorriendo separando por ;  y obteniendo la posicion 1
                        //IsEmpty. Devuelve el valor 1 (true) si hay un campo vacío; de lo contrario, devuelve el valor 0 (false).
                        //Si el espacio 1 esta vacio puede que sea un empate o que perdio el equipo 1
                        if (!pronosticos.get(i).split(";")[2].isEmpty()) { //Si el espacio 1 no esta vacio es porque el equipo 1 GAN (ahi esta la cruz)
                            resultadoPronostico = ResultadoEnum.GANADOR;
                        } else {
                            if (!pronosticos.get(i).split(";")[4].isEmpty()) { //PIERDE EQUIPO 1
                                resultadoPronostico = ResultadoEnum.PERDEDOR;
                            } else { //EMPATE
                                resultadoPronostico = ResultadoEnum.EMPATE;
                            }
                        }
                        //Asignamos los valores de las variables mediante los set al pronostico
                        pronostico.setParticipante(participante);
                        pronostico.setPartido(partido); //Asignamos los partidos de la ronda
                        pronostico.setEquipo(partido.getEquipo1()); //Asignamos al equipo y luego su resultado
                        pronostico.setResultado(resultadoPronostico); //seteamos el resultado
                        pronosticosRonda.add(pronostico); //Almacenamos el pronostico en el array
                    }
                    i++;
                }
            }
        }
        Pronostico[] pronosticosArray = new Pronostico[pronosticosRonda.size()];
        return pronosticosRonda.toArray(pronosticosArray); //El metodo retorna el array de los pronosticos
    }

    //METODO PARA ASIGNAR LOS PRONOSTICOS CARGADOS A LOS PARTICIPANTES
    public static void asignarPronosticos(Participante[] participantes, Pronostico[] pronosticos) {
        List<Pronostico> pronosticoParticipantes = new ArrayList<Pronostico>();
        for (Participante participante : participantes) {
            for (Pronostico pronostico : pronosticos) {
                //si el participante del pronostico es igual al participante
                if(pronostico.getParticipante().equals(participante)){
                    pronosticoParticipantes.add(pronostico);

                }
            }
            Pronostico[] pronosticosPariticipante = new Pronostico[pronosticoParticipantes.size()];
            participante.setPronosticos(pronosticoParticipantes.toArray(pronosticosPariticipante));
            pronosticoParticipantes.clear(); //borramos

        }
    }


    //METODO PARA VALIDAR EL ARCHIVO DE RESULTADOS
    public static boolean validarArchivoResultados(String linea) {
        String[] contenidoLinea = linea.split(";");
        //Validar la cantidad de celdas de la linea
        if(contenidoLinea.length !=5) {System.out.println("Error - cantidad de columnas incorrecto");}

        //validar que los goles sean enteros
        //Para ellos primero los convertimos
        try {
            Integer.parseInt(contenidoLinea[2]); //goles equipo 1
            Integer.parseInt(contenidoLinea[3]); //goles equipo 2
        } catch (Exception e) {
            System.out.println("Error - formato de goles incorrecto");
            return false;
        }
        return true; //si pasa ambas validaciones sera verdadero
    }

    public static boolean validarArchivoPronosticos(String linea) {
        String[] contenidoLinea = linea.split(";");
        if(contenidoLinea.length !=6) {System.out.println("Error - cantidad de columnas incorrecto");}
        return true; //si cumple con la cantidad de columnas entonces pasa.
    }

    //METODO PARA CONTAR EL PUNTAJE
    public static void contarPuntaje(Pronostico[] pronosticos, Participante[] participantes) { //recibe como parametro los pronosticos
        //recorremos los participantes
        for(Participante participante : participantes){
            int totalpuntosparticipante = 0;
            totalpuntosparticipante += participante.puntaje();
            System.out.println("El puntaje del participante "+participante.getNombre()+" es de: "+totalpuntosparticipante);
        }
    }

    //METODO EXTRA PARA MOSTRAR INFO DEL PARTIDO
    public static void mostrarPartidos(Participante[] participantes,Ronda[] rondas, Pronostico[] pronostico) {
        //mostramos los pronosticos por cada participante
        for (Participante participante : participantes) {
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println("Pronosticos del participante "+participante.getNombre());
            for(Ronda ronda : rondas) {
                for(Pronostico pronostic : pronostico){
                    int i=0;
                    if(pronostic.getParticipante().equals(participante)) {

                        System.out.println("Partido " + i + ": " + pronostic.getPartido().getEquipo1().getNombre() + " - " + pronostic.getPartido().getEquipo2().getNombre()
                                + "| Resultado: " + pronostic.getEquipo().getNombre() + " " + pronostic.getPartido().resultado(pronostic.getEquipo())
                                + "| Su pronostico fue: " + pronostic.getResultado());
                        i++;
                    }

                }
            }
        }

    }

}

