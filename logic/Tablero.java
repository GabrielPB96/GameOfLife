package logic;

/**
 * Write a description of class Tablero here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.awt.Color;
import java.io.Serializable;

public class Tablero implements Serializable{
    private Celula[][] celulas;
    
    public Tablero(int filas, int columnas) {
        celulas = new Celula[filas][columnas];
        init();
    }
    
    private void init() {
        for(int i=0; i<celulas.length; i++){
            for(int j=0; j<celulas[i].length; j++){
                celulas[i][j] = new Celula(i, j);
            }
        }
    }
    
    public void reset() {
        for(int i=0; i<celulas.length; i++){
            for(int j=0; j<celulas[i].length; j++){
                celulas[i][j].dead();
                celulas[i][j].resetTimeLife();
            }
        }
    }
    
    public void establecerDisposicionInicial(Posicion[] p) {
        for(int i=0; i<p.length; i++) {
            celulas[p[i].getFila()][p[i].getColumna()].revivir();
        }
    }
    
    public void actualizarVecinos() {
        for(int i=0; i<celulas.length; i++){
            for(int j=0; j<celulas[i].length; j++){
                celulas[i][j].actualizarVecinosVivos(celulas);
            }
        }
    }
    
    public void play() {
        for(int i=0; i<celulas.length; i++){
            for(int j=0; j<celulas[i].length; j++){
                int vecinosVivos = celulas[i][j].vecinosVivos();
                if(vecinosVivos < 2 || vecinosVivos > 3 ) {
                    celulas[i][j].dead();        
                }
                if(!celulas[i][j].vivo() && vecinosVivos == 3){
                    celulas[i][j].revivir(); 
                }
            }
        }
        actualizarVecinos();
    }
    
    public Celula[][] getTablero() {
        return celulas;
    }
    
    public Tablero clone() {
        Celula[][] clone = new Celula[celulas.length][celulas[0].length];
        for(int i=0; i<celulas.length; i++){
            for(int j=0; j<celulas[i].length; j++){
                clone[i][j] = celulas[i][j].clone();
            }
        }
        Tablero cloneBoard = new Tablero(0, 0);
        cloneBoard.celulas = clone;
        return cloneBoard;
    }
    
    public int filas() {
        return celulas.length;
    }
    
    public int columnas() {
        return celulas[0].length;
    }
    
    public void revivir(int fila, int columna) {
        celulas[fila][columna].revivir();
    }
}
