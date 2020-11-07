package com.pf.scoringsalud.puntuable.EstrategiaMedicion;

import com.pf.scoringsalud.puntuable.Actividad;

public class MedicionEjerRun {

    private EstrategiaEjerRun estrategia;
    private Actividad a;
    public MedicionEjerRun(Actividad a){
        this.a=a;
        setEstrategia(a);
    }
    private void setEstrategia(Actividad a){
        if(a.tieneAcelerometro()){
            estrategia = new EstrategiaEjerRun_Acelerometro(a);
        }else if(a.tieneProximity()){
            estrategia = new EstrategiaEjerRun_Proximity(a);
        }
    }

    public EstrategiaEjerRun getEstrategia() {
        return estrategia;
    }
}
