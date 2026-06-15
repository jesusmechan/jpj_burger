package com.mycompany.jpj_burgers.vista;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Ventana principal. Editar layout en pestaña Design.
 * Las pestañas se cargan al ejecutar la aplicación.
 */
public class VentanaPrincipal extends javax.swing.JFrame {

    private FormularioPedido formularioPedido;
    private PanelCocina panelCocina;
    private PanelPersonalizar panelPersonalizar;

    public VentanaPrincipal() {
        initComponents();
        aplicarTemaInstitucional();
        if (java.beans.Beans.isDesignTime()) {
            mostrarPestanasEnDesign();
        } else {
            cargarPestanas();
        }
    }

    private void aplicarTemaInstitucional() {
        getContentPane().setBackground(TemaInstitucional.COLOR_FONDO);
        TemaInstitucional.estilizarPestanas(pestanas);
    }

    private void mostrarPestanasEnDesign() {
        pestanas.addTab("📋  Nuevo Pedido", new FormularioPedido());
        pestanas.addTab("🍳  Cocina", new PanelCocina());
        pestanas.addTab("🔧  Personalizar", new PanelPersonalizar());
        setSize(1000, 650);
    }

    private void cargarPestanas() {
        formularioPedido = new FormularioPedido();
        panelCocina = new PanelCocina();
        panelPersonalizar = new PanelPersonalizar();

        pestanas.addTab("📋  Nuevo Pedido", formularioPedido);
        pestanas.addTab("🍳  Cocina", panelCocina);
        pestanas.addTab("🔧  Personalizar", panelPersonalizar);

        setSize(1000, 650);
        setLocationRelativeTo(null);
    }

    public FormularioPedido getFormularioPedido() {
        return formularioPedido;
    }

    public PanelCocina getPanelCocina() {
        return panelCocina;
    }

    public PanelPersonalizar getPanelPersonalizar() {
        return panelPersonalizar;
    }

    public void mostrar() {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }
            setVisible(true);
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pestanas = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JPJ Burgers - Sistema de Pedidos");

        pestanas.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        pestanas.setMinimumSize(new java.awt.Dimension(800, 500));
        pestanas.setPreferredSize(new java.awt.Dimension(1000, 650));

        getContentPane().setBackground(new java.awt.Color(245, 245, 245));
        getContentPane().add(pestanas, java.awt.BorderLayout.CENTER);
        setMinimumSize(new java.awt.Dimension(900, 600));
        setPreferredSize(new java.awt.Dimension(1000, 650));
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane pestanas;
    // End of variables declaration//GEN-END:variables
}
