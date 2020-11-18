package com.pf.scoringsalud.puntuable.estrategiaMedicion;

import com.pf.scoringsalud.puntuable.Actividad;

public abstract class EstrategiaEjerRun {
    Actividad a;
    public EstrategiaEjerRun(Actividad a){
        this.a=a;
    }

    public abstract boolean posicionCorrecta(double x, double y, double z, int num);
    public abstract boolean posicionInicio(double x, double y, double z);

}
