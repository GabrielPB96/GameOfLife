package logic;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

/**
 * Write a description of class Celula here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Celula extends JButton implements MouseListener {
    private boolean vivo;
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
        setBackground(Color.BLACK);
        setCursor(new Cursor(12));
        addMouseListener(this);
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
        setBackground(Color.BLACK);
    }
    
    public void revivir() {
        vivo = true;
        if(vecinosVivosActuales >= 2 && vecinosVivosActuales <= 3) {
            if(tiempoEstatico < 10) {
                tiempoEstatico++;
            }
            if(tiempoEstatico == 10) {
                setBackground(new Color(34, 177, 76));
            }else if(tiempoEstatico >= 5) {
                    setBackground(new Color(181, 230, 29));
            }else{
                setBackground(Color.WHITE);
            }
        }else{
            tiempoEstatico = 0;
            setBackground(Color.WHITE);
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
    
    public void mouseClicked(MouseEvent e){
        vivo = !vivo;
        if(vivo) {
            setBackground(Color.WHITE);
        }else{
            setBackground(Color.BLACK);
        }
    }
    
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
}
