package com.mycompany.jpj_burgers.modelo.principios;

import com.mycompany.jpj_burgers.modelo.dominio.Pedido;

public interface IValidadorPedido {

    boolean esValido(Pedido pedido);

    String obtenerMensajeValidacion(Pedido pedido);
}
