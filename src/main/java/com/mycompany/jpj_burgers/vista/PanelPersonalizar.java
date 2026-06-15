package com.mycompany.jpj_burgers.vista;

import com.mycompany.jpj_burgers.controlador.ControladorPersonalizar;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Panel del patrón Builder — diseño institucional JPJ Burgers.
 */
public class PanelPersonalizar extends javax.swing.JPanel {

    private ControladorPersonalizar controlador;
    private JPanel panelEncabezadoFormulario;
    private JPanel panelEncabezadoVista;

    public PanelPersonalizar() {
        initComponents();
        aplicarDisenoInstitucional();
    }

    private void aplicarDisenoInstitucional() {
        TemaInstitucional.aplicarFondo(this);
        setBorder(new EmptyBorder(0, 12, 12, 12));

        TemaInstitucional.estilizarTituloPrincipal(lblTitulo, "Constructor Personalizado (Builder)");

        panelContenido.setBackground(TemaInstitucional.COLOR_FONDO);
        panelContenido.setBorder(new EmptyBorder(0, 0, 0, 0));

        TemaInstitucional.estilizarTarjeta(panelFormulario);
        TemaInstitucional.estilizarTarjeta(panelVistaPrevia);

        panelEncabezadoFormulario = TemaInstitucional.crearEncabezadoTarjeta("🔧  Configuración Builder", null);
        panelEncabezadoVista = TemaInstitucional.crearEncabezadoTarjeta("👁  Vista Previa", null);

        TemaInstitucional.estilizarCampo(campoNombre);
        TemaInstitucional.estilizarBotonPrimario(btnConstruir, "✓  CONSTRUIR HAMBURGUESA");
        TemaInstitucional.estilizarAreaTexto(areaVistaPrevia);

        scrollVistaPrevia.setBorder(BorderFactory.createLineBorder(TemaInstitucional.COLOR_BORDE));
        scrollVistaPrevia.setBackground(TemaInstitucional.COLOR_BLANCO);
        scrollVistaPrevia.getViewport().setBackground(TemaInstitucional.COLOR_BLANCO);

        lblVistaPrevia.setVisible(false);

        configurarLayoutFormulario();
        configurarLayoutVistaPrevia();
        configurarEventosBotones();
        reorganizarContenido();
    }

    private void configurarLayoutFormulario() {
        GridBagConstraints gbc;
        JPanel contenido = new JPanel(new GridBagLayout());
        contenido.setBackground(TemaInstitucional.COLOR_BLANCO);
        contenido.setBorder(new EmptyBorder(0, 20, 16, 20));

        TemaInstitucional.estilizarEtiqueta(lblNombre, "NOMBRE DE LA BURGER");
        gbc = crearGbc(0, 0, 2, GridBagConstraints.WEST, GridBagConstraints.NONE, 0);
        gbc.insets = new Insets(0, 0, 4, 0);
        contenido.add(lblNombre, gbc);

        gbc = crearGbc(0, 1, 2, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 1.0);
        gbc.insets = new Insets(0, 0, 16, 0);
        contenido.add(campoNombre, gbc);

        TemaInstitucional.estilizarEtiqueta(lblIngredientes, "INGREDIENTES");
        gbc = crearGbc(0, 2, 2, GridBagConstraints.WEST, GridBagConstraints.NONE, 0);
        gbc.insets = new Insets(0, 0, 8, 0);
        contenido.add(lblIngredientes, gbc);

        gbc = crearGbc(0, 3, 2, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 1.0);
        gbc.insets = new Insets(0, 0, 6, 0);
        contenido.add(TemaInstitucional.crearFilaCheck(chkQueso, "Queso cheddar"), gbc);

        gbc = crearGbc(0, 4, 2, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 1.0);
        gbc.insets = new Insets(0, 0, 6, 0);
        contenido.add(TemaInstitucional.crearFilaCheck(chkBacon, "Bacon crujiente"), gbc);

        gbc = crearGbc(0, 5, 2, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 1.0);
        gbc.insets = new Insets(0, 0, 6, 0);
        contenido.add(TemaInstitucional.crearFilaCheck(chkCebolla, "Cebolla caramelizada"), gbc);

        gbc = crearGbc(0, 6, 2, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 1.0);
        gbc.insets = new Insets(0, 0, 16, 0);
        contenido.add(TemaInstitucional.crearFilaCheck(chkJalapenos, "Jalapeños"), gbc);

        gbc = crearGbc(0, 7, 2, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 1.0);
        contenido.add(btnConstruir, gbc);

        panelFormulario.removeAll();
        panelFormulario.setLayout(new BorderLayout());
        panelFormulario.add(panelEncabezadoFormulario, BorderLayout.NORTH);
        panelFormulario.add(contenido, BorderLayout.CENTER);
    }

    private void configurarLayoutVistaPrevia() {
        scrollVistaPrevia.setBorder(BorderFactory.createEmptyBorder(0, 16, 16, 16));

        panelVistaPrevia.removeAll();
        panelVistaPrevia.setLayout(new BorderLayout());
        panelVistaPrevia.add(panelEncabezadoVista, BorderLayout.NORTH);
        panelVistaPrevia.add(scrollVistaPrevia, BorderLayout.CENTER);
    }

    private void reorganizarContenido() {
        remove(lblTitulo);
        remove(panelContenido);

        panelContenido.removeAll();
        panelContenido.setLayout(new BorderLayout(16, 0));
        panelContenido.setBorder(new EmptyBorder(0, 0, 0, 0));
        panelContenido.add(panelFormulario, BorderLayout.WEST);
        panelContenido.add(panelVistaPrevia, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(lblTitulo, BorderLayout.NORTH);
        add(panelContenido, BorderLayout.CENTER);
    }

    private GridBagConstraints crearGbc(int x, int y, int ancho, int anchor, int fill, double pesoX) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = ancho;
        gbc.anchor = anchor;
        gbc.fill = fill;
        gbc.weightx = pesoX;
        return gbc;
    }

    private void configurarEventosBotones() {
        TemaInstitucional.enlazarBoton(btnConstruir, this::btnConstruirActionPerformed);
    }

    public void asignarControlador(ControladorPersonalizar controlador) {
        this.controlador = controlador;
    }

    public String obtenerNombre() {
        return campoNombre.getText().trim();
    }

    public boolean tieneQueso() {
        return chkQueso.isSelected();
    }

    public boolean tieneBacon() {
        return chkBacon.isSelected();
    }

    public boolean tieneCebolla() {
        return chkCebolla.isSelected();
    }

    public boolean tieneJalapenos() {
        return chkJalapenos.isSelected();
    }

    public void mostrarVistaPrevia(String nombre, String descripcion, double precio) {
        areaVistaPrevia.setText(String.format(
                "Nombre: %s%n%nDescripción:%n%s%n%nPrecio base: S/ %.2f",
                nombre,
                descripcion,
                precio
        ));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        lblTitulo = new javax.swing.JLabel();
        panelContenido = new javax.swing.JPanel();
        panelFormulario = new javax.swing.JPanel();
        lblNombre = new javax.swing.JLabel();
        campoNombre = new javax.swing.JTextField();
        lblIngredientes = new javax.swing.JLabel();
        chkQueso = new javax.swing.JCheckBox();
        chkBacon = new javax.swing.JCheckBox();
        chkCebolla = new javax.swing.JCheckBox();
        chkJalapenos = new javax.swing.JCheckBox();
        btnConstruir = new javax.swing.JButton();
        panelVistaPrevia = new javax.swing.JPanel();
        lblVistaPrevia = new javax.swing.JLabel();
        scrollVistaPrevia = new javax.swing.JScrollPane();
        areaVistaPrevia = new javax.swing.JTextArea();

        setBackground(new java.awt.Color(245, 245, 245));
        setLayout(new java.awt.BorderLayout());
        setMinimumSize(new java.awt.Dimension(750, 450));
        setPreferredSize(new java.awt.Dimension(850, 520));

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 20));
        lblTitulo.setText("Constructor Personalizado (Builder)");
        add(lblTitulo, java.awt.BorderLayout.NORTH);

        panelContenido.setBackground(new java.awt.Color(245, 245, 245));
        panelContenido.setLayout(new java.awt.BorderLayout());
        panelContenido.setMinimumSize(new java.awt.Dimension(700, 400));
        panelContenido.setOpaque(true);
        panelContenido.setPreferredSize(new java.awt.Dimension(820, 460));

        panelFormulario.setBackground(java.awt.Color.WHITE);
        panelFormulario.setMinimumSize(new java.awt.Dimension(300, 350));
        panelFormulario.setOpaque(true);
        panelFormulario.setPreferredSize(new java.awt.Dimension(360, 400));
        panelFormulario.setLayout(new java.awt.GridBagLayout());

        lblNombre.setText("Nombre:");
        panelFormulario.add(lblNombre);

        campoNombre.setColumns(18);
        campoNombre.setText("Mi Burger JPJ");
        panelFormulario.add(campoNombre);

        lblIngredientes.setText("Ingredientes:");
        panelFormulario.add(lblIngredientes);

        chkQueso.setText("Queso cheddar");
        panelFormulario.add(chkQueso);

        chkBacon.setText("Bacon crujiente");
        panelFormulario.add(chkBacon);

        chkCebolla.setText("Cebolla caramelizada");
        panelFormulario.add(chkCebolla);

        chkJalapenos.setText("Jalapeños");
        panelFormulario.add(chkJalapenos);

        btnConstruir.setText("Construir hamburguesa");
        panelFormulario.add(btnConstruir);

        panelContenido.add(panelFormulario, java.awt.BorderLayout.WEST);

        panelVistaPrevia.setBackground(java.awt.Color.WHITE);
        panelVistaPrevia.setLayout(new java.awt.BorderLayout());
        panelVistaPrevia.setMinimumSize(new java.awt.Dimension(350, 300));
        panelVistaPrevia.setOpaque(true);
        panelVistaPrevia.setPreferredSize(new java.awt.Dimension(480, 400));

        lblVistaPrevia.setText("Vista previa:");
        panelVistaPrevia.add(lblVistaPrevia, java.awt.BorderLayout.NORTH);

        areaVistaPrevia.setColumns(30);
        areaVistaPrevia.setEditable(false);
        areaVistaPrevia.setRows(10);
        scrollVistaPrevia.setViewportView(areaVistaPrevia);

        panelVistaPrevia.add(scrollVistaPrevia, java.awt.BorderLayout.CENTER);

        panelContenido.add(panelVistaPrevia, java.awt.BorderLayout.CENTER);

        add(panelContenido, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnConstruirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConstruirActionPerformed
        if (controlador != null) {
            controlador.construirHamburguesa();
        }
    }//GEN-LAST:event_btnConstruirActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaVistaPrevia;
    private javax.swing.JButton btnConstruir;
    private javax.swing.JTextField campoNombre;
    private javax.swing.JCheckBox chkBacon;
    private javax.swing.JCheckBox chkCebolla;
    private javax.swing.JCheckBox chkJalapenos;
    private javax.swing.JCheckBox chkQueso;
    private javax.swing.JLabel lblIngredientes;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblVistaPrevia;
    private javax.swing.JPanel panelContenido;
    private javax.swing.JPanel panelFormulario;
    private javax.swing.JPanel panelVistaPrevia;
    private javax.swing.JScrollPane scrollVistaPrevia;
    // End of variables declaration//GEN-END:variables
}
