import java.io.File;
import net.sourceforge.tess4j.*;
public class ReconText {
    String text;
    public static void main(String[] args) {
        System.out.println(new ReconText(new File("src\\main\\resources\\img\\op.jpg")).getText());
    }
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
