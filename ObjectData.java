import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ObjectData {

    private int [][] table;
    private int [] pedidos;

    public void setFromFile(String name) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(name));

            // La primera linea del archivo tiene el numero de pasteleros
            // para esta ejecucion
            int pasteleros = Integer.parseInt(reader.readLine());

            // La segunda linea tiene el numero de tipos de pasteles
            int tipos = Integer.parseInt(reader.readLine());

            // La tercera linea tiene los pedidos separados por guion
            String [] strPedidos = reader.readLine().split("-");

            // Inicializamos los parametros referentes a la tabla de
            // costes de realizar cada tipo de pastel por cada pastelero
            table = new int [pasteleros][tipos];
            pedidos = new int [pasteleros];
            String[] line;
            for (int i = 0; i < pasteleros; i++) {
                pedidos[i] = Integer.parseInt(strPedidos[i]);

                // Cada linea corresponde a los costes de cada tipo
                // para un pastelero determinado
                line = reader.readLine().split("\\s");
                for (int j = 0; j < tipos; j++) {
                    table[i][j] = Integer.parseInt(line[j]);
                }
            }
            reader.close();
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    public void setFromInputs() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduczca el numero de pasteleros: ");
        int pasteleros = sc.nextInt();
        System.out.print("Introduczca el numero de tipos de pasteles: ");
        int tipos = sc.nextInt();
        System.out.println(
            "Introduczca todos los pedidos separados por \"-\": "
        );
        String [] strPedidos = sc.next().split("-");
        System.out.println(
            "Introduzca los costes de realizar cada tipo" +
            " de pastel por cada pastelero"
        );
        pedidos = new int[pasteleros];
        table = new int[pasteleros][tipos];
        for (int i = 0; i < pasteleros; i++) {
            pedidos[i] = Integer.parseInt(strPedidos[i]);
            System.out.println("\nPastelero numero " + (i+1) + ": ");
            for (int j = 0; j < tipos; j++) {
                System.out.print("coste de hacer el tipo " + (j+1) + ":");
                table[i][j] = sc.nextInt();
            }
        }
        sc.close();
    }

    public int[] getPedidos() {
        return pedidos;
    }

    public int[][] getTable() {
        return table;
    }
}
