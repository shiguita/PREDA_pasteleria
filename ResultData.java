import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;

public class ResultData {
    int [] solucion;
    int costeT;

    public int getCosteT() {
        return costeT;
    }

    public int[] getSolucion() {
        return solucion;
    }

    public void getResult(ObjectData data, boolean traza, String str) {

        // Tabla con los costes de hacer cada tipo por cada pastelero
        int [][] costes = data.getTable();

        // Lista de pedidos
        int [] pedidos = data.getPedidos();

        // Algoritmo de ramificacion y poda que busca la solucion optima
        BranchAndBound(costes, pedidos, traza);

        // En funcion del caso seleccionado por consola imprimimos
        // por pantalla el resultado o se graba en un archivo
        if (str == "") {
            printResult();
        } else {
            resultToFile(str);
        }
    }

    private void BranchAndBound(int[][] costes, int[] pedidos, boolean traza) {
        // Para inciar el proceso empezamos con el monticulo vacio
        PriorityQueue<Nodo> heap = new PriorityQueue<Nodo>(new Comparator<Nodo>() {
            public int compare(Nodo i, Nodo j) {
                return i.costeTotal - j.costeTotal;
            }
        });

        // Inicialzamos dos nodos, "n" y "hijo"

        // "n" representa el nodo que se evalua
        Nodo n = new Nodo(
            new int[costes.length], new boolean[costes.length], 0, 0, 0
        );

        // "hijo" representa los nodos descendientes del nodo "n"
        Nodo hijo = new Nodo(
            new int[costes.length], new boolean[costes.length], 0, 0, 0
        );

        // Se inicializa el parametro "solucion"
        solucion = new int[costes.length];
        
        // Necesario para que no se salte el primer pedido
        n.k = -1;
        // Se inicializa la estimacion optimista
        n.upperBound = UpperBound(costes, pedidos, n.costeTotal, n.k);
        // Se inicializa la estimacion pesimista
        int cota = LowerBound(costes, pedidos, n.costeTotal, n.k);


        // Empezamos la busqueda de la solucion
        heap.add(n);
        // Mientras que el monticulo no este vacio y cumpla con la cota:
        // Se puede alcanzar una mejor resultado por el camino actual
        // que el de la estimacion pesimista
        while (!heap.isEmpty() && heap.peek().upperBound <= cota) {

            // Como guardamos los nodos en un monticulo de minimos
            // el nodo de la cima tiene el menor coste, lo escogemos
            // para buscar la solucion
            n = heap.poll();

            // Generamos los hijos
            hijo.k = n.k + 1;
            hijo.asignados = n.asignados;
            hijo.pasteleros = n.pasteleros;

            // En este bucle vamos asignando pasteleros que esten libres
            // al pedido actual para encontrar el mas adecuado para ese pedido
            for (int i = 0; i < costes.length; i++) {
                if(!hijo.asignados[i]){
                    hijo.pasteleros[hijo.k] = i+1;
                    hijo.asignados[i] = true;
                    hijo.costeTotal = n.costeTotal + costes[i][pedidos[hijo.k]-1];

                    // Si el nodo hijo alcanza el ultimo pedido tenemos una
                    // solucion, comprobamos si el coste total de esta es
                    // mejor que la cota actual, en cuyo caso actualizamos
                    // el valor de la cota para no tener en cuenta soluciones
                    // con peor coste que la cota
                    if (hijo.k == costes.length-1) {
                        if (cota >= hijo.costeTotal) {
                            solucion = hijo.pasteleros;
                            costeT = hijo.costeTotal;
                            cota = costeT;
                        }
                    } else {
                        // El nodo "hijo" no ha llegado al ultimo pedido,
                        // no tenemos una solucion completa, calculamos su
                        // estimacion optimista y lo añadimos al monticulo
                        hijo.upperBound = UpperBound(
                            costes, pedidos, n.costeTotal, n.k
                        );
                        heap.add(
                            new Nodo(
                                hijo.pasteleros.clone(), hijo.asignados.clone(),
                                hijo.k, hijo.costeTotal, hijo.upperBound
                            )
                        );

                        // Calculamos la estimacion pesimista del hijo
                        // en caso de que obtengamos mejor valor que
                        // la cota, actualizando su valor y asi no valorar
                        // soluciones peores
                        int estimacion = LowerBound(
                            costes, pedidos, hijo.costeTotal, hijo.k
                        );
                        if (cota > estimacion) cota = estimacion;
                    }                
                    // Desasignamos el pastelero "i" para probar con
                    // otro para el mismo pedido por si acaso puede
                    // obtener una mejor solucion.
                    hijo.asignados[i] = false;
                }
            }
        }
    }

    // Algoritmo de calculo de limite superior o estimacion optimista
    private int UpperBound(int[][] costes, int[] pedidos, int costeTotal, int k) {
        int estimacion = costeTotal;
        int menorCoste = 0;
        for (int i = k + 1; i < costes.length; i++) {
            menorCoste = costes[0][pedidos[i] - 1];
            for (int j = 1; j < costes.length; j++) {
                if (menorCoste > costes[j][pedidos[i] - 1]) {
                    menorCoste = costes[j][pedidos[i] - 1];
                }
            }
            estimacion += menorCoste;
        }
        return estimacion;
    }

    // Algoritmo de calculo de limite inferior o estimacion pesimista
    private int LowerBound(int[][] costes, int[] pedidos, int costeTotal, int k) {
        int estimacion = costeTotal;
        int mayorCoste = 0;
        for (int i = k + 1; i < costes.length; i++) {
            mayorCoste = costes[0][pedidos[i] - 1];
            for (int j = 1; j < costes.length; j++) {
                if (mayorCoste < costes[j][pedidos[i] - 1]) {
                    mayorCoste = costes[j][pedidos[i] - 1];
                }
            }
            estimacion += mayorCoste;
        }
        return estimacion;
    }

    private void printResult() {
        String res = "";
        for (int i : solucion) {
            res += i + "-";
        }
        System.out.println("\nSolucion:");
        System.out.println(res.substring(0, res.length()-1));
        System.out.println(costeT);
    }

    private void resultToFile(String file) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            String res = "";
            for (int i : solucion) {
                res += i + "-";
            }
            writer.write(res.substring(0,res.length()-1));
            writer.write(String.valueOf(costeT));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
