public class Nodo implements Cloneable{

    int [] pasteleros;
    boolean [] asignados;
    int k;
    int costeTotal;
    int upperBound;

    public Nodo(int [] pasteleros, boolean [] asignados,
                int k, int costeTotal, int upperBound) {
        // Array de "boolean" donde cada posicion hace referencia
        // a un pastelero, cuando un elemento esta a "true"
        // significa que ese pastelero se ha seleccionado
        this.asignados = asignados;

        // Array de enteros donde cada posicion equivale al
        // respectivo pedido, el valor asociado referencia
        // el pastelero asignado a ese pedido
        this.pasteleros = pasteleros;

        // Valor entero que indica que pedido se está
        // evaluando en ese momento en el algoritmo
        this.k = k;

        // Valor entero que guarda el coste total 
        // hasta el momento de hacer los pedidos 
        // segun el pastelero asignado a ese pedido
        this.costeTotal = costeTotal;

        // Estimacion optimista del coste posible
        // al terminar el algoritmo teniendo en cuenta
        // el coste de los pedidos ya asignados mas el
        // mejor coste posible de los que quedan
        this.upperBound = upperBound;
    }
}
