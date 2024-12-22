import java.io.IOException;
import java.util.Scanner;

/**
 * Gestiona la interaccion con el usuario a traves de un menu en consola.
 */

public class InterfazUsuario {

    // Propiedades:

    private final LibroDeRecetas libroDeRecetas;
    private final PlanificadorSemanal planificador;
    private final int maxIngredientes;
    private final int maxInstrucciones;

    /**
     * Constructor de la clase.
     *
     * @param maxIngredientes   Número máximo de Ingredientes
     * @param maxInstrucciones  Número máximo de Instrucciones
     * @param maxRecetasEnLibro Número máximo de Recetas en el Libro
     */

    public InterfazUsuario(int maxIngredientes, int maxInstrucciones, int maxRecetasEnLibro) {
        this.maxIngredientes = maxIngredientes;
        this.maxInstrucciones = maxInstrucciones;
        this.libroDeRecetas = new LibroDeRecetas(maxRecetasEnLibro);
        this.planificador = new PlanificadorSemanal();
    }

    /**
     * Constructor de la clase a partir de un archivo.
     *
     * @param maxIngredientes   Número máximo de Ingredientes
     * @param maxInstrucciones  Número máximo de Instrucciones
     * @param maxRecetasEnLibro Número máximo de Recetas en el Libro
     * @param archivoRecetas    Nombre del archivo desde el que se cargarán recetas
     */

    public InterfazUsuario(int maxIngredientes, int maxInstrucciones, int maxRecetasEnLibro, String archivoRecetas) {
        this(maxIngredientes, maxInstrucciones, maxRecetasEnLibro);
        try {
            libroDeRecetas.cargarRecetasDeArchivo(archivoRecetas, maxIngredientes, maxInstrucciones);
        } catch (IOException ex) {
            System.out.println("Error al cargar el archivo.");
        }
    }

    /**
     * Inicializa la interfaz de usuario.
     */

    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        menuPrincipal(scanner);
        scanner.close();
    }

    /**
     * Menú de opciones del usuario.
     *
     * @param scanner Entrada de datos
     */

    private void menuPrincipal(Scanner scanner) {
        String cadena = "";
        cadena += "--- Menú Principal ---\n";
        cadena += "1. Agregar Receta\n";
        cadena += "2. Consultar/Editar Receta\n";
        cadena += "3. Planificar Comidas\n";
        cadena += "4. Guardar Recetas\n";
        cadena += "5. Cargar Recetas\n";
        cadena += "6. Guardar Plan Semanal\n";
        cadena += "7. Salir\n\n";
        cadena += "Elige una opción: ";
        int opcion;
        do {
            System.out.println();
            opcion = Utilidades.leerNumero(scanner, cadena, 1, 7);
            switch (opcion) {
                case 1 -> agregarReceta(scanner);
                case 2 -> consultarReceta(scanner);
                case 3 -> planificarComidas(scanner);
                case 4 -> guardarRecetas(scanner);
                case 5 -> cargarRecetas(scanner);
                case 6 -> guardarPlanSemanal(scanner);
            }
        } while (opcion != 7);
        System.out.println("¡Has salido del recetario con éxito!");
    }

    /**
     * Función 1 del menú.
     * Permite agregar una receta al libro de recetas.
     *
     * @param scanner Entrada de datos
     */

    private void agregarReceta(Scanner scanner) {
        if (!libroDeRecetas.recetasCompletas()) {
            boolean fin = false;
            String nom;
            nom = Utilidades.leerCadena(scanner, "Nombre de la receta: ");
            Receta receta = new Receta(nom, maxIngredientes, maxInstrucciones);
            String ingrediente;
            ingrediente = Utilidades.leerCadena(scanner, "Introduce los ingredientes (una línea por ingrediente, escribe 'fin' para terminar): ");
            while (!ingrediente.equalsIgnoreCase("fin") && !fin) {
                receta.agregarIngrediente(ingrediente);
                if (!receta.ingredientesCompletos()) {
                    ingrediente = Utilidades.leerCadena(scanner, "Introduce los ingredientes (una línea por ingrediente, escribe 'fin' para terminar): ");
                } else {
                    System.out.println("Ha añadido el número máximo de ingredientes posible.");
                    fin = true;
                }
            }
            fin = false;
            String instruccion;
            instruccion = Utilidades.leerCadena(scanner, "Introduce las instrucciones (una línea por instrucción, escribe 'fin' para terminar): ");
            while (!instruccion.equalsIgnoreCase("fin") && !fin) {
                receta.agregarInstruccion(instruccion);
                if (!receta.instruccionesCompletas()) {
                    instruccion = Utilidades.leerCadena(scanner, "Introduce las instrucciones (una línea por instrucción, escribe 'fin' para terminar): ");
                } else {
                    System.out.println("Ha añadido el número máximo de instrucciones posible.");
                    fin = true;
                }
            }
            libroDeRecetas.agregarReceta(receta);
            System.out.println("¡Receta agregada exitosamente!");
        } else System.out.println("No se pudo añadir la receta.");
    }

    /**
     * Función 2 del menú.
     * Permite consultar una receta y editarla.
     *
     * @param scanner Entrada de datos
     */

    private void consultarReceta(Scanner scanner) {
        Receta busqueda = buscarRecetaPorNombre(scanner);
        if (!busqueda.getNombre().equalsIgnoreCase("ELIMINARRR")) {
            System.out.println(busqueda);
            System.out.println();
            editarReceta(scanner, busqueda);
        }
    }

    /**
     * Busca una receta por una subcadena de su nombre.
     * Metodo auxiliar.
     *
     * @param scanner Entrada de datos
     * @return Receta seleccionada.
     */

    private Receta buscarRecetaPorNombre(Scanner scanner) {
        String textoBusqueda = Utilidades.leerCadena(scanner, "Introduce el texto de la receta a buscar (-FIN- para volver): ");
        Receta respuesta;
        if (textoBusqueda.equalsIgnoreCase("FIN") || textoBusqueda.equalsIgnoreCase("-FIN-")) {
            respuesta = new Receta("ELIMINARRR", maxIngredientes, maxInstrucciones);
        } else {
            Receta[] recetasEncontradas = libroDeRecetas.buscarRecetaPorNombre(textoBusqueda);
            while (recetasEncontradas[0] == null && !textoBusqueda.equalsIgnoreCase("fin") && !textoBusqueda.equalsIgnoreCase("-FIN-")) { //recetasEncontradas esta vacio
                System.out.println("No se han encontrado recetas con ese nombre. Prueba otra vez.");
                textoBusqueda = Utilidades.leerCadena(scanner, "Introduce el texto de la receta a buscar (-FIN- para volver): ");
                recetasEncontradas = libroDeRecetas.buscarRecetaPorNombre(textoBusqueda);
            }
            if (!textoBusqueda.equalsIgnoreCase("fin") && !textoBusqueda.equalsIgnoreCase("-FIN-")) {
                System.out.println("Recetas encontradas:");
                respuesta = seleccionarReceta(scanner, recetasEncontradas);
            } else respuesta = new Receta("ELIMINARRR", maxIngredientes, maxInstrucciones);
        }
        return respuesta;
    }

    /**
     * Permite seleccionar una receta entre varias recetas encontradas.
     * Función auxiliar.
     *
     * @param scanner Entrada de datos
     * @param recetas Array de recetas que coinciden con la búsqueda hecha
     * @return Receta seleccionada.
     */

    private Receta seleccionarReceta(Scanner scanner, Receta[] recetas) {
        boolean fin = false;
        int totalRecetas = -1;
        for (int i = 0; i < recetas.length && !fin; i++) {
            if (recetas[i] != null) {
                System.out.println((i + 1) + ". " + recetas[i].getNombre());
            }
            if (i == recetas.length - 1) { // Para que cuando todas las recetas del libro coincidan con la búsqueda, no de IndexOutOfBounds
                totalRecetas = recetas.length;
            } else {
                if (recetas[i + 1] == null) {
                    totalRecetas = i + 1;
                    fin = true;
                }
            }
        }
        int numReceta = Utilidades.leerNumero(scanner, "Elige una receta: ", 1, totalRecetas) - 1;
        return recetas[numReceta];
    }

    /**
     * Permite editar una receta y/o eliminarla.
     * Función auxiliar.
     *
     * @param scanner      Entrada de datos
     * @param seleccionada Receta seleccionada
     */

    private void editarReceta(Scanner scanner, Receta seleccionada) {
        String menu = "\n";
        menu += "1. Añadir ingrediente\n";
        menu += "2. Añadir instrucción\n";
        menu += "3. Eliminar receta\n";
        menu += "4. Volver\n";
        System.out.println(menu);
        int opcion2;
        opcion2 = Utilidades.leerNumero(scanner, "Elige una opción: ", 1, 4);
        boolean fin = false;
        while (opcion2 != 4 && !fin) {
            do {
                switch (opcion2) {
                    case 1: {
                        if (!seleccionada.ingredientesCompletos()) {
                            String s = Utilidades.leerCadena(scanner, "Introduce el ingrediente a añadir: ");
                            seleccionada.agregarIngrediente(s);
                        } else {
                            System.out.println("No se pueden añadir más ingredientes.");
                        }
                        break;
                    }
                    case 2: {
                        if (!seleccionada.instruccionesCompletas()) {
                            String s = Utilidades.leerCadena(scanner, "Introduce la instrucción a añadir: ");
                            seleccionada.agregarInstruccion(s);
                        } else {
                            System.out.println("No se pueden añadir más instrucciones.");
                        }
                        break;
                    }
                    case 3: {
                        libroDeRecetas.eliminarReceta(seleccionada);
                        System.out.println("Receta eliminada.");
                        fin = true;
                        break;
                    }
                    case 4: {
                        fin = true;
                        break;
                    }
                }
                if (!fin) {
                    System.out.println(menu);
                    opcion2 = Utilidades.leerNumero(scanner, "Elige una opción: ", 1, 4);
                }
            } while (opcion2 != 3 && !fin);
        }
    }

    /**
     * Función 3 del menú.
     * Permite visualizar y modificar el plan semanal.
     *
     * @param scanner Entrada de datos
     */

    private void planificarComidas(Scanner scanner) {
        System.out.println("Planificación de comidas para la semana:");
        System.out.print(planificador.toString());
        System.out.print("\n\n");
        int dia;
        dia = Utilidades.leerDiaDeLaSemana(scanner, "Introduce el día de la semana (L, M, X, J, V, S, D): ");
        String diaTexto = Utilidades.posicionADiaSemana(dia);
        Receta busqueda;
        busqueda = buscarRecetaPorNombre(scanner);
        if (!busqueda.getNombre().equalsIgnoreCase("ELIMINARRR")) {
            planificador.agregarComida(dia, busqueda);
            System.out.println("Receta planificada para " + diaTexto);
        }
    }

    /**
     * Función 4 del menú.
     * Permite guardar las recetas del libro en un archivo de texto.
     *
     * @param scanner Entrada de datos
     */

    private void guardarRecetas(Scanner scanner) {
        String nomArchivo = Utilidades.leerCadena(scanner, "Introduce el nombre del archivo donde guardar las recetas: ");
        if (!nomArchivo.endsWith(".txt")) nomArchivo += ".txt";
        try {
            libroDeRecetas.guardarRecetasEnArchivo(nomArchivo);
            System.out.println("Recetas guardadas en " + nomArchivo);
        } catch (IOException ex) {
            System.out.println("Error al guardar archivo.");
        }
    }

    /**
     * Función 5 del menú.
     * Permite cargar las recetas del libro desde un archivo de texto.
     *
     * @param scanner Entrada de datos
     */

    private void cargarRecetas(Scanner scanner) {
        String nomArchivo = Utilidades.leerCadena(scanner, "Introduce la ruta del archivo de donde cargar las recetas: ");
        try {
            libroDeRecetas.cargarRecetasDeArchivo(nomArchivo, maxIngredientes, maxInstrucciones);
            System.out.println("Recetas cargadas desde " + nomArchivo);
        } catch (IOException ex) {
            System.out.println("Error al cargar archivo.");
        }
    }

    /**
     * Función 6 del menú.
     * Permite guardar el plan semanal en un archivo de texto.
     *
     * @param scanner Entrada de datos
     */

    private void guardarPlanSemanal(Scanner scanner) {
        String nomArchivo = Utilidades.leerCadena(scanner, "Introduce el nombre del archivo donde guardar el plan semanal: ");
        if (!nomArchivo.endsWith(".txt")) nomArchivo += ".txt";
        try {
            planificador.guardarPlanEnArchivo(nomArchivo);
            System.out.println("Plan semanal guardado en " + nomArchivo);
        } catch (IOException ex) {
            System.out.println("Error al guardar archivo.");
        }
    }

}