package com.mycompany.jpj_burgers.modelo.patrones.decorator;

import com.mycompany.jpj_burgers.modelo.dominio.Hamburguesa;

public class DecoradorQueso extends DecoradorHamburguesa {

    public DecoradorQueso(Hamburguesa hamburguesaBase) {
        super(hamburguesaBase, "Queso extra", 2.0);
    }
}
