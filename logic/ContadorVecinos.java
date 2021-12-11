package logic;

public class ContadorVecinos {
    private Celula celula;
    private boolean infinite;
    private int cantidad;
    
    public ContadorVecinos(Celula celula) {
        this.celula = celula;
        infinite = true;
    }
    
    public void setInfinite(boolean b) {
        infinite = b;
    }
    
    public int cantidadVecinosVivos(Celula[][] celulas) {
        int fila = celula.getFila();
        int columna = celula.getColumna();
        return contarVecinos(fila, columna, celulas, true);
    }
    
    private int contarVecinos(int fila, int columna, Celula[][] celulas, boolean vivos) {
        if(infinite) {
            contarVecinosInfinite(fila, columna, celulas, vivos);
        }else{
            contarVecinosFinite(fila, columna, celulas, vivos);
        }
        return cantidad;
    }
    
    private void contarVecinosFinite(int fila, int columna, Celula[][] celulas, boolean vivos) {
        cantidad = 0;
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
        
        if(!vivos) cantidad =  8 - cantidad;
    }
    
    private void contarVecinosInfinite(int fila, int columna, Celula[][] celulas, boolean vivos) {
        cantidad = 0;
        int fila_real, columna_real;

        fila_real = filaReal(fila - 1, celulas.length-1);
        if(celulas[fila_real][columna].vivo()) cantidad++;

        columna_real = columnaReal(columna + 1, celulas[0].length-1);
        if(celulas[fila][columna_real].vivo()) cantidad++; 

        fila_real = filaReal(fila + 1, celulas.length-1);
        if(celulas[fila_real][columna].vivo()) cantidad++; 

        columna_real = columnaReal(columna - 1, celulas[0].length-1);
        if(celulas[fila][columna_real].vivo()) cantidad++; 

        fila_real = filaReal(fila - 1, celulas.length-1);
        columna_real = columnaReal(columna + 1, celulas[0].length-1);
        if(celulas[fila_real][columna_real].vivo()) cantidad++; 

        fila_real = filaReal(fila + 1, celulas.length-1);
        columna_real = columnaReal(columna - 1, celulas[0].length-1);
        if(celulas[fila_real][columna_real].vivo()) cantidad++; 

        fila_real = filaReal(fila + 1, celulas.length-1);
        columna_real = columnaReal(columna + 1, celulas[0].length-1);
        if(celulas[fila_real][columna_real].vivo()) cantidad++; 

        fila_real = filaReal(fila - 1, celulas.length-1);
        columna_real = columnaReal(columna - 1, celulas[0].length-1);
        if(celulas[fila_real][columna_real].vivo()) cantidad++; 
        
        if(!vivos) cantidad = 8 - cantidad;
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
    
    private int filaReal(int fila, int limite) {
        if (fila < 0) {
            return limite;
        }
        if (fila > limite) {
            return 0;
        }
        return fila;
    }
    
    private int columnaReal(int colum, int limite) {
        if (colum < 0) {
            return limite;
        }
        if (colum > limite) {
            return 0;
        }
        return colum;
    }
}
