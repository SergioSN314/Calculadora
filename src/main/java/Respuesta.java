import java.io.Serializable;

/**
 * Clase para crea el paquete de datos que el <code>Servidor</code> envía a la <code>Calculadora</code>
 */
public class Respuesta implements Serializable {
     private String op;
     private String resp;
     private Lista history;
     private String fecha;

     /**
      * Constructor de <code>Respuesta</code>
      * @param op operación evaluada por el servidor
      * @param resp respuesa a la operación
      */
     Respuesta(String op, String resp) {
          this.op = op;
          this.resp = resp;
          this.history=null;
          this.fecha=null;
     }

     /**
      * Constructor de <code>Respuesta</code>, usada para crear entradas en el historial que se añaden a la <code>Lista</code> <code>history</code>
      * @param op operación evaluada por el servidor
      * @param resp respuesa a la operación
      * @param fecha <code>String</code> que representa la fehca y hora a la que se hizo la solicitud que genera esta respuesta
      */
     public Respuesta(String op, String resp, String fecha) {
          this.op = op;
          this.resp = resp;
          this.history=null;
          this.fecha=fecha;
     }
     public String getOp() {
          return this.op;
     }
     public void setOp(String op) {
          this.op = op;
     }
     public String getResp() {
          return this.resp;
     }
     public void setResp(String resp) {
          this.resp = resp;
     }
     public Lista getHistory() {
          return this.history;
     }
     public void setHistory(Lista history) {
          this.history = history;
     }
     public String getFecha() {
          return this.fecha;
     }
     public void setFecha(String fecha) {
          this.fecha = fecha;
     }
     public String toString(){
         return String.format("%s \t = \t %s \t (%s)",this.getOp(),this.getResp(),this.getFecha());
     }
}

