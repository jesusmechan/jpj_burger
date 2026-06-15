package com.mycompany.jpj_burgers.modelo.patrones.factory;

import com.mycompany.jpj_burgers.modelo.dominio.Hamburguesa;

public class HamburguesaBbq implements ProductoHamburguesa {

    @Override
    public Hamburguesa crear() {
        return new Hamburguesa(
                "BBQ JPJ",
                "Doble carne, bacon, cebolla caramelizada y salsa BBQ",
                18.90
        );
    }
}
