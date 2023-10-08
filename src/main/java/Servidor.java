import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor implements Runnable{

    protected ServerSocket serverSocket;
    protected Socket sSocket;
    protected Socket enviarRespuesta;
    protected ObjectInputStream entrada;
    protected ObjectOutputStream envio;
    protected Solicitud solicitud;


    public static void main(String[] args) {
        ExpresionConstante expresion = new Exponenciacion(new Constante(4.0), new Constante(3.0));
        ExpresionBooleana expresion2 = new Xor(new Booleano(true), new Booleano(true));
        ExpresionBooleana expresion3 = new Not(new Booleano(false));

        double resultado = expresion.evaluar();
        boolean resultado2 = expresion2.evaluar();
        boolean resultado3 = expresion3.evaluar();


        System.out.println("Resultado: " + resultado);
        System.out.println("Resultado: " + resultado2);
        System.out.println("Resultado: " + resultado3);

        new Servidor();
    }

    Servidor(){
        try {
            serverSocket=new ServerSocket(9999);
            sSocket=serverSocket.accept();
            entrada=new ObjectInputStream(sSocket.getInputStream());


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Thread hilo=new Thread(this);
        hilo.start();
    }
    @Override
    public void run() {
        while (true){
            try {
                Respuesta respuesta = null;
                solicitud = (Solicitud) entrada.readObject();
                if (solicitud.getImg()==null){
                    //TODO: Evaluar expresion escrita
                    System.out.println(solicitud.getIp() + " solicita: "+ solicitud.getOp());
                    respuesta=new Respuesta(solicitud.getOp(),"Respuesta de "+ solicitud.getOp());
                    System.out.println("Respuesta enviada a: "+ solicitud.getIp());

                }else if (solicitud.getImg()!=null){
                    //TODO: Leer y evaluar expresion en imagen
                    System.out.println("Imagen recibida de: "+ solicitud.getIp());
                    solicitud.setOp(new ReconText(solicitud.getImg()).getText());
                    respuesta = new Respuesta(solicitud.getOp(),"Respuesta de "+ solicitud.getOp());
                    System.out.println(respuesta.getResp());

                }
                enviarRespuesta=new Socket(solicitud.getIp(),9090);
                envio=new ObjectOutputStream(enviarRespuesta.getOutputStream());
                if (respuesta!=null){
                    envio.writeObject(respuesta);
                }


            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }
}

