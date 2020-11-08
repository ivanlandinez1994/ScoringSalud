package com.pf.scoringsalud.puntuable.Medidor;


public class Proximity implements Medible {

    protected int tapado;

    public Proximity(){
        tapado =0 ;

    }

    public int getTapado() {
        return tapado;
    }

    @Override
    public String getTipo() {
        return "Proximity";
    }
}