package com.mycompany.jpj_burgers.modelo.patrones.decorator;

import com.mycompany.jpj_burgers.modelo.dominio.Hamburguesa;

public class DecoradorAguacate extends DecoradorHamburguesa {

    public DecoradorAguacate(Hamburguesa hamburguesaBase) {
        super(hamburguesaBase, "Aguacate", 2.5);
    }
}
