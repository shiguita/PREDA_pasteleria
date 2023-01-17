import java.io.File;

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

        // Objeto donde se recogen los datos del problema
        ObjectData data = new ObjectData();

        // Objeto que contiene los datos de la solucion
        ResultData result = new ResultData();

        switch (args.length) {
            case 0:
                data.setFromInputs();
                result.getResult(data, false, "");
                break;
            case 1:
                // Solo un argumento: "-h"
                if (args[0].equals("-h")) {
                    System.out.println(msg);
                    break;
                }
                // Solo un argumento: "-t"
                if (args[0].equals("-t")) {
                    data.setFromInputs();
                    result.getResult(data, true, "");
                } else {
                    // Solo un argumento: fichero de entrada
                    if (new File(args[0]).exists()) {
                        data.setFromFile(args[0]);
                        result.getResult(data, false, "");
                    } else {
                        // Solo un argumento: fichero de salida
                        data.setFromInputs();
                        result.getResult(data, false, args[0]);
                    }
                }
                break;
            case 2:
                // Dos argumento, el primero "-t"
                if (args[0] == "-t") {
                    // El segundo es el fichero de entrada
                    if (new File(args[1]).exists()) {
                        data.setFromFile(args[1]);
                        result.getResult(data, true, "");
                    } else {
                        // El segundo es el fichero de salida
                        data.setFromInputs();
                        result.getResult(data, true, args[1]);
                    }
                } else { // Dos ficheros
                    if (new File(args[1]).exists()) {
                        System.err.println(
                            "\n ERROR: No se permite como fichero de salida un archivo que " +
                            "ya existe, por favor indique un archivo que no exista en el sistema"
                        );
                        break;
                    }
                    // El primero es el fichero de entrada que existe
                    if (new File(args[0]).exists()) {
                        data.setFromFile(args[0]);
                        result.getResult(data, false, args[1]);
                    } else {
                        // El fichero de entrada no existe
                        System.out.println(
                            "\nEl fichero de entrada indicado no existe en el sistema, " +
                            "se procederá a pedir los datos por consola\n"
                        );
                        data.setFromInputs();
                        result.getResult(data, false, args[1]);
                    }
                }
                break;
            case 3:
                // Tres argumentos: "-t"; ficheros de entrada y salida
                if (new File(args[2]).exists()) {
                    System.err.println(
                        "\n ERROR: No se permite como fichero de salida un archivo que " +
                        "ya existe, por favor indique un archivo que no exista en el sistema");
                    break;
                }
                // El fichero de entrada existe
                if (new File(args[1]).exists()) {
                    data.setFromFile(args[0]);
                    result.getResult(data, true, args[2]);
                } else {
                    // El fichero de entrada no existe
                    System.out.println(
                        "\nEl fichero de entrada indicado no existe en el sistema, " +
                        "se procederá a pedir los datos por consola\n");
                    data.setFromInputs();
                    result.getResult(data, true, args[2]);
                }
                break;
            default:
                System.err.println(
                        "Utilice -h para ver como ejecutar el programa");
                break;
        }
    }
}