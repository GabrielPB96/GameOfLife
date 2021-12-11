package visual;
import javax.swing.JMenuItem;
import javax.swing.JButton;


/**
 * Write a description of class Item here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Item extends JMenuItem {
    private int index;
    private JButton boton;
    
    public Item (String name, int index, JButton boton) {
        super(name);
        this.index = index;
        this.boton = boton;
    }
    
    public int getIndex() {
        return index;
    }
    
    public JButton getButton() {
        return boton;
    }
}
