package com.pf.scoringsalud.puntuable.medidor;


public class Acelerometro implements Medible {


    protected double [] posicionUno;
    protected double [] posicionDos;

    public Acelerometro(double[] posicionUno) {
        this.posicionUno = posicionUno;
    }

    public Acelerometro(double[] posicionUno, double[] posicionDos) {
        this.posicionUno = posicionUno;
        this.posicionDos = posicionDos;
    }

    //Getters / Setters
    public double[] getPosicionUno() {
        return posicionUno;
    }

    public void setPosicionUno(double[] posicionUno) {
        this.posicionUno = posicionUno;
    }

    public double[] getPosicionDos() {
        return posicionDos;
    }

    public void setPosicionDos(double[] posicionDos) {
        this.posicionDos = posicionDos;
    }


    // Get Tipo
    @Override
    public String getTipo() {
        return "Acelerometro";
    }
}
