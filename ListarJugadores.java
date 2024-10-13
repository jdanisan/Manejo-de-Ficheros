package JD_PE;

import java.io.*;
import java.util.Scanner;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ListarJugadores {

    public static int obtenerTipoArchivo(Scanner sc) {
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
    public static void listarJugadores(String ruta, int tipoArchivo) {
        switch (tipoArchivo) {
            case 1:
                listarJugadoresTxt(ruta);
                break;
            case 2:
                listarJugadoresDat(ruta);
                break;
            case 3:
                listarJugadoresObjetoBinario(ruta);
                break;
            case 4:
                listarJugadoresXML(ruta);
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

    // Listar jugadores de archivo binario secuencial (.dat)
    private static void listarJugadoresDat(String ruta) {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(ruta))) {
            while (true) {
                int id = dis.readInt();
                String nickName = dis.readUTF();
                int experience = dis.readInt();
                int lifeLevel = dis.readInt();
                int coins = dis.readInt();
                int hardCoins = dis.readInt();

                System.out.println("ID: " + id);
                System.out.println("Nick Name: " + nickName.trim());
                System.out.println("Experiencia: " + experience);
                System.out.println("Nivel de Vida: " + lifeLevel);
                System.out.println("Monedas: " + coins);
                System.out.println("Monedas de Pago: " + hardCoins);
                System.out.println("------------------------------");
            }
        } catch (EOFException e) {
            // Fin de archivo
        } catch (IOException e) {
            System.out.println("Error de lectura del archivo binario.");
        }
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
    public static void seleccionarArchivo(Scanner sc) {
        System.out.println("Escribe la ruta del archivo que deseas listar: ");
        String ruta = sc.next();

        System.out.println("Selecciona el tipo de archivo:");
        System.out.println("1. Archivo de texto");
        System.out.println("2. Archivo binario secuencial (.dat)");
        System.out.println("3. Archivo de objetos binario");
        System.out.println("4. Archivo XML");
        int tipoArchivo = sc.nextInt();

        listarJugadores(ruta, tipoArchivo);
    }
}
