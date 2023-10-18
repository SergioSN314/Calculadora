import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Servidor implements Runnable{
    protected ServerSocket serverSocket;
    protected Socket sSocket;
    protected Socket enviarRespuesta;
    protected ObjectInputStream entrada;
    protected ObjectOutputStream envio;
    protected Solicitud solicitud;
    protected FileWriter writer;
    public static void main(String[] args) {
        new Servidor();
    }
    Servidor(){
        try {
            serverSocket = new ServerSocket(9999);
            sSocket = serverSocket.accept();
            entrada = new ObjectInputStream(sSocket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Thread hilo = new Thread(this);
        hilo.start();
    }
    @Override
    public void run() {
        while (true){
            try {
                Respuesta respuesta;
                solicitud = (Solicitud) entrada.readObject();
                System.out.println(solicitud.getImg());
                String op;
                String expresionModificada="";
                if (solicitud.getImg()==null){
//                    System.out.println(solicitud.getIp() + " solicita: "+ solicitud.getOp());
                    op= solicitud.getOp();
                    expresionModificada= modificarExp(op);
                    System.out.println(expresionModificada);
                }else if (solicitud.getImg()!=null){
//                    System.out.println("Imagen recibida de: "+ solicitud.getIp());
                    solicitud.setOp(new ReconText(solicitud.getImg()).getText());
                    op= solicitud.getOp();
                    expresionModificada= modificarExp(op);
                }
                try {
                    ArbolBinarioExpresiones abe =new ArbolBinarioExpresiones(expresionModificada);
                    respuesta=new Respuesta(expresionModificada,String.valueOf(abe.evaluaExpresion()));
                    SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy - H:m:s ");
                    Date fecha=new Date();
                    writer=new FileWriter("src\\main\\resources\\history.csv",true);
                    writer.write(String.format("%s,%s,%s,%s\n",solicitud.getIp(),solicitud.getOp(),respuesta.getResp(),sdf.format(fecha)));
                    writer.close();
                    BufferedReader reader = null;
                    String line;
                    Lista history= new Lista();
                    try {
                        reader=new BufferedReader(new FileReader("src\\main\\resources\\history.csv"));
                        while ((line=reader.readLine())!=null){
                            String[] row= line.split(",");
                            if (Objects.equals(row[0], solicitud.getIp())) {
                                Respuesta resp = new Respuesta(row[1],row[2],row[3]);
                                history.insertLast(resp);
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        if (reader != null) {
                            reader.close();
                        }
                    }
                    respuesta.setHistory(history);
                }catch (Exception e){
                    e.printStackTrace();
                    respuesta=new Respuesta(expresionModificada,"ERROR");
                }
                enviarRespuesta=new Socket(solicitud.getIp(),9090);
                envio=new ObjectOutputStream(enviarRespuesta.getOutputStream());
                envio.writeObject(respuesta);
                System.out.println("Respuesta enviada a: "+ solicitud.getIp());
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public static String modificarExp(String texto) {
        return texto.replace("true", "T").replace("false","F").replace("^","x").replace("**","^").replace(" ","");
    }

}

