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
            MostrarMenu();
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
                BajaJugadores.eliminarJugadorPorID(elegirZona(sc), elegirID(sc));
                break;
            case 3:
                //Modificar datos de un jugador
                System.out.println("¿En qué nos hemos equivocado?");
                ModificarJugadores.modificarJugadorPorID(elegirZona(sc), elegirID(sc), sc);
                break;
            case 4:
                //Buscar jugador por ID
                BuscarJugadores.buscarJugadorPorID(elegirZona(sc), elegirID(sc)).toString();
                System.out.println("¿Qué usuario quieres buscar?");
                break;
            case 5:
                //Listar todos los jugadores registrados
                ListarJugadores.listarJugadores(elegirZona(sc), ListarJugadores.obtenerTipoArchivo(sc));
                break;
            case 6:
                System.out.println("Mostrando submenu...");
                break;
            case 7:
                System.out.println("Hasta la proxima, jefe");
                System.exit(0);
                break;
            default:
                throw new AssertionError();
        }

    }

    public static void MostrarMenu() {

        String objetivo;
        objetivo = "Submenu2";
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
                JugadorBinario(ruta, sc);
                LeerJugador(ruta, elegirID(sc));
                main(new String[]{});
                break;
            case 5:
                System.out.println("Creando fichero de texto XML...");
                JugadorXML(ruta, sc);
                main(new String[]{});
                break;
                
            default:
                throw new AssertionError();
        }
    }

    /*Fichero de acceso aleatorio binario *****************************************************************************************/
    public static void JugadorBinario(String ruta, Scanner sc) throws FileNotFoundException {
        File archivo = new File(ruta + "\\Jugadores2.dat");
        int id=0;
        try (RandomAccessFile raf = new RandomAccessFile(archivo, "rw")) {
            raf.seek(raf.length());

            Jugadores jugador = crearJugadores(sc);
            jugador.setUser_id(id++);

            raf.writeInt(jugador.getUser_id());
            raf.writeUTF(String.format("%-20s", jugador.getNick_name()));
            raf.writeInt(jugador.getExperience());
            raf.writeInt(jugador.getLife_level());
            raf.writeInt(jugador.getCoins());
            raf.writeInt(jugador.getHard_coins());

            System.out.println("Jugador guardado en el archivo binario.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void LeerJugador(String ruta, int id) throws FileNotFoundException {
        File archivo = new File(ruta + "\\Jugadores2.dat");
        try (RandomAccessFile raf = new RandomAccessFile(archivo, "r")) {
            boolean encontrado = false;

            while (raf.getFilePointer() < raf.length()) {
                int userId = raf.readInt();
                String nickName = raf.readUTF();
                int experience = raf.readInt();
                int lifeLevel = raf.readInt();
                int coins = raf.readInt();
                int hardCoins = raf.readInt();

                if (userId == id) {
                    System.out.println("ID: " + userId);
                    System.out.println("Nombre: " + nickName.trim());
                    System.out.println("Experiencia: " + experience);
                    System.out.println("Nivel de vida: " + lifeLevel);
                    System.out.println("Monedas: " + coins);
                    System.out.println("Monedas difíciles: " + hardCoins);
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                System.out.println("Jugador con ID " + id + " no encontrado.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static int elegirID(Scanner sc){
        int id;
        System.out.println("Dime el ID a buscar");
        id=sc.nextInt();
        return id;
    }
 /*Ficheros XML ****************************************************************************************************************/
    public static void JugadorXML(String ruta, Scanner sc) throws FileNotFoundException {

        Path rutaT = Paths.get(ruta + "\\Jugadores2.xml");
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc;
            Element rootElement;

            if (Files.exists(rutaT)) {
                doc = docBuilder.parse(rutaT.toFile());
                doc.getDocumentElement().normalize();
                rootElement = doc.getDocumentElement();
            } else {
                doc = docBuilder.newDocument();
                rootElement = doc.createElement("jugadores");
                doc.appendChild(rootElement);
            }

            // Elemento jugador
            Element jugador = doc.createElement("jugador");
            rootElement.appendChild(jugador);

            System.out.println("Introduce el ID del jugador: ");
            int userId = sc.nextInt();
            sc.nextLine();  // limpiar el buffer

            System.out.println("Introduce el nombre del jugador: ");
            String nickName = sc.nextLine();

            System.out.println("Introduce el nivel de vida: ");
            int lifeLevel = sc.nextInt();

            System.out.println("Introduce el número de monedas: ");
            int coins = sc.nextInt();

            System.out.println("Introduce el número de monedas de pago: ");
            int hardCoins = sc.nextInt();

            // Atributos del jugador
            Element id = doc.createElement("id");
            id.appendChild(doc.createTextNode(Integer.toString(userId)));
            jugador.appendChild(id);

            Element nombre = doc.createElement("nickname");
            nombre.appendChild(doc.createTextNode(nickName));
            jugador.appendChild(nombre);

            Element nivelVida = doc.createElement("lifelevel");
            nivelVida.appendChild(doc.createTextNode(Integer.toString(lifeLevel)));
            jugador.appendChild(nivelVida);

            Element monedas = doc.createElement("coins");
            monedas.appendChild(doc.createTextNode(Integer.toString(coins)));
            jugador.appendChild(monedas);

            Element monedasDificiles = doc.createElement("hardcoins");
            monedasDificiles.appendChild(doc.createTextNode(Integer.toString(hardCoins)));
            jugador.appendChild(monedasDificiles);

            // Guardar el contenido en un archivo XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(rutaT.toString()));

            transformer.transform(source, result);

            System.out.println("Archivo guardado en: " + rutaT.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
