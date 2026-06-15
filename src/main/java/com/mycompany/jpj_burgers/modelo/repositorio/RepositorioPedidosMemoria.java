package com.mycompany.jpj_burgers.modelo.repositorio;

import com.mycompany.jpj_burgers.modelo.dominio.Pedido;
import com.mycompany.jpj_burgers.modelo.principios.IRepositorioPedidos;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RepositorioPedidosMemoria implements IRepositorioPedidos {

    private final Map<String, Pedido> pedidos = new LinkedHashMap<>();

    @Override
    public void guardar(Pedido pedido) {
        pedidos.put(pedido.obtenerId(), pedido);
    }

    @Override
    public Optional<Pedido> buscarPorId(String id) {
        return Optional.ofNullable(pedidos.get(id));
    }

    @Override
    public List<Pedido> listarTodos() {
        return new ArrayList<>(pedidos.values());
    }
}
