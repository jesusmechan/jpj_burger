package com.mycompany.jpj_burgers.vista;

import com.mycompany.jpj_burgers.controlador.ControladorCocina;
import com.mycompany.jpj_burgers.modelo.dominio.Pedido;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 * Panel de cocina — diseño institucional JPJ Burgers.
 */
public class PanelCocina extends javax.swing.JPanel {

    private ControladorCocina controlador;
    private DefaultTableModel modeloTabla;
    private JPanel panelTarjeta;
    private JPanel panelEncabezadoTarjeta;

    public PanelCocina() {
        initComponents();
        aplicarDisenoInstitucional();
        configurarTabla();
    }

    private void aplicarDisenoInstitucional() {
        TemaInstitucional.aplicarFondo(this);
        setBorder(new EmptyBorder(0, 12, 12, 12));

        TemaInstitucional.estilizarTituloPrincipal(lblTitulo, "Panel de Cocina");

        panelTarjeta = new JPanel(new BorderLayout());
        TemaInstitucional.estilizarTarjeta(panelTarjeta);

        TemaInstitucional.estilizarBadgeInfo(lblEstado, "Esperando pedidos...");
        panelEncabezadoTarjeta = TemaInstitucional.crearEncabezadoTarjeta("🍳  Monitoreo de Cocina", lblEstado);

        TemaInstitucional.estilizarScroll(scrollTabla);
        scrollTabla.setBorder(BorderFactory.createEmptyBorder(0, 16, 8, 16));

        panelBotones.setBackground(TemaInstitucional.COLOR_BLANCO);
        panelBotones.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, TemaInstitucional.COLOR_BORDE),
                new EmptyBorder(16, 20, 20, 20)
        ));
        panelBotones.setLayout(new FlowLayout(FlowLayout.RIGHT, 12, 0));

        TemaInstitucional.estilizarBotonSecundario(btnActualizar, "↻  Actualizar");
        TemaInstitucional.estilizarBotonPrimario(btnAvanzar, "▶  Avanzar estado");

        configurarEventosBotones();
        reorganizarLayout();
    }

    private void reorganizarLayout() {
        remove(panelEncabezado);
        remove(scrollTabla);
        remove(panelBotones);

        panelTarjeta.removeAll();
        panelTarjeta.add(panelEncabezadoTarjeta, BorderLayout.NORTH);
        panelTarjeta.add(scrollTabla, BorderLayout.CENTER);
        panelTarjeta.add(panelBotones, BorderLayout.SOUTH);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 8, 0);
        add(lblTitulo, gbc);

        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(panelTarjeta, gbc);
    }

    private void configurarEventosBotones() {
        TemaInstitucional.enlazarBoton(btnActualizar, this::btnActualizarActionPerformed);
        TemaInstitucional.enlazarBoton(btnAvanzar, this::btnAvanzarActionPerformed);
    }

    private void configurarTabla() {
        modeloTabla = new DefaultTableModel(
                new String[]{"ID", "CLIENTE", "ESTADO", "PAGO", "TOTAL", "HORA"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        TemaInstitucional.estilizarTabla(tablaPedidos, modeloTabla);
    }

    public void asignarControlador(ControladorCocina controlador) {
        this.controlador = controlador;
    }

    public String obtenerIdPedidoSeleccionado() {
        int fila = tablaPedidos.getSelectedRow();
        if (fila < 0 || modeloTabla == null) {
            return null;
        }
        Object valor = modeloTabla.getValueAt(fila, 0);
        return valor != null ? valor.toString() : null;
    }

    public void actualizarTabla(List<Pedido> pedidos) {
        if (modeloTabla == null) {
            return;
        }
        modeloTabla.setRowCount(0);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm");
        for (Pedido pedido : pedidos) {
            modeloTabla.addRow(new Object[]{
                pedido.obtenerId(),
                pedido.obtenerNombreCliente(),
                pedido.obtenerEstado(),
                pedido.obtenerMetodoPago(),
                String.format("S/ %.2f", pedido.obtenerTotal()),
                pedido.obtenerFechaCreacion().format(formato)
            });
        }
    }

    public void establecerEstado(String mensaje) {
        TemaInstitucional.estilizarBadgeInfo(lblEstado, mensaje);
    }

    public void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Validación", JOptionPane.WARNING_MESSAGE);
    }

    public void mostrarInformacion(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelEncabezado = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        scrollTabla = new javax.swing.JScrollPane();
        tablaPedidos = new javax.swing.JTable();
        panelBotones = new javax.swing.JPanel();
        btnActualizar = new javax.swing.JButton();
        btnAvanzar = new javax.swing.JButton();

        setBackground(new java.awt.Color(245, 245, 245));
        setLayout(new java.awt.BorderLayout());
        setMinimumSize(new java.awt.Dimension(700, 450));
        setPreferredSize(new java.awt.Dimension(850, 520));

        panelEncabezado.setBackground(new java.awt.Color(245, 245, 245));
        panelEncabezado.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        panelEncabezado.setLayout(new java.awt.BorderLayout());
        panelEncabezado.setMinimumSize(new java.awt.Dimension(400, 40));
        panelEncabezado.setOpaque(true);
        panelEncabezado.setPreferredSize(new java.awt.Dimension(800, 44));

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 20));
        lblTitulo.setText("Panel de Cocina");
        panelEncabezado.add(lblTitulo, java.awt.BorderLayout.WEST);

        lblEstado.setText("Esperando pedidos...");
        panelEncabezado.add(lblEstado, java.awt.BorderLayout.EAST);

        add(panelEncabezado, java.awt.BorderLayout.NORTH);

        tablaPedidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "ID", "Cliente", "Estado", "Pago", "Total", "Hora"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaPedidos.setPreferredScrollableViewportSize(new java.awt.Dimension(600, 280));
        tablaPedidos.setRowHeight(24);
        scrollTabla.setViewportView(tablaPedidos);

        add(scrollTabla, java.awt.BorderLayout.CENTER);

        panelBotones.setBackground(new java.awt.Color(245, 245, 245));
        panelBotones.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        panelBotones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 8, 5));
        panelBotones.setMinimumSize(new java.awt.Dimension(400, 48));
        panelBotones.setOpaque(true);
        panelBotones.setPreferredSize(new java.awt.Dimension(800, 52));

        btnActualizar.setText("Actualizar");
        panelBotones.add(btnActualizar);

        btnAvanzar.setText("Avanzar estado");
        panelBotones.add(btnAvanzar);

        add(panelBotones, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        if (controlador != null) {
            controlador.actualizarVista();
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnAvanzarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAvanzarActionPerformed
        if (controlador != null) {
            controlador.avanzarPedido();
        }
    }//GEN-LAST:event_btnAvanzarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAvanzar;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JPanel panelEncabezado;
    private javax.swing.JScrollPane scrollTabla;
    private javax.swing.JTable tablaPedidos;
    // End of variables declaration//GEN-END:variables
}
