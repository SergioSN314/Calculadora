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

/**
 * Clase que crea la aplicación cliente de la calculadora
 * @author Alejandro Solís Bolaños
 * @author Sergio Salazar Nuñez
 * @see javax.swing.JFrame
 * @see java.lang.Runnable
 * @see java.awt.event.ActionListener
 */
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

    public static void main(String[] args) {new Calculadora(serverIp,serverPort);}

    /**
     * Constructor de la calculadora, genera la GUI y conecta con un <code>Servidor</code> con la IP y el puerto especificados en los parametros <code>serverIp</code> y <code>serverPort</code>
     * @param serverIp <code>String</code> con la dirrecon IP del servidor al que se conectará la caculadora
     * @param serverPort <code>int</code> con el número de puerto por el cual se conectará con el servidor
     */
    Calculadora(String serverIp, int serverPort){
        InetAddress localHost;
        try {
            localHost = InetAddress.getLocalHost();
            this.clientIp = (localHost.getHostAddress());           //obtiene la direccion IP del cliente
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
            enviarSolicitud= new Socket(serverIp,serverPort);
            envio = new ObjectOutputStream(enviarSolicitud.getOutputStream());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//<<<<<<<<<<<<<<<<<<<<<<<<<<<< INTERFAZ GRAFICA >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Calculadora");
        this.setSize(WIDTH, HEIGHT);
        this.setLayout(new BorderLayout());
        this.setResizable(false);

        panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(WIDTH-400, HEIGHT));
        panel.setBackground(new Color(0, 0, 0, 255));

        input= new JTextField();
        input.setPreferredSize(new Dimension(WIDTH,80));
        input.setBackground(new Color(35, 35, 35));
        input.setForeground(new Color(220, 220, 220, 255));
        input.setFont(font);
        input.setEditable(false);

        output=new JLabel();
        output.setPreferredSize(new Dimension(WIDTH,80));
        output.setHorizontalAlignment(JLabel.RIGHT);
        output.setFont(font);
        output.setBackground(Color.DARK_GRAY);
        output.setForeground(new Color(220, 220, 220, 255));

        GridBagConstraints gbc =new GridBagConstraints();

        gbc.weightx=1;
        gbc.weighty=1;
        gbc.fill=GridBagConstraints.BOTH;
        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< CREACION DE BOTONES >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
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

        for (int i = 0; i < 28; i++) {           //Configura todos los botones creados
            Buttons[i].addActionListener(this);
            Buttons[i].setFont(font);
            Buttons[i].setFocusable(false);
            Buttons[i].setPreferredSize(new Dimension(80,80));
            Buttons[i].setForeground(new Color(220, 220, 220, 255));
            switch (i){
                case 0,1,2,3,4,5,6,7,8,9:
                    Buttons[i].setBackground(new Color(0, 24, 21, 255));
                    break;
                case 10:
                    Buttons[i].setBackground(new Color(0, 20, 0));
                    break;
                case 11,12,13,14,15,16:
                    Buttons[i].setBackground(new Color(0, 10, 19, 255));
                    break;
                case 17,18,19,20:
                    Buttons[i].setBackground(new Color(0, 20, 24, 255));
                    break;
                case 21,22,23,24:
                    Buttons[i].setBackground(new Color(0, 19, 17, 255));
                    break;
                case 25:
                    Buttons[i].setBackground(new Color(0, 0, 0, 255));
                    break;
                case 26,27:
                    Buttons[i].setBackground(new Color(15, 0, 21, 255));
                    break;
            }
        }
        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ACOMODO DE LOS COMPONENTES DE GUI >>>>>>>>>>>>>>>>>>>>>>>>>>>
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
        panel.add(bLeft,gbc);

        gbc.gridx++;
        panel.add(bRight,gbc);

        gbc.gridx++;
        panel.add(bAnd,gbc);
        gbc.gridx++;
        panel.add(bOr,gbc);

        //FILA 2
        gbc.gridy++;
        gbc.gridx=1;
        panel.add(bOpenPar,gbc);
        gbc.gridx++;
        panel.add(bClosePar,gbc);
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
        gbc.gridwidth=1;
        gbc.gridx=0;
        panel.add(bDel,gbc);
        gbc.gridx=1;
        gbc.gridwidth=2;
        panel.add(b0,gbc);
        gbc.gridx=3;
        panel.add(bEqual,gbc);

        historyPanel=new JPanel();
        historyPanel.setLayout(new BorderLayout());
        historyPanel.setPreferredSize(new Dimension(400,HEIGHT));
        historyPanel.setBackground(new Color(0, 24, 21, 255));

        historyLablel=new JLabel("Historial");
        historyLablel.setFont(font);
        historyLablel.setForeground(new Color(220, 220, 220, 255));
        historyLablel.setBackground(new Color(0, 24, 21, 255));
        historyLablel.setHorizontalAlignment(JLabel.CENTER);
        historyLablel.setPreferredSize(new Dimension(400,105));
        historyPanel.add(historyLablel,BorderLayout.NORTH);

        history= new JList<>();
        listModel= new DefaultListModel<>();
        history.setModel(listModel);
        history.setPreferredSize(new Dimension(400,HEIGHT-105));
        history.setBackground(new Color(0, 19, 17, 255));
        history.setForeground(new Color(187, 187, 187));
        history.setFont(font);
        historyScroll=new JScrollPane(history);
        historyScroll.setPreferredSize(new Dimension(400,HEIGHT-105));
        history.setFixedCellHeight(50);
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
             openCam();

        } else if (e.getSource().equals(bEqual)) {
             equal();

        } else if (e.getSource().equals(bDel)) {
             delete();

        } else if (e.getSource().equals(bClr)) {
             clear();

        } else if (e.getSource().equals(bLeft)) {
             moveCaret("left");

        } else if (e.getSource().equals(bRight)) {
             moveCaret("right");

        }else{
            JButton btn = (JButton) e.getSource();
            typeBtn(btn);
        }
    }

    /**
     * Abre la cámara del dispositivo y espera a que se capture la imagen deseada
     * @see Camara
     */
    private void openCam() {
        camara= new  Camara();
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
    }

    /**
     * Escribe en el <code>JTextField</code> <code>input</code> el carácter correspondiente al botón presionado
     *
     * @param btn <code>JButton</code> presionado en la calculadora
     * @see String#substring(int, int) 
     */
    private void typeBtn(JButton btn) {
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

    /**
     * Mueve el carrete del <code>JTextField</code> <code>input</code> hacia la dirección especificada por le parámetro dir
     * @param dir Indica la dirección en la que se moverá el carrete.
     *            "right" = se mueve hacia la derecha.
     *            "left" = se mueve hacia la izquierda.
     * @see JTextField#getCaretPosition() 
     * @see JTextField#setCaretPosition(int) 
     */
    private void moveCaret(String dir) {
        try {
            switch (dir) {
                case "right":
                    input.setCaretPosition(input.getCaretPosition()+1);
                    break;
                case "left":
                    input.setCaretPosition(input.getCaretPosition()-1);
                    break;
            }
        } catch (Exception ignored) {
        }
    }

    /**
     * Limpia el <code>JTextField</code> <code>input</code>
     */
    private void clear() {
        input.setText("");
    }

    /**
     * Borra el carácter anterior al carrete en el <code>JTextField</code> <code>input</code>
     * @see String#substring(int, int) 
     */
    private void delete() {
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
    }

    /**
     * Envia la operación especificada en el <code>JTextField</code> <code>input</code> al servidor para que sea procesada
     */
    private void equal() {
        Solicitud solicitud=new Solicitud(clientIp,input.getText());
        try {
            envio.writeObject(solicitud);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Matiene una conexión constante con el <code>Servior</code> y actualiza constantemente los elementos de la calculadora
     */
    @Override
    public void run() {
        try {
            recibirRespuesta = new ServerSocket(9090);
            while (true) {
                rRespuesta = recibirRespuesta.accept();
                entrada = new ObjectInputStream(rRespuesta.getInputStream());
                Respuesta respuesta = (Respuesta) entrada.readObject();
                actualizar(respuesta);
                rRespuesta.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Actualiza los componentes de la calculadora a partir de la respuesta recibida del servidor
     * @param respuesta <code>Respuesta</code> recibida del servidor
     * @see Respuesta
     */
    private void actualizar(Respuesta respuesta) {
        input.setText(respuesta.getOp());
        if (respuesta.getResp().equals("ERROR")){
            output.setForeground(new Color(162, 0, 0));
            output.setText(respuesta.getResp());
        }else {
            output.setForeground(new Color(220, 220, 220));
            output.setText(respuesta.getResp());
        }

        if (respuesta.getHistory()!=null){
            listModel.clear();
            Nodo current= respuesta.getHistory().getHead();
            for (int i = 0; i < respuesta.getHistory().getSize(); i++) {
                //System.out.println(current.getData().toString());
                listModel.addElement(current.getData());
                current=current.getNext();
            }
        }
    }
}