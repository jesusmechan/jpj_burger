package com.mycompany.jpj_burgers.modelo.patrones.decorator;

import com.mycompany.jpj_burgers.modelo.dominio.Hamburguesa;

public abstract class DecoradorHamburguesa {

    protected final Hamburguesa hamburguesaBase;
    protected final String nombreExtra;
    protected final double precioExtra;

    protected DecoradorHamburguesa(Hamburguesa hamburguesaBase, String nombreExtra, double precioExtra) {
        this.hamburguesaBase = hamburguesaBase;
        this.nombreExtra = nombreExtra;
        this.precioExtra = precioExtra;
    }

    public String obtenerDescripcionCompleta() {
        return hamburguesaBase.obtenerDescripcion() + " + " + nombreExtra;
    }

    public double obtenerPrecioTotal() {
        return hamburguesaBase.obtenerPrecioBase() + precioExtra;
    }

    public String obtenerNombre() {
        return hamburguesaBase.obtenerNombre();
    }
}
