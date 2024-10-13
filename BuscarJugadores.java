package JD_PE;

import java.io.*;

public class BuscarJugadores {

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
                     return new Jugadores(nick_name, experience, life_level,  coins,  hard_coins);
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
                    return new Jugadores(nick_name, experience, life_level,  coins,  hard_coins);
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


}
