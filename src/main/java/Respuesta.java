import java.io.Serializable;

public class Respuesta implements Serializable {
     private String op;
     private String resp;
     private Lista history;
     private String fecha;

     Respuesta(String op, String resp,Lista history) {
          this.op = op;
          this.resp = resp;
          this.history=history;
          this.fecha=null;
     }
     Respuesta(String op, String resp) {
          this.op = op;
          this.resp = resp;
          this.history=null;
          this.fecha=null;
     }

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
         return String.format("%s = %s (%s)",this.getOp(),this.getResp(),this.getFecha());
     }
}

