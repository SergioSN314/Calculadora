import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Calculadora extends JFrame implements ActionListener,Runnable {
    final int WIDTH = 1200;
    final int HEIGHT= 800;
    private String clientIp;
    public static String serverIp = "192.168.1.72";
    public static int serverPort = 9999;
    protected Socket enviarSolicitud;
    protected ObjectOutputStream envio;
    protected ServerSocket recibirRespuesta;
    protected Socket rRespuesta;
    protected ObjectInputStream entrada;
    Font font= new Font("OCR A Extended",Font.PLAIN,30);
    JPanel panel,historyPanel;
    JButton[] Buttons = new JButton[28];
    JButton b0, b1, b2, b3, b4, b5, b6, b7, b8, b9;
    JButton bPlus, bMinus, bEqual, bMult, bDiv, bMod, bExp, bAnd, bOr, bXOr, bNot, bCam,bOpenPar,bClosePar,bLeft,bRight, bClr, bDel;
    JTextField input;
    JLabel output,historyLablel;
    JList<Respuesta> history;
    DefaultListModel<Respuesta> listModel;
    JScrollPane historyScroll;
    Camara camara;
    public static void main(String[] args) {new Calculadora();}
    Calculadora(){
        InetAddress localHost;
        try {
            localHost = InetAddress.getLocalHost();
            this.clientIp = (localHost.getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
            enviarSolicitud= new Socket(serverIp,serverPort);
            envio = new ObjectOutputStream(enviarSolicitud.getOutputStream());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Calculadora");
        this.setSize(WIDTH, HEIGHT);
        this.setLayout(new BorderLayout());
        this.setResizable(false);

        panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(WIDTH-400, HEIGHT));
        panel.setBackground(Color.black);

        input= new JTextField();
        input.setPreferredSize(new Dimension(WIDTH,80));
        input.setBackground(Color.WHITE);
        input.setFont(font);
        input.setEditable(false);

        output=new JLabel();
        output.setPreferredSize(new Dimension(WIDTH,80));
        output.setHorizontalAlignment(JLabel.RIGHT);
        output.setFont(font);
        output.setBackground(Color.DARK_GRAY);
        output.setForeground(Color.WHITE);

        GridBagConstraints gbc =new GridBagConstraints();

        gbc.weightx=1;
        gbc.weighty=1;
        gbc.fill=GridBagConstraints.BOTH;

        b0 = new JButton("0");
        b1 = new JButton("1");
        b2 = new JButton("2");
        b3 = new JButton("3");
        b4 = new JButton("4");
        b5 = new JButton("5");
        b6 = new JButton("6");
        b7 = new JButton("7");
        b8 = new JButton("8");
        b9 = new JButton("9");
        bEqual = new JButton("=");
        bPlus = new JButton("+");
        bMinus = new JButton("-");
        bMult = new JButton("*");
        bDiv = new JButton("/");
        bMod = new JButton("%");
        bExp = new JButton("**");
        bAnd = new JButton("&");
        bOr = new JButton("|");
        bXOr = new JButton("^");
        bNot = new JButton("~");
        bOpenPar = new JButton("(");
        bClosePar = new JButton(")");
        bLeft = new JButton("<-");
        bRight= new JButton("->");
        bCam = new JButton("Cam");
        bClr = new JButton("Clr");
        bDel = new JButton("Del");

        Buttons[0] = b0;
        Buttons[1] = b1;
        Buttons[2] = b2;
        Buttons[3] = b3;
        Buttons[4] = b4;
        Buttons[5] = b5;
        Buttons[6] = b6;
        Buttons[7] = b7;
        Buttons[8] = b8;
        Buttons[9] = b9;
        Buttons[10] = bEqual;
        Buttons[11] = bPlus;
        Buttons[12] = bMinus;
        Buttons[13] = bMult;
        Buttons[14] = bDiv;
        Buttons[15] = bMod;
        Buttons[16] = bExp;
        Buttons[17] = bAnd;
        Buttons[18] = bOr;
        Buttons[19] = bXOr;
        Buttons[20] = bNot;
        Buttons[21] = bOpenPar;
        Buttons[22] = bClosePar;
        Buttons[23] = bLeft;
        Buttons[24] = bRight;
        Buttons[25] = bCam;
        Buttons[26] = bClr;
        Buttons[27] = bDel;

        for (int i = 0; i < 28; i++) {
            Buttons[i].addActionListener(this);
            Buttons[i].setFont(font);
            Buttons[i].setBackground(Color.darkGray);
            Buttons[i].setForeground(Color.WHITE);
            Buttons[i].setFocusable(false);
            Buttons[i].setPreferredSize(new Dimension(80,80));
        }
        // PANEL SUPERIOR
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth=4;
        gbc.gridheight=1;
        panel.add(input,gbc);
        gbc.gridwidth=1;
        gbc.gridx=4;
        panel.add(bCam,gbc);
        gbc.gridx=0;
        gbc.gridy=1;
        gbc.gridwidth=5;
        panel.add(output,gbc);

        //FILA 1
        gbc.gridx=0;
        gbc.gridy=2;
        gbc.gridwidth=1;
        gbc.gridheight=2;
        panel.add(bClr,gbc);

        gbc.gridheight=1;
        gbc.gridx=1;
        panel.add(bOpenPar,gbc);

        gbc.gridx++;
        panel.add(bClosePar,gbc);

        gbc.gridx++;
        panel.add(bLeft,gbc);
        gbc.gridx++;
        panel.add(bRight,gbc);

        //FILA 2
        gbc.gridy++;
        gbc.gridx=1;
        panel.add(bAnd,gbc);
        gbc.gridx++;
        panel.add(bOr,gbc);
        gbc.gridx++;
        panel.add(bXOr,gbc);
        gbc.gridx++;
        panel.add(bNot,gbc);

        //FILA 3
        gbc.gridy++;
        gbc.gridx=0;
        panel.add(b7,gbc);
        gbc.gridx++;
        panel.add(b8,gbc);
        gbc.gridx++;
        panel.add(b9,gbc);
        gbc.gridx++;
        panel.add(bExp,gbc);
        gbc.gridx++;
        panel.add(bMod,gbc);

        //FILA 4
        gbc.gridy++;
        gbc.gridx=0;
        panel.add(b4,gbc);
        gbc.gridx++;
        panel.add(b5,gbc);
        gbc.gridx++;
        panel.add(b6,gbc);
        gbc.gridx++;
        panel.add(bMult,gbc);
        gbc.gridx++;
        panel.add(bDiv,gbc);

        //FILA 5
        gbc.gridy++;
        gbc.gridx=0;
        panel.add(b1,gbc);
        gbc.gridx++;
        panel.add(b2,gbc);
        gbc.gridx++;
        panel.add(b3,gbc);
        gbc.gridx++;
        panel.add(bPlus,gbc);
        gbc.gridx++;
        panel.add(bMinus,gbc);

        //FILA 6
        gbc.gridy++;
        gbc.gridx=0;
        gbc.gridwidth=2;
        panel.add(b0,gbc);
        gbc.gridx=2;
        panel.add(bEqual,gbc);
        gbc.gridwidth=1;
        gbc.gridx=4;
        panel.add(bDel,gbc);

        historyPanel=new JPanel();
        historyPanel.setLayout(new BorderLayout());
        historyPanel.setPreferredSize(new Dimension(400,HEIGHT));
        historyPanel.setBackground(Color.darkGray);

        historyLablel=new JLabel("Historial");
        historyLablel.setFont(font);
        historyLablel.setForeground(Color.WHITE);
        historyLablel.setHorizontalAlignment(JLabel.CENTER);
        historyLablel.setPreferredSize(new Dimension(400,120));
        historyPanel.add(historyLablel,BorderLayout.NORTH);

        history= new JList<>();
        listModel= new DefaultListModel<>();
        history.setModel(listModel);
        history.setPreferredSize(new Dimension(400,HEIGHT-120));
        history.setBackground(Color.LIGHT_GRAY);
        history.setForeground(Color.DARK_GRAY);
        history.setFont(font);
        historyScroll=new JScrollPane(history);
        historyScroll.setPreferredSize(new Dimension(400,HEIGHT-120));
        historyPanel.add(historyScroll, BorderLayout.SOUTH);

        history.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Respuesta selec= history.getSelectedValue();
                input.setText(selec.getOp());
                output.setText(selec.getResp());
            }
        });

        this.add(panel,BorderLayout.WEST);
        this.add(historyPanel,BorderLayout.EAST);
        this.pack();
        this.setVisible(true);

        Thread hilo = new Thread(this);
        hilo.start();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
         if (e.getSource().equals(bCam)){
             camara= new  Camara(serverIp,serverPort,clientIp);
             Thread espera= new  Thread(new Runnable() {
                 @Override
                 public void run() {
                     while (true){
                         if (camara.camThread.isInterrupted()){
                             Solicitud solicitud=new Solicitud(clientIp,camara.foto);
                             try {
                                 envio.writeObject(solicitud);
                             } catch (IOException ex) {
                                 throw new RuntimeException(ex);
                             }
                             break;
                         }
                     }
                 }
             });
             espera.start();
        } else if (e.getSource().equals(bEqual)) {
             Solicitud solicitud=new Solicitud(clientIp,input.getText());
             try {
                 envio.writeObject(solicitud);
             } catch (IOException ex) {
                 throw new RuntimeException(ex);
             }
        } else if (e.getSource().equals(bDel)) {
             int i = input.getCaretPosition();
             int len= input.getText().length();
             if (i==len) {
                 input.setText(input.getText().substring(0,len-1));
             }else if (i>0){
                 String subS1 = input.getText().substring(0, i-1);
                 String subS2 = input.getText().substring(i);
                 input.setText(subS1.concat(subS2));
                 input.setCaretPosition(i-1);
             }
        } else if (e.getSource().equals(bClr)) {
            input.setText("");
        } else if (e.getSource().equals(bLeft)) {
            try {
                input.setCaretPosition(input.getCaretPosition()-1);
            }catch (Exception ignored){
            }

        } else if (e.getSource().equals(bRight)) {
            try {
                input.setCaretPosition(input.getCaretPosition()+1);
            }catch (Exception ignored){
            }
        }else{
            JButton btn = (JButton) e.getSource();

            int i = input.getCaretPosition();
            int len= input.getText().length();
            if (i==len) {
                input.setText(input.getText().concat(btn.getText()));
            }else if (i>=0){
                String subS1 = input.getText().substring(0, i);
                String subS2 = input.getText().substring(i);
                input.setText(subS1.concat(btn.getText()).concat(subS2));
                input.setCaretPosition(i+1);
            }
        }
    }
    @Override
    public void run() {
        try {
            recibirRespuesta = new ServerSocket(9090);
            while (true) {
                rRespuesta = recibirRespuesta.accept();
                entrada = new ObjectInputStream(rRespuesta.getInputStream());
                Respuesta respuesta = (Respuesta) entrada.readObject();
                input.setText(respuesta.getOp());
                output.setText(respuesta.getResp());

                listModel.clear();
                System.out.println(respuesta.getHistory().getSize());
                Nodo current= respuesta.getHistory().getHead();
                for (int i = 0; i < respuesta.getHistory().getSize(); i++) {
                    System.out.println(current.getData().toString());
                    listModel.addElement(current.getData());
                    current=current.getNext();
                }
                rRespuesta.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}