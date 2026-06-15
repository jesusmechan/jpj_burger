package com.mycompany.jpj_burgers.modelo.principios;

import com.mycompany.jpj_burgers.modelo.dominio.Pedido;

public interface ICalculadorPrecio {

    double calcularTotal(Pedido pedido);
}
