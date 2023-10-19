import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Clase que crea un servidor para el servicio de calculadora, se comunica con clientes, recibe solicitudes, evalua expresiones matemáticas y devuelve respuestas a los clientes
 * @author Alejandro Solís Bolaños
 */
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

    /**
     * Constructor de <code>Servidor</code>
     *
     */
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

    /**
     * Mantiene al servidor consantemente a la escucha de solicitudes de clientes, procesa la información solicitada, actualiza el historial y envía una respuesta al cliente correspondiente
     *
     * @see Solicitud
     * @see Respuesta
     * @see ArbolBinarioExpresiones
     * @see Servidor#modificarExp(String)
     * @see ReconText#getText()
     */
    @Override
    public void run() {
        while (true){
            try {
                Respuesta respuesta;
                solicitud = (Solicitud) entrada.readObject();
                String op;
                String expresionModificada = "";
                if (solicitud.getImg() == null) {
                    System.out.println(solicitud.getIp() + " solicita: " + solicitud.getOp());
                    op = solicitud.getOp();
                    expresionModificada = modificarExp(op);
                    System.out.println(expresionModificada);
                } else if (solicitud.getImg() != null) {
                    System.out.println("Imagen recibida de: " + solicitud.getIp());
                    solicitud.setOp(new ReconText(solicitud.getImg()).getText());
                    op = solicitud.getOp();
                    expresionModificada = modificarExp(op);
                }
                BufferedReader reader = null;
                try {
                    ArbolBinarioExpresiones abe = new ArbolBinarioExpresiones(expresionModificada);
                    respuesta = new Respuesta(expresionModificada, String.valueOf(abe.evaluaExpresion()));
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - H:m:s ");
                    Date fecha = new Date();
                    writer = new FileWriter("src\\main\\resources\\history.csv", true);
                    writer.write(String.format("%s,%s,%s,%s\n", solicitud.getIp(), solicitud.getOp(), respuesta.getResp(), sdf.format(fecha)));
                    writer.close();
                    String line;
                    Lista history = new Lista();
                    reader = new BufferedReader(new FileReader("src\\main\\resources\\history.csv"));
                    while ((line = reader.readLine()) != null) {
                        String[] row = line.split(",");
                        if (Objects.equals(row[0], solicitud.getIp())) {
                            Respuesta resp = new Respuesta(row[1], row[2], row[3]);
                            history.insertFirst(resp);
                        }
                    }

                    respuesta.setHistory(history);
                } catch (Exception e) {
                    e.printStackTrace();
                    respuesta = new Respuesta(expresionModificada, "ERROR");
                } finally {
                    if (reader != null) {
                        reader.close();
                    }
                }
                enviarRespuesta = new Socket(solicitud.getIp(), 9090);
                envio = new ObjectOutputStream(enviarRespuesta.getOutputStream());
                envio.writeObject(respuesta);
                System.out.println("Respuesta enviada a: " + solicitud.getIp());
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * Recibe un <code>String</code> y lo modifica para minimizar errores al evaluar expresiones matemáticas
     * @author Sergio Salazar Nuñez
     * @param texto <code>String</code> en la que se desean sustituir carácteres
     * @return <code>String</code> modificada
     */
    public static String modificarExp(String texto) {
        return texto.replace("^","x").replace("**","^").replace(" ","");
    }

}

