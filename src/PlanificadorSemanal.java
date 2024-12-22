import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * Permite planificar recetas para cada dia de la semana, así como el plan semanal de un archivo.
 */

public class PlanificadorSemanal {

    // Propiedades:

    private final Receta[] diasSemana;
    private final String[] nomDias = new String[]{"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};

    /**
     * Constructor de la clase.
     */

    public PlanificadorSemanal() {
        this.diasSemana = new Receta[7];
    }

    /**
     * Permite planificar una comida para un día de la semana en concreto.
     *
     * @param dia    Día de la semana
     * @param receta Receta a planificar
     */

    public void agregarComida(int dia, Receta receta) {
        diasSemana[dia] = receta;
    }

    /**
     * Representación textual del plan semanal para luego poder mostrarlo por pantalla.
     */

    @Override
    public String toString() {
        StringBuilder respuesta = new StringBuilder();
        int longColum = 1;
        boolean vacio = true;
        boolean fin = false;
        for (int i = 0; i < diasSemana.length && !fin; i++) {
            if (diasSemana[i] != null) {
                vacio = false;
                fin = true;
                break;
            }
        }
        if (vacio) longColum = 11;
        else {
            for (int i = 0; i < diasSemana.length; i++) {
                if (diasSemana[i] != null && diasSemana[i].getNombre().length() > longColum) {
                    longColum = diasSemana[i].getNombre().length() + 2;
                }
            }
        }
        for (int i = 0; i < longColum * 7; i++) {
            respuesta.append("-");
        }
        respuesta.append("\n ");
        for (int i = 0; i < 7; i++) {
            int espacios = longColum - nomDias[i].length();
            respuesta.append(nomDias[i]);
            for (int j = 0; j < espacios; j++) {
                respuesta.append(" ");
            }
        }
        respuesta.deleteCharAt(respuesta.length() - 1);
        respuesta.append("\n");
        for (int i = 0; i < longColum * 7; i++) {
            respuesta.append("-");
        }
        respuesta.append("\n");
        if (!vacio) {
            respuesta.append(" ");
        }
        for (int i = 0; i < diasSemana.length; i++) {
            if (diasSemana[i] == null) {
                for (int j = 0; j < longColum; j++) {
                    respuesta.append(" ");
                }
            } else {
                respuesta.append(diasSemana[i].getNombre());
                for (int j = 0; j < longColum - diasSemana[i].getNombre().length(); j++) {
                    respuesta.append(" ");
                }
            }
        }
        if (!vacio) {
            respuesta.deleteCharAt(respuesta.length() - 1);
        }
        respuesta.append("\n");
        for (int i = 0; i < longColum * 7; i++) {
            respuesta.append("-");
        }
        respuesta.append("\n\n");
        return respuesta.toString();
    }

    /**
     * Guarda el plan semanal en un archivo de texto generando una representación textual simplificada.
     *
     * @param nombreArchivo Nombre del Archivo en donde se guardará el plan
     * @throws IOException Error relacionado con el archivo
     */

    public void guardarPlanEnArchivo(String nombreArchivo) throws IOException {
        File fich = new File(nombreArchivo);
        PrintWriter out = new PrintWriter(fich);
        for (int i = 0; i < nomDias.length; i++) {
            if (diasSemana[i] == null) {
                out.print(nomDias[i] + ": ---\n");
            } else out.print(nomDias[i] + ": " + diasSemana[i].getNombre() + "\n");
        }
        out.close();
    }

}