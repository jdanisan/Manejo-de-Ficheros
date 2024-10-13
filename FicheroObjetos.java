package JD_PE;
import java.io.*;
import java.nio.file.*;
import java.util.Scanner;

public class FicheroObjetos {

    public static void FicheroObjetos(String ruta, Scanner sc) throws IOException {
        Path rutaT = Paths.get(ruta + "\\Jugadores.dat");

        // Crear el directorio si no existe
        if (!Files.exists(rutaT.getParent())) {
            Files.createDirectories(rutaT.getParent());
        }

        int nuevoId = 1; // ID por defecto para el primer jugador

        if (Files.exists(rutaT)) {
            nuevoId = obtenerUltimoID(rutaT) + 1; // Obtener el último ID y aumentar en 1
            System.out.println("El fichero DAT ya existe. Se añadirá un nuevo jugador.");
        } else {
            System.out.println("No existe fichero, se creará uno nuevo.");
        }

        // Crear el nuevo ObjectOutputStream
        try (AppendableObjectOutputStream objectOut = new AppendableObjectOutputStream(new FileOutputStream(rutaT.toFile(), true))) {
            // Crear un nuevo jugador con el nuevo ID
            Jugadores jugador = Jugadores.crearJugadores(sc);
            jugador.setUser_id(nuevoId); // Asignar el nuevo ID al jugador
            objectOut.writeObject(jugador); // Escribir el jugador en el archivo
            System.out.println("Jugador añadido: " + jugador);
        }
    }

    // Método para obtener el último ID del archivo
    public static int obtenerUltimoID(Path rutaT) {
        int ultimoId = 0;
        try (ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(rutaT.toFile()))) {
            while (true) {
                Jugadores jugador = (Jugadores) objectIn.readObject(); // Leer cada jugador
                if (jugador.getUser_id() > ultimoId) {
                    ultimoId = jugador.getUser_id(); // Actualizar el último ID
                }
            }
        } catch (EOFException e) {
            // Fin del archivo alcanzado, se ha leído todo
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ultimoId;
    }

    // Clase para permitir la escritura en modo de añadir
    private static class AppendableObjectOutputStream extends ObjectOutputStream {
        public AppendableObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }

        protected void writeStreamHeader() throws IOException {
            // No escribir el encabezado para permitir añadir
            reset();
        }
    }
}
