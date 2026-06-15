package com.mycompany.jpj_burgers.modelo.patrones.strategy;

import com.mycompany.jpj_burgers.modelo.principios.IProcesadorPago;
import java.util.LinkedHashMap;
import java.util.Map;

public class ContextoPago {

    private final Map<String, IProcesadorPago> estrategias = new LinkedHashMap<>();

    public ContextoPago() {
        estrategias.put("Efectivo", new PagoEfectivo());
        estrategias.put("Tarjeta", new PagoTarjeta());
        estrategias.put("Yape", new PagoYape());
    }

    public String[] obtenerMetodosDisponibles() {
        return estrategias.keySet().toArray(String[]::new);
    }

    public IProcesadorPago obtenerEstrategia(String metodo) {
        return estrategias.get(metodo);
    }
}
