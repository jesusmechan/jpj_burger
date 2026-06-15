package com.mycompany.jpj_burgers.modelo.patrones.observer;

import com.mycompany.jpj_burgers.modelo.dominio.Pedido;
import java.util.ArrayList;
import java.util.List;

public class SujetoPedido {

    private final List<ObservadorPedido> observadores = new ArrayList<>();

    public void suscribir(ObservadorPedido observador) {
        observadores.add(observador);
    }

    public void desuscribir(ObservadorPedido observador) {
        observadores.remove(observador);
    }

    public void notificar(Pedido pedido, String mensaje) {
        for (ObservadorPedido observador : observadores) {
            observador.alActualizarPedido(pedido, mensaje);
        }
    }
}
