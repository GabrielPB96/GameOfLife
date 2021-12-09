package visual;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import logic.*;

public class Visual extends JFrame{
    private Lamina lamina;
    private JButton play, reset, pause, resume, subir, bajar;
    private JLabel velocidad;
    private Toolkit pantalla = Toolkit.getDefaultToolkit();
    private Icon bajarIco = new ImageIcon(pantalla.getImage("./assets/bajar.png").getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    private Icon subirIco = new ImageIcon(pantalla.getImage("./assets/subir.png").getScaledInstance(20, 20, Image.SCALE_SMOOTH));    
    
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
        JPanel laminaNorth = new JPanel();
        JPanel laminaSouth = new JPanel();
        
        laminaNorth.setPreferredSize(new Dimension(400, 30));
        laminaSouth.setPreferredSize(new Dimension(400, 30));
        laminaNorth.setBackground(new Color(84, 166, 186));
        laminaSouth.setBackground(new Color(84, 166, 186));
        
        play = new JButton("PLAY");
        reset = new JButton("RESET");
        velocidad = new JLabel("VELOCIDAD");
        pause = new JButton("PAUSE");
        resume = new JButton("RESUME");
        subir = new JButton(subirIco);
        bajar = new JButton(bajarIco);
        
        play.setPreferredSize(new Dimension(70, 20));
        pause.setPreferredSize(new Dimension(90, 20));
        resume.setPreferredSize(new Dimension(90, 20));
        reset.setPreferredSize(new Dimension(90, 20));
        subir.setPreferredSize(new Dimension(20, 20));
        bajar.setPreferredSize(new Dimension(20, 20));
        
        pause.setCursor(new Cursor(12));
        resume.setCursor(new Cursor(12));
        subir.setCursor(new Cursor(12));
        bajar.setCursor(new Cursor(12));
        play.setCursor(new Cursor(12));
        reset.setCursor(new Cursor(12));

        subir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if (!lamina.subirVelocidad()) {
                    pantalla.beep();
                    JOptionPane.showMessageDialog(Visual.this, "Speed Limit");
                }
            }
        });
        
        bajar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if (!lamina.bajarVelocidad()) {
                    pantalla.beep();
                    JOptionPane.showMessageDialog(Visual.this, "Speed Limit");
                }
            }
        });
        
        pause.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lamina.pause();
            }
        });
        
        resume.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lamina.resume();
            }
        });
            
        play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(!lamina.enJuego()) {
                    lamina.init();
                    lamina.play();
                }else{
                    JOptionPane.showMessageDialog(Visual.this, "Reset the Board");
                }
            }
        });
        
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                lamina.stop();
                lamina.reset();
            }
        });
        
        laminaNorth.add(velocidad);
        laminaNorth.add(subir);
        laminaNorth.add(bajar);
        laminaNorth.add(reset);
        
        laminaSouth.add(play);
        laminaSouth.add(pause);
        laminaSouth.add(resume);
        
        play.setVisible(false);
        reset.setVisible(false);
        add(laminaNorth, BorderLayout.NORTH);
        add(laminaSouth, BorderLayout.SOUTH);
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
    private Hilo animation;
    
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
    
    public boolean subirVelocidad() {
        if (dislay > 1) {
            dislay = dislay - 3;
            return true;
        }
        return false;
    }
    
    public boolean bajarVelocidad() {
        if (dislay < 201) {
            dislay = dislay + 3;
            return true;
        }
        return false;
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
    
    public void stop() {
        run = false;
        if (animation != null) animation.newStop();
    }
    
    public void pause() {
        if (animation != null) animation.newSuspend();
    }
    
    public void resume() {
        if (animation != null) animation.newResume();
    }
    
    public void play() {
        run = true;
        animation = new Hilo(new Runnable(){
            public void run(){
                while(!animation.end) {
                    tablero.play();
                    paintCelulas();
                    try{
                        synchronized(animation) {
                            while(animation.suspend) {
                                animation.wait();
                            }
                        }
                        Thread.sleep(dislay);
                    }catch(Exception e) {}
                }        
            }
        });
        animation.start();
    }
    
    public boolean enJuego() {
        return run;
    }
}
