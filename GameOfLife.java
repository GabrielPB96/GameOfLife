
/**
 * Write a description of class GameOfLife here.
 * https://github.com/GabrielPB96/GameOfLife.git
 * @author (Pantoja Gabriel)
 * @version (1.1 08-12-2021)
 */
import visual.*;
import javax.swing.*;
public class GameOfLife {
    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
          public void run() {
            //Bienvenida b = new Bienvenida();
            Visual v = new Visual();
            /*ible(false);
            if(b.isVisible()) {
                b.dispose();
                v.setVisible(true);
            }else{
                v.dispose();
            }*/
          }
        });
    }
}
