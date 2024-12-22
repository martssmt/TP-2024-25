import java.util.Scanner;


/**
 * Clase con métodos de utilidad para la entrada de datos por teclado.
 */

public class Utilidades {

    /**
     * Muestra un mensaje por pantalla y devuelve una cadena de texto introducida por el usuario.
     *
     * @param teclado Scanner
     * @param s       Instrucciones de lo que hay que introducir
     * @return Cadena leída.
     */

    public static String leerCadena(Scanner teclado, String s) {
        System.out.print(s);
        String cadena = teclado.nextLine();
        return cadena;
    }

    /**
     * Muestra un mensaje por pantalla y lee un número introducido por el usuario.
     *
     * @param teclado Scanner
     * @param mensaje Instrucciones de lo que hay que introducir
     * @param minimo  Límite inferior
     * @param maximo  Límite superior
     * @return Número válido (contenido en los límites introducidos).
     */

    public static int leerNumero(Scanner teclado, String mensaje, int minimo, int maximo) {
        boolean esValido = false;
        int num = 0;
        while (!esValido) {
            System.out.print(mensaje);
            if (teclado.hasNextInt()) {
                num = teclado.nextInt();
                if (num >= minimo && num <= maximo) esValido = true;
                else teclado.nextLine();
            } else teclado.nextLine();
        }
        teclado.nextLine(); // Limpia el buffer de la entrada
        return num;
    }

    /**
     * Muestra un mensaje por pantalla y lee un número entero introducido por el usuario que representa un día de la semana.
     *
     * @param teclado Scanner
     * @param mensaje Instrucciones de lo que hay que introducir
     * @return Número indicador del día de la semana.
     */

    public static int leerDiaDeLaSemana(Scanner teclado, String mensaje) {
        String dia;
        int posicion;
        do {
            System.out.print(mensaje);
            dia = teclado.nextLine().toUpperCase();
            switch (dia) {
                case "L" -> posicion = 0;
                case "M" -> posicion = 1;
                case "X" -> posicion = 2;
                case "J" -> posicion = 3;
                case "V" -> posicion = 4;
                case "S" -> posicion = 5;
                case "D" -> posicion = 6;
                default -> posicion = -1;
            }
        } while (posicion == -1);
        return posicion;
    }

    /**
     * Convierte el carácter de un día de la semana a su número entero correspondiente.
     *
     * @param dia Carácter del dia de la semana a convertir
     * @return Número indicador del día de la semana.
     */

    public static int diaSemanaAPosicion(String dia) {
        int posicion;
        String dia1 = dia.substring(0, 1).toUpperCase();
        switch (dia1) {
            case "L" -> posicion = 0;
            case "M" -> posicion = 1;
            case "X" -> posicion = 2;
            case "J" -> posicion = 3;
            case "V" -> posicion = 4;
            case "S" -> posicion = 5;
            case "D" -> posicion = 6;
            default -> posicion = -1;
        }
        return posicion;
    }

    /**
     * Convierte el número entero de un día de la semana al nombre completo del día al que le corresponde esa posición.
     *
     * @param pos Número indicador del día de la semana
     * @return Nombre del día de la semana correspondiente.
     */

    public static String posicionADiaSemana(int pos) {
        String dia = "Desconocido";
        switch (pos) {
            case 0 -> dia = "Lunes";
            case 1 -> dia = "Martes";
            case 2 -> dia = "Miércoles";
            case 3 -> dia = "Jueves";
            case 4 -> dia = "Viernes";
            case 5 -> dia = "Sábado";
            case 6 -> dia = "Domingo";
        }
        return dia;
    }

}