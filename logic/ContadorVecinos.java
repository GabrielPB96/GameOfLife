package logic;

public class ContadorVecinos{
    private Celula celula;
    
    public ContadorVecinos(Celula celula) {
        this.celula = celula;
    }
    
    public int cantidadVecinosVivos(Celula[][] celulas) {
        int fila = celula.getFila();
        int columna = celula.getColumna();
        return contarVecinos(fila, columna, celulas, true);
    }
    
    private int contarVecinos(int fila, int columna, Celula[][] celulas, boolean vivos) {
        int cantidad = 0;
        if(posicionValida(fila - 1, columna, celulas)) {
            if(celulas[fila - 1][columna].vivo()) cantidad++;
        }
        
        if(posicionValida(fila, columna + 1, celulas)) {
            if(celulas[fila][columna + 1].vivo()) cantidad++; 
        }
        
        if(posicionValida(fila + 1, columna, celulas)) {
            if(celulas[fila + 1][columna].vivo()) cantidad++; 
        }
        
        if(posicionValida(fila, columna - 1, celulas)) {
            if(celulas[fila][columna - 1].vivo()) cantidad++; 
        }
        
        if(posicionValida(fila - 1, columna + 1, celulas)) {
            if(celulas[fila - 1][columna + 1].vivo()) cantidad++; 
        }
        
        if(posicionValida(fila + 1, columna + 1, celulas)) {
            if(celulas[fila + 1][columna + 1].vivo()) cantidad++; 
        }
        
        if(posicionValida(fila + 1, columna - 1, celulas)) {
            if(celulas[fila + 1][columna - 1].vivo()) cantidad++; 
        }
        
        if(posicionValida(fila - 1, columna - 1, celulas)) {
            if(celulas[fila - 1][columna - 1].vivo()) cantidad++; 
        }
        
        if(!vivos) return 8 - cantidad;
        return cantidad;
    }
    
    private boolean posicionValida(int fila, int columna, Celula[][] celulas) {
        if(fila < 0 || fila > celulas.length - 1) {
            return false;
        }
        
        if(columna < 0 || columna > celulas[fila].length - 1) {
            return false;
        }
        
        return true;
    }
}
