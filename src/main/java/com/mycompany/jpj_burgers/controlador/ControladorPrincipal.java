package com.mycompany.jpj_burgers.controlador;

import com.mycompany.jpj_burgers.modelo.patrones.facade.FachadaPedidos;
import com.mycompany.jpj_burgers.vista.VentanaPrincipal;

public class ControladorPrincipal {

    private final FachadaPedidos modelo;
    private final VentanaPrincipal ventana;

    public ControladorPrincipal() {
        this.modelo = new FachadaPedidos();
        this.ventana = new VentanaPrincipal();

        new ControladorPedido(modelo, ventana.getFormularioPedido());
        new ControladorCocina(modelo, ventana.getPanelCocina());
        new ControladorPersonalizar(ventana.getPanelPersonalizar());
    }

    public void iniciar() {
        ventana.mostrar();
    }
}
