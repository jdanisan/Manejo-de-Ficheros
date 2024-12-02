/*Alta de jugadores:
Pedir al usuario que ingrese los datos del jugador (ID, nick, experiencia, nivel de vida, monedas).
Guardar los datos en el tipo de fichero seleccionado en la configuración.
 */
package JD_PE;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Vespertino
 */
public class Jugadores implements Serializable {

    private int user_id;
    private String nick_name;
    private int experience;
    private int life_level;
    private int coins;
    private int hard_coins;
    private DataInputStream dataInput;

    /*public Jugadores(int user_id, String nick_name, int experience,
            int life_level, int coins, int hard_coins) {
        this.user_id = user_id;
        this.nick_name = nick_name;
        this.experience = experience;
        this.life_level = life_level;
        this.coins = coins;
        this.hard_coins = hard_coins;
    }*/
    private static int contadorId = 0;

    public Jugadores(String nick_name, int experience, int life_level, int coins, int hard_coins) {
        this.user_id = contadorId++;
        this.nick_name = nick_name;
        this.experience = experience;
        this.life_level = life_level;
        this.coins = coins;
        this.hard_coins = hard_coins;
    }


    public Jugadores(String nick_name, int experience, int lifeLevel, int coins) {
    }


    @Override
    public String toString() {
        return '\n' + "(Jugador="
                + "User_id= " + getUser_id()
                + ", Nick_name= " + getNick_name() + '\''
                + ", Experiencia= " + getExperience() + '\''
                + ", Nivel de Vida= " + getLife_level() + '\''
                + ", Monedas= " + getCoins()
                + ", Monedas de Pago= " + getHard_coins()
                + ')';
    }

    public static Jugadores crearJugadores(Scanner sc) {

        //Revisar para que el id sea incremental.
        System.out.println("Dime el nick Name del jugador");
        String nick_name = sc.next();
        System.out.println("¿Qué experiencia tiene este jugador?");
        int experience = sc.nextInt();
        System.out.println("¿Cuál es la vida actual de su personaje?");
        int life_level = sc.nextInt();
        System.out.println("¿Cuantas monedas tiene de las pruebas?");
        int coins = sc.nextInt();
        System.out.println("¿Cuantas monedas de pago tiene?");
        int hard_coins = sc.nextInt();
        return new Jugadores(nick_name, experience, life_level, coins, hard_coins);

    }


    /**
     * @return the user_id
     */
    public int getUser_id() {
        return user_id;
    }

    /**
     * @param user_id the user_id to set
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    /**
     * @return the nick_name
     */
    public String getNick_name() {
        return nick_name;
    }

    /**
     * @param nick_name the nick_name to set
     */
    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    /**
     * @return the experience
     */
    public int getExperience() {
        return experience;
    }

    /**
     * @param experience the experience to set
     */
    public void setExperience(int experience) {
        this.experience = experience;
    }

    /**
     * @return the life_level
     */
    public int getLife_level() {
        return life_level;
    }

    /**
     * @param life_level the life_level to set
     */
    public void setLife_level(int life_level) {
        this.life_level = life_level;
    }

    /**
     * @return the coins
     */
    public int getCoins() {
        return coins;
    }

    /**
     * @param coins the coins to set
     */
    public void setCoins(int coins) {
        this.coins = coins;
    }

    /**
     * @return the hard_coins
     */
    public int getHard_coins() {
        return hard_coins;
    }

    /**
     * @param hard_coins the hard_coins to set
     */
    public void setHard_coins(int hard_coins) {
        this.hard_coins = hard_coins;
    }

    public static Jugadores buscarJugadorPorID(String ruta, int id) {
        // Identificar el tipo de archivo por la extensión
        if (ruta.endsWith(".txt")) {
            return buscarJugadorTxt(ruta, id);
        } else if (ruta.endsWith(".dat")) {
            return buscarJugadorDat(ruta, id);
        } else if (ruta.endsWith(".bin")) {
            return buscarJugadorBinario(ruta, id);
        } else if (ruta.endsWith(".obj")) {
            return buscarJugadorObjetoBinario(ruta, id);
        } else {
            System.out.println("Tipo de archivo no reconocido.");
            return null;
        }
    }

    // Buscar jugador en un archivo de texto plano
    private static Jugadores buscarJugadorTxt(String ruta, int id) {
        File archivo = new File(ruta);

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] datosJugador = linea.split(",");  // Asumiendo que los datos están separados por comas

                // Verificar si la línea tiene el formato correcto
                if (datosJugador.length < 6) {
                    System.out.println("Línea malformada: " + linea);
                    continue; // Saltar a la siguiente línea
                }

                // Extraer el ID
                try {
                    int idJugador = Integer.parseInt(datosJugador[0].split("=")[1].trim());

                    if (idJugador == id) {
                        // Crear y devolver el objeto Jugador
                        return crearJugadorDesdeDatos(datosJugador);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error al procesar el ID en la línea: " + linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;  // Retornar null si no se encuentra el jugador
    }


    // Buscar jugador en un archivo .dat (binario secuencial)
    private static Jugadores buscarJugadorDat(String ruta, int id) {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(ruta))) {
            while (dis.available() > 0) {
                int idJugador = dis.readInt();
                String nick_name = dis.readUTF();
                int experience = dis.readInt();
                int life_level = dis.readInt();
                int coins = dis.readInt();
                int hard_coins = dis.readInt();

                if (idJugador == id) {
                    return new Jugadores(nick_name, experience, life_level, coins, hard_coins);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Buscar jugador en un archivo binario de acceso aleatorio
    private static Jugadores buscarJugadorBinario(String ruta, int id) {
        try (RandomAccessFile raf = new RandomAccessFile(ruta, "r")) {
            while (raf.getFilePointer() < raf.length()) {
                int idJugador = raf.readInt();
                String nick_name = raf.readUTF();
                int experience = raf.readInt();
                int life_level = raf.readInt();
                int coins = raf.readInt();
                int hard_coins = raf.readInt();

                if (idJugador == id) {
                    return new Jugadores(nick_name, experience, life_level, coins, hard_coins);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Buscar jugador en un archivo de objetos binarios
    private static Jugadores buscarJugadorObjetoBinario(String ruta, int id) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta))) {
            while (true) {
                Jugadores jugador = (Jugadores) ois.readObject(); // Lanzará EOFException cuando se acaben los objetos
                if (jugador.getUser_id() == id) {
                    return jugador;
                }
            }
        } catch (EOFException e) {
            // Fin de archivo alcanzado
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Jugadores crearJugadorDesdeDatos(String[] datosJugador) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static void eliminarJugadorPorID(String ruta, int id) {
        File archivo = new File(ruta);
        File archivoTemp = new File(ruta + ".tmp");  // Archivo temporal

        // Verificar si el archivo original existe
        if (!archivo.exists()) {
            System.out.println("El archivo no existe: " + ruta);
            return;
        }

        boolean jugadorEliminado = false;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo));
             BufferedWriter bw = new BufferedWriter(new FileWriter(archivoTemp))) {

            String linea;

            // Leer cada línea del archivo original
            while ((linea = br.readLine()) != null) {
                // Extraemos solo el ID usando una expresión regular
                try {
                    String idStr = linea.replaceAll("^.*User_id=\\s*(\\d+).*$", "$1").trim();  // Extraer el ID
                    int idJugador = Integer.parseInt(idStr);  // Convertir a entero

                    // Si el ID no coincide con el jugador a eliminar, copiamos la línea al archivo temporal
                    if (idJugador != id) {
                        bw.write(linea);
                        bw.newLine();
                    } else {
                        jugadorEliminado = true;
                        System.out.println("Jugador con ID " + idJugador + " ha sido eliminado.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error al procesar el ID en la línea: " + linea);
                }
            }

        } catch (IOException e) {
            System.out.println("Error al procesar el archivo: " + e.getMessage());
            e.printStackTrace();
        }

        // Reemplazar el archivo original por el temporal si el jugador fue eliminado
        if (jugadorEliminado) {
            if (archivo.delete()) {
                boolean renombrado = archivoTemp.renameTo(archivo);
                if (!renombrado) {
                    System.out.println("Error al renombrar el archivo temporal.");
                }
            } else {
                System.out.println("No se pudo eliminar el archivo original.");
            }
        } else {
            // Si no se eliminó ningún jugador, borrar el archivo temporal
            archivoTemp.delete();
        }
    }

    /*Fichero de acceso aleatorio binario *****************************************************************************************/
    public static void JugadorBinario(String ruta, Scanner sc) throws FileNotFoundException {
        File archivo = new File(ruta + "\\Jugadores2.dat");
        int id = 0;
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

    public static int elegirID(Scanner sc) {
        int id;
        System.out.println("Dime el ID a buscar");
        id = sc.nextInt();
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

    public static int obtenerTipoArchivoLista(Scanner sc) {
        System.out.println("Selecciona el tipo de archivo:");
        System.out.println("1. Archivo de texto");
        System.out.println("2. Archivo binario secuencial (.dat)");
        System.out.println("3. Archivo de objetos binario");
        System.out.println("4. Archivo XML");
        System.out.print("Elige una opción: ");

        int tipoArchivo = sc.nextInt();
        return tipoArchivo;
    }

    // Método principal para listar jugadores según el tipo de archivo
    public static void listarJugadores(String ruta, int tipoArchivo) throws IOException {
        switch (tipoArchivo) {
            case 1:
                listarJugadoresTxt(ruta + "Jugadores.txt");
                break;
            case 2:
                listarJugadoresDat(ruta + "Jugadores.dat");
                break;
            case 3:
                listarJugadoresObjetoBinario(ruta + "Jugadores.dat");
                break;
            case 4:
                listarJugadoresXML(ruta + "Jugadores.xml");
                break;
            default:
                System.out.println("Tipo de archivo no soportado.");
        }
    }

    // Listar jugadores de archivo de texto
    private static void listarJugadoresTxt(String ruta) {
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);  // Mostrar cada línea
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado.");
        } catch (IOException e) {
            System.out.println("Error de lectura del archivo.");
        }
    }

    public static void listarJugadoresDat(String ruta) throws IOException {
        // Lista para almacenar los jugadores leídos del archivo
        List<Jugadores> jugadores = new ArrayList<>();

        // Abrir el archivo y leerlo usando DataInputStream dentro de un bloque try-with-resources
        try (DataInputStream dataInput = new DataInputStream(new FileInputStream(ruta))) {
            // Bucle infinito que se detendrá cuando alcancemos el final del archivo
            while (true) {
                try {
                    // Leer un jugador desde el archivo y añadirlo a la lista
                    Jugadores jugador = leerJugador(dataInput);
                    jugadores.add(jugador);
                } catch (EOFException e) {
                    // Capturar la excepción que indica que hemos llegado al final del archivo
                    break; // Salir del bucle
                }
            }
        }

        // Iterar por la lista de jugadores y mostrar sus datos en la consola
        for (Jugadores jugador : jugadores) {
            System.out.println("ID: " + jugador.getUser_id()); // Mostrar ID del jugador
            System.out.println("Nick Name: " + jugador.getNick_name()); // Mostrar apodo del jugador
            System.out.println("Experiencia: " + jugador.getExperience()); // Mostrar experiencia
            System.out.println("Nivel de Vida: " + jugador.getLife_level()); // Mostrar nivel de vida
            System.out.println("Monedas: " + jugador.getCoins()); // Mostrar monedas
            System.out.println("Monedas de Pago: " + jugador.getHard_coins()); // Mostrar monedas premium
            System.out.println("------------------------------"); // Separador entre jugadores
        }
    }

    private static Jugadores leerJugador(DataInputStream dataInput) throws IOException {
        // Leer los datos de un jugador desde el flujo binario

        String nick = dataInput.readUTF(); // Leer el apodo (cadena UTF)
        int experience = dataInput.readInt(); // Leer la experiencia (entero)
        int lifeLevel = dataInput.readInt(); // Leer el nivel de vida (entero)
        int coins = dataInput.readInt(); // Leer la cantidad de monedas (entero)
        int hardCoins = dataInput.readInt(); // Leer la cantidad de monedas premium (entero)

        // Crear y devolver un nuevo objeto Jugadores con los datos leídos
        return new Jugadores(nick, experience, lifeLevel, coins, hardCoins);
    }

    // Listar jugadores de archivo de objetos binarios
    private static void listarJugadoresObjetoBinario(String ruta) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta))) {
            while (true) {
                Jugadores jugador = (Jugadores) ois.readObject();
                System.out.println(jugador);  // Usamos el método toString() del objeto Jugadores
                System.out.println("------------------------------");
            }
        } catch (EOFException e) {
            // Fin de archivo
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error de lectura del archivo de objetos.");
        }
    }

    // Listar jugadores de archivo XML
    private static void listarJugadoresXML(String ruta) {
        try {
            File xmlFile = new File(ruta);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("jugador");

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) nNode;

                    String id = elem.getElementsByTagName("id").item(0).getTextContent();
                    String nickName = elem.getElementsByTagName("nickname").item(0).getTextContent();
                    String lifeLevel = elem.getElementsByTagName("lifelevel").item(0).getTextContent();
                    String coins = elem.getElementsByTagName("coins").item(0).getTextContent();
                    String hardCoins = elem.getElementsByTagName("hardcoins").item(0).getTextContent();

                    System.out.println("ID: " + id);
                    System.out.println("Nick Name: " + nickName);
                    System.out.println("Nivel de Vida: " + lifeLevel);
                    System.out.println("Monedas: " + coins);
                    System.out.println("Monedas de Pago: " + hardCoins);
                    System.out.println("------------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al leer el archivo XML.");
        }
    }

    // Método auxiliar para pedir al usuario la ruta y el tipo de archivo
    public static void seleccionarArchivo(Scanner sc) throws IOException {
        System.out.println("Escribe la ruta del archivo que deseas listar: ");
        String ruta = sc.next();

        System.out.println("Selecciona el tipo de archivo:");
        System.out.println("1. Archivo de texto");
        System.out.println("2. Archivo binario secuencial (.dat)");
        System.out.println("3. Archivo de objetos binario");
        System.out.println("4. Archivo XML");
        int tipoArchivo = sc.nextInt();

        Jugadores.listarJugadoresDat(ruta);
    }

}
