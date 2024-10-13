/*Alta de jugadores:
Pedir al usuario que ingrese los datos del jugador (ID, nick, experiencia, nivel de vida, monedas).
Guardar los datos en el tipo de fichero seleccionado en la configuración.
 */
package JD_PE;

import java.io.Serializable;
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

    
    public Jugadores() {
    }
    

    @Override
    public String toString() {
        return '\n'+"(Jugador="
                + "User_id= " + getUser_id()
                + ", Nick_name= " + getNick_name() + '\''
                + ", Experiencia= " + getExperience() + '\''
                + ", Nivel de Vida= " + getLife_level() + '\''
                + ", Monedas= " + getCoins()
                + ", Monedas de Pago= " + getHard_coins()
                + ')';
    }
    public static Jugadores crearJugadores(Scanner sc)
    {
        
        //Revisar para que el id sea incremental.
        System.out.println("Dime el nick Name del jugador");
        String nick_name=sc.next();
        System.out.println("¿Qué experiencia tiene este jugador?");
        int experience=sc.nextInt();
        System.out.println("¿Cuál es la vida actual de su personaje?");
        int life_level=sc.nextInt();
        System.out.println("¿Cuantas monedas tiene de las pruebas?");
        int coins=sc.nextInt();
        System.out.println("¿Cuantas monedas de pago tiene?");
        int hard_coins=sc.nextInt();
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
}
