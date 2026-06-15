package com.mycompany.jpj_burgers.modelo.dominio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Pedido {

    private final String id;
    private final String nombreCliente;
    private final List<ItemPedido> items;
    private final LocalDateTime fechaCreacion;
    private String estado;
    private String metodoPago;

    public Pedido(String nombreCliente) {
        this.id = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.nombreCliente = nombreCliente;
        this.items = new ArrayList<>();
        this.fechaCreacion = LocalDateTime.now();
        this.estado = "PENDIENTE";
    }

    public Pedido(
            String id,
            String nombreCliente,
            LocalDateTime fechaCreacion,
            String estado,
            String metodoPago,
            List<ItemPedido> items
    ) {
        this.id = id;
        this.nombreCliente = nombreCliente;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
        this.metodoPago = metodoPago;
        this.items = new ArrayList<>(items);
    }

    public String obtenerId() {
        return id;
    }

    public String obtenerNombreCliente() {
        return nombreCliente;
    }

    public List<ItemPedido> obtenerItems() {
        return Collections.unmodifiableList(items);
    }

    public LocalDateTime obtenerFechaCreacion() {
        return fechaCreacion;
    }

    public String obtenerEstado() {
        return estado;
    }

    public void establecerEstado(String estado) {
        this.estado = estado;
    }

    public String obtenerMetodoPago() {
        return metodoPago;
    }

    public void establecerMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public void agregarItem(ItemPedido item) {
        items.add(item);
    }

    public double obtenerTotal() {
        return items.stream().mapToDouble(ItemPedido::obtenerSubtotal).sum();
    }
}
