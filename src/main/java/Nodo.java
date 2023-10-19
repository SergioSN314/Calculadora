import java.io.Serializable;

/**
 * Clase que para crear nodos para <code>Lista</code>
 * @author Alejandro Solís Bolaños
 * @see Lista
 */
public class Nodo implements Serializable {
    private Respuesta data;
    protected Nodo next;
    protected Nodo prev;

    public Nodo(Respuesta data){
        this.data=data;
        this.next=null;
        this.prev=null;
    }

    public Respuesta getData() {
        return this.data;
    }

    public void setData(Respuesta data) {
        this.data = data;
    }

    public Nodo getNext() {
        return this.next;
    }

    public void setNext(Nodo next) {
        this.next = next;
    }

    public Nodo getPrev() {
        return this.prev;
    }

    public void setPrev(Nodo prev) {
        this.prev = prev;
    }
}
