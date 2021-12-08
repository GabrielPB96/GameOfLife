package logic;

/**
 * Write a description of class Tablero here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.awt.Color;
public class Tablero{
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
                if(vecinosVivos == 3){
                    celulas[i][j].revivir(); 
                }
            }
        }
        actualizarVecinos();
    }
    
    public Celula[][] getTablero() {
        return celulas;
    }
}
