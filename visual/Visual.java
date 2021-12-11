package visual;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import logic.*;
import storage.*;

public class Visual extends JFrame {
    private Lamina lamina;
    private JButton play, reset, pause, resume, subir, bajar, save, showPatterns;
    private JComboBox comboInfinite;
    private JLabel velocidad;
    private Toolkit pantalla = Toolkit.getDefaultToolkit();
    private Icon bajarIco = new ImageIcon(pantalla.getImage("./assets/bajar.png").getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    private Icon subirIco = new ImageIcon(pantalla.getImage("./assets/subir.png").getScaledInstance(20, 20, Image.SCALE_SMOOTH));    
    private Storage storage;
    
    public Visual() {
        lamina = new Lamina();
        storage = new Storage();
        
        addWindowListener(new OyenteWindow());
        
        setTitle("Game Of Life");
        setBounds(0, 0, pantalla.getScreenSize().height-45, pantalla.getScreenSize().height-45);
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
        OyenteButton oyenteButton = new OyenteButton();
        
        laminaNorth.setPreferredSize(new Dimension(400, 30));
        laminaSouth.setPreferredSize(new Dimension(400, 30));
        laminaNorth.setBackground(new Color(84, 166, 186));
        laminaSouth.setBackground(new Color(84, 166, 186));
        
        play = new JButton("PLAY");
        reset = new JButton("RESET");
        velocidad = new JLabel("VELOCIDAD: 50 ");
        pause = new JButton("PAUSE");
        resume = new JButton("RESUME");
        subir = new JButton(subirIco);
        bajar = new JButton(bajarIco);
        save = new JButton("SAVE");
        showPatterns = new JButton("PATTERNS");
        
        comboInfinite = new JComboBox();
        comboInfinite.addItem("Infinite");
        comboInfinite.addItem("Finite");
        
        play.setPreferredSize(new Dimension(70, 20));
        pause.setPreferredSize(new Dimension(90, 20));
        resume.setPreferredSize(new Dimension(90, 20));
        reset.setPreferredSize(new Dimension(90, 20));
        subir.setPreferredSize(new Dimension(20, 20));
        bajar.setPreferredSize(new Dimension(20, 20));
        save.setPreferredSize(new Dimension(70, 20));
        showPatterns.setPreferredSize(new Dimension(100, 20));
        
        pause.setCursor(new Cursor(12));
        resume.setCursor(new Cursor(12));
        subir.setCursor(new Cursor(12));
        bajar.setCursor(new Cursor(12));
        play.setCursor(new Cursor(12));
        reset.setCursor(new Cursor(12));
        showPatterns.setCursor(new Cursor(12));
        
        subir.addActionListener(oyenteButton);
        bajar.addActionListener(oyenteButton);
        reset.addActionListener(oyenteButton);
        play.addActionListener(oyenteButton);
        pause.addActionListener(oyenteButton);
        resume.addActionListener(oyenteButton);
        save.addActionListener(oyenteButton);
        showPatterns.addActionListener(oyenteButton);
        comboInfinite.addActionListener(oyenteButton);
        
        laminaNorth.add(velocidad);
        laminaNorth.add(subir);
        laminaNorth.add(bajar);
        laminaNorth.add(reset);
        laminaNorth.add(showPatterns);
        laminaNorth.add(comboInfinite);
        
        laminaSouth.add(play);
        laminaSouth.add(pause);
        laminaSouth.add(resume);
        laminaSouth.add(save);
        
        play.setVisible(false);
        reset.setVisible(false);
        add(laminaNorth, BorderLayout.NORTH);
        add(laminaSouth, BorderLayout.SOUTH);
        add(lamina, BorderLayout.CENTER);
    }
    
    public void loadPattern(ArrayList<int[]> posiciones) {
        lamina.loadPattern(posiciones);
    }
    
    private class OyenteWindow implements WindowListener {
        public void windowClosing(WindowEvent e) {
            storage.saveObject();
        }
        
        public void windowActivated(WindowEvent e){}
        
        public void windowClosed(WindowEvent e){}
        public void windowDeactivated(WindowEvent e){}
        public void windowDeiconified(WindowEvent e){}
        public void windowIconified(WindowEvent e){}
        public void windowOpened(WindowEvent e){}
    }
    
    private class OyenteButton implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(subir)) {
                if (!lamina.subirVelocidad()) {
                    pantalla.beep();
                    JOptionPane.showMessageDialog(Visual.this, "Speed Limit");
                }
                velocidad.setText("VELOCIDAD: " + (201 - lamina.getVelocidad()) + " ");
            }else if(e.getSource().equals(bajar)) {
                if (!lamina.bajarVelocidad()) {
                    pantalla.beep();
                    JOptionPane.showMessageDialog(Visual.this, "Speed Limit");
                }
                velocidad.setText("VELOCIDAD: " + (201 - lamina.getVelocidad()) + " ");
            }else if(e.getSource().equals(reset)) {
                lamina.stop();
                lamina.reset();
            }else if(e.getSource().equals(play)) {
                if(!lamina.enJuego()) {
                    lamina.init();
                    lamina.play();
                }else{
                    JOptionPane.showMessageDialog(Visual.this, "Reset the Board");
                }
            }else if(e.getSource().equals(pause)) {
                lamina.pause();
            }else if(e.getSource().equals(resume)) {
                lamina.resume();
            }else if(e.getSource().equals(save)) {
                String name = JOptionPane.showInputDialog(Visual.this, "Name Pattern");
                if (!name.isEmpty()) {
                    ArrayList<int[]> posicionesMarcadas = getPosiciones();
                    Pattern pattern = new Pattern(name, posicionesMarcadas);
                    storage.save(pattern);
                }else {
                    JOptionPane.showMessageDialog(Visual.this, "Error, nombre vacio...");
                }
            }else if(e.getSource().equals(showPatterns)) {
                new PatternsFrame(Visual.this, storage.getPatterns());
            }else if(e.getSource().equals(comboInfinite)) {
                if (((String)comboInfinite.getSelectedItem()).equals("Infinite")) {
                    lamina.getBoard().infinite(true);
                }else {
                    lamina.getBoard().infinite(false);
                }
            }
        }
        
        public ArrayList<int[]> getPosiciones() {
            ArrayList<int[]> p = new ArrayList<int[]>();
            Celula[][] aux = lamina.getBoard().getTablero();
            for(int i = 0; i < aux.length; i++){
                for(int j = 0; j < aux[i].length; j++) {
                    if(aux[i][j].vivo()) {
                        int[] pos = {i, j};
                        p.add(pos);
                    }
                }
            }
            return p;
        }
    }
}

class Lamina extends JPanel {
    private Tablero tablero;
    private ArrayList<Celula> celulas;
    private boolean run;
    private int delay;
    private Hilo animation;
    
    public Lamina() {
        run = false;
        delay = 151;
        setBackground(new Color(238, 193, 112));
        celulas = new ArrayList<Celula>();
        tablero = new Tablero(78, 76);
        paintTablero();
    }
    
    public int getVelocidad() {
        return delay;
    }
    
    public boolean subirVelocidad() {
        //delay > 101
        if (delay > 81) {
            delay = delay - 1;
            return true;
        }
        return false;
    }
    
    public boolean bajarVelocidad() {
        if (delay < 200) {
            delay = delay + 1;
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
                        Thread.sleep(delay);
                    }catch(Exception e) {}
                }        
            }
        });
        animation.setDaemon(true);
        animation.start();
    }
    
    public boolean enJuego() {
        return run;
    }
    
    public Tablero getBoard() {
        return tablero;
    }
    
    public void loadPattern(ArrayList<int[]> posiciones) {
        stop();
        tablero.reset();
        for (int[] pos : posiciones) {
            tablero.revivir(pos[0], pos[1]);
        } 
        updateUI();
    }
}
