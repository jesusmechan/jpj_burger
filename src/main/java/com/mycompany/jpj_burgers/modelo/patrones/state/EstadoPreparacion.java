package com.mycompany.jpj_burgers.modelo.patrones.state;

import com.mycompany.jpj_burgers.modelo.dominio.Pedido;

public class EstadoPreparacion implements EstadoPedido {

    @Override
    public String obtenerNombre() {
        return "EN PREPARACIÓN";
    }

    @Override
    public EstadoPedido avanzar(Pedido pedido) {
        pedido.establecerEstado("LISTO");
        return new EstadoListo();
    }
}
