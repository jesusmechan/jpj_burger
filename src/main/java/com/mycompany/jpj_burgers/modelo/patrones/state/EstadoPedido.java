package com.mycompany.jpj_burgers.modelo.patrones.state;

import com.mycompany.jpj_burgers.modelo.dominio.Pedido;

public interface EstadoPedido {

    String obtenerNombre();

    EstadoPedido avanzar(Pedido pedido);
}
