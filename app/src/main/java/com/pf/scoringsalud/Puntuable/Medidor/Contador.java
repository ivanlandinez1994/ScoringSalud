package com.pf.scoringsalud.Puntuable.Medidor;


public class Contador implements Medible {
    private int tiempo;

    public Contador(int tiempo){
        this.tiempo=tiempo;
    }

    @Override
    public String getTipo() {
        return "Contador";
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public long getDuracionSegundos(){

        return tiempo/1000;
    }

}
