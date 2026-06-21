package com.mycompany.jpj_burgers.modelo.patrones.singleton;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Patrón Singleton: único punto de acceso a la configuración y conexión con SQL Server.
 */
public class GestorConexion {

    private static GestorConexion instancia;

    private final String url;
    private final String usuario;
    private final String password;

    private GestorConexion() {
        Properties propiedades = cargarPropiedades();
        this.url = propiedades.getProperty("db.url");
        this.usuario = propiedades.getProperty("db.user");
        this.password = propiedades.getProperty("db.password");
        registrarDriver();
    }

    public static synchronized GestorConexion obtenerInstancia() {
        if (instancia == null) {
            instancia = new GestorConexion();
        }
        return instancia;
    }

    public Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(url, usuario, password);
    }

    public void probarConexion() throws SQLException {
        try (Connection conexion = obtenerConexion()) {
            if (!conexion.isValid(5)) {
                throw new SQLException("La conexión a SQL Server no es válida.");
            }
        }
    }

    private static Properties cargarPropiedades() {
        Properties propiedades = new Properties();
        try (InputStream entrada = GestorConexion.class
                .getClassLoader()
                .getResourceAsStream("db.properties")) {
            if (entrada == null) {
                throw new IllegalStateException("No se encontró el archivo db.properties en resources.");
            }
            propiedades.load(entrada);
            return propiedades;
        } catch (IOException e) {
            throw new IllegalStateException("Error al cargar db.properties.", e);
        }
    }

    private static void registrarDriver() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("No se encontró el driver JDBC de SQL Server.", e);
        }
    }
}
