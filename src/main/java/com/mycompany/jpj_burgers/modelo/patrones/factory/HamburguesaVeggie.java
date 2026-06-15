package com.mycompany.jpj_burgers.modelo.patrones.factory;

import com.mycompany.jpj_burgers.modelo.dominio.Hamburguesa;

public class HamburguesaVeggie implements ProductoHamburguesa {

    @Override
    public Hamburguesa crear() {
        return new Hamburguesa(
                "Veggie JPJ",
                "Medallón de lentejas, aguacate, rúcula y mayo vegana",
                14.50
        );
    }
}
