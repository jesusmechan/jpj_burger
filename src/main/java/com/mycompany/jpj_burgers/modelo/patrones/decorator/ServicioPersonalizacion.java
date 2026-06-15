package com.mycompany.jpj_burgers.modelo.patrones.decorator;

import com.mycompany.jpj_burgers.modelo.dominio.Hamburguesa;
import java.util.List;

public class ServicioPersonalizacion {

    public record HamburguesaPersonalizada(String nombre, String descripcion, double precio) {}

    public HamburguesaPersonalizada personalizar(Hamburguesa base, List<String> extras) {
        String descripcion = base.obtenerDescripcion();
        double precio = base.obtenerPrecioBase();

        for (String extra : extras) {
            DecoradorHamburguesa decorador = crearDecorador(base, extra);
            if (decorador != null) {
                descripcion += " + " + decorador.nombreExtra;
                precio += decorador.precioExtra;
            }
        }

        return new HamburguesaPersonalizada(base.obtenerNombre(), descripcion, precio);
    }

    private DecoradorHamburguesa crearDecorador(Hamburguesa base, String extra) {
        return switch (extra) {
            case "Queso extra" -> new DecoradorQueso(base);
            case "Bacon" -> new DecoradorBacon(base);
            case "Aguacate" -> new DecoradorAguacate(base);
            default -> null;
        };
    }

    public String[] obtenerExtrasDisponibles() {
        return new String[]{"Queso extra", "Bacon", "Aguacate"};
    }
}
