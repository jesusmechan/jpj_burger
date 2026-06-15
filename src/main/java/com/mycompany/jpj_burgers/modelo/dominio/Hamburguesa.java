package com.mycompany.jpj_burgers.modelo.dominio;

public class Hamburguesa {

    private final String nombre;
    private final String descripcion;
    private final double precioBase;

    public Hamburguesa(String nombre, String descripcion, double precioBase) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioBase = precioBase;
    }

    public String obtenerNombre() {
        return nombre;
    }

    public String obtenerDescripcion() {
        return descripcion;
    }

    public double obtenerPrecioBase() {
        return precioBase;
    }
}
