package JD_PE;

import java.io.*;
import java.util.Scanner;

public class BajaJugadores {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Solicitar al usuario el ID del jugador a eliminar
        System.out.println("Introduce el ID del jugador a eliminar: ");
        int id = sc.nextInt();
        sc.nextLine();  // limpiar el buffer

        // Ruta del archivo de jugadores (modifica la ruta)
        System.out.println("¿Cual es la ruta del archivo?");
        String ruta = sc.next();

        // Llamar al método para eliminar el jugador del archivo
        eliminarJugadorPorID(ruta, id);

        // Cerrar el scanner
        sc.close();
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

}
