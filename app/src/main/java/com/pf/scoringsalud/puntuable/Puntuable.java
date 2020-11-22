package com.pf.scoringsalud.puntuable;



public abstract class Puntuable {

    protected   String codigo;
    protected   String nombre;
    protected   int unidadesOtorgables;
    protected   String descripcion;


    public Puntuable(String codigo, String nombre, int unidadesOtorgables,
                      String descripcion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.unidadesOtorgables = unidadesOtorgables;
        this.descripcion=descripcion;
    }
    public abstract String getDetalle();

    //getters / setters

    public String getNombre() {
        return nombre;
    }

    public int getUnidadesOtorgables() {
        return unidadesOtorgables;
    }

    public String getDescripcion() {
        return descripcion;
    }
}