package com.mycompany.jpj_burgers.modelo.principios;

import com.mycompany.jpj_burgers.modelo.dominio.Pedido;

public class CalculadorPrecio implements ICalculadorPrecio {

    @Override
    public double calcularTotal(Pedido pedido) {
        return pedido.obtenerTotal();
    }
}
