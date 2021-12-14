package logic;

/**
 * Write a description of class Tablero here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.awt.Color;
import java.util.ArrayList;

public class Tablero {
    private Celula[][] celulas;
    private ArrayList<Celula> celulasVivas;
    
    public Tablero(int filas, int columnas) {
        celulas = new Celula[filas][columnas];
        celulasVivas = new ArrayList<Celula>();
        init();
    }
    
    private void init() {
        for(int i=0; i<celulas.length; i++){
            for(int j=0; j<celulas[i].length; j++){
                celulas[i][j] = new Celula(i, j);
            }
        }
    }
    
    public void infinite(boolean b) {
        for(int i=0; i<celulas.length; i++){
            for(int j=0; j<celulas[i].length; j++){
                celulas[i][j].infinite(b);
            }
        }
    }
    
    public void reset() {
        for(int i=0; i<celulas.length; i++){
            for(int j=0; j<celulas[i].length; j++){
                celulas[i][j].dead();
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
        Thread game = new Thread(new Runnable(){
            public void run(){
                for(int i=0; i<celulas.length; i++){
                    for(int j=0; j<celulas[i].length; j++){
                        int vecinosVivos = celulas[i][j].vecinosVivos();
                        if(vecinosVivos < 2 || vecinosVivos > 3) {
                            celulas[i][j].dead();
                        }else if(celulas[i][j].vivo() && (vecinosVivos >= 2 && vecinosVivos <= 3)){
                            celulas[i][j].aumentarTime(); 
                        }else if(vecinosVivos == 3) {
                            celulas[i][j].revivir();
                        }
                    }
                }
                actualizarVecinos();
            }
        });
        game.start();
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
