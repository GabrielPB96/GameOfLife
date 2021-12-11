
/**
 * Write a description of class GameOfLife here.
 *
 * @author (Pantoja Gabriel)
 * @version (1.1 08-12-2021)
 */
import visual.*;
public class GameOfLife {
    public static void main(String args[]) {
        Bienvenida b = new Bienvenida();
        Visual v = new Visual();
        v.setVisible(false);
        if(b.isVisible()) {
            b.dispose();
            v.setVisible(true);
        }else{
            v.dispose();
        }
    }
}
