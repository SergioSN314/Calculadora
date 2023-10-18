import java.io.Serializable;

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
