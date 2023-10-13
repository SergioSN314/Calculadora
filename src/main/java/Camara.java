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
import java.io.File;

public class Camara extends JFrame implements ActionListener,Runnable {
    JLabel camaraScreen;
    JButton captura;

    final int WIDTH=650;
    final int HEIGHT=600;
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    boolean clicked = false;
    VideoCapture video;
    Mat image;
    public File foto;
    String serverIp, clientIp;
    int serverPort;
    public Thread camThread;

    public Camara(String serverIp, int serverPort, String clientIp) {
        camThread = new Thread(this);
        camThread.start();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    super.windowClosing(e);
                    video.release();
                    image.release();
                } catch (Exception ignored) {
                }
            }
        });
        this.foto = null;
        this.serverIp = serverIp;
        this.clientIp = clientIp;
        this.serverPort = serverPort;

    }
    @Override
    public void run() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Camara");
        this.setLayout(null);
        this.setSize(WIDTH, HEIGHT);
        this.setVisible(true);
        System.out.println(Core.VERSION);

        camaraScreen = new JLabel();
        camaraScreen.setBounds(0, 0, WIDTH, HEIGHT-100);
        this.add(camaraScreen);

        captura = new JButton("Capturar");
        captura.setBounds((WIDTH/2)-100, HEIGHT-100, 100, 50);
        captura.addActionListener(this);
        this.add(captura);

        video = new VideoCapture(0);
        image = new Mat();
        byte[] imageData;
        ImageIcon icon;
        while (true) {
            video.read(image);
            final MatOfByte buf = new MatOfByte();
            Imgcodecs.imencode(".jpg", image, buf);
            imageData = buf.toArray();
            icon = new ImageIcon(imageData);
            camaraScreen.setIcon(icon);
            if (clicked) {
                Imgcodecs.imwrite("src\\main\\resources\\img\\op.jpg", image);
                clicked = false;
                int ans = JOptionPane.showConfirmDialog(this, "Desea enviar la foto?", "Confirmacion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
                if (ans == 0) {

                    this.foto = new File("src\\main\\resources\\img\\op.jpg");
                    this.dispose();
                    this.camThread.interrupt();


                } else if (ans == 1) {
                    System.out.println("Cancelar");
                }
            }

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(captura)) {
            clicked = true;
        }
    }
}


