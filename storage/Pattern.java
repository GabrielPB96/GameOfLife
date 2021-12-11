package storage;
import logic.Tablero;
import java.io.Serializable;
import java.util.*;

/**
 * Write a description of class Pattern here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Pattern implements Serializable{
    private String name;
    private ArrayList<int[]> posiciones;
    
    public Pattern (String name, ArrayList<int[]> posiciones) {
        this.name  = name;
        this.posiciones = posiciones;
    }
    
    public String getName() {
        return name;
    }
    
    public ArrayList<int[]> getPosiciones () {
        return posiciones;
    }
    
    public boolean equals(Object o) {
        if (o instanceof Pattern) {
            Pattern q = (Pattern)o;
            return name.equals(q.name);
        }
        return false;
    }
}
