import java.io.File;
import net.sourceforge.tess4j.*;
public class ReconText {
    String text;
    ReconText(File image){
        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath("src/main/resources/tessdata/");
        try {
            text = tesseract.doOCR(image);
        } catch (TesseractException e) {
            System.out.println(e.getMessage());
        }
    }
    public String getText() {
        return text;
    }
}
