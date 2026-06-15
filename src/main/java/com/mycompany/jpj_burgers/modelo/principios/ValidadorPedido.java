package com.mycompany.jpj_burgers.modelo.principios;

import com.mycompany.jpj_burgers.modelo.dominio.Pedido;

public class ValidadorPedido implements IValidadorPedido {

    @Override
    public boolean esValido(Pedido pedido) {
        return pedido.obtenerNombreCliente() != null
                && !pedido.obtenerNombreCliente().isBlank()
                && !pedido.obtenerItems().isEmpty();
    }

    @Override
    public String obtenerMensajeValidacion(Pedido pedido) {
        if (pedido.obtenerNombreCliente() == null || pedido.obtenerNombreCliente().isBlank()) {
            return "El nombre del cliente es obligatorio.";
        }
        if (pedido.obtenerItems().isEmpty()) {
            return "Debe agregar al menos un producto al pedido.";
        }
        return "Pedido válido.";
    }
}
