import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor  {


    public static void main(String[] args) {
        String expresionOriginal = "7+4*(10+3)-800";

        String expresionModificada = ModificarExp(expresionOriginal);

        ArbolBinarioExpresiones ABE =new ArbolBinarioExpresiones(expresionModificada);

        System.out.println("Expresion :"+expresionOriginal);
        System.out.println("Formato Posfijo :"+ABE.Formatoposfijo());
        System.out.println("Resultado : "+ABE.EvaluaExpresion());

    }

    public static String ModificarExp(String texto) {

        return texto.replace("true", "T").replace("false","F").replace("^","x").replace("**","^").replace(" ","");
    }

}
