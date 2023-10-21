/**
 * Clase que crea arboles de expresiones
 * @author Sergio Salazar Núñez
 */

public class PilaArbolExp {

    private NodoPila tope;

    /**
     * Metodo constructor que inicializa el tope apuntando a null
     */
    public PilaArbolExp(){
        tope=null;
    }

    /**
     * Metodo para insertar un elemento en la pila
     * @param elemento variable tipo nodoarbol done se almacena el nodo que querramos insertar
     */
    public void insertar(NodoArbol elemento){
        NodoPila nuevo;
        nuevo= new NodoPila(elemento);
        nuevo.siguiente=tope;
        tope=nuevo;
        //Ahora el tope es el nuevo elemento ingresado
    }

    /**
     * Metodo para averiguar si la pila esta vacia
     * @return true en caso de que este vacia, false en caso contrario
     */
    public boolean pilaVacia(){
        return tope==null;
    }

    /**
     * Metodo para ver el dato del tope de la pila
     * @return dato del tope
     */
    public NodoArbol topePila(){
        return tope.dato;
    }

    /**
     * Metodo para quitar el nodo del tope de la pila
     * @return Nodo de la pila
     */
    public NodoArbol quitar(){
        NodoArbol aux=null;
        if (!pilaVacia()){
            aux=tope.dato;
            tope=tope.siguiente;
        }
        return aux;
    }
}
