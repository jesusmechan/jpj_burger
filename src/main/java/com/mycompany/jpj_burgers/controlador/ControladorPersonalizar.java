package com.mycompany.jpj_burgers.controlador;

import com.mycompany.jpj_burgers.modelo.dominio.Hamburguesa;
import com.mycompany.jpj_burgers.modelo.patrones.builder.ConstructorHamburguesa;
import com.mycompany.jpj_burgers.vista.PanelPersonalizar;

public class ControladorPersonalizar {

    private final PanelPersonalizar vista;

    public ControladorPersonalizar(PanelPersonalizar vista) {
        this.vista = vista;
        vista.asignarControlador(this);
        construirHamburguesa();
    }

    public void construirHamburguesa() {
        ConstructorHamburguesa constructor = new ConstructorHamburguesa()
                .conNombre(vista.obtenerNombre())
                .conPrecioBase(12.0);

        if (vista.tieneQueso()) {
            constructor.agregarIngrediente("Queso cheddar");
        }
        if (vista.tieneBacon()) {
            constructor.agregarIngrediente("Bacon crujiente");
        }
        if (vista.tieneCebolla()) {
            constructor.agregarIngrediente("Cebolla caramelizada");
        }
        if (vista.tieneJalapenos()) {
            constructor.agregarIngrediente("Jalapeños");
        }

        Hamburguesa hamburguesa = constructor.construir();
        vista.mostrarVistaPrevia(
                hamburguesa.obtenerNombre(),
                hamburguesa.obtenerDescripcion(),
                hamburguesa.obtenerPrecioBase()
        );
    }
}
