import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.core.Core;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Camara extends JFrame implements ActionListener {

    JLabel camaraScreen;
    JButton captura;


    static {System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}

    boolean clicked =false;

    VideoCapture video;
    Mat image;

    public Camara(){
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Camara");
        this.setLayout(null);
        this.setSize(500, 500);
        this.setVisible(true);
        System.out.println(Core.VERSION);

        camaraScreen=new JLabel();
        camaraScreen.setBounds(0,0,500,400);
        this.add(camaraScreen);

        captura= new JButton("Capturar");
        captura.setBounds(200,400,100,50);
        captura.addActionListener(this);
        this.add(captura);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                video.release();
                image.release();
            }
        });

    }
    public void startCam(){
        video=new VideoCapture(0);
        image=new Mat();
        byte[] imageData;
        ImageIcon icon;
        while (true){
            video.read(image);
            final MatOfByte buf = new MatOfByte();
            Imgcodecs.imencode(".jpg",image,buf);
            imageData= buf.toArray();
            icon=new ImageIcon(imageData);
            camaraScreen.setIcon(icon);

            if (clicked){
                //TODO: toma la foto
                clicked=false;
            }

        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(captura)){
            clicked=true;
        }

    }
}
