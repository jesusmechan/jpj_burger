package com.mycompany.jpj_burgers.vista;

import com.mycompany.jpj_burgers.controlador.ControladorPedido;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 * Formulario de pedido — diseño institucional JPJ Burgers.
 * Los eventos se mantienen en btnAgregar, btnConfirmar y btnEliminar.
 */
public class FormularioPedido extends javax.swing.JPanel {

    private static final Color COLOR_FONDO = new Color(244, 244, 246);
    private static final Color COLOR_PRIMARIO = new Color(139, 26, 26);
    private static final Color COLOR_TEXTO = new Color(45, 55, 72);
    private static final Color COLOR_TEXTO_SEC = new Color(113, 128, 150);
    private static final Color COLOR_BLANCO = Color.WHITE;
    private static final Color COLOR_BORDE = new Color(226, 232, 240);
    private static final Color COLOR_BADGE = new Color(255, 243, 205);
    private static final Color COLOR_BADGE_TEXTO = new Color(133, 77, 14);
    private static final Color COLOR_ENCABEZADO_TABLA = new Color(247, 250, 252);
    private static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 22);
    private static final Font FUENTE_SECCION = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font FUENTE_ETIQUETA = new Font("Segoe UI", Font.BOLD, 11);
    private static final Font FUENTE_CAMPO = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FUENTE_TOTAL = new Font("Segoe UI", Font.BOLD, 26);
    private static final String PLACEHOLDER_CLIENTE = "Nombre completo o RUC";

    private ControladorPedido controlador;
    private DefaultTableModel modeloCarrito;
    private boolean placeholderActivo = true;

    private JPanel panelEncabezadoDatos;
    private JPanel panelEncabezadoCarrito;

    public FormularioPedido() {
        initComponents();
        aplicarDisenoInstitucional();
        spinnerCantidad.setModel(new SpinnerNumberModel(1, 1, 99, 1));
        configurarTabla();
    }

    private void aplicarDisenoInstitucional() {
        setBackground(COLOR_FONDO);

        lblTitulo.setFont(FUENTE_TITULO);
        lblTitulo.setForeground(COLOR_TEXTO);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBorder(new EmptyBorder(16, 8, 12, 8));

        splitPrincipal.setDividerLocation(420);
        splitPrincipal.setResizeWeight(0.46);
        splitPrincipal.setBorder(BorderFactory.createEmptyBorder(0, 12, 12, 12));
        splitPrincipal.setBackground(COLOR_FONDO);

        crearEncabezados();
        estilizarTarjetas();
        configurarLayoutPanelDatos();
        configurarLayoutPanelCarrito();
        configurarLayoutPanelPieCarrito();
        aplicarEstilosCampos();
        aplicarEstilosBotones();
        configurarEventosBotones();

        remove(lblTitulo);
        remove(splitPrincipal);
        add(lblTitulo, BorderLayout.NORTH);
        add(splitPrincipal, BorderLayout.CENTER);
    }

    private void crearEncabezados() {
        panelEncabezadoDatos = new JPanel(new BorderLayout());
        panelEncabezadoDatos.setBackground(COLOR_BLANCO);
        panelEncabezadoDatos.setBorder(new EmptyBorder(16, 20, 12, 20));

        JLabel lblInfo = new JLabel("📋  Información del Pedido");
        lblInfo.setFont(FUENTE_SECCION);
        lblInfo.setForeground(COLOR_TEXTO);
        panelEncabezadoDatos.add(lblInfo, BorderLayout.WEST);

        panelEncabezadoCarrito = new JPanel(new BorderLayout(12, 0));
        panelEncabezadoCarrito.setBackground(COLOR_BLANCO);
        panelEncabezadoCarrito.setBorder(new EmptyBorder(16, 20, 12, 20));

        JLabel lblResumen = new JLabel("🛒  Resumen del Pedido");
        lblResumen.setFont(FUENTE_SECCION);
        lblResumen.setForeground(COLOR_TEXTO);
        panelEncabezadoCarrito.add(lblResumen, BorderLayout.WEST);

        lblNumeroPedido.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblNumeroPedido.setForeground(COLOR_BADGE_TEXTO);
        lblNumeroPedido.setBackground(COLOR_BADGE);
        lblNumeroPedido.setOpaque(true);
        lblNumeroPedido.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(250, 230, 150)),
                new EmptyBorder(6, 14, 6, 14)
        ));
        lblNumeroPedido.setHorizontalAlignment(SwingConstants.CENTER);
        panelEncabezadoCarrito.add(lblNumeroPedido, BorderLayout.EAST);
    }

    private void estilizarTarjetas() {
        panelDatos.setBackground(COLOR_BLANCO);
        panelDatos.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE),
                new EmptyBorder(0, 0, 16, 0)
        ));

        panelCarrito.setBackground(COLOR_BLANCO);
        panelCarrito.setBorder(BorderFactory.createLineBorder(COLOR_BORDE));

        panelPieCarrito.setBackground(COLOR_BLANCO);
        panelPieCarrito.setMaximumSize(new Dimension(Integer.MAX_VALUE, 280));
        panelPieCarrito.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, COLOR_BORDE),
                new EmptyBorder(16, 20, 20, 20)
        ));
    }

    private void configurarLayoutPanelDatos() {
        GridBagConstraints gbc;
        JPanel contenido = new JPanel(new GridBagLayout());
        contenido.setBackground(COLOR_BLANCO);
        contenido.setBorder(new EmptyBorder(0, 20, 8, 20));

        estilizarEtiqueta(lblCliente, "CLIENTE");
        gbc = crearGbc(0, 0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, 0, 0);
        gbc.insets = new Insets(0, 0, 4, 0);
        contenido.add(lblCliente, gbc);

        gbc = crearGbc(0, 1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 1.0, 0);
        gbc.insets = new Insets(0, 0, 16, 0);
        contenido.add(campoCliente, gbc);

        estilizarEtiqueta(lblHamburguesa, "HAMBURGUESA");
        gbc = crearGbc(0, 2, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, 0, 0);
        gbc.insets = new Insets(0, 0, 4, 8);
        contenido.add(lblHamburguesa, gbc);

        estilizarEtiqueta(lblCantidad, "CANTIDAD");
        gbc = crearGbc(1, 2, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, 0, 0);
        gbc.insets = new Insets(0, 0, 4, 0);
        contenido.add(lblCantidad, gbc);

        gbc = crearGbc(0, 3, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 0.65, 0);
        gbc.insets = new Insets(0, 0, 16, 8);
        contenido.add(comboHamburguesa, gbc);

        gbc = crearGbc(1, 3, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 0.35, 0);
        gbc.insets = new Insets(0, 0, 16, 0);
        contenido.add(spinnerCantidad, gbc);

        estilizarEtiqueta(lblExtras, "EXTRAS SUGERIDOS");
        gbc = crearGbc(0, 4, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, 0, 0);
        gbc.insets = new Insets(0, 0, 8, 0);
        contenido.add(lblExtras, gbc);

        gbc = crearGbc(0, 5, 2, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 1.0, 0);
        gbc.insets = new Insets(0, 0, 6, 0);
        contenido.add(crearFilaExtra(chkQuesoExtra, "Queso extra", "S/ 2.50"), gbc);

        gbc = crearGbc(0, 6, 2, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 1.0, 0);
        gbc.insets = new Insets(0, 0, 6, 0);
        contenido.add(crearFilaExtra(chkBacon, "Bacon", "S/ 3.50"), gbc);

        gbc = crearGbc(0, 7, 2, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 1.0, 0);
        gbc.insets = new Insets(0, 0, 16, 0);
        contenido.add(crearFilaExtra(chkAguacate, "Aguacate", "S/ 4.00"), gbc);

        gbc = crearGbc(0, 8, 2, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 1.0, 0);
        contenido.add(btnAgregar, gbc);

        panelDatos.removeAll();
        panelDatos.setLayout(new BorderLayout());
        panelDatos.add(panelEncabezadoDatos, BorderLayout.NORTH);
        panelDatos.add(contenido, BorderLayout.CENTER);
    }

    private void configurarLayoutPanelCarrito() {
        scrollCarrito.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 16));
        scrollCarrito.setBackground(COLOR_BLANCO);
        scrollCarrito.getViewport().setBackground(COLOR_BLANCO);

        panelCarrito.removeAll();
        panelCarrito.setLayout(new BorderLayout());
        panelCarrito.add(panelEncabezadoCarrito, BorderLayout.NORTH);
        panelCarrito.add(scrollCarrito, BorderLayout.CENTER);
        panelCarrito.add(panelPieCarrito, BorderLayout.SOUTH);
    }

    private void configurarLayoutPanelPieCarrito() {
        GridBagConstraints gbc;
        panelPieCarrito.removeAll();
        panelPieCarrito.setLayout(new GridBagLayout());

        estilizarEtiqueta(lblMetodoPago, "MÉTODO DE PAGO");
        gbc = crearGbc(0, 0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, 0, 0);
        gbc.insets = new Insets(0, 0, 4, 16);
        panelPieCarrito.add(lblMetodoPago, gbc);

        estilizarEtiqueta(lblTotal, "TOTAL A PAGAR");
        gbc = crearGbc(1, 0, 1, 1, GridBagConstraints.EAST, GridBagConstraints.NONE, 0, 0);
        gbc.insets = new Insets(0, 0, 4, 0);
        panelPieCarrito.add(lblTotal, gbc);

        gbc = crearGbc(0, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 1.0, 0);
        gbc.insets = new Insets(0, 0, 12, 16);
        panelPieCarrito.add(comboPago, gbc);

        lblTotalValor.setFont(FUENTE_TOTAL);
        lblTotalValor.setForeground(COLOR_PRIMARIO);
        lblTotalValor.setHorizontalAlignment(SwingConstants.RIGHT);
        gbc = crearGbc(1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.NONE, 0, 0);
        gbc.insets = new Insets(0, 0, 12, 0);
        panelPieCarrito.add(lblTotalValor, gbc);

        gbc = crearGbc(0, 2, 2, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 1.0, 0);
        gbc.insets = new Insets(0, 0, 8, 0);
        panelPieCarrito.add(btnEliminar, gbc);

        gbc = crearGbc(0, 3, 2, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 1.0, 0);
        panelPieCarrito.add(btnConfirmar, gbc);
    }

    private JPanel crearFilaExtra(JCheckBox checkBox, String nombre, String precio) {
        checkBox.setText(nombre);
        checkBox.setFont(FUENTE_CAMPO);
        checkBox.setForeground(COLOR_TEXTO);
        checkBox.setBackground(COLOR_BLANCO);
        checkBox.setFocusPainted(false);

        JLabel lblPrecio = new JLabel(precio);
        lblPrecio.setFont(FUENTE_CAMPO);
        lblPrecio.setForeground(COLOR_TEXTO_SEC);

        JPanel fila = new JPanel(new BorderLayout(12, 0));
        fila.setBackground(COLOR_BLANCO);
        fila.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE),
                new EmptyBorder(10, 14, 10, 14)
        ));
        fila.add(checkBox, BorderLayout.WEST);
        fila.add(lblPrecio, BorderLayout.EAST);
        return fila;
    }

    private void estilizarEtiqueta(JLabel etiqueta, String texto) {
        etiqueta.setText(texto);
        etiqueta.setFont(FUENTE_ETIQUETA);
        etiqueta.setForeground(COLOR_TEXTO_SEC);
    }

    private GridBagConstraints crearGbc(int x, int y, int ancho, int alto, int anchor, int fill, double pesoX, double pesoY) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = ancho;
        gbc.gridheight = alto;
        gbc.anchor = anchor;
        gbc.fill = fill;
        gbc.weightx = pesoX;
        gbc.weighty = pesoY;
        return gbc;
    }

    private void aplicarEstilosCampos() {
        campoCliente.setFont(FUENTE_CAMPO);
        campoCliente.setForeground(COLOR_TEXTO_SEC);
        campoCliente.setText(PLACEHOLDER_CLIENTE);
        campoCliente.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE),
                new EmptyBorder(10, 12, 10, 12)
        ));
        campoCliente.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (placeholderActivo) {
                    campoCliente.setText("");
                    campoCliente.setForeground(COLOR_TEXTO);
                    placeholderActivo = false;
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (campoCliente.getText().isBlank()) {
                    placeholderActivo = true;
                    campoCliente.setForeground(COLOR_TEXTO_SEC);
                    campoCliente.setText(PLACEHOLDER_CLIENTE);
                }
            }
        });

        estilizarCombo(comboHamburguesa);
        estilizarCombo(comboPago);
        estilizarSpinner();
    }

    private void estilizarCombo(javax.swing.JComboBox<?> combo) {
        combo.setFont(FUENTE_CAMPO);
        combo.setBackground(COLOR_BLANCO);
        combo.setForeground(COLOR_TEXTO);
    }

    private void estilizarSpinner() {
        spinnerCantidad.setFont(FUENTE_CAMPO);
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinnerCantidad.getEditor();
        editor.getTextField().setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE),
                new EmptyBorder(8, 10, 8, 10)
        ));
        editor.getTextField().setFont(FUENTE_CAMPO);
    }

    private void aplicarEstilosBotones() {
        estilizarBotonPrimario(btnAgregar, "+  AGREGAR AL PEDIDO");
        estilizarBotonPrimario(btnConfirmar, "✓  CONFIRMAR PEDIDO INSTITUCIONAL");

        btnEliminar.setText("🗑  Eliminar seleccionado");
        btnEliminar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnEliminar.setForeground(COLOR_PRIMARIO);
        btnEliminar.setBackground(COLOR_BLANCO);
        btnEliminar.setFocusPainted(false);
        btnEliminar.setBorderPainted(true);
        btnEliminar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE),
                new EmptyBorder(8, 12, 8, 12)
        ));
        btnEliminar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnEliminar.setUI(new javax.swing.plaf.basic.BasicButtonUI());
    }

    private void estilizarBotonPrimario(javax.swing.JButton boton, String texto) {
        boton.setText(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        boton.setForeground(COLOR_BLANCO);
        boton.setBackground(COLOR_PRIMARIO);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setOpaque(true);
        boton.setBorder(new EmptyBorder(14, 20, 14, 20));
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.setUI(new javax.swing.plaf.basic.BasicButtonUI());
    }

    private void configurarEventosBotones() {
        enlazarBoton(btnAgregar, this::btnAgregarActionPerformed);
        enlazarBoton(btnEliminar, this::btnEliminarActionPerformed);
        enlazarBoton(btnConfirmar, this::btnConfirmarActionPerformed);
    }

    private void enlazarBoton(javax.swing.JButton boton, java.awt.event.ActionListener accion) {
        for (java.awt.event.ActionListener listener : boton.getActionListeners()) {
            boton.removeActionListener(listener);
        }
        boton.addActionListener(accion);
    }

    private void configurarTabla() {
        modeloCarrito = new DefaultTableModel(
                new String[]{"ARTÍCULO", "CANT.", "EXTRAS", "PRECIO"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaCarrito.setModel(modeloCarrito);
        tablaCarrito.setRowHeight(44);
        tablaCarrito.setFont(FUENTE_CAMPO);
        tablaCarrito.setForeground(COLOR_TEXTO);
        tablaCarrito.setGridColor(COLOR_BORDE);
        tablaCarrito.setShowVerticalLines(false);
        tablaCarrito.setIntercellSpacing(new Dimension(0, 1));
        tablaCarrito.setSelectionBackground(new Color(254, 242, 242));
        tablaCarrito.setSelectionForeground(COLOR_TEXTO);

        JTableHeader encabezado = tablaCarrito.getTableHeader();
        encabezado.setFont(FUENTE_ETIQUETA);
        encabezado.setForeground(COLOR_TEXTO_SEC);
        encabezado.setBackground(COLOR_ENCABEZADO_TABLA);
        encabezado.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, COLOR_BORDE));
        encabezado.setPreferredSize(new Dimension(encabezado.getPreferredSize().width, 36));

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setBorder(new EmptyBorder(0, 8, 0, 8));
        for (int i = 0; i < tablaCarrito.getColumnCount(); i++) {
            if (i == 3) {
                DefaultTableCellRenderer derecha = new DefaultTableCellRenderer();
                derecha.setHorizontalAlignment(SwingConstants.RIGHT);
                derecha.setBorder(new EmptyBorder(0, 8, 0, 8));
                derecha.setForeground(COLOR_TEXTO);
                tablaCarrito.getColumnModel().getColumn(i).setCellRenderer(derecha);
            } else if (i == 1) {
                DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
                centro.setHorizontalAlignment(SwingConstants.CENTER);
                centro.setBorder(new EmptyBorder(0, 8, 0, 8));
                tablaCarrito.getColumnModel().getColumn(i).setCellRenderer(centro);
            } else {
                tablaCarrito.getColumnModel().getColumn(i).setCellRenderer(renderer);
            }
        }
    }

    public void asignarControlador(ControladorPedido controlador) {
        this.controlador = controlador;
    }

    public String obtenerNombreCliente() {
        if (placeholderActivo) {
            return "";
        }
        return campoCliente.getText().trim();
    }

    public String obtenerTipoHamburguesa() {
        Object seleccion = comboHamburguesa.getSelectedItem();
        return seleccion != null ? seleccion.toString() : "";
    }

    public int obtenerCantidad() {
        return (Integer) spinnerCantidad.getValue();
    }

    public List<String> obtenerExtrasSeleccionados() {
        List<String> extras = new ArrayList<>();
        if (chkQuesoExtra.isSelected()) {
            extras.add("Queso extra");
        }
        if (chkBacon.isSelected()) {
            extras.add("Bacon");
        }
        if (chkAguacate.isSelected()) {
            extras.add("Aguacate");
        }
        return extras;
    }

    public String obtenerMetodoPago() {
        Object seleccion = comboPago.getSelectedItem();
        return seleccion != null ? seleccion.toString() : "";
    }

    public int obtenerIndiceFilaSeleccionada() {
        return tablaCarrito.getSelectedRow();
    }

    public void configurarTiposHamburguesa(String[] tipos) {
        comboHamburguesa.removeAllItems();
        for (String tipo : tipos) {
            comboHamburguesa.addItem(tipo);
        }
    }

    public void configurarMetodosPago(String[] metodos) {
        comboPago.removeAllItems();
        for (String metodo : metodos) {
            comboPago.addItem(metodo);
        }
    }

    public void limpiarCarrito() {
        if (modeloCarrito != null) {
            modeloCarrito.setRowCount(0);
        }
    }

    public void agregarFilaCarrito(String articulo, int cantidad, String extras, double precio) {
        if (modeloCarrito != null) {
            modeloCarrito.addRow(new Object[]{
                articulo,
                cantidad,
                extras,
                String.format("S/ %.2f", precio)
            });
        }
    }

    public void establecerTotal(double total) {
        lblTotalValor.setText(String.format("S/ %.2f", total));
    }

    public void establecerNumeroPedido(String numero) {
        lblNumeroPedido.setText("PEDIDO #" + numero);
    }

    public void limpiarExtras() {
        chkQuesoExtra.setSelected(false);
        chkBacon.setSelected(false);
        chkAguacate.setSelected(false);
    }

    public void limpiarFormulario() {
        placeholderActivo = true;
        campoCliente.setForeground(COLOR_TEXTO_SEC);
        campoCliente.setText(PLACEHOLDER_CLIENTE);
        spinnerCantidad.setValue(1);
        limpiarExtras();
        limpiarCarrito();
    }

    public void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Validación", JOptionPane.WARNING_MESSAGE);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarExito(String idPedido) {
        JOptionPane.showMessageDialog(
                this,
                "Pedido #" + idPedido + " registrado correctamente.",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        splitPrincipal = new javax.swing.JSplitPane();
        panelDatos = new javax.swing.JPanel();
        lblCliente = new javax.swing.JLabel();
        campoCliente = new javax.swing.JTextField();
        lblHamburguesa = new javax.swing.JLabel();
        comboHamburguesa = new javax.swing.JComboBox();
        lblCantidad = new javax.swing.JLabel();
        spinnerCantidad = new javax.swing.JSpinner();
        lblExtras = new javax.swing.JLabel();
        chkQuesoExtra = new javax.swing.JCheckBox();
        chkBacon = new javax.swing.JCheckBox();
        chkAguacate = new javax.swing.JCheckBox();
        btnAgregar = new javax.swing.JButton();
        panelCarrito = new javax.swing.JPanel();
        lblNumeroPedido = new javax.swing.JLabel();
        scrollCarrito = new javax.swing.JScrollPane();
        tablaCarrito = new javax.swing.JTable();
        panelPieCarrito = new javax.swing.JPanel();
        lblMetodoPago = new javax.swing.JLabel();
        comboPago = new javax.swing.JComboBox();
        lblTotal = new javax.swing.JLabel();
        lblTotalValor = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JButton();
        btnConfirmar = new javax.swing.JButton();

        setBackground(new java.awt.Color(245, 245, 245));
        setMinimumSize(new java.awt.Dimension(850, 520));
        setPreferredSize(new java.awt.Dimension(920, 580));
        setLayout(new java.awt.BorderLayout());

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 20));
        lblTitulo.setText("Formulario de Pedido");
        add(lblTitulo, java.awt.BorderLayout.NORTH);

        splitPrincipal.setDividerLocation(400);
        splitPrincipal.setMinimumSize(new java.awt.Dimension(800, 460));
        splitPrincipal.setPreferredSize(new java.awt.Dimension(900, 520));
        splitPrincipal.setResizeWeight(0.5);

        panelDatos.setBackground(new java.awt.Color(255, 255, 255));
        panelDatos.setMinimumSize(new java.awt.Dimension(320, 300));
        panelDatos.setPreferredSize(new java.awt.Dimension(380, 420));
        panelDatos.setLayout(new java.awt.GridBagLayout());

        lblCliente.setText("Cliente:");
        panelDatos.add(lblCliente);

        campoCliente.setColumns(20);
        panelDatos.add(campoCliente);

        lblHamburguesa.setText("Hamburguesa:");
        panelDatos.add(lblHamburguesa);

        comboHamburguesa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Clásica" }));
        panelDatos.add(comboHamburguesa);

        lblCantidad.setText("Cantidad:");
        panelDatos.add(lblCantidad);
        panelDatos.add(spinnerCantidad);

        lblExtras.setText("Extras:");
        panelDatos.add(lblExtras);

        chkQuesoExtra.setText("Queso extra");
        panelDatos.add(chkQuesoExtra);

        chkBacon.setText("Bacon");
        panelDatos.add(chkBacon);

        chkAguacate.setText("Aguacate");
        panelDatos.add(chkAguacate);

        btnAgregar.setText("Agregar al pedido");
        panelDatos.add(btnAgregar);

        splitPrincipal.setLeftComponent(panelDatos);

        panelCarrito.setBackground(new java.awt.Color(255, 255, 255));
        panelCarrito.setMinimumSize(new java.awt.Dimension(400, 300));
        panelCarrito.setPreferredSize(new java.awt.Dimension(500, 420));
        panelCarrito.setLayout(new BoxLayout(panelCarrito, BoxLayout.Y_AXIS));

        lblNumeroPedido.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 8, 6, 8));
        lblNumeroPedido.setText("Pedido #----");
        panelCarrito.add(lblNumeroPedido);

        scrollCarrito.setMinimumSize(new java.awt.Dimension(400, 180));
        scrollCarrito.setPreferredSize(new java.awt.Dimension(480, 240));
        scrollCarrito.setViewportView(tablaCarrito);

        panelCarrito.add(scrollCarrito);

        panelPieCarrito.setBackground(new java.awt.Color(250, 250, 250));
        panelPieCarrito.setMaximumSize(new java.awt.Dimension(32767, 200));
        panelPieCarrito.setMinimumSize(new java.awt.Dimension(400, 160));
        panelPieCarrito.setPreferredSize(new java.awt.Dimension(480, 180));
        panelPieCarrito.setLayout(new java.awt.GridBagLayout());

        lblMetodoPago.setText("Método de pago:");
        panelPieCarrito.add(lblMetodoPago);

        comboPago.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Efectivo", "Tarjeta", "Yape" }));
        panelPieCarrito.add(comboPago);

        lblTotal.setText("Total:");
        panelPieCarrito.add(lblTotal);

        lblTotalValor.setFont(new java.awt.Font("Segoe UI", 1, 16));
        lblTotalValor.setText("S/ 0.00");
        panelPieCarrito.add(lblTotalValor);

        btnEliminar.setText("Eliminar seleccionado");
        panelPieCarrito.add(btnEliminar);

        btnConfirmar.setText("Confirmar pedido");
        panelPieCarrito.add(btnConfirmar);

        panelCarrito.add(panelPieCarrito);

        splitPrincipal.setRightComponent(panelCarrito);

        add(splitPrincipal, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        if (controlador != null) {
            controlador.agregarAlCarrito();
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        if (controlador != null) {
            controlador.confirmarPedido();
        }
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (controlador != null) {
            controlador.eliminarSeleccionDelCarrito();
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JTextField campoCliente;
    private javax.swing.JCheckBox chkAguacate;
    private javax.swing.JCheckBox chkBacon;
    private javax.swing.JCheckBox chkQuesoExtra;
    private javax.swing.JComboBox comboHamburguesa;
    private javax.swing.JComboBox comboPago;
    private javax.swing.JLabel lblCantidad;
    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblExtras;
    private javax.swing.JLabel lblHamburguesa;
    private javax.swing.JLabel lblMetodoPago;
    private javax.swing.JLabel lblNumeroPedido;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblTotalValor;
    private javax.swing.JPanel panelCarrito;
    private javax.swing.JPanel panelDatos;
    private javax.swing.JPanel panelPieCarrito;
    private javax.swing.JScrollPane scrollCarrito;
    private javax.swing.JSpinner spinnerCantidad;
    private javax.swing.JSplitPane splitPrincipal;
    private javax.swing.JTable tablaCarrito;
    // End of variables declaration//GEN-END:variables
}
