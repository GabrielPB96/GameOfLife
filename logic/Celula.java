package logic;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.geom.*;
/**
 * Write a description of class Celula here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Celula extends Rectangle2D.Double {
    public static boolean active = false;
    
    private boolean vivo;
    private Color color;
    private Posicion posicion;
    private ContadorVecinos contador;
    private int vecinosVivosActuales;
    private int vecinosVivosAntiguos;
    private int tiempoEstatico;
    
    public Celula(int fila, int columna) {
        posicion = new Posicion(fila, columna);
        vivo = false;
        vecinosVivosActuales = 0;
        vecinosVivosAntiguos = 0;
        tiempoEstatico = 0;
        color = Color.BLACK;
        contador = new ContadorVecinos(this);
    }
    
    public boolean vivo() {
        return vivo;
    }
    
    public void dead() {
        vivo = false;
        vecinosVivosActuales = 0;
        vecinosVivosAntiguos = 0;
        tiempoEstatico = 0;
        color = Color.BLACK;
    }
    
    public void revivir() {
        vivo = true;
        color = Color.WHITE;
    }
    
    public void aumentarTime() {
        if(tiempoEstatico < 10) {
            tiempoEstatico++;
        }
        if(tiempoEstatico == 10) {
            color = new Color(34, 177, 76);
        }else if(tiempoEstatico >= 5) {
            color = new Color(181, 230, 29);
        }else{
            color = Color.WHITE;
        }
    }
    
    public int getFila() {
        return posicion.getFila();
    }
    
    public int getColumna() {
        return posicion.getColumna();
    }
    
    public void actualizarVecinosVivos(Celula[][] celulas) {
        vecinosVivosAntiguos = vecinosVivosActuales;
        vecinosVivosActuales =  contador.cantidadVecinosVivos(celulas);
    }
    
    public int vecinosVivos(){
        return vecinosVivosActuales;
    }
    
    public int vecinosAntiguos() {
        return vecinosVivosAntiguos;
    }
    
    public Celula clone() {
        Celula clone = new Celula(posicion.getFila(), posicion.getColumna());
        clone.vivo = this.vivo();
        clone.vecinosVivosActuales = this.vecinosVivosActuales;
        clone.vecinosVivosAntiguos = this.vecinosVivosAntiguos;
        clone.tiempoEstatico = this.tiempoEstatico;
        return clone;
    }
    
    public void infinite(boolean b) {
        contador.setInfinite(b);
    }
    
    private void marcar() {
        vivo = !vivo;
        if(vivo) {
            revivir();
        }else{
            dead();
        }
    }
    
    public Color getColor() {
        return color;
    }
}
