package modelo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GestorPronosticos {
    
    public static void main(String[] args) {
    	//Indicamos las rutas de entrada
    	String archivoResultados = "C:\\Users\\Usuario\\eclipse-workspace\\TPI_Java\\archivos\\resultados.csv";
    	String archivoPronosticos = "C:\\Users\\Usuario\\eclipse-workspace\\TPI_Java\\archivos\\pronostico.csv"; //obtenemos al archivo 
    	//El gestor va a tener que crear la ronda (con los partidos y equipos) y el pronostico 
    	//Llamamos a los metodos para cargar la ronda y el pronostico desde el archivo 
    	
    	Ronda ronda = cargarRonda(archivoResultados); //creamos la ronda mediante el metodo cargarRonda
    	Pronostico[] pronostico = cargarPronostico(archivoPronosticos, ronda); //Creamos el pronostico mediante el metodo cargarPronostico donde se le pasa la ronda como parametro. 
    	
    	//Mostramos los puntajes del participante obtenidos en la ronda
    	System.out.println("El puntaje del participante es: "+contarPuntaje(pronostico));
    	System.out.println("-------------------------------------------------------------------------------");
    	
    	//A modo extra muestro los partidos con sus resultados y el pronostico del participante. 
    	System.out.println("PARTIDOS DE LA RONDA:");
    	mostrarPartidos(ronda, pronostico);
    }
    
    
    //METODO PARA CARGAR LA RONDA 
    public static Ronda cargarRonda(String archivoResultados) {
    	 //Creamos la ronda leyendo el archivo 
    	Ronda ronda = new Ronda(); 
    	List<String> resultados = new ArrayList<String>(); //La lista que contendra los resultados 
    	
    	//Leemos el archivo por ende usamos un Try
    	try {
    		resultados = Files.readAllLines(Paths.get(archivoResultados),StandardCharsets.ISO_8859_1); //para evitar la excepcion realizamos una codificacion 
    		resultados.remove(0); //removemos la primer linea que es la que tiene los titulos. 
    		
    	} catch(IOException e) {
    		System.out.println("Error al leer el archivo");
    		e.printStackTrace();
    	}
    	Partido[] partidosRonda = new Partido[resultados.size()]; //creamos los partidos pasando como parametro la cantidad  
    	int i=0;
    	for (String resultado : resultados) { //Leemos cada linea del archivo 
    		String[] vectorResultado = resultado.split(";"); //lee por cada separcion ; 
    		//Creamos el partido con sus datos (Equipo, goles)
    		Equipo equipo1 = new Equipo();
    		equipo1.setNombre(vectorResultado[0]); //le pasamos el nombre del equipo mediante el set
    		Equipo equipo2 = new Equipo();
    		equipo2.setNombre(vectorResultado[3]); //le pasamos el nombre del equipo mediante el set 
    		
    		Partido partido = new Partido();
    		//Seteamos el partido con los datos de la linea del archivo 
    		partido.setEquipo1(equipo1);
    		partido.setEquipo2(equipo2);
    		partido.setGolesEquipo1(Integer.valueOf(vectorResultado[1])); //convertimos el gol que esta en string a int
    		partido.setGolesEquipo2(Integer.valueOf(vectorResultado[2]));
    	
    		
    		//Agregamos el partido al array 
    		partidosRonda[i] = partido; 
    		i++; //incrementamos una ubicacion del array que tiene los partidos
    	}
    	//seteamos los partidos a la ronda
    	ronda.setCantPartidos(i); //indicamos la cantidad de partidos
    	ronda.setPartidos(partidosRonda);
    	return ronda; //retorna la ronda
    }
    
    //METODO PARA CARGAR EL PRONOSTICO 
    public static Pronostico[] cargarPronostico(String archivoPronosticos, Ronda ronda) { 
    	List<String> pronosticos = new ArrayList<String>(); //La lista que contendra los pronosticos del archivo 

    	//Leemos el archivo por ende usamos un Try
    	try {
    		pronosticos = Files.readAllLines(Paths.get(archivoPronosticos),StandardCharsets.ISO_8859_1); //para evitar la excepcion realizamos una codificacion 
    		pronosticos.remove(0); //removemos la primer linea que es la que tiene los titulos. 
    		
    	} catch(IOException e) {
    		System.out.println("Error al leer el archivo");
    		e.printStackTrace();
    	}
    	
    	Pronostico[] pronosticosRonda = new Pronostico[ronda.getCantPartidos()]; //creamos el array para los pronostico del tamaño de la cantidad de partidos que tiene la ronda
    	Partido[] partidosRonda = ronda.getPartidos(); //obtenemos los partidos de la ronda
    	
    	for(int i=0; i < ronda.getCantPartidos(); i++) { //recorremos los partidos
    		//por cada partido creamos el pronostico 
    		Pronostico pronostico = new Pronostico(); 
    		ResultadoEnum resultadoPronostico; //Creamos una variable de ResultadoEnum
    		
    		//INDICAR EL RESULTADOO
    		if(pronosticos.get(i).split(";")[1].isEmpty()) { //obtenemos la linea que estamos recorriendo separando por ;  y obteniendo la posicion 1
    			//IsEmpty. Devuelve el valor 1 (true) si hay un campo vacío; de lo contrario, devuelve el valor 0 (false).
    			//Si el espacio 1 esta vacio puede que sea un empate o que perdio el equipo 1
    			if(pronosticos.get(i).split(";")[3].isEmpty()) { //EMPATE 
    				resultadoPronostico = ResultadoEnum.EMPATE; 
    			} else { //PIERDE EQUIPO 1
    				resultadoPronostico = ResultadoEnum.PERDEDOR; 
    			}
    			
    		} else { //Si el espacio 1 no esta vacio es porque el equipo 1 GAN (ahi esta la cruz) 
    			resultadoPronostico = ResultadoEnum.GANADOR; 
    		}
    		//Asignamos los valores de las variables mediante los set al pronostico 
    		pronostico.setPartido(partidosRonda[i]); //Asignamos los partidos de la ronda
    		pronostico.setEquipo(partidosRonda[i].getEquipo1()); //Asignamos al equipo y luego su resultado 
    		pronostico.setResultado(resultadoPronostico); //seteamos el resultado 
    		
    		pronosticosRonda[i] = pronostico; //Almacenamos el pronostico en el array 
    		
    	}
    	
    	return pronosticosRonda; //El metodo retorna el array de los pronosticos 
    }
    
    //METODO PARA CONTAR EL PUNTAJE
    public static int contarPuntaje(Pronostico[] pronosticos) { //recibe como parametro los pronosticos
    	int totalpuntos = 0; 
    	for (Pronostico pronostico : pronosticos) { //con el forEach recorremos todos los pronosticos 
    		totalpuntos += pronostico.puntos(); //llamamos al metodo de la clase pronostico que asigna los puntos 
    		
    	}
    	return totalpuntos; 
    	
    }
    
    //METODO EXTRA PARA MOSTRAR INFO DEL PARTIDO 
    public static void mostrarPartidos(Ronda ronda, Pronostico[] pronostico) {
    	int j=1;
    	for(int i=0; i < ronda.getCantPartidos() ; i++) {
    		Partido partidoPronostico = ronda.getPartidos()[i];
    		Equipo equipoPronostico = partidoPronostico.getEquipo1();
    		System.out.println("Partido "+j+": "+ partidoPronostico.getEquipo1().getNombre()+" - "+ partidoPronostico.getEquipo2().getNombre()
    										+ "| Resultado: "+ equipoPronostico.getNombre()+" "+partidoPronostico.resultado(equipoPronostico)
    		   								+ "| Tu pronostico: "+ pronostico[i].getResultado());
    		j++;
    	}
    }
    
}