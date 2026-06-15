package com.mycompany.jpj_burgers.controlador;

import com.mycompany.jpj_burgers.modelo.dominio.ItemPedido;
import com.mycompany.jpj_burgers.modelo.dominio.Pedido;
import com.mycompany.jpj_burgers.modelo.patrones.command.ComandoRegistrarPedido;
import com.mycompany.jpj_burgers.modelo.patrones.facade.FachadaPedidos;
import com.mycompany.jpj_burgers.modelo.principios.IValidadorPedido;
import com.mycompany.jpj_burgers.modelo.principios.ValidadorPedido;
import com.mycompany.jpj_burgers.vista.FormularioPedido;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ControladorPedido {

    private static final AtomicInteger CONTADOR_PEDIDO = new AtomicInteger(1200);

    private final FachadaPedidos modelo;
    private final FormularioPedido vista;
    private final IValidadorPedido validador;
    private final List<ItemPedido> itemsActuales = new ArrayList<>();

    public ControladorPedido(FachadaPedidos modelo, FormularioPedido vista) {
        this(modelo, vista, new ValidadorPedido());
    }

    public ControladorPedido(FachadaPedidos modelo, FormularioPedido vista, IValidadorPedido validador) {
        this.modelo = modelo;
        this.vista = vista;
        this.validador = validador;
        vista.asignarControlador(this);
        inicializarVista();
    }

    private void inicializarVista() {
        vista.configurarTiposHamburguesa(modelo.obtenerTiposHamburguesa());
        vista.configurarMetodosPago(modelo.obtenerMetodosPago());
        vista.establecerNumeroPedido(String.valueOf(CONTADOR_PEDIDO.incrementAndGet()));
        vista.establecerTotal(0);
    }

    public void agregarAlCarrito() {
        ItemPedido item = modelo.crearItemPedido(
                vista.obtenerTipoHamburguesa(),
                vista.obtenerExtrasSeleccionados(),
                vista.obtenerCantidad()
        );

        itemsActuales.add(item);
        refrescarCarrito();
        vista.limpiarExtras();
    }

    public void eliminarSeleccionDelCarrito() {
        int indice = vista.obtenerIndiceFilaSeleccionada();
        if (indice < 0) {
            vista.mostrarAdvertencia("Seleccione un ítem del carrito.");
            return;
        }
        eliminarDelCarrito(indice);
    }

    private void eliminarDelCarrito(int indice) {
        if (indice >= 0 && indice < itemsActuales.size()) {
            itemsActuales.remove(indice);
            refrescarCarrito();
        }
    }

    private void refrescarCarrito() {
        vista.limpiarCarrito();
        for (ItemPedido item : itemsActuales) {
            DatosLinea linea = parsearItem(item);
            vista.agregarFilaCarrito(
                    linea.nombre(),
                    item.obtenerCantidad(),
                    linea.extras(),
                    item.obtenerSubtotal()
            );
        }
        vista.establecerTotal(calcularTotal());
    }

    private DatosLinea parsearItem(ItemPedido item) {
        String descripcion = item.obtenerDescripcion();
        String nombre = descripcion;
        String extras = "Sin extras";

        if (descripcion.contains("(")) {
            nombre = descripcion.substring(0, descripcion.indexOf('(')).trim();
            String contenido = descripcion.substring(descripcion.indexOf('(') + 1, descripcion.lastIndexOf(')'));

            if (contenido.contains(" + ")) {
                String[] partes = contenido.split(" \\+ ");
                extras = String.join(", ", java.util.Arrays.copyOfRange(partes, 1, partes.length));
            }
        }

        return new DatosLinea(nombre, extras);
    }

    public void confirmarPedido() {
        Pedido pedido = construirPedidoDesdeVista();

        if (!validador.esValido(pedido)) {
            vista.mostrarAdvertencia(validador.obtenerMensajeValidacion(pedido));
            return;
        }

        ComandoRegistrarPedido comando = new ComandoRegistrarPedido(
                modelo,
                pedido,
                vista.obtenerMetodoPago()
        );

        if (comando.ejecutar()) {
            vista.mostrarExito(pedido.obtenerId());
            limpiarPedido();
            vista.establecerNumeroPedido(String.valueOf(CONTADOR_PEDIDO.incrementAndGet()));
        } else {
            vista.mostrarError("No se pudo registrar el pedido.");
        }
    }

    private Pedido construirPedidoDesdeVista() {
        Pedido pedido = new Pedido(vista.obtenerNombreCliente());
        itemsActuales.forEach(pedido::agregarItem);
        return pedido;
    }

    private void limpiarPedido() {
        itemsActuales.clear();
        vista.limpiarFormulario();
        vista.establecerTotal(0);
    }

    private double calcularTotal() {
        return itemsActuales.stream().mapToDouble(ItemPedido::obtenerSubtotal).sum();
    }

    private record DatosLinea(String nombre, String extras) {}
}
