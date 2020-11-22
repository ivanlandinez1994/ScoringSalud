package com.pf.scoringsalud.puntuable;

import com.pf.scoringsalud.puntuable.medidor.Acelerometro;
import com.pf.scoringsalud.puntuable.medidor.Contador;
import com.pf.scoringsalud.puntuable.medidor.Medible;
import com.pf.scoringsalud.puntuable.medidor.Proximity;

import java.util.ArrayList;

public class Actividad extends Puntuable {

    protected String articulacion;
    protected boolean PosicionUnica;
    protected int repeticiones;
    protected int repeticionesRealizadas;
    protected ArrayList<Medible> medidores;
    protected String rutaGif;
    public Actividad(String codigo, String nombre, int puntosOtorgables,String articulacion,
                     String rutaGif, String descripcion,boolean PosicionUnica,int repeticiones) {
        super(codigo, nombre,puntosOtorgables,descripcion);
        this.articulacion=articulacion;
        this.PosicionUnica=PosicionUnica;
        this.rutaGif = rutaGif;
        setRepeticiones(repeticiones);
        setRepeticionesRealizadas(0);
    }

    public Actividad(String codigo, String nombre, int puntosOtorgables,String articulacion,
                     String rutaGif, String descripcion,boolean PosicionUnica,int repeticiones, ArrayList<Medible> medidores) {
        super(codigo, nombre,puntosOtorgables,descripcion);
        this.articulacion = articulacion;
        this.PosicionUnica=PosicionUnica;
        setRepeticiones(repeticiones);
        setRepeticionesRealizadas(0);
        this.medidores = medidores;
        this.rutaGif = rutaGif;

    }


    @Override
    public String getDetalle() {
        return "Actividad: "+getNombre() + " Repeticiones: " + getRepeticiones() +" Tiempo total: "+ getTiempoTotal_String() ;
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

    private String getTiempoTotal_String(){
        String tiempo="No usa Contador";
        for (Medible m: medidores) {
            if(m instanceof Contador){
                tiempo =String.valueOf(((Contador)m).getDuracionSegundos()*getRepeticiones());
            }
        }
        return tiempo;
    }

    public void setArticulacion(String articulacion) {
        this.articulacion = articulacion;
    }

    public String getRutaGif() {
        return rutaGif;
    }

    public void setRutaGif(String rutaGif) {
        this.rutaGif = rutaGif;
    }
}
