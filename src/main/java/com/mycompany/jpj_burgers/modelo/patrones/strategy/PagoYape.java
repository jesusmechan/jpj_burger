package com.mycompany.jpj_burgers.modelo.patrones.strategy;

import com.mycompany.jpj_burgers.modelo.principios.IProcesadorPago;

public class PagoYape implements IProcesadorPago {

    @Override
    public boolean procesarPago(double monto) {
        return monto > 0;
    }

    @Override
    public String obtenerNombreMetodo() {
        return "Yape";
    }
}
