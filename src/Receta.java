/**
 * Representa una receta con un nombre, ingredientes e instrucciones.
 * Permite agregar ingredientes e instrucciones.
 * Permite guardar y cargar recetas desde archivos.
 */

public class Receta {

    // Propiedades:

    private final String nombre;
    private final int maxIngredientes;
    private final int maxInstrucciones;
    private final String[] ingredientes;
    private final String[] instrucciones;

    /**
     * Constructor de la clase.
     *
     * @param nombre           Nombre de la receta
     * @param maxIngredientes  Número máximo de Ingredientes
     * @param maxInstrucciones Número máximo de Instrucciones
     */

    public Receta(String nombre, int maxIngredientes, int maxInstrucciones) {
        this.nombre = nombre;
        this.maxIngredientes = maxIngredientes;
        this.maxInstrucciones = maxInstrucciones;
        this.ingredientes = new String[maxIngredientes];
        this.instrucciones = new String[maxInstrucciones];

    }

    /**
     * Getter
     *
     * @return Nombre de la receta.
     */

    public String getNombre() {
        return nombre;
    }

    /**
     * Getter
     *
     * @return Ingredientes de la receta.
     */

    public String[] getIngredientes() {
        return ingredientes;
    }

    /**
     * Getter
     *
     * @return Instrucciones de la receta.
     */

    public String[] getInstrucciones() {
        return instrucciones;
    }

    /**
     * Getter
     *
     * @return Número máximo de ingredientes de la receta.
     */

    public int getMaxIngredientes() {
        return maxIngredientes;
    }

    /**
     * Getter
     *
     * @return Número máximo de instrucciones de la receta.
     */

    public int getMaxInstrucciones() {
        return maxInstrucciones;
    }

    /**
     * Permite agregar ingredientes a una receta.
     *
     * @param ingrediente Ingrediente a agregar
     * @return Si se ha agregado correctamente o no.
     */

    public boolean agregarIngrediente(String ingrediente) {
        boolean fin = false;
        if (ingredientesCompletos()) {
            System.out.println("No se pueden añadir más ingredientes.");
        } else {
            for (int i = 0; !fin && i < maxIngredientes; i++) {
                if (ingredientes[i] == null) {
                    ingredientes[i] = ingrediente;
                    fin = true;
                }
            }
        }
        return fin;
    }

    /**
     * Permite agregar instrucciones a una receta.
     *
     * @param instruccion Instrucción a agregar
     * @return Si se ha agregado correctamente o no.
     */

    public boolean agregarInstruccion(String instruccion) {
        boolean fin = false;
        if (instruccionesCompletas()) {
            System.out.println("No se pueden añadir más ingredientes.");
        } else {
            for (int i = 0; !fin && i < maxInstrucciones; i++) {
                if (instrucciones[i] == null) {
                    instrucciones[i] = instruccion;
                    fin = true;
                }
            }
        }
        return fin;
    }

    /**
     * Devuelve si la lista de ingredientes está completa.
     *
     * @return True si la lista de ingredientes está completa.
     */

    public boolean ingredientesCompletos() {
        return ingredientes[ingredientes.length - 1] != null;
    }

    /**
     * Devuelve si la lista de instrucciones está completa.
     *
     * @return True si la lista de instrucciones está completa.
     */

    public boolean instruccionesCompletas() {
        return instrucciones[instrucciones.length - 1] != null;
    }

    /**
     * Devuelve el número de ingredientes que se ha añadido a una receta.
     *
     * @return Número de ingredientes en la receta.
     */

    public int numIngredientes() {
        int cont = 0;
        boolean fin = false;
        for (int i = 0; !fin && i < maxIngredientes; i++) {
            if (ingredientes[i] != null) {
                cont++;
            } else fin = true;
        }
        return cont;
    }

    /**
     * Devuelve el número de instrucciones que se ha añadido a una receta.
     *
     * @return Número de instrucciones en la receta.
     */

    public int numInstrucciones() {
        int cont = 0;
        boolean fin = false;
        for (int i = 0; !fin && i < maxInstrucciones; i++) {
            if (instrucciones[i] != null) {
                cont++;
            } else fin = true;
        }
        return cont;
    }

    /**
     * Obtiene la información de la receta en formato de texto.
     */

    @Override
    public String toString() {
        boolean fin = false;
        StringBuilder respuesta = new StringBuilder();
        respuesta.append("Receta: " + this.nombre + "\n");
        respuesta.append("Ingredientes:\n");
        for (int i = 0; !fin && i < maxIngredientes; i++) {
            if (ingredientes[i] != null) {
                respuesta.append("- " + ingredientes[i] + "\n");
            } else fin = true;
        }
        fin = false;
        respuesta.append("Instrucciones:\n");
        for (int i = 0; !fin && i < maxInstrucciones; i++) {
            if (instrucciones[i] != null) {
                int cont = i + 1;
                respuesta.append(cont + ". " + instrucciones[i] + "\n");
            } else fin = true;
        }
        return respuesta.toString();
    }

    /**
     * Genera el formato de texto para guardar y cargar recetas a partir de un archivo de texto.
     */

    public String toRawString() {
        boolean fin = false;
        StringBuilder respuesta = new StringBuilder();
        respuesta.append(nombre + "\n");
        for (int i = 0; !fin && i < maxIngredientes; i++) {
            if (ingredientes[i] != null) {
                respuesta.append(ingredientes[i] + "\n");
            } else fin = true;
        }
        fin = false;
        respuesta.append("INSTRUCCIONES\n");
        for (int i = 0; !fin && i < maxInstrucciones; i++) {
            if (instrucciones[i] != null) {
                respuesta.append(instrucciones[i] + "\n");
            } else fin = true;
        }
        respuesta.append("-----\n");
        return respuesta.toString();
    }

}