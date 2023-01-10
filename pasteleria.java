
/**
 * pasteleria
 */
public class pasteleria {
    static String msg = "SINTAXIS: pasteleria [-t][-h] [fichero entrada]" +
            "\n-t                               Traza del algoritmo" +
            "\n-h                               Muestra este mensaje" +
            "\n[fichero de entrada]         nombre del fichero de entrada" +
            "\n[fichero de salida]          nombre del fichero de salida";
    public static void main(String[] args) {
        switch (args.length) {
            case 0:
                // todo por consola
                break;
            case 1:
                // -h o -t o fichero
                break;
            case 2:
                // -t + fichero o entrada + salida
                break;
            case 3:
                // -t + entrada + salida
                break;
            default:
                System.err.println(
                        "Utilice -h para ver como ejecutar el programa");
                break;
        }
    }
}