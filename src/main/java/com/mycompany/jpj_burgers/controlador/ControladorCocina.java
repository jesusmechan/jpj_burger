package com.mycompany.jpj_burgers.controlador;

import com.mycompany.jpj_burgers.modelo.dominio.Pedido;
import com.mycompany.jpj_burgers.modelo.patrones.command.ComandoAvanzarPedido;
import com.mycompany.jpj_burgers.modelo.patrones.facade.FachadaPedidos;
import com.mycompany.jpj_burgers.modelo.patrones.observer.ObservadorPedido;
import com.mycompany.jpj_burgers.vista.PanelCocina;

public class ControladorCocina implements ObservadorPedido {

    private final FachadaPedidos modelo;
    private final PanelCocina vista;

    public ControladorCocina(FachadaPedidos modelo, PanelCocina vista) {
        this.modelo = modelo;
        this.vista = vista;
        vista.asignarControlador(this);
        modelo.obtenerGestorPedidos().obtenerSujetoPedido().suscribir(this);
        actualizarVista();
    }

    public void avanzarPedido() {
        String idPedido = vista.obtenerIdPedidoSeleccionado();
        if (idPedido == null || idPedido.isBlank()) {
            vista.mostrarAdvertencia("Seleccione un pedido.");
            return;
        }

        ComandoAvanzarPedido comando = new ComandoAvanzarPedido(modelo, idPedido);
        if (comando.ejecutar()) {
            actualizarVista();
        } else {
            vista.mostrarInformacion("El pedido ya fue entregado o no existe.");
        }
    }

    public void actualizarVista() {
        vista.actualizarTabla(modelo.listarPedidos());
    }

    @Override
    public void alActualizarPedido(Pedido pedido, String mensaje) {
        vista.establecerEstado(mensaje);
        actualizarVista();
    }
}
