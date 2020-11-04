package com.pf.scoringsalud.Puntuable.Medidor;


public class Acelerometro implements Medible {


    protected int [] posicionUno;
    protected int [] posicionDos;

    public Acelerometro(int[] posicionUno) {
        this.posicionUno = posicionUno;
    }

    public Acelerometro(int[] posicionUno, int[] posicionDos) {
        this.posicionUno = posicionUno;
        this.posicionDos = posicionDos;
    }

    //Getters / Setters
    public int[] getPosicionUno() {
        return posicionUno;
    }

    public void setPosicionUno(int[] posicionUno) {
        this.posicionUno = posicionUno;
    }

    public int[] getPosicionDos() {
        return posicionDos;
    }

    public void setPosicionDos(int[] posicionDos) {
        this.posicionDos = posicionDos;
    }


    // Get Tipo
    @Override
    public String getTipo() {
        return "Acelerometro";
    }
}
