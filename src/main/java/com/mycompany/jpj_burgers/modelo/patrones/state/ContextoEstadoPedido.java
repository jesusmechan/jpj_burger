package com.mycompany.jpj_burgers.modelo.patrones.state;

import com.mycompany.jpj_burgers.modelo.dominio.Pedido;

public class ContextoEstadoPedido {

    public EstadoPedido resolverEstado(Pedido pedido) {
        return switch (pedido.obtenerEstado()) {
            case "PENDIENTE" -> new EstadoPendiente();
            case "EN PREPARACIÓN" -> new EstadoPreparacion();
            case "LISTO" -> new EstadoListo();
            case "ENTREGADO" -> new EstadoEntregado();
            default -> new EstadoPendiente();
        };
    }

    public boolean avanzar(Pedido pedido) {
        EstadoPedido actual = resolverEstado(pedido);
        if (actual instanceof EstadoEntregado) {
            return false;
        }
        actual.avanzar(pedido);
        return true;
    }
}
