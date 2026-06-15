package com.mycompany.jpj_burgers.modelo.principios;

public interface IProcesadorPago {

    boolean procesarPago(double monto);

    String obtenerNombreMetodo();
}
