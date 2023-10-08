import java.io.Serializable;

public class Respuesta implements Serializable {
     private String op;
     private String resp;

     Respuesta(String op, String resp) {
          this.op = op;
          this.resp = resp;
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
}
