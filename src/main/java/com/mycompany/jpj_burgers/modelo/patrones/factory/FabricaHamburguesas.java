package com.mycompany.jpj_burgers.modelo.patrones.factory;

import com.mycompany.jpj_burgers.modelo.dominio.Hamburguesa;
import java.util.LinkedHashMap;
import java.util.Map;

public class FabricaHamburguesas {

    private final Map<String, ProductoHamburguesa> productos = new LinkedHashMap<>();

    public FabricaHamburguesas() {
        productos.put("Clásica", new HamburguesaClasica());
        productos.put("Veggie", new HamburguesaVeggie());
        productos.put("BBQ", new HamburguesaBbq());
    }

    public String[] obtenerTiposDisponibles() {
        return productos.keySet().toArray(String[]::new);
    }

    public Hamburguesa crearHamburguesa(String tipo) {
        ProductoHamburguesa producto = productos.get(tipo);
        if (producto == null) {
            throw new IllegalArgumentException("Tipo de hamburguesa no disponible: " + tipo);
        }
        return producto.crear();
    }
}
