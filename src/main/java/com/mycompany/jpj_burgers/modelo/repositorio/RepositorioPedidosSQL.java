package com.mycompany.jpj_burgers.modelo.repositorio;

import com.mycompany.jpj_burgers.modelo.dominio.ItemPedido;
import com.mycompany.jpj_burgers.modelo.dominio.Pedido;
import com.mycompany.jpj_burgers.modelo.principios.IRepositorioPedidos;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RepositorioPedidosSQL implements IRepositorioPedidos {

    private final GestorConexion gestorConexion = GestorConexion.obtenerInstancia();

    @Override
    public void guardar(Pedido pedido) {
        try {
            if (existePedido(pedido.obtenerId())) {
                actualizarPedido(pedido);
            } else {
                insertarPedido(pedido);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Error al guardar el pedido en SQL Server.", e);
        }
    }

    @Override
    public Optional<Pedido> buscarPorId(String id) {
        try (Connection conexion = gestorConexion.obtenerConexion();
             CallableStatement procedimiento = conexion.prepareCall("{CALL dbo.sp_BuscarPedidoPorId(?)}")) {

            procedimiento.setString(1, id);

            try (ResultSet cabecera = procedimiento.executeQuery()) {
                if (!cabecera.next()) {
                    return Optional.empty();
                }

                Pedido pedido = mapearPedido(cabecera);
                cargarDetalle(conexion, pedido);
                return Optional.of(pedido);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Error al buscar el pedido " + id + ".", e);
        }
    }

    @Override
    public List<Pedido> listarTodos() {
        Map<String, Pedido> pedidos = new LinkedHashMap<>();

        try (Connection conexion = gestorConexion.obtenerConexion();
             CallableStatement procedimiento = conexion.prepareCall("{CALL dbo.sp_ListarPedidos}");
             ResultSet resultado = procedimiento.executeQuery()) {

            while (resultado.next()) {
                Pedido pedido = mapearPedido(resultado);
                pedidos.put(pedido.obtenerId(), pedido);
            }

            if (!pedidos.isEmpty()) {
                cargarDetalleTodos(conexion, pedidos);
            }

            return new ArrayList<>(pedidos.values());
        } catch (SQLException e) {
            throw new IllegalStateException("Error al listar los pedidos.", e);
        }
    }

    private boolean existePedido(String idPedido) throws SQLException {
        String sql = "SELECT 1 FROM Pedido WHERE id_pedido = ?";
        try (Connection conexion = gestorConexion.obtenerConexion();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {
            consulta.setString(1, idPedido);
            try (ResultSet resultado = consulta.executeQuery()) {
                return resultado.next();
            }
        }
    }

    private void insertarPedido(Pedido pedido) throws SQLException {
        try (Connection conexion = gestorConexion.obtenerConexion();
             CallableStatement procedimiento = conexion.prepareCall(
                     "{CALL dbo.sp_RegistrarPedido(?, ?, ?, ?)}")) {

            procedimiento.setString(1, pedido.obtenerId());
            procedimiento.setString(2, pedido.obtenerNombreCliente());
            procedimiento.setString(3, pedido.obtenerMetodoPago());
            procedimiento.setString(4, construirJsonDetalle(pedido));
            procedimiento.execute();
        }
    }

    private void actualizarPedido(Pedido pedido) throws SQLException {
        String sql = """
                UPDATE p
                SET p.id_estado = e.id_estado,
                    p.id_metodo_pago = mp.id_metodo_pago,
                    p.total = ?
                FROM Pedido p
                INNER JOIN EstadoPedido e ON e.nombre = ?
                LEFT JOIN MetodoPago mp ON mp.nombre = ?
                WHERE p.id_pedido = ?
                """;

        try (Connection conexion = gestorConexion.obtenerConexion();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {
            consulta.setDouble(1, pedido.obtenerTotal());
            consulta.setString(2, pedido.obtenerEstado());
            consulta.setString(3, pedido.obtenerMetodoPago());
            consulta.setString(4, pedido.obtenerId());
            consulta.executeUpdate();
        }
    }

    private void cargarDetalle(Connection conexion, Pedido pedido) throws SQLException {
        String sql = """
                SELECT descripcion, precio_unitario, cantidad
                FROM DetallePedido
                WHERE id_pedido = ?
                ORDER BY id_detalle
                """;

        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
            consulta.setString(1, pedido.obtenerId());
            try (ResultSet resultado = consulta.executeQuery()) {
                while (resultado.next()) {
                    pedido.agregarItem(new ItemPedido(
                            resultado.getString("descripcion"),
                            resultado.getDouble("precio_unitario"),
                            resultado.getInt("cantidad")
                    ));
                }
            }
        }
    }

    private void cargarDetalleTodos(Connection conexion, Map<String, Pedido> pedidos) throws SQLException {
        String sql = """
                SELECT id_pedido, descripcion, precio_unitario, cantidad
                FROM DetallePedido
                ORDER BY id_detalle
                """;

        try (PreparedStatement consulta = conexion.prepareStatement(sql);
             ResultSet resultado = consulta.executeQuery()) {
            while (resultado.next()) {
                Pedido pedido = pedidos.get(resultado.getString("id_pedido"));
                if (pedido != null) {
                    pedido.agregarItem(new ItemPedido(
                            resultado.getString("descripcion"),
                            resultado.getDouble("precio_unitario"),
                            resultado.getInt("cantidad")
                    ));
                }
            }
        }
    }

    private Pedido mapearPedido(ResultSet resultado) throws SQLException {
        Timestamp fecha = resultado.getTimestamp("fecha_creacion");
        LocalDateTime fechaCreacion = fecha != null
                ? fecha.toLocalDateTime()
                : LocalDateTime.now();

        return new Pedido(
                resultado.getString("id_pedido"),
                resultado.getString("nombre_cliente"),
                fechaCreacion,
                resultado.getString("estado"),
                resultado.getString("metodo_pago"),
                new ArrayList<>()
        );
    }

    private String construirJsonDetalle(Pedido pedido) {
        StringBuilder json = new StringBuilder("[");
        boolean primero = true;

        for (ItemPedido item : pedido.obtenerItems()) {
            if (!primero) {
                json.append(',');
            }
            json.append("{\"descripcion\":\"")
                    .append(escaparJson(item.obtenerDescripcion()))
                    .append("\",\"precio_unitario\":")
                    .append(String.format("%.2f", item.obtenerPrecioUnitario()))
                    .append(",\"cantidad\":")
                    .append(item.obtenerCantidad())
                    .append('}');
            primero = false;
        }

        json.append(']');
        return json.toString();
    }

    private String escaparJson(String texto) {
        return texto
                .replace("\\", "\\\\")
                .replace("\"", "\\\"");
    }
}
