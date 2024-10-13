package JD_PE;

import java.io.*;
import java.nio.file.*;
import java.util.Scanner;

public class FicheroBinario {

    // Método que maneja la creación y escritura en el archivo binario
    public static void FicheroBinario(String ruta, Scanner sc) throws IOException {
        Path rutaT = Paths.get(ruta + "\\Jugadores.dat");
        int id_user = 1;
        if (!Files.exists(rutaT)) {
            System.out.println("No exite fichero");
            File fichDat = new File(rutaT.toString());//Declaramos el fichero con la ruta pedida anteriormente.

            FileOutputStream fileout = new FileOutputStream(fichDat);

            //FileInputStream filein = new FileInputStream(fichDat);
            int j;
            Jugadores jugador = Jugadores.crearJugadores(sc);
            id_user = obtenerUltimoID(rutaT) + 1; // Incrementar el último ID encontrado

            char[] cad = jugador.toString().toCharArray();
            for (j = 0; j < cad.length; j++) {
                fileout.write(cad[j]); 
            }
            fileout.close();
        } else {
            System.out.println("El fichero DAT ya existe");
            File fichDat = new File(rutaT.toString());//Declaramos el fichero con la ruta pedida anteriormente.
            FileOutputStream fileout = new FileOutputStream(fichDat, true);
            int j;
            Jugadores jugador = Jugadores.crearJugadores(sc);
            id_user = obtenerUltimoID(rutaT) + 1; // Incrementar el último ID encontrado
            char[] cad = jugador.toString().toCharArray();
            for (j = 0; j < cad.length; j++) {
                fileout.write(cad[j]);
            }
            fileout.close();
        }

    }

// Método para obtener el último ID del archivo
public static int obtenerUltimoID(Path rutaT) {
    int ultimoId = -1; // Inicializamos en -1 para indicar que no se encontró un ID válido
    try (BufferedReader br = new BufferedReader(new FileReader(rutaT.toFile()))) {
        String linea;
        String ultimoRegistro = null;

        // Leer todas las líneas y obtener la última línea del archivo
        while ((linea = br.readLine()) != null) {
            ultimoRegistro = linea; // Capturamos la última línea
        }

        // Si el archivo tiene datos, extraer el ID de la última línea
        if (ultimoRegistro != null && !ultimoRegistro.isEmpty()) {
            // El ID aparece después de "User_id=", lo extraemos
            int startIndex = ultimoRegistro.indexOf("User_id=") + 8; // Obtener la posición del inicio del ID
            if (startIndex >= 8) { // Verificamos si se encontró "User_id="
                int endIndex = ultimoRegistro.indexOf(",", startIndex); // Buscar el final del ID
                if (endIndex == -1) {
                    endIndex = ultimoRegistro.length(); // Si no se encontró, tomar hasta el final
                }
                String idString = ultimoRegistro.substring(startIndex, endIndex).trim(); // Extraer el ID y quitar espacios
                try {
                    ultimoId = Integer.parseInt(idString); // Convertir el ID a entero
                } catch (NumberFormatException e) {
                    System.err.println("Error al convertir el ID a número: " + e.getMessage());
                }
            }
        }
    } catch (IOException e) {
        System.err.println("Error al leer el archivo: " + e.getMessage());
    }
    return ultimoId;
}

}
