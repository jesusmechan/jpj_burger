package com.mycompany.jpj_burgers;

import com.mycompany.jpj_burgers.controlador.ControladorPrincipal;
import com.mycompany.jpj_burgers.modelo.patrones.singleton.GestorConexion;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Jpj_burgers {

    public static void main(String[] args) {
        try {
            GestorConexion.obtenerInstancia().probarConexion();
            new ControladorPrincipal().iniciar();
        } catch (Exception e) {
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                    null,
                    "No se pudo conectar a SQL Server.\n\n"
                            + "Verifique que:\n"
                            + "1. SQL Server Express esté iniciado\n"
                            + "2. La base JPJ_Burgers exista (ejecute el script SQL)\n"
                            + "3. Las credenciales en db.properties sean correctas\n\n"
                            + "Detalle: " + e.getMessage(),
                    "Error de conexión",
                    JOptionPane.ERROR_MESSAGE
            ));
        }
    }
}
