package com.mycompany.jpj_burgers.modelo.patrones.command;

import com.mycompany.jpj_burgers.modelo.patrones.facade.FachadaPedidos;

public class ComandoAvanzarPedido implements ComandoPedido {

    private final FachadaPedidos fachada;
    private final String idPedido;

    public ComandoAvanzarPedido(FachadaPedidos fachada, String idPedido) {
        this.fachada = fachada;
        this.idPedido = idPedido;
    }

    @Override
    public boolean ejecutar() {
        return fachada.avanzarEstadoPedido(idPedido);
    }

    @Override
    public String obtenerDescripcion() {
        return "Avanzar estado del pedido " + idPedido;
    }
}
