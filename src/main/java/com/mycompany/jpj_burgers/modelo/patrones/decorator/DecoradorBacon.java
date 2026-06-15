package com.mycompany.jpj_burgers.modelo.patrones.decorator;

import com.mycompany.jpj_burgers.modelo.dominio.Hamburguesa;

public class DecoradorBacon extends DecoradorHamburguesa {

    public DecoradorBacon(Hamburguesa hamburguesaBase) {
        super(hamburguesaBase, "Bacon", 3.0);
    }
}
