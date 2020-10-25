package com.pf.scoringsalud.Puntuable;

import com.pf.scoringsalud.Puntuable.Medidor.Contador;
import com.pf.scoringsalud.Puntuable.Medidor.Medible;

public class Actividad extends Puntuable {

    protected boolean PosicionUnica;
    protected int repeticiones;
    protected int repeticionesRealizadas;
    protected Medible[] medidores;

    public Actividad(String codigo, String nombre, int puntosOtorgables,String articulacion,
                     String rutaGif, String descripcion,boolean PosicionUnica,int repeticiones) {
        super(codigo, nombre,puntosOtorgables,articulacion,rutaGif,descripcion);
        this.PosicionUnica=PosicionUnica;
        setRepeticiones(repeticiones);
        setRepeticionesRealizadas(0);
    }
    public Actividad(String codigo, String nombre, int puntosOtorgables,String articulacion,
                     String rutaGif, String descripcion,boolean PosicionUnica,int repeticiones, Medible[] medidores) {
        super(codigo, nombre,puntosOtorgables,articulacion,rutaGif,descripcion);
        this.PosicionUnica=PosicionUnica;
        setRepeticiones(repeticiones);
        setRepeticionesRealizadas(0);
        this.medidores = medidores;
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
    public Medible[]  getMedidores() {
        return medidores;
    }
    public void setMedidores(Medible[] medidores) {
        this.medidores = medidores;
    }

    public long getDuracionSegundos(){
        long tiempo=0;
        for(int i =0;i<medidores.length;i++){
            if(medidores[i]instanceof Contador){
                tiempo = (((Contador)medidores[i]).getTiempo())/1000;
            }
        }
        return tiempo;
    }

    public long getDuracionTotalSegundos(){
        long tiempo=0;
        for(int i =0;i<medidores.length;i++){
            if(medidores[i]instanceof Contador){
                tiempo = (this.repeticiones*((Contador)medidores[i]).getTiempo())/1000;
            }
        }
        return tiempo;
    }





}
