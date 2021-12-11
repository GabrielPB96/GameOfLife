package visual;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Write a description of class Bienvenida here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Bienvenida extends JFrame {
    public Bienvenida() {
        setBounds(0, 0, 400, 300);
        LaminaBienvenida l = new LaminaBienvenida();
        addWindowListener(new OyenteWindow());
        add(l);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        l.logo();
        try{
            Thread.sleep(2000);
        }catch(Exception e) {}
    }
    
    public static void main(String[] args) {
        new Bienvenida();
    }
    
    private class OyenteWindow implements WindowListener {
        public void windowClosing(WindowEvent e) {
            System.out.println("GAUARDADO");
        }
        
        public void windowActivated(WindowEvent e){}
        
        public void windowClosed(WindowEvent e){}
        public void windowDeactivated(WindowEvent e){}
        public void windowDeiconified(WindowEvent e){}
        public void windowIconified(WindowEvent e){}
        public void windowOpened(WindowEvent e){}
    }
}

class LaminaBienvenida extends JPanel {
    private ImageIcon[] logo;;
    private Toolkit pantalla = Toolkit.getDefaultToolkit();
    
    public LaminaBienvenida() {
        BoxLayout box = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(box);
    }
    
    private void loadLogo() {
        logo = new ImageIcon[4];
        logo[0] = new ImageIcon(pantalla.getImage("./assets/g.png").getScaledInstance(50, 70, Image.SCALE_SMOOTH));
        logo[1] = new ImageIcon(pantalla.getImage("./assets/a.png").getScaledInstance(50, 70, Image.SCALE_SMOOTH));
        logo[2] = new ImageIcon(pantalla.getImage("./assets/m.png").getScaledInstance(50, 70, Image.SCALE_SMOOTH));
        logo[3] = new ImageIcon(pantalla.getImage("./assets/e.png").getScaledInstance(50, 70, Image.SCALE_SMOOTH));
    }
    
    public void logo() {
        loadLogo();
        JPanel panelPo = new JPanel();
        JPanel panelLo = new JPanel();
        panelPo.setBackground(new Color(84, 166, 186));
        panelLo.setBackground(new Color(84, 166, 186));
        panelPo.add(new JLabel(new ImageIcon(pantalla.getImage("./assets/po.gif")), SwingConstants.CENTER));
        add(panelPo);
        add(panelLo);
        updateUI();
        try{
            Thread.sleep(1000);
            for (ImageIcon log : logo) {
                panelLo.add(new JLabel(log, SwingConstants.CENTER));
                updateUI();
                Thread.sleep(200);
            }
        }catch(Exception e) {}    
    }
}
