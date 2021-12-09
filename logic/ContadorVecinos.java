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
        
        if(!vivos) return 8 - cantidad;
        return cantidad;
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
