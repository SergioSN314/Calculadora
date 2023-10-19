import java.io.Serializable;

/**
 * Clase para crear a lista doblemente enlazada
 *
 * @see Nodo
 */
public class Lista implements Serializable {
    protected Nodo head;
    protected Nodo last;
    protected int size;

    public Lista(){
        this.head=null;
        this.last=null;
        this.size=0;
    }
    public boolean isEmpty(){return this.head==null;}

    public int getSize(){return this.size;}

    public void insertFirst(Respuesta data){
        Nodo nodo = new Nodo(data);
        if (this.isEmpty()){
            this.head = this.last = nodo;
            nodo.setPrev(null);
        }else{
            this.head.setPrev(nodo);
            nodo.setNext(this.head);
            nodo.setPrev(null);
            this.head=nodo;
        }
        this.size++;
    }
    public void insertLast(Respuesta data){
        Nodo nodo = new Nodo(data);
        if (this.isEmpty()){
            this.head = this.last=nodo;
        }else{
            nodo.setPrev(this.last);
            this.last.setNext(nodo);
            this.last=nodo;
        }
        this.size++;
    }
    public Nodo getHead() {
        return this.head;
    }

    public void setHead(Nodo head) {
        this.head = head;
    }

    public Nodo getLast() {
        return this.last;
    }

    public void setLast(Nodo last) {
        this.last = last;
    }

    public void displayList(){
        Nodo current=this.head;
        while (current!=null){
            System.out.println(current.getData().toString());
            current=current.getNext();
        }
    }
}

