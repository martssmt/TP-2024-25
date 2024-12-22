import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


/**
 * Almacena un conjunto de recetas.
 * Permite agregar, buscar y eliminar recetas.
 * Permite guardar y cargar recetas desde archivos.
 */

public class LibroDeRecetas {

    // Propiedades:

    private final int maxRecetasEnLibro;
    private final Receta[] recetas;

    /**
     * Constructor de la clase.
     *
     * @param maxRecetasEnLibro Número máximo de Recetas en el Libro
     */

    public LibroDeRecetas(int maxRecetasEnLibro) {
        this.maxRecetasEnLibro = maxRecetasEnLibro;
        this.recetas = new Receta[maxRecetasEnLibro];
    }

    /**
     * Permite agregar una receta.
     *
     * @param receta Receta a agregar
     * @return si la operación se ha podido realizar correctamente
     */

    public boolean agregarReceta(Receta receta) {
        boolean fin = false;
        if (!recetasCompletas()) {
            for (int i = 0; !fin && i < maxRecetasEnLibro; i++) {
                if (recetas[i] == null) {
                    recetas[i] = receta;
                    fin = true;
                }
            }
        } else System.out.println("No se pudo añadir la receta.");
        return fin;
    }

    /**
     * Permite buscar una receta en el libro.
     *
     * @param texto Nombre o parte del nombre de la receta
     * @return Array con aquellas recetas que contienen el texto de búsqueda como subcadena.
     */

    public Receta[] buscarRecetaPorNombre(String texto) {
        boolean fin = false;
        Receta[] respuesta = new Receta[maxRecetasEnLibro];
        int contOcupadas = 0;
        for (int i = 0; !fin && i < maxRecetasEnLibro; i++) {
            if (recetas[i] != null && !texto.isEmpty()) {
                if (recetas[i].getNombre().toLowerCase().contains(texto.toLowerCase())) {
                    respuesta[contOcupadas] = recetas[i];
                    contOcupadas++;
                }
            } else if (texto.isEmpty()) {
                respuesta = recetas;
                fin = true;
            } else fin = true;
        }
        return respuesta;
    }

    /**
     * Permite guardar recetas en un fichero de texto usando una representación textual de las recetas.
     *
     * @param nombreArchivo Nombre del Archivo a guardar recetas
     * @throws IOException Error relacionado con el archivo
     */

    public void guardarRecetasEnArchivo(String nombreArchivo) throws IOException {
        File fich = new File(nombreArchivo);
        PrintWriter out = new PrintWriter(fich);
        boolean fin = false;
        for (int i = 0; !fin && i < maxRecetasEnLibro; i++) {
            if (recetas[i] != null) {
                out.print(recetas[i].toRawString());
            } else fin = true;
        }
        out.close();
    }

    /**
     * Permite cargar recetas a partir de un fichero de texto usando una representación textual de las recetas.
     *
     * @param nombreArchivo    Nombre del Archivo desde el que se cargarán recetas
     * @param maxIngredientes  Número máximo de Ingredientes
     * @param maxInstrucciones Número máximo de Instrucciones
     * @throws IOException Error relacionado con el archivo
     */

    public void cargarRecetasDeArchivo(String nombreArchivo, int maxIngredientes, int maxInstrucciones) throws IOException {
        Scanner in = new Scanner(new FileReader(nombreArchivo));
        String nomReceta;
        int index = 0;
        while (in.hasNextLine() && index < maxRecetasEnLibro) {
            nomReceta = in.nextLine();
            this.recetas[index] = new Receta(nomReceta, maxIngredientes, maxInstrucciones);
            String linea = in.nextLine();
            while (!linea.equals("INSTRUCCIONES")) {
                if (!recetas[index].ingredientesCompletos()) this.recetas[index].agregarIngrediente(linea);
                if (in.hasNextLine()) {
                    linea = in.nextLine();
                }
            }
            linea = in.nextLine();
            while (!linea.equals("-----")) {
                if (!recetas[index].instruccionesCompletas()) this.recetas[index].agregarInstruccion(linea);
                if (in.hasNextLine()) {
                    linea = in.nextLine();
                }
            }
            index++;
        }
        in.close();
    }

    /**
     * Permite saber si el libro de recetas está completo o no.
     *
     * @return True si el libro está completo
     */

    public boolean recetasCompletas() {
        boolean completas = true;
        if (maxRecetasEnLibro != 0) completas = recetas[maxRecetasEnLibro - 1] != null;
        return completas;
    }

    /**
     * Devuelve el número de recetas que contiene el libro.
     *
     * @return Número de recetas en el libro
     */

    public int numRecetas() {
        boolean fin = false;
        int cont = 0;
        for (int i = 0; !fin && i < maxRecetasEnLibro; i++) {
            if (recetas[i] != null) {
                cont++;
            } else fin = true;
        }
        return cont;
    }

    /**
     * Permite eliminar una receta en concreto del libro.
     *
     * @param seleccionada Receta a eliminar
     */

    public void eliminarReceta(Receta seleccionada) {
        boolean fin = false;
        for (int i = 0; !fin && i < maxRecetasEnLibro; i++) {
            if (recetas[i] == seleccionada) {
                fin = true;
                boolean fin2 = false;
                for (int j = i; !fin2 && j < maxRecetasEnLibro - 1; j++) {
                    if (recetas[j] != null) {
                        recetas[j] = recetas[j + 1];
                    } else fin2 = true;
                }
                recetas[maxRecetasEnLibro - 1] = null;
            }
        }
    }

}