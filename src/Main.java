import java.util.Scanner;

/**
 * Clase principal.
 *
 * @author martssmt
 * @author tomastte
 * @version 1.0.0
 */

public class Main {

    /**
     * Función init que pregunta al usuario si quiere cargar recetas desde un archivo.
     *
     * @param teclado                   Entrada de datos
     * @param maxIngredientesPorReceta  Número máximo de ingredientes que puede contener una receta
     * @param maxInstruccionesPorReceta Número máximo de instrucciones que puede contener una receta
     * @param maxRecetasEnLibro         Número máximo de recetas que puede contener el libro de recetas
     * @param nombreArchivoRecetas      Nombre del archivo de texto donde se guardarán las recetas
     * @return interfaz del usuario
     */

    private static InterfazUsuario init(Scanner teclado, int maxIngredientesPorReceta, int maxInstruccionesPorReceta, int maxRecetasEnLibro, String nombreArchivoRecetas) {
        InterfazUsuario interfaz;

        String s = Utilidades.leerCadena(teclado, "¿Cargar recetario desde un archivo? (Responde 'S' o 'N'): ");
        while (!s.equalsIgnoreCase("S") && !s.equalsIgnoreCase("N")) {
            System.out.println("Mensaje no válido.");
            s = Utilidades.leerCadena(teclado, "¿Cargar recetario desde un archivo? (Responde 'S' o 'N'): ");
        }

        if (s.equalsIgnoreCase("S")) {
            System.out.println("\tAVISOS:");
            System.out.println("\t\t-Si el archivo no existe o la ruta es incorrecta, se inicializará un recetario vacío.");
            System.out.println("\t\t-Si el número de recetas en el archivo excede el número introducido correspondiente al máximo de recetas en el libro, se guardarán las primeras.");
            System.out.println("\t\t-Pasará de la misma manera con los ingredientes e instrucciones.");
            if (nombreArchivoRecetas == null) {
                System.out.println("No se ha introducido ningún archivo (ver primer aviso).");
                interfaz = new InterfazUsuario(maxIngredientesPorReceta, maxInstruccionesPorReceta, maxRecetasEnLibro);
            } else
                interfaz = new InterfazUsuario(maxIngredientesPorReceta, maxInstruccionesPorReceta, maxRecetasEnLibro, nombreArchivoRecetas);
        } else {
            interfaz = new InterfazUsuario(maxIngredientesPorReceta, maxInstruccionesPorReceta, maxRecetasEnLibro);
        }
        return interfaz;
    }

    /**
     * Función main que inicializa una interfaz de usuario, y con ella un libro de recetas.
     *
     * @param args Argumentos de entrada conteniendo los parámetros del libro de recetas y libro de recetas
     */

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        String nombreArchivoRecetas = null;
        InterfazUsuario interfaz;

        try {
            int maxIngredientesPorReceta = Integer.parseInt(args[0]);
            int maxInstruccionesPorReceta = Integer.parseInt(args[1]);
            int maxRecetasEnLibro = Integer.parseInt(args[2]);
            if (args.length > 3) nombreArchivoRecetas = args[3];

            interfaz = init(teclado, maxIngredientesPorReceta, maxInstruccionesPorReceta, maxRecetasEnLibro, nombreArchivoRecetas);
            interfaz.iniciar(); // Ejecuta el aplicativo

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            teclado.close();
        }
    }
}