package com.pf.scoringsalud.puntuable.EstrategiaMedicion;

import com.pf.scoringsalud.puntuable.Actividad;
import com.pf.scoringsalud.puntuable.Medidor.Acelerometro;
import com.pf.scoringsalud.puntuable.Medidor.Medible;

public class EstrategiaEjerRun_Acelerometro extends EstrategiaEjerRun {

    private String eje;
    private Acelerometro acelerometro;

    public EstrategiaEjerRun_Acelerometro(Actividad a) {
        super(a);
        setAcelerometro();
        setEje();
    }

    private void setAcelerometro(){
        for (Medible m:a.getMedidores()) {
            if(m instanceof Acelerometro)
                acelerometro=(Acelerometro)m;
        }
    }

    private void setEje(){
        if(acelerometro.getPosicionUno()[0] != acelerometro.getPosicionDos()[0]){
            this.eje = "X";
        }
        if(acelerometro.getPosicionUno()[1] != acelerometro.getPosicionDos()[1]){
            this.eje = "Y";
        }
        if(acelerometro.getPosicionUno()[2] != acelerometro.getPosicionDos()[2]){
            this.eje = "Z";
        }
    }



    @Override
    public boolean posicionCorrecta(double x, double y, double z, int numero) {
        boolean posicionCorrecta = false;
        switch (eje) {
            case "X":
                if (x > acelerometro.getPosicionUno()[0] && numero == 0) {
                    posicionCorrecta = true;
                }
                if (x < acelerometro.getPosicionDos()[0] && numero == 1) {
                    posicionCorrecta = true;
                }
                break;
            case "Y":
                if (y > acelerometro.getPosicionUno()[1] && numero == 0) {
                    posicionCorrecta = true;
                }
                if (y < acelerometro.getPosicionDos()[1] && numero == 1) {
                    posicionCorrecta = true;
                }

                break;
            case "Z":
                if (z > acelerometro.getPosicionUno()[2] && numero == 0) {
                    posicionCorrecta = true;
                }
                if (z < acelerometro.getPosicionDos()[2] && numero == 1) {
                    posicionCorrecta = true;
                }

                break;
            default:
                posicionCorrecta = true;
                break;
        }
        return posicionCorrecta;
    }

    @Override
    public boolean posicionInicio(double x, double y, double z) {
        boolean posicionInicio = false;
        switch (eje) {
            case "X":
                if ( (y>=acelerometro.getPosicionUno()[1]-2 && y <= acelerometro.getPosicionUno()[1]+2) &&
                        (z>=acelerometro.getPosicionUno()[2]-2 && z <= acelerometro.getPosicionUno()[2]+2) ) {
                    posicionInicio = true;
                }
                break;
            case "Y":
                if ( (x>=acelerometro.getPosicionUno()[0]-2 && x <= acelerometro.getPosicionUno()[0]+2) &&
                        (z>=acelerometro.getPosicionUno()[2]-2 && z <= acelerometro.getPosicionUno()[2]+2) ) {
                    posicionInicio = true;
                }

                break;
            case "Z":
                if ( (x>=acelerometro.getPosicionUno()[0]-2 && x <= acelerometro.getPosicionUno()[0]+2) &&
                        (y>=acelerometro.getPosicionUno()[1]-2 && y <= acelerometro.getPosicionUno()[1]+2) ) {
                    posicionInicio = true;
                }

                break;
            default:
                posicionInicio = false;
                break;
        }
        return posicionInicio;
    }
}
