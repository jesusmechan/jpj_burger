package com.mycompany.jpj_burgers.modelo.principios;

import com.mycompany.jpj_burgers.modelo.dominio.Pedido;
import java.util.List;
import java.util.Optional;

public interface IRepositorioPedidos {

    void guardar(Pedido pedido);

    Optional<Pedido> buscarPorId(String id);

    List<Pedido> listarTodos();
}
