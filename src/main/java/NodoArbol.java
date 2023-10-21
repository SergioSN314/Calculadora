/**
 * Clase de nodos para el arbol de expresiones
 * @author Sergio Salazar Núñez
 */

public class NodoArbol {
    //Nodo de tipo pila
    Object dato;
    NodoArbol izquierdo;
    NodoArbol derecho;

    /**
     * Constructor de Nodo Arbol, asigna dato al nodo y inicializa apuntando a null el nodo izquierdo y derecho
     * @param x Dato del nodo del arbol
     */
    public NodoArbol(Object x){
        dato=x;
        izquierdo= null;
        derecho=null;

    }
}
