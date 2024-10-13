package JD_PE;

import java.io.*;
import java.util.Scanner;

public class ModificarJugadores {

    public static void modificarJugadorPorID(String ruta, int id, Scanner sc) {
        // Identificar el tipo de archivo por la extensión
        if (ruta.endsWith(".txt")) {
            modificarJugadorTxt(ruta, id, sc);
        } else if (ruta.endsWith(".dat")) {
            modificarJugadorDat(ruta, id, sc);
        } else if (ruta.endsWith(".bin")) {
            modificarJugadorBinario(ruta, id, sc);
        } else if (ruta.endsWith(".obj")) {
            modificarJugadorObjetoBinario(ruta, id, sc);
        } else {
            System.out.println("Tipo de archivo no reconocido.");
        }
    }

    // Modificar jugador en un archivo de texto plano
    private static void modificarJugadorTxt(String ruta, int id, Scanner sc) {
        File archivo = new File(ruta);
        File archivoTemp = new File(ruta + ".tmp");

        try (BufferedReader br = new BufferedReader(new FileReader(archivo)); BufferedWriter bw = new BufferedWriter(new FileWriter(archivoTemp))) {

            String linea;
            boolean jugadorModificado = false;

            while ((linea = br.readLine()) != null) {
                // Eliminar paréntesis y dividir por comas
                linea = linea.replace("(", "").replace(")", "");  // Eliminar paréntesis
                String[] datosJugador = linea.split(",");  // Dividir por comas

                // Extraer el ID del jugador
                int idJugador = -1;
                for (String dato : datosJugador) {
                    if (dato.trim().startsWith("User_id=")) {
                        idJugador = Integer.parseInt(dato.split("=")[1].trim());
                        break;
                    }
                }

                if (idJugador == id) {
                    // Pedir los nuevos datos al usuario
                    System.out.println("Introduce el nuevo nombre del jugador: ");
                    String nuevoNombre = sc.next();
                    System.out.println("Introduce la nueva experiencia: ");
                    int nuevaExperiencia = sc.nextInt();
                    System.out.println("Introduce el nuevo nivel de vida: ");
                    int nuevoNivelVida = sc.nextInt();
                    System.out.println("Introduce el nuevo número de monedas: ");
                    int nuevasMonedas = sc.nextInt();

                    // Sobreescribir con los nuevos datos
                    String nuevaLinea = String.format("(Jugador=User_id= %d, Nick_name= %s, Experiencia= %d, Nivel de Vida= %d, Monedas= %d, Monedas de Pago= %d)",
                            idJugador, nuevoNombre, nuevaExperiencia, nuevoNivelVida, nuevasMonedas, 25);  // Asignando monedas de pago como 25
                    bw.write(nuevaLinea);
                    bw.newLine();
                    jugadorModificado = true;
                } else {
                    bw.write(linea);
                    bw.newLine();
                }
            }

            if (jugadorModificado) {
                System.out.println("Jugador modificado con éxito.");
            } else {
                System.out.println("Jugador con ID " + id + " no encontrado.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Reemplazar el archivo original por el archivo temporal
        if (archivo.delete()) {
            archivoTemp.renameTo(archivo);
        } else {
            System.out.println("No se pudo eliminar el archivo original.");
        }
    }

    // Modificar jugador en un archivo binario de acceso aleatorio
    private static void modificarJugadorBinario(String ruta, int id, Scanner sc) {
        try (RandomAccessFile raf = new RandomAccessFile(ruta, "rw")) {
            boolean encontrado = false;

            while (raf.getFilePointer() < raf.length()) {
                long posicionActual = raf.getFilePointer();
                int idJugador = raf.readInt();
                String nickName = raf.readUTF();
                int experiencia = raf.readInt();
                int nivelVida = raf.readInt();
                int monedas = raf.readInt();
                int monedasDificiles = raf.readInt();

                if (idJugador == id) {
                    // Pedir los nuevos datos
                    System.out.println("Introduce el nuevo nombre del jugador: ");
                    String nuevoNombre = sc.next();
                    System.out.println("Introduce la nueva experiencia: ");
                    int nuevaExperiencia = sc.nextInt();
                    System.out.println("Introduce el nuevo nivel de vida: ");
                    int nuevoNivelVida = sc.nextInt();
                    System.out.println("Introduce el nuevo número de monedas: ");
                    int nuevasMonedas = sc.nextInt();

                    // Volver a la posición inicial del jugador
                    raf.seek(posicionActual);
                    raf.writeInt(idJugador);               // ID (no cambia)
                    raf.writeUTF(String.format("%-20s", nuevoNombre));  // Nombre (ajustado a 20 caracteres)
                    raf.writeInt(nuevaExperiencia);         // Nueva experiencia
                    raf.writeInt(nuevoNivelVida);           // Nuevo nivel de vida
                    raf.writeInt(nuevasMonedas);            // Nuevas monedas
                    raf.writeInt(monedasDificiles);         // Monedas difíciles (no cambia)

                    System.out.println("Jugador modificado con éxito.");
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

    // Modificar jugador en un archivo .dat (binario secuencial)
    private static void modificarJugadorDat(String ruta, int id, Scanner sc) {
        File archivo = new File(ruta);
        File archivoTemp = new File(ruta + ".tmp");

        try (DataInputStream dis = new DataInputStream(new FileInputStream(archivo)); DataOutputStream dos = new DataOutputStream(new FileOutputStream(archivoTemp))) {

            boolean jugadorModificado = false;

            // Bucle para leer el archivo hasta el final
            try {
                while (true) {
                    // Leer los datos del jugador
                    int idJugador = dis.readInt();
                    String nickName = dis.readUTF();
                    int experiencia = dis.readInt();
                    int nivelVida = dis.readInt();
                    int monedas = dis.readInt();
                    int monedasDifíciles = dis.readInt();

                    if (idJugador == id) {
                        // Pedir nuevos datos
                        System.out.println("Introduce el nuevo nombre del jugador: ");
                        String nuevoNombre = sc.next();
                        System.out.println("Introduce la nueva experiencia: ");
                        int nuevaExperiencia = sc.nextInt();
                        System.out.println("Introduce el nuevo nivel de vida: ");
                        int nuevoNivelVida = sc.nextInt();
                        System.out.println("Introduce el nuevo número de monedas: ");
                        int nuevasMonedas = sc.nextInt();

                        // Escribir los nuevos datos en el archivo temporal
                        dos.writeInt(idJugador);
                        dos.writeUTF(nuevoNombre);
                        dos.writeInt(nuevaExperiencia);
                        dos.writeInt(nuevoNivelVida);
                        dos.writeInt(nuevasMonedas);
                        dos.writeInt(monedasDifíciles);  // No cambiamos las monedas difíciles

                        jugadorModificado = true;
                    } else {
                        // Escribir los datos existentes en el archivo temporal sin cambios
                        dos.writeInt(idJugador);
                        dos.writeUTF(nickName);
                        dos.writeInt(experiencia);
                        dos.writeInt(nivelVida);
                        dos.writeInt(monedas);
                        dos.writeInt(monedasDifíciles);
                    }
                }
            } catch (EOFException eof) {
                // Fin del archivo alcanzado
                System.out.println("Fin del archivo alcanzado.");
            }

            if (jugadorModificado) {
                System.out.println("Jugador modificado con éxito.");
            } else {
                System.out.println("Jugador con ID " + id + " no encontrado.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Reemplazar el archivo original por el archivo temporal
        if (archivo.delete()) {
            archivoTemp.renameTo(archivo);
        } else {
            System.out.println("No se pudo eliminar el archivo original.");
        }
    }

    // Modificar jugador en un archivo de objetos binario
    private static void modificarJugadorObjetoBinario(String ruta, int id, Scanner sc) {
        // Modificar jugador en un archivo de objetos binario

        File archivo = new File(ruta);
        File archivoTemp = new File(ruta + ".tmp");

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo)); ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivoTemp))) {

            boolean jugadorModificado = false;

            while (true) {
                try {
                    Jugadores jugador = (Jugadores) ois.readObject();

                    if (jugador.getUser_id() == id) {
                        // Pedir nuevos datos al usuario
                        System.out.println("Introduce el nuevo nombre del jugador: ");
                        String nuevoNombre = sc.next();
                        System.out.println("Introduce la nueva experiencia: ");
                        int nuevaExperiencia = sc.nextInt();
                        System.out.println("Introduce el nuevo nivel de vida: ");
                        int nuevoNivelVida = sc.nextInt();
                        System.out.println("Introduce el nuevo número de monedas: ");
                        int nuevasMonedas = sc.nextInt();

                        // Actualizar los datos del jugador
                        jugador.setNick_name(nuevoNombre);
                        jugador.setExperience(nuevaExperiencia);
                        jugador.setLife_level(nuevoNivelVida);
                        jugador.setCoins(nuevasMonedas);

                        jugadorModificado = true;
                    }

                    // Escribir el objeto (modificado o no) al archivo temporal
                    oos.writeObject(jugador);

                } catch (EOFException e) {
                    break;  // Fin del archivo
                }
            }

            if (jugadorModificado) {
                System.out.println("Jugador modificado con éxito.");
            } else {
                System.out.println("Jugador con ID " + id + " no encontrado.");
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Reemplazar el archivo original por el archivo temporal
        if (archivo.delete()) {
            archivoTemp.renameTo(archivo);
        } else {
            System.out.println("No se pudo eliminar el archivo original.");
        }

    }
}
