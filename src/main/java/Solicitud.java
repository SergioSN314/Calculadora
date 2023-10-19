import java.io.File;
import java.io.Serializable;
/**
 * Clase para crear pauqetes de información que envia la <code>Calculadora</code> al <code>Servidor</code>
 */
public class Solicitud implements Serializable {
    private String ip,op;
    private File img;

    /**
     * Constructor de <code>Solicitud</code> para entradas de texto
     * @param ip dirección ip del ciente que envía la solicitud
     * @param op operación a enviar al para su evaluación
     */
    Solicitud(String ip, String op){
        this.ip=ip;
        this.op=op;
        this.img=null;
    }

    /**
     * Constructo de <code>Solicitud</code> para entradas de imagen
     * @param ip dirección IP del cliente que envía la solicitud
     * @param img imagen a enviar al servidor para su evaluación
     */
    Solicitud(String ip, File img){
        this.ip=ip;
        this.img=img;
        this.op=null;
    }
    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOp() {
        return this.op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public File getImg() {
        return this.img;
    }

    public void setImg(File img) {
        this.img = img;
    }
}
