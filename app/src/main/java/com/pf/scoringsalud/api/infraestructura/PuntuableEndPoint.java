package com.pf.scoringsalud.api.infraestructura;

public class PuntuableEndPoint {
    private String tipo;
    private String nombre;
    private int unidades;
    private String detalle;
    private String userMail;

    public PuntuableEndPoint(String tipo, String nombre, int unidades, String detalle, String userMail) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.unidades = unidades;
        this.detalle = detalle;
        this.userMail = userMail;
    }
}
