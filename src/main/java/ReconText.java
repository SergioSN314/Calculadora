import java.io.File;
import net.sourceforge.tess4j.*;

/**
 * Clase usada para reconocer el texto de una imágen usadno la librería Tesseract
 */
public class ReconText {
    String text;

    /**
     * Constructor del reconocimiento de texto, extrae el texto de la imagen y lo almacena en la variable <code>text</code>
     * @param image variable tipo <code>java.io.File</code> que contiene una imagen de la cual se desea extraer el texto
     */
    ReconText(File image){
        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath("src/main/resources/tessdata/");
        try {
            text = tesseract.doOCR(image);
        } catch (TesseractException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Retorna el texto extraído dela imagen
     * @return el valor del <code>String</code> <code>text</code>
     */
    public String getText() {
        return text;
    }
}
