package com.mycompany.jpj_burgers.modelo.patrones.observer;

import com.mycompany.jpj_burgers.modelo.dominio.Pedido;

public interface ObservadorPedido {

    void alActualizarPedido(Pedido pedido, String mensaje);
}
