import java.io.File;

public class Calculadora {
    public static void main(String[] args) {

        System.out.println("Calculadora");
        File image = new File("src/main/resources/img/op.png");
        ReconText reconText = new  ReconText(image);
        System.out.println(reconText.getText());
    }
}