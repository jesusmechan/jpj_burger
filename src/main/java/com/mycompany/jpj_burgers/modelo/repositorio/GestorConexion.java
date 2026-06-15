package com.mycompany.jpj_burgers.modelo.repositorio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GestorConexion {

    private static final GestorConexion INSTANCIA = new GestorConexion();

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("No se encontró el driver JDBC de SQL Server.", e);
        }
    }

    private GestorConexion() {
    }

    public static GestorConexion obtenerInstancia() {
        return INSTANCIA;
    }

    public Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(
                ConfiguracionBaseDatos.obtenerUrl(),
                ConfiguracionBaseDatos.obtenerUsuario(),
                ConfiguracionBaseDatos.obtenerPassword()
        );
    }

    public void probarConexion() throws SQLException {
        try (Connection conexion = obtenerConexion()) {
            if (!conexion.isValid(5)) {
                throw new SQLException("La conexión a SQL Server no es válida.");
            }
        }
    }
}
