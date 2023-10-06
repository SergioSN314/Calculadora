import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculadora extends JFrame implements ActionListener {
    final int WIDTH = 800;
    final int HEIGHT= 800;


    Font font= new Font("OCR A Extended",Font.PLAIN,30);
    JPanel panel;

    JButton[] Buttons = new JButton[28];
    JButton b0, b1, b2, b3, b4, b5, b6, b7, b8, b9;
    JButton bPlus, bMinus, bEqual, bMult, bDiv, bMod, bExp, bAnd, bOr, bXOr, bNot, bCam,bOpenPar,bClosePar,bLeft,bRight, bClr, bDel;
    JTextField input;
    JLabel output;

    Calculadora(){

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Calculadora");
        this.setSize(WIDTH, HEIGHT);

        panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.setBackground(Color.black);

        input= new JTextField();
        input.setPreferredSize(new Dimension(WIDTH,80));
        input.setBackground(Color.WHITE);
        input.setFont(font);
        input.setEditable(false);
//        input.setText("INPUT");


        output=new JLabel();
        output.setPreferredSize(new Dimension(WIDTH,80));
        output.setHorizontalAlignment(JLabel.RIGHT);
        output.setFont(font);
        output.setBackground(Color.DARK_GRAY);
        output.setForeground(Color.WHITE);
//        output.setText("OUTPUT");

        GridBagConstraints gbc =new GridBagConstraints();

        gbc.weightx=1;
        gbc.weighty=1;
        gbc.fill=GridBagConstraints.BOTH;


        b0=new JButton("0");
        b1=new JButton("1");
        b2=new JButton("2");
        b3=new JButton("3");
        b4=new JButton("4");
        b5=new JButton("5");
        b6=new JButton("6");
        b7=new JButton("7");
        b8=new JButton("8");
        b9=new JButton("9");
        bEqual =new JButton("=");
        bPlus =new JButton("+");
        bMinus =new JButton("-");
        bMult =new JButton("*");
        bDiv =new JButton("/");
        bMod =new JButton("%");
        bExp =new JButton("**");
        bAnd =new JButton("&");
        bOr =new JButton("|");
        bXOr =new JButton("^");
        bNot = new JButton("~");
        bOpenPar =new JButton("(");
        bClosePar =new JButton(")");
        bLeft =new JButton("<-");
        bRight=new JButton("->");
        bCam =new JButton("Cam");
        bClr = new JButton("Clr");
        bDel = new JButton("Del");

        Buttons[0]=b0;
        Buttons[1]=b1;
        Buttons[2]=b2;
        Buttons[3]=b3;
        Buttons[4]=b4;
        Buttons[5]=b5;
        Buttons[6]=b6;
        Buttons[7]=b7;
        Buttons[8]=b8;
        Buttons[9]=b9;
        Buttons[10]=bEqual;
        Buttons[11]=bPlus;
        Buttons[12]=bMinus;
        Buttons[13]=bMult;
        Buttons[14]=bDiv;
        Buttons[15]=bMod;
        Buttons[16]=bExp;
        Buttons[17]=bAnd;
        Buttons[18]=bOr;
        Buttons[19]=bXOr;
        Buttons[20]=bNot;
        Buttons[21]=bOpenPar;
        Buttons[22]=bClosePar;
        Buttons[23]=bLeft;
        Buttons[24]=bRight;
        Buttons[25]=bCam;
        Buttons[26]=bClr;
        Buttons[27]=bDel;

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



        this.add(panel);
        this.pack();
        this.setVisible(true);



        /*System.out.println("Calculadora");

        File image = new File("src/main/resources/img/op.png");
        ReconText reconText = new  ReconText(image);
        System.out.println(reconText.getText());*/

    }


    @Override
    public void actionPerformed(ActionEvent e) {
         if (e.getSource().equals(bCam)){
            //TODO:abrir camara
             openCam();
        } else if (e.getSource().equals(bEqual)) {
            //TODO: enviar al servidor para calcular resultado
            output.setText("RESULTADO de :"+ input.getText());
        } else if (e.getSource().equals(bDel)) {
            input.setText(input.getText().substring(0,input.getText().length()-1));
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
            input.setText(input.getText().concat(btn.getText()));
        }
    }

    private void openCam() {

        Camara camara= new  Camara();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    camara.startCam();
                }catch (Exception ignored){}

            }
        }).start();

    }
}