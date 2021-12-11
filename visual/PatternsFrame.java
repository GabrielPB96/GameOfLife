package visual;
import javax.swing.*;
import java.util.ArrayList;
import storage.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Write a description of class PatternsFrame here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PatternsFrame extends JDialog{
    private ArrayList<Pattern> patterns;
    private Visual owner;
    private final String password = "adminGPB96";
    
    public PatternsFrame(Visual owner, ArrayList<Pattern> patterns) {
        super(owner, "Patterns", true);
        this.patterns = patterns;
        this.owner = owner;
        createFrame();
        setLocationRelativeTo(owner);
        setVisible(true);
    }

    private void createFrame() {
        JScrollPane scroll = new JScrollPane();
        JPanel lamina = new JPanel();
        
        lamina.setLayout(new FlowLayout());
        lamina.setPreferredSize(new Dimension(250, patterns.size() * 26));
        setBounds(0, 0, 300, 300);
        createButtons(lamina);
        scroll.setViewportView(lamina);
        scroll.setPreferredSize(new Dimension(250, 250));
        add(scroll);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
    
    private void createButtons(JPanel lamina) {
        int i = 0;
        for (Pattern pattern : patterns) {
            JButton boton = new JButton(pattern.getName());
            boton.setPreferredSize(new Dimension(200, 20));
            JPopupMenu popup = new JPopupMenu();
            JMenuItem item = new Item("Delete", i, boton);
            item.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    Item botonAction = (Item)e.getSource();
                    String passwordInput = JOptionPane.showInputDialog(PatternsFrame.this, "Ingrese el passaword");
                    if (passwordInput.equals(password)) {
                        patterns.remove(botonAction.getIndex());
                        lamina.remove(botonAction.getButton());
                        lamina.updateUI();
                    }else {
                            JDialog messageIncorrecto = new JDialog(PatternsFrame.this, "Incorrecto", true);
                            JLabel msg = new JLabel("Password Incorrecto.");
                            JPanel panel = new JPanel();
                            panel.add(msg);
                            messageIncorrecto.setBounds(0, 0, 300, 100);
                            messageIncorrecto.setLocationRelativeTo(PatternsFrame.this);
                            messageIncorrecto.add(panel);
                            messageIncorrecto.setVisible(true);
                    }
                }
            });
            popup.add(item);
            boton.setComponentPopupMenu(popup);
            boton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    PatternsFrame.this.owner.loadPattern(pattern.getPosiciones());
                    PatternsFrame.this.dispose();
                }
            });
            lamina.add(boton);
            i++;
        }
    }

    public static void main() {
        ArrayList<Pattern> p = new ArrayList();
        p.add(new Pattern("Patron 1", null));
        p.add(new Pattern("Patron 2", null));
        p.add(new Pattern("Patron 3", null));
        p.add(new Pattern("Patron 4", null));
        p.add(new Pattern("Patron 5", null));
        p.add(new Pattern("Patron 6", null));
        p.add(new Pattern("Patron 7", null));
        p.add(new Pattern("Patron 8", null));
        p.add(new Pattern("Patron 9", null));
        p.add(new Pattern("Patron 10", null));
        p.add(new Pattern("Patron 11", null));
        p.add(new Pattern("Patron 12", null));
        p.add(new Pattern("Patron 13", null));
        p.add(new Pattern("Patron 14", null));
        p.add(new Pattern("Patron 15", null));

        new PatternsFrame(null, p);
    }
}
