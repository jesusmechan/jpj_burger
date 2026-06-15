package com.mycompany.jpj_burgers.modelo.patrones.command;

import com.mycompany.jpj_burgers.modelo.dominio.Pedido;
import com.mycompany.jpj_burgers.modelo.patrones.facade.FachadaPedidos;

public class ComandoRegistrarPedido implements ComandoPedido {

    private final FachadaPedidos fachada;
    private final Pedido pedido;
    private final String metodoPago;

    public ComandoRegistrarPedido(FachadaPedidos fachada, Pedido pedido, String metodoPago) {
        this.fachada = fachada;
        this.pedido = pedido;
        this.metodoPago = metodoPago;
    }

    @Override
    public boolean ejecutar() {
        return fachada.registrarPedido(pedido, metodoPago);
    }

    @Override
    public String obtenerDescripcion() {
        return "Registrar pedido de " + pedido.obtenerNombreCliente();
    }
}
