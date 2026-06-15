package com.mycompany.jpj_burgers.modelo.patrones.strategy;

import com.mycompany.jpj_burgers.modelo.principios.IProcesadorPago;

public class PagoEfectivo implements IProcesadorPago {

    @Override
    public boolean procesarPago(double monto) {
        return monto > 0;
    }

    @Override
    public String obtenerNombreMetodo() {
        return "Efectivo";
    }
}
