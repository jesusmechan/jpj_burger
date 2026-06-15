package com.mycompany.jpj_burgers.modelo.repositorio;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfiguracionBaseDatos {

    private static final Properties PROPIEDADES = new Properties();

    static {
        try (InputStream entrada = ConfiguracionBaseDatos.class
                .getClassLoader()
                .getResourceAsStream("db.properties")) {
            if (entrada == null) {
                throw new IllegalStateException("No se encontró el archivo db.properties en resources.");
            }
            PROPIEDADES.load(entrada);
        } catch (IOException e) {
            throw new IllegalStateException("Error al cargar db.properties.", e);
        }
    }

    private ConfiguracionBaseDatos() {
    }

    public static String obtenerUrl() {
        return PROPIEDADES.getProperty("db.url");
    }

    public static String obtenerUsuario() {
        return PROPIEDADES.getProperty("db.user");
    }

    public static String obtenerPassword() {
        return PROPIEDADES.getProperty("db.password");
    }
}
