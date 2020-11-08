package com.pf.scoringsalud.puntuable;

import com.pf.scoringsalud.puntuable.Medidor.Acelerometro;
import com.pf.scoringsalud.puntuable.Medidor.Contador;
import com.pf.scoringsalud.puntuable.Medidor.Medible;
import com.pf.scoringsalud.puntuable.Medidor.Proximity;

import java.util.ArrayList;

public class Actividad extends Puntuable {

    protected   String articulacion;
    protected boolean PosicionUnica;
    protected int repeticiones;
    protected int repeticionesRealizadas;
    protected ArrayList<Medible> medidores;
    protected   int rutaGif;
    public Actividad(String codigo, String nombre, int puntosOtorgables,String articulacion,
                     int rutaGif, String descripcion,boolean PosicionUnica,int repeticiones) {
        super(codigo, nombre,puntosOtorgables,descripcion);
        this.articulacion=articulacion;
        this.PosicionUnica=PosicionUnica;
        this.rutaGif = rutaGif;
        setRepeticiones(repeticiones);
        setRepeticionesRealizadas(0);
    }
    public Actividad(String codigo, String nombre, int puntosOtorgables,String articulacion,
                     int rutaGif, String descripcion,boolean PosicionUnica,int repeticiones, ArrayList<Medible> medidores) {
        super(codigo, nombre,puntosOtorgables,descripcion);
        this.articulacion = articulacion;
        this.PosicionUnica=PosicionUnica;
        setRepeticiones(repeticiones);
        setRepeticionesRealizadas(0);
        this.medidores = medidores;
        this.rutaGif = rutaGif;
    }

    @Override
    public void calcularPuntos() {
    };

    @Override
    public String getDetalle() {
        return null;
    };

    //getters / setters
    public void setRepeticiones(int repeticiones) {

        if(PosicionUnica) {
            this.repeticiones = repeticiones;
        }else {
            this.repeticiones = repeticiones + repeticiones;
        }

    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public int getRepeticionesRealizadas() {
        return repeticionesRealizadas;
    }

    public void setRepeticionesRealizadas(int repeticionesRealizadas) {
        this.repeticionesRealizadas = repeticionesRealizadas;
    }

    public boolean isPosicionUnica() {
        return PosicionUnica;
    }

    public void setPosicionUnica(boolean posicionUnica) {
        PosicionUnica = posicionUnica;
    }
    public ArrayList<Medible>  getMedidores() {
        return medidores;
    }
    public void setMedidores(ArrayList<Medible> medidores) {
        this.medidores = medidores;
    }

    public boolean tieneContador(){
        boolean tiene = false;
        for (Medible m: medidores) {
            if(m instanceof Contador){
                tiene=true;
            }
        }
        return tiene;
    }
    public boolean tieneAcelerometro(){
        boolean tiene = false;
        for (Medible m: medidores) {
            if(m instanceof Acelerometro){
                tiene=true;
            }
        }
        return tiene;
    }
    public boolean tieneProximity(){
        boolean tiene = false;
        for (Medible m: medidores) {
            if(m instanceof Proximity){
                tiene=true;
            }
        }
        return tiene;
    }
    public String getArticulacion() {
        return articulacion;
    }

    public void setArticulacion(String articulacion) {
        this.articulacion = articulacion;
    }

    public int getRutaGif() {
        return rutaGif;
    }

    public void setRutaGif(int rutaGif) {
        this.rutaGif = rutaGif;
    }
}
