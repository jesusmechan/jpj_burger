package com.mycompany.jpj_burgers.vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 * Paleta y utilidades visuales compartidas — diseño institucional JPJ Burgers.
 */
public final class TemaInstitucional {

    public static final Color COLOR_FONDO = new Color(244, 244, 246);
    public static final Color COLOR_PRIMARIO = new Color(139, 26, 26);
    public static final Color COLOR_TEXTO = new Color(45, 55, 72);
    public static final Color COLOR_TEXTO_SEC = new Color(113, 128, 150);
    public static final Color COLOR_BLANCO = Color.WHITE;
    public static final Color COLOR_BORDE = new Color(226, 232, 240);
    public static final Color COLOR_BADGE = new Color(255, 243, 205);
    public static final Color COLOR_BADGE_TEXTO = new Color(133, 77, 14);
    public static final Color COLOR_BADGE_INFO = new Color(219, 234, 254);
    public static final Color COLOR_BADGE_INFO_TEXTO = new Color(30, 64, 175);
    public static final Color COLOR_ENCABEZADO_TABLA = new Color(247, 250, 252);
    public static final Color COLOR_SELECCION_TABLA = new Color(254, 242, 242);

    public static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FUENTE_SECCION = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FUENTE_ETIQUETA = new Font("Segoe UI", Font.BOLD, 11);
    public static final Font FUENTE_CAMPO = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FUENTE_TOTAL = new Font("Segoe UI", Font.BOLD, 26);

    private TemaInstitucional() {
    }

    public static Border bordeTarjeta() {
        return BorderFactory.createLineBorder(COLOR_BORDE);
    }

    public static Border bordeCampo() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE),
                new EmptyBorder(10, 12, 10, 12)
        );
    }

    public static Border bordeFila() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE),
                new EmptyBorder(10, 14, 10, 14)
        );
    }

    public static void aplicarFondo(JPanel panel) {
        panel.setBackground(COLOR_FONDO);
        panel.setOpaque(true);
    }

    public static void estilizarTituloPrincipal(JLabel etiqueta, String texto) {
        etiqueta.setText(texto);
        etiqueta.setFont(FUENTE_TITULO);
        etiqueta.setForeground(COLOR_TEXTO);
        etiqueta.setHorizontalAlignment(SwingConstants.CENTER);
        etiqueta.setBorder(new EmptyBorder(16, 8, 12, 8));
        etiqueta.setOpaque(false);
    }

    public static void estilizarEtiqueta(JLabel etiqueta, String texto) {
        etiqueta.setText(texto);
        etiqueta.setFont(FUENTE_ETIQUETA);
        etiqueta.setForeground(COLOR_TEXTO_SEC);
    }

    public static void estilizarEncabezadoSeccion(JLabel etiqueta, String texto) {
        etiqueta.setText(texto);
        etiqueta.setFont(FUENTE_SECCION);
        etiqueta.setForeground(COLOR_TEXTO);
    }

    public static void estilizarBadge(JLabel etiqueta, String texto) {
        etiqueta.setText(texto);
        etiqueta.setFont(new Font("Segoe UI", Font.BOLD, 11));
        etiqueta.setForeground(COLOR_BADGE_TEXTO);
        etiqueta.setBackground(COLOR_BADGE);
        etiqueta.setOpaque(true);
        etiqueta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(250, 230, 150)),
                new EmptyBorder(6, 14, 6, 14)
        ));
        etiqueta.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public static void estilizarBadgeInfo(JLabel etiqueta, String texto) {
        etiqueta.setText(texto);
        etiqueta.setFont(new Font("Segoe UI", Font.BOLD, 11));
        etiqueta.setForeground(COLOR_BADGE_INFO_TEXTO);
        etiqueta.setBackground(COLOR_BADGE_INFO);
        etiqueta.setOpaque(true);
        etiqueta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(191, 219, 254)),
                new EmptyBorder(6, 14, 6, 14)
        ));
        etiqueta.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public static void estilizarTarjeta(JPanel panel) {
        panel.setBackground(COLOR_BLANCO);
        panel.setBorder(bordeTarjeta());
        panel.setOpaque(true);
    }

    public static void estilizarCampo(JTextField campo) {
        campo.setFont(FUENTE_CAMPO);
        campo.setForeground(COLOR_TEXTO);
        campo.setBorder(bordeCampo());
    }

    public static void estilizarCombo(JComboBox<?> combo) {
        combo.setFont(FUENTE_CAMPO);
        combo.setBackground(COLOR_BLANCO);
        combo.setForeground(COLOR_TEXTO);
    }

    public static void estilizarCheckBox(JCheckBox checkBox, String texto) {
        checkBox.setText(texto);
        checkBox.setFont(FUENTE_CAMPO);
        checkBox.setForeground(COLOR_TEXTO);
        checkBox.setBackground(COLOR_BLANCO);
        checkBox.setFocusPainted(false);
    }

    public static void estilizarAreaTexto(JTextArea area) {
        area.setFont(FUENTE_CAMPO);
        area.setForeground(COLOR_TEXTO);
        area.setBackground(COLOR_BLANCO);
        area.setBorder(new EmptyBorder(12, 14, 12, 14));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
    }

    public static void estilizarScroll(JScrollPane scroll) {
        scroll.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 16));
        scroll.setBackground(COLOR_BLANCO);
        scroll.getViewport().setBackground(COLOR_BLANCO);
    }

    public static void estilizarBotonPrimario(JButton boton, String texto) {
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

    public static void estilizarBotonSecundario(JButton boton, String texto) {
        boton.setText(texto);
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        boton.setForeground(COLOR_PRIMARIO);
        boton.setBackground(COLOR_BLANCO);
        boton.setFocusPainted(false);
        boton.setBorderPainted(true);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE),
                new EmptyBorder(10, 16, 10, 16)
        ));
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.setUI(new javax.swing.plaf.basic.BasicButtonUI());
    }

    public static void estilizarTabla(JTable tabla, DefaultTableModel modelo) {
        tabla.setModel(modelo);
        tabla.setRowHeight(44);
        tabla.setFont(FUENTE_CAMPO);
        tabla.setForeground(COLOR_TEXTO);
        tabla.setGridColor(COLOR_BORDE);
        tabla.setShowVerticalLines(false);
        tabla.setIntercellSpacing(new Dimension(0, 1));
        tabla.setSelectionBackground(COLOR_SELECCION_TABLA);
        tabla.setSelectionForeground(COLOR_TEXTO);

        JTableHeader encabezado = tabla.getTableHeader();
        encabezado.setFont(FUENTE_ETIQUETA);
        encabezado.setForeground(COLOR_TEXTO_SEC);
        encabezado.setBackground(COLOR_ENCABEZADO_TABLA);
        encabezado.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, COLOR_BORDE));
        encabezado.setPreferredSize(new Dimension(encabezado.getPreferredSize().width, 36));

        DefaultTableCellRenderer izquierda = new DefaultTableCellRenderer();
        izquierda.setBorder(new EmptyBorder(0, 8, 0, 8));

        DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
        centro.setHorizontalAlignment(SwingConstants.CENTER);
        centro.setBorder(new EmptyBorder(0, 8, 0, 8));

        DefaultTableCellRenderer derecha = new DefaultTableCellRenderer();
        derecha.setHorizontalAlignment(SwingConstants.RIGHT);
        derecha.setBorder(new EmptyBorder(0, 8, 0, 8));
        derecha.setForeground(COLOR_TEXTO);

        for (int i = 0; i < tabla.getColumnCount(); i++) {
            if (i == tabla.getColumnCount() - 1 || "PRECIO".equals(tabla.getColumnName(i)) || "TOTAL".equals(tabla.getColumnName(i))) {
                tabla.getColumnModel().getColumn(i).setCellRenderer(derecha);
            } else if ("CANT.".equals(tabla.getColumnName(i)) || "HORA".equals(tabla.getColumnName(i)) || "ID".equals(tabla.getColumnName(i))) {
                tabla.getColumnModel().getColumn(i).setCellRenderer(centro);
            } else {
                tabla.getColumnModel().getColumn(i).setCellRenderer(izquierda);
            }
        }
    }

    public static void estilizarPestanas(JTabbedPane pestanas) {
        pestanas.setFont(new Font("Segoe UI", Font.BOLD, 13));
        pestanas.setBackground(COLOR_FONDO);
        pestanas.setForeground(COLOR_TEXTO);
        pestanas.setBorder(new EmptyBorder(8, 12, 0, 12));
    }

    public static JPanel crearEncabezadoTarjeta(String titulo, JLabel badge) {
        JPanel encabezado = new JPanel(new java.awt.BorderLayout(12, 0));
        encabezado.setBackground(COLOR_BLANCO);
        encabezado.setBorder(new EmptyBorder(16, 20, 12, 20));

        JLabel lblTitulo = new JLabel(titulo);
        estilizarEncabezadoSeccion(lblTitulo, titulo);
        encabezado.add(lblTitulo, java.awt.BorderLayout.WEST);

        if (badge != null) {
            encabezado.add(badge, java.awt.BorderLayout.EAST);
        }
        return encabezado;
    }

    public static JPanel crearFilaCheck(JCheckBox checkBox, String nombre) {
        estilizarCheckBox(checkBox, nombre);
        JPanel fila = new JPanel(new java.awt.BorderLayout());
        fila.setBackground(COLOR_BLANCO);
        fila.setBorder(bordeFila());
        fila.add(checkBox, java.awt.BorderLayout.WEST);
        return fila;
    }

    public static void enlazarBoton(JButton boton, java.awt.event.ActionListener accion) {
        for (java.awt.event.ActionListener listener : boton.getActionListeners()) {
            boton.removeActionListener(listener);
        }
        boton.addActionListener(accion);
    }
}
