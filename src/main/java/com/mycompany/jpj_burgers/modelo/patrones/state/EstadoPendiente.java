package com.mycompany.jpj_burgers.modelo.patrones.state;

import com.mycompany.jpj_burgers.modelo.dominio.Pedido;

public class EstadoPendiente implements EstadoPedido {

    @Override
    public String obtenerNombre() {
        return "PENDIENTE";
    }

    @Override
    public EstadoPedido avanzar(Pedido pedido) {
        pedido.establecerEstado("EN PREPARACIÓN");
        return new EstadoPreparacion();
    }
}
