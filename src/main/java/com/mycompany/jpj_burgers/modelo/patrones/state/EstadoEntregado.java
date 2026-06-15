package com.mycompany.jpj_burgers.modelo.patrones.state;

import com.mycompany.jpj_burgers.modelo.dominio.Pedido;

public class EstadoEntregado implements EstadoPedido {

    @Override
    public String obtenerNombre() {
        return "ENTREGADO";
    }

    @Override
    public EstadoPedido avanzar(Pedido pedido) {
        return this;
    }
}
