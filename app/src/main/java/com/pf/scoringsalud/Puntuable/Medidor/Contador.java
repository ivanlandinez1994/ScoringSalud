package com.pf.scoringsalud.Puntuable.Medidor;


public class Contador implements Medible {
    protected long tiempo;

    public Contador(long tiempo){
        this.tiempo=tiempo;
    }

    @Override
    public String getTipo() {
        return "Contador";
    }

    public long getTiempo() {
        return tiempo;
    }

    public void setTiempo(long tiempo) {
        this.tiempo = tiempo;
    }
}
