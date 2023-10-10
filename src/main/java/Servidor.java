import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor extends Observable implements Runnable {
    private int puerto;

    public Servidor(int puerto){
        this.puerto=puerto;
    }

    public static void main(String[] args) {
        String cadena="90+8";
        boolean cadena2=true^true;
        String b="true^true";



        String expresionOriginal = "4/3-2";
        String expresionModificada = reemplazarPalabras(expresionOriginal);
        String modiboolean= reemplazarPalabras(b);
        System.out.println("Debe de dar :"+cadena2);
        System.out.println(b);
        System.out.println(modiboolean);

        //ArbolBinarioExpresiones ABE =new ArbolBinarioExpresiones(expresionModificada);
        ArbolBinarioExpresiones ABE2 = new ArbolBinarioExpresiones(modiboolean);


        System.out.println("Me esta dando : "+ABE2.EvaluaExpresionBoolean());
        //System.out.println(cadena2);
        //System.out.println(ABE.toString(2));
        //System.out.println(ABE.EvaluaExpresion());
        //System.out.println(cadena.charAt(2));

    }
    public static String reemplazarPalabras(String texto) {
        // Reemplazar "true" por "T"
        String textoModificado = texto.replace("true", "T").replace("false","F").replace("^","x").replace("**","^");

        // Agregar más reemplazos según sea necesario
        // Ejemplo: textoModificado = textoModificado.replace("otraPalabra", "otraReemplazo");

        return textoModificado;
    }
    @Override
    public void run() {

        ServerSocket servidor = null;
        Socket sc = null;
        DataInputStream in;
        DataOutputStream out;



        try {
            //Creamos el socket del servidor
            servidor = new ServerSocket(puerto);
            System.out.println("Servidor iniciado");

            //Siempre estara escuchando peticiones
            while (true) {

                //Espero a que un cliente se conecte
                sc = servidor.accept();

                System.out.println("Cliente conectado");
                in = new DataInputStream(sc.getInputStream());

                //Leo el mensaje que me envia
                String mensaje = in.readUTF();

                System.out.println(mensaje);

                this.setChanged();
                this.notifyObservers(mensaje);
                this.clearChanged();

                //Cierro el socket
                sc.close();
                System.out.println("Cliente desconectado");

            }

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
