package com.mycompany.jpj_burgers.modelo.patrones.singleton;

import com.mycompany.jpj_burgers.modelo.dominio.Pedido;
import com.mycompany.jpj_burgers.modelo.patrones.observer.SujetoPedido;
import com.mycompany.jpj_burgers.modelo.principios.IRepositorioPedidos;
import com.mycompany.jpj_burgers.modelo.repositorio.RepositorioPedidosSQL;

public class GestorPedidos {

    private static GestorPedidos instancia;

    private final IRepositorioPedidos repositorio;
    private final SujetoPedido sujetoPedido;

    private GestorPedidos() {
        this.repositorio = new RepositorioPedidosSQL();
        this.sujetoPedido = new SujetoPedido();
    }

    public static synchronized GestorPedidos obtenerInstancia() {
        if (instancia == null) {
            instancia = new GestorPedidos();
        }
        return instancia;
    }

    public IRepositorioPedidos obtenerRepositorio() {
        return repositorio;
    }

    public SujetoPedido obtenerSujetoPedido() {
        return sujetoPedido;
    }

    public void publicarActualizacion(Pedido pedido, String mensaje) {
        sujetoPedido.notificar(pedido, mensaje);
    }
}
