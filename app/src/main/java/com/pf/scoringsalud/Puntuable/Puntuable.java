package com.pf.scoringsalud.Puntuable;


import java.time.LocalDateTime;

public abstract class Puntuable {

    protected   String codigo;
    protected   String nombre;
    protected   String articulacion;
    protected   int puntosOtorgables;
    protected   int puntosObtenidos;
    protected   LocalDateTime fecha;
    protected   String rutaGif;
    protected   String descripcion;
    protected   String detalle;


    public Puntuable(String codigo, String nombre, int puntosOtorgables,String articulacion,
                     String rutaGif, String descripcion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.puntosOtorgables = puntosOtorgables;
        this.articulacion=articulacion;
        this.rutaGif=rutaGif;
        this.descripcion=descripcion;
        fecha = LocalDateTime.now();
    }

    public abstract void calcularPuntos();
    public abstract String getDetalle();

    //getters / setters
    public int getPuntosOtorgables() {
        return puntosOtorgables;
    }

    public void setPuntosOtorgables(int puntosOtorgables) {
        this.puntosOtorgables = puntosOtorgables;
    }

    public int getPuntosObtenidos() {
        return puntosObtenidos;
    }

    public void setPuntosObtenidos(int puntosObtenidos) {
        this.puntosObtenidos = puntosObtenidos;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {

        this.fecha = fecha;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getArticulacion() {
        return articulacion;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}