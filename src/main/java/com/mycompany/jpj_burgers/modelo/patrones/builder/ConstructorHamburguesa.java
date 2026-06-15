package com.mycompany.jpj_burgers.modelo.patrones.builder;

import com.mycompany.jpj_burgers.modelo.dominio.Hamburguesa;

public class ConstructorHamburguesa {

    private String nombre = "Hamburguesa Personalizada";
    private String descripcion = "Carne, pan e ingredientes a elección";
    private double precioBase = 12.0;

    public ConstructorHamburguesa conNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public ConstructorHamburguesa conDescripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public ConstructorHamburguesa conPrecioBase(double precioBase) {
        this.precioBase = precioBase;
        return this;
    }

    public ConstructorHamburguesa agregarIngrediente(String ingrediente) {
        this.descripcion += ", " + ingrediente;
        this.precioBase += 1.5;
        return this;
    }

    public Hamburguesa construir() {
        return new Hamburguesa(nombre, descripcion, precioBase);
    }
}
