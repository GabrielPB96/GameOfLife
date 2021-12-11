package logic;
import java.io.Serializable;

public class Posicion implements Serializable{
    private int fila;
    private int columna;
    
    public Posicion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }
    
    public int getFila() {
        return fila;
    }
    
    public int getColumna() {
        return columna;
    }
}
