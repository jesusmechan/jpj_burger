package com.mycompany.jpj_burgers.modelo.patrones.factory;

import com.mycompany.jpj_burgers.modelo.dominio.Hamburguesa;

public class HamburguesaClasica implements ProductoHamburguesa {

    @Override
    public Hamburguesa crear() {
        return new Hamburguesa(
                "Clásica JPJ",
                "Carne 150g, lechuga, tomate, cebolla y salsa especial",
                15.90
        );
    }
}
