package visual;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import logic.*;
import java.awt.geom.*;
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
    private int height;
    
    public Visual() {
        height = pantalla.getScreenSize().height-45;
        
        lamina = new Lamina();
        storage = new Storage();
        
        addWindowListener(new OyenteWindow());
        
        setTitle("Game Of Life");
        setBounds(0, 0, 826, height);
        setLocationRelativeTo(null);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        init();
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
        velocidad = new JLabel("SPEED: " + (201 - lamina.getVelocidad()) + "  ");
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
                velocidad.setText("SPPED: " + (201 - lamina.getVelocidad()) + " ");
            }else if(e.getSource().equals(bajar)) {
                if (!lamina.bajarVelocidad()) {
                    pantalla.beep();
                    JOptionPane.showMessageDialog(Visual.this, "Speed Limit");
                }
                velocidad.setText("SPPED: " + (201 - lamina.getVelocidad()) + " ");
            }else if(e.getSource().equals(reset)) {
                lamina.stop();
                lamina.reset();
            }else if(e.getSource().equals(play)) {
                if(!lamina.enJuego()) {
                    lamina.play();
                }else{
                    pantalla.beep();
                    JOptionPane.showMessageDialog(Visual.this, "Reset the Board");
                }
            }else if(e.getSource().equals(pause)) {
                lamina.pause();
            }else if(e.getSource().equals(resume)) {
                lamina.resume();
            }else if(e.getSource().equals(save)) {
                if (!lamina.enJuego()) {
                    String name = JOptionPane.showInputDialog(Visual.this, "Name Pattern");
                    if(name != null) {
                        if (!name.isEmpty()) {
                            ArrayList<int[]> posicionesMarcadas = getPosiciones();
                            Pattern pattern = new Pattern(name, posicionesMarcadas);
                            storage.save(pattern);
                        }else {
                            JOptionPane.showMessageDialog(Visual.this, "Error, empty name...");
                        }
                    }else {
                        JOptionPane.showMessageDialog(Visual.this, "Error, null name...");
                    }
                }else {
                    JOptionPane.showMessageDialog(Visual.this, "Error, Reset the board...");
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
    private boolean run;
    private int delay;
    private Hilo animation;
    
    public Lamina() {
        run = false;
        setLayout(null);
        setBackground(Color.GRAY);
        setCursor(new Cursor(12));
        delay = 100;
        addMouseListener(new Oyente());
        addMouseMotionListener(new Oyente());
        tablero = new Tablero(78, 90);
    }
    
    public int getVelocidad() {
        return delay;
    }
    
    public boolean subirVelocidad() {
        //delay > 101
        if (delay > 61) {
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
    
    public void paintTablero(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        var t = tablero.getTablero();
        for(int i = 0; i < t.length; i++) {
            for(int j = 0; j < t[0].length; j++) {
                g2.setColor(t[i][j].getColor());
                g2.fillRect(t[i][j].getColumna() * 9, t[i][j].getFila() * 8, 8, 7);
            }
        } 
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintTablero(g);
    }
    
    public void reset() {
        tablero.reset();
        repaint();
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
        tablero.actualizarVecinos();
        animation = new Hilo(new Runnable(){
            public void run(){
                while(!animation.end) {
                    tablero.play();
                    repaint();
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
        repaint();
    }
    
    private class Oyente extends MouseAdapter {
        public void mouseClicked(MouseEvent e){
            int x, y;
            x = e.getX();
            y = e.getY();
            var t = tablero.getTablero();
            
            for (int i = 0; i < t.length; i++) {
                for (int j = 0; j < t[i].length; j++) {
                    Rectangle2D.Double r = new Rectangle2D.Double(t[i][j].getColumna() * 9, t[i][j].getFila() * 8, 8 , 7);
                    if (r.contains(x, y)) {
                        if(!t[i][j].vivo()) {
                            t[i][j].revivir();
                        }else{
                            t[i][j].dead();
                        }
                    }
                }
            }
            repaint();
        }
        
        public void mouseDragged(MouseEvent e) {
            //completar   
        }
    }
}
