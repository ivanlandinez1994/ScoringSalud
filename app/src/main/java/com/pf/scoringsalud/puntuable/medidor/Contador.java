package com.pf.scoringsalud.puntuable.medidor;


public class Contador implements com.pf.scoringsalud.puntuable.medidor.Medible {
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
