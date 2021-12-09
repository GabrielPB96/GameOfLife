package visual;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import logic.*;

public class Visual extends JFrame{
    private Lamina lamina;
    private JButton play, reset;
    private Toolkit pantalla = Toolkit.getDefaultToolkit();
    
    public Visual() {
        lamina = new Lamina();
        setTitle("Game Of Life");
        setBounds(0, 0, pantalla.getScreenSize().height-35, pantalla.getScreenSize().height-35);
        setLocationRelativeTo(null);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        init();
        setVisible(true);
        play.setVisible(true);
        reset.setVisible(true);
        setVisible(true);
    }
    
    public void init() {
        setLayout(new BorderLayout());
        JPanel laminaSouth = new JPanel();
        JButton subirVelocidad, bajarVelocidad;
            JLabel dislay = new JLabel("Delay: 50  ");
        
        subirVelocidad = new JButton("subir");
        bajarVelocidad = new JButton("bajar");
        subirVelocidad.setCursor(new Cursor(12));
        bajarVelocidad.setCursor(new Cursor(12));
        
        subirVelocidad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(lamina.enJuego()) {
                    lamina.subirVelocidad();
                    dislay.setText("Delay: "+lamina.getVelocidad()+"  ");
                }
            }
        });
        
        bajarVelocidad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(lamina.enJuego()) {
                    lamina.bajarVelocidad();
                    dislay.setText("Delay: "+lamina.getVelocidad()+"  ");
                }
            }
        });
        
        play = new JButton("PLAY");
        reset = new JButton("RESET");
        
        play.setCursor(new Cursor(12));
        reset.setCursor(new Cursor(12));
            
        play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(!lamina.enJuego()) {
                    lamina.init();
                    lamina.play(100);
                }else{
                    JOptionPane.showMessageDialog(Visual.this, "Reset the Board");
                }
            }
        });
        
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                lamina.setRun(false);
                lamina.reset();
            }
        });
        
        laminaSouth.add(dislay);
        laminaSouth.add(subirVelocidad);
        laminaSouth.add(bajarVelocidad);
        laminaSouth.add(play);
        
        play.setVisible(false);
        reset.setVisible(false);
        add(laminaSouth, BorderLayout.SOUTH);
        add(reset, BorderLayout.NORTH);
        add(lamina, BorderLayout.CENTER);
    }
    
    
    public static void main(String args[]) {
        new Bienvenida();
        new Visual();
    }
}

class Lamina extends JPanel {
    private Tablero tablero;
    private ArrayList<Celula> celulas;
    private boolean run;
    private int dislay;
    
    public Lamina() {
        run = false;
        dislay = 50;
        setBackground(new Color(238, 193, 112));
        celulas = new ArrayList<Celula>();
        tablero = new Tablero(78, 76);
        paintTablero();
    }
    
    public int getVelocidad() {
        return dislay;
    }
    
    public void subirVelocidad() {
        if (dislay > 1) {
            dislay = dislay - 1;
        }
    }
    
    public void bajarVelocidad() {
        if (dislay < 151) {
            dislay = dislay + 1;
        }
    }
    
    public void setRun(boolean n) {
        run = n;
    }
    
    public void paintTablero() {
        removeAll();
        setLayout(new GridLayout(78, 76));
        var t = tablero.getTablero();
        for(int i = 0; i < t.length; i++) {
            for(int j = 0; j < t[0].length; j++) {
                add(t[i][j]);
                celulas.add(t[i][j]);
            }
        } 
        updateUI();
    }
    
    public void reset() {
        tablero.reset();
        for(Celula b: celulas){
            b.setBackground(Color.BLACK);
            b.dead();
        }
    }
    
    public void paintCelulas(){
        var t = tablero.getTablero();
        int pos = 0;
        for(int i = 0; i < t.length; i++) {
            for(int j = 0; j < t[0].length; j++) {
                Celula b = celulas.get(pos);
                if(t[i][j].vivo()) {
                    b.revivir();
                }else{
                    b.dead();
                }
                pos++;
            }
        } 
    }
    
    public void init() {
        var t = tablero.getTablero();
        int pos = 0;
        for(int i = 0; i < t.length; i++) {
            for(int j = 0; j < t[0].length; j++) {
                if(celulas.get(pos).vivo()) {
                    t[i][j].revivir();
                }
                pos++;
            }
        }
        tablero.actualizarVecinos();
    }
    
    public void play(int numIteraciones) {
        run = true;
        Thread animacion = new Thread(new Runnable(){
            private int n = numIteraciones;
            public void run(){
                while(run) {
                    tablero.play();
                    paintCelulas();
                    try{
                        Thread.sleep(dislay);
                    }catch(Exception e) {}
                }        
            }
        });
        animacion.start();
    }
    
    public boolean enJuego() {
        return run;
    }
}
