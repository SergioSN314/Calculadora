/**
 * Clase para los nodos de la lista enlazada
 * @author Sergio Salazar Núñez
 */

public class NodoPila {
    NodoArbol dato;
    NodoPila siguiente;

    /**
     * Constructor de Nodo Pila, asigna dato al nodo y inicializa apuntando a null el nodo siguiente de la Pila
     * @param x Dato del Nodo de la lista enlazada
     */
    public NodoPila(NodoArbol x){
        dato=x;
        siguiente= null;
    }
}
