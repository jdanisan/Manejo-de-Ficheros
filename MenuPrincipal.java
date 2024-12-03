package JD_PE;

import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import JD_PE.*;
import static JD_PE.Jugadores.crearJugadores;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Vespertino
 */
public class MenuPrincipal {

    public static void main(String[] args) throws InterruptedException, FileNotFoundException, IOException {
        //La entrada del usuario la leeres por medio de Scanner
        Scanner sc = new Scanner(System.in);
        int eleccion = 0;
//        File jugadores = new File(); 
        BufferedReader config = new BufferedReader(
                //En caso de pasr la base de datos a otro lado, habrá que modificar esta ruta.
                //new FileReader("D:\\Cesar\\config.txt"));
                new FileReader("C:\\Users\\JD\\Desktop\\config.txt"));

        while (eleccion <= 0 || eleccion >= 8) {
            MostrarMenu("Submenu");
            TimeUnit.SECONDS.sleep(3);
            eleccion = sc.nextInt();

        }
        switch (eleccion) {
            case 1:
                //Lllamar a la clase Altajugadores
                CrearFichero(sc, config);
                System.out.println("Ingreso de un jugador");

                break;
            case 2:
                //llamar a la función baja de jugadores
                System.out.println("Vamos a eliminar un registro");
                Jugadores.eliminarJugadorPorID(elegirZona(sc), Jugadores.elegirID(sc));
                break;
            case 3:
                //Modificar datos de un jugador
                System.out.println("¿En qué nos hemos equivocado?");
                ModificarJugadores.modificarJugadorPorID(elegirZona(sc), Jugadores.elegirID(sc), sc);
                break;
            case 4:
                //Buscar jugador por ID
                Jugadores.buscarJugadorPorID(elegirZona(sc), Jugadores.elegirID(sc)).toString();
                System.out.println("¿Qué usuario quieres buscar?");
                break;
            case 5:
                //Listar todos los jugadores registrados
                Jugadores.listarJugadores(elegirZona(sc), Jugadores.obtenerTipoArchivoLista(sc));
                break;
            case 6:
                System.out.println("Mostrando submenu...");
                MostrarMenu("5- Fichero de texto XML");
                break;
            case 7:
                System.out.println("Hasta la proxima, jefe");
                System.exit(0);
                break;
            default:
                throw new AssertionError();
        }

    }

    public static void MostrarMenu(String Objetivo) {


        String objetivo = Objetivo;

        try {
            BufferedReader config = new BufferedReader(
                    //En caso de pasar la base de datos a otro lado, habrá que modificar esta ruta.
                    //new FileReader("D:\\Cesar\\config.txt"));
                    new FileReader("C:\\Users\\JD\\Desktop\\config.txt"));
            String linea;
            while ((linea = config.readLine()) != null) {
                if (!linea.contentEquals(objetivo)) {
                    System.out.println(linea);
                } else {
                    break;
                }

            }

            config.close();
        } catch (FileNotFoundException fn) {
            System.out.println("Hay un error con el menu");
        } catch (IOException io) {
            System.out.println("Error de localizaciones");
        }
    }
    
    public static String elegirZona(Scanner sc){
       String ruta, linea, objetivo, linea2;
        int elegir;
        linea = "Submenu2";
        linea2 = "Buenas";
        System.out.println("¿Me puedes escribir la ruta para el archivo?");
        ruta = sc.next();
        return ruta;
    }
    
    private static void CrearFichero(Scanner sc, BufferedReader config) throws IOException, InterruptedException {
        String ruta, linea, objetivo, linea2;
        int elegir;
        linea = "Submenu2";
        linea2 = "Buenas";
        System.out.println("¿Me puedes escribir la ruta para el archivo?");
        ruta = sc.next();
        while ((objetivo = config.readLine()) != null) {
            if (objetivo.contains(linea)) {
                while ((objetivo = config.readLine()) != null) {
                    System.out.println(objetivo);
                    if (objetivo.contains(linea2)) {
                        break;
                    }
                }

            }
        }
        System.out.println("¿Qué tipo de archivo quieres como base de datos?");
        elegir = sc.nextInt();
        switch (elegir) {
            case 1:
                System.out.println("Creando fichero secuencial de texto...");
                FicheroSecTxt.escribirFicheroSecuencial(ruta, sc);
                main(new String[]{});
                break;
                
            case 2:
                System.out.println("Creando fichero secuencial binario...");
                FicheroBinario.FicheroBinario(ruta, sc);//REVISAR************************************
                //FicheroBinario(ruta,sc);
                main(new String[]{});
                break;
               
            case 3:
                System.out.println("Creando fichero de objetos...");
                FicheroObjetos.FicheroObjetos(ruta, sc);
                main(new String[]{});
                break;
                
            case 4:
                System.out.println("Creando fichero de acceso aleatorio binario...");
                Jugadores.JugadorBinario(ruta, sc);
                Jugadores.LeerJugador(ruta, Jugadores.elegirID(sc));
                main(new String[]{});
                break;
            case 5:
                System.out.println("Creando fichero de texto XML...");
                Jugadores.JugadorXML(ruta, sc);
                main(new String[]{});
                break;
                
            default:
                throw new AssertionError();
        }
    }



}
