import jdk.jfr.FlightRecorder;

import java.io.File;
import java.io.Serializable;

public class Solicitud implements Serializable {
    private String ip,op;
    private File img;

    Solicitud(String ip, String op){
        this.ip=ip;
        this.op=op;
        this.img=null;
    }
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
