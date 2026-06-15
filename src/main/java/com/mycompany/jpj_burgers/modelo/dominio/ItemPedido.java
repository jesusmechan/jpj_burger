package com.mycompany.jpj_burgers.modelo.dominio;

public class ItemPedido {

    private final String descripcion;
    private final double precioUnitario;
    private final int cantidad;

    public ItemPedido(String descripcion, double precioUnitario, int cantidad) {
        this.descripcion = descripcion;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
    }

    public String obtenerDescripcion() {
        return descripcion;
    }

    public double obtenerPrecioUnitario() {
        return precioUnitario;
    }

    public int obtenerCantidad() {
        return cantidad;
    }

    public double obtenerSubtotal() {
        return precioUnitario * cantidad;
    }
}
