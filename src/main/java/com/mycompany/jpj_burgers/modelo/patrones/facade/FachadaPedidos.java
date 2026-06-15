package com.mycompany.jpj_burgers.modelo.patrones.facade;

import com.mycompany.jpj_burgers.modelo.dominio.Hamburguesa;
import com.mycompany.jpj_burgers.modelo.dominio.ItemPedido;
import com.mycompany.jpj_burgers.modelo.dominio.Pedido;
import com.mycompany.jpj_burgers.modelo.patrones.decorator.ServicioPersonalizacion;
import com.mycompany.jpj_burgers.modelo.patrones.factory.FabricaHamburguesas;
import com.mycompany.jpj_burgers.modelo.patrones.singleton.GestorPedidos;
import com.mycompany.jpj_burgers.modelo.patrones.state.ContextoEstadoPedido;
import com.mycompany.jpj_burgers.modelo.patrones.strategy.ContextoPago;
import com.mycompany.jpj_burgers.modelo.principios.CalculadorPrecio;
import com.mycompany.jpj_burgers.modelo.principios.IProcesadorPago;
import com.mycompany.jpj_burgers.modelo.principios.IRepositorioPedidos;
import com.mycompany.jpj_burgers.modelo.principios.IValidadorPedido;
import com.mycompany.jpj_burgers.modelo.principios.ICalculadorPrecio;
import com.mycompany.jpj_burgers.modelo.principios.ValidadorPedido;
import java.util.List;
import java.util.Optional;

/**
 * Capa Modelo central: expone la lógica de negocio a los controladores.
 */
public class FachadaPedidos {

    private final IRepositorioPedidos repositorio;
    private final IValidadorPedido validador;
    private final ICalculadorPrecio calculadorPrecio;
    private final ContextoPago contextoPago;
    private final ContextoEstadoPedido contextoEstado;
    private final GestorPedidos gestorPedidos;
    private final FabricaHamburguesas fabricaHamburguesas;
    private final ServicioPersonalizacion servicioPersonalizacion;

    public FachadaPedidos() {
        this.gestorPedidos = GestorPedidos.obtenerInstancia();
        this.repositorio = gestorPedidos.obtenerRepositorio();
        this.validador = new ValidadorPedido();
        this.calculadorPrecio = new CalculadorPrecio();
        this.contextoPago = new ContextoPago();
        this.contextoEstado = new ContextoEstadoPedido();
        this.fabricaHamburguesas = new FabricaHamburguesas();
        this.servicioPersonalizacion = new ServicioPersonalizacion();
    }

    public boolean registrarPedido(Pedido pedido, String metodoPago) {
        if (!validador.esValido(pedido)) {
            return false;
        }

        IProcesadorPago procesador = contextoPago.obtenerEstrategia(metodoPago);
        if (procesador == null || !procesador.procesarPago(calculadorPrecio.calcularTotal(pedido))) {
            return false;
        }

        pedido.establecerMetodoPago(procesador.obtenerNombreMetodo());
        pedido.establecerEstado("PENDIENTE");
        repositorio.guardar(pedido);
        gestorPedidos.publicarActualizacion(pedido, "Pedido registrado correctamente.");
        return true;
    }

    public boolean avanzarEstadoPedido(String idPedido) {
        Optional<Pedido> pedidoOpt = repositorio.buscarPorId(idPedido);
        if (pedidoOpt.isEmpty()) {
            return false;
        }

        Pedido pedido = pedidoOpt.get();
        boolean avanzado = contextoEstado.avanzar(pedido);
        if (avanzado) {
            repositorio.guardar(pedido);
            gestorPedidos.publicarActualizacion(pedido, "Estado actualizado a: " + pedido.obtenerEstado());
        }
        return avanzado;
    }

    public List<Pedido> listarPedidos() {
        return repositorio.listarTodos();
    }

    public Optional<Pedido> buscarPedido(String idPedido) {
        return repositorio.buscarPorId(idPedido);
    }

    public String[] obtenerMetodosPago() {
        return contextoPago.obtenerMetodosDisponibles();
    }

    public String[] obtenerTiposHamburguesa() {
        return fabricaHamburguesas.obtenerTiposDisponibles();
    }

    public String[] obtenerExtrasDisponibles() {
        return servicioPersonalizacion.obtenerExtrasDisponibles();
    }

    public ItemPedido crearItemPedido(String tipoHamburguesa, List<String> extras, int cantidad) {
        Hamburguesa base = fabricaHamburguesas.crearHamburguesa(tipoHamburguesa);
        ServicioPersonalizacion.HamburguesaPersonalizada personalizada =
                servicioPersonalizacion.personalizar(base, extras);

        return new ItemPedido(
                personalizada.nombre() + " (" + personalizada.descripcion() + ")",
                personalizada.precio(),
                cantidad
        );
    }

    public GestorPedidos obtenerGestorPedidos() {
        return gestorPedidos;
    }

    public FabricaHamburguesas obtenerFabricaHamburguesas() {
        return fabricaHamburguesas;
    }

    public ServicioPersonalizacion obtenerServicioPersonalizacion() {
        return servicioPersonalizacion;
    }
}
