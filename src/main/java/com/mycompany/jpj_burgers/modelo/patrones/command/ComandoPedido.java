package com.mycompany.jpj_burgers.modelo.patrones.command;

public interface ComandoPedido {

    boolean ejecutar();

    String obtenerDescripcion();
}
