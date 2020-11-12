package com.pf.scoringsalud.puntuable.estrategiaMedicion;

import com.pf.scoringsalud.puntuable.Actividad;
import com.pf.scoringsalud.puntuable.medidor.Medible;
import com.pf.scoringsalud.puntuable.medidor.Proximity;

public class EstrategiaEjerRun_Proximity extends EstrategiaEjerRun {
    private Proximity proximity;

    public EstrategiaEjerRun_Proximity(Actividad a) {
        super(a);
        getProximity();

    }

    private Proximity getProximity(){
        Proximity p = null;
        for (Medible m:a.getMedidores()) {
            if(m instanceof Proximity)
                p=(Proximity)m;
        }
        return p;
    }

    @Override
    public boolean posicionCorrecta(double x, double y, double z, int num) {
        boolean posicionCorrecta = false;
        if(proximity.getTapado() == x)
            posicionCorrecta = true;
        return false;
    }

    @Override
    public boolean posicionInicio(double x, double y, double z) {
        boolean posicionCorrecta = false;
        if(proximity.getTapado() == x)
            posicionCorrecta = true;
        return false;
    }
}
