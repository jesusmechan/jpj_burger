package com.mycompany.jpj_burgers.modelo.patrones.state;

import com.mycompany.jpj_burgers.modelo.dominio.Pedido;

public class EstadoListo implements EstadoPedido {

    @Override
    public String obtenerNombre() {
        return "LISTO";
    }

    @Override
    public EstadoPedido avanzar(Pedido pedido) {
        pedido.establecerEstado("ENTREGADO");
        return new EstadoEntregado();
    }
}
