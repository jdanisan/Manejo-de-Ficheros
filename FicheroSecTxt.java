package JD_PE;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FicheroSecTxt {

    public static void escribirFicheroSecuencial(String ruta, Scanner sc) throws IOException {
        Path rutaT = Paths.get(ruta + "\\Jugadores.txt");
        int id_user = 1; // Comenzamos con ID 1 si el archivo no existe

        // Si el archivo ya existe, obtener el último ID asignado
        if (Files.exists(rutaT)) {
            id_user = obtenerUltimoID(rutaT.toString()) + 1; // Incrementar el último ID encontrado
        }

        // Crear o abrir el archivo
        File ficherotxt = new File(rutaT.toString());
        try (BufferedWriter fic = new BufferedWriter(new FileWriter(ficherotxt, true))) {
            // Crear un nuevo jugador y asignarle el siguiente ID disponible
            Jugadores jugador = Jugadores.crearJugadores(sc);
            jugador.setUser_id(id_user);

            // Escribir el jugador en el fichero y añadir una nueva línea
            fic.write(jugador.toString());
            fic.newLine();
        }

        System.out.println("Jugador añadido correctamente con ID: " + id_user);
    }

    // Método para obtener el último ID del archivo
    public static int obtenerUltimoID(String ruta) {
        int ultimoId = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            String ultimoRegistro = null;

            // Leer todas las líneas y obtener la última línea del archivo
            while ((linea = br.readLine()) != null) {
                ultimoRegistro = linea;
            }

            // Si el archivo tiene datos, extraer el ID de la última línea
            if (ultimoRegistro != null && !ultimoRegistro.isEmpty()) {
                // El ID aparece después de "User_id=", lo extraemos con regex o substring
                int startIndex = ultimoRegistro.indexOf("User_id=") + 8; // Obtener la posición del inicio del ID
                int endIndex = ultimoRegistro.indexOf(",", startIndex); // Buscar el final del ID
                String idString = ultimoRegistro.substring(startIndex, endIndex).trim(); // Extraer el ID y quitar
                                                                                         // espacios
                ultimoId = Integer.parseInt(idString); // Convertir el ID a entero
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return ultimoId;
    }

}
