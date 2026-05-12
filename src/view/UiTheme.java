package view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public final class UiTheme {

    public static final Color BACKGROUND = new Color(245, 247, 250);
    public static final Color SURFACE = new Color(255, 255, 255);
    public static final Color PRIMARY = new Color(46, 123, 216);
    public static final Color SECONDARY = new Color(235, 238, 243);
    public static final Color DANGER = new Color(200, 62, 62);
    public static final Color TEXT_PRIMARY = new Color(33, 37, 41);
    public static final Color TEXT_MUTED = new Color(100, 108, 118);
    public static final Color BORDER = new Color(225, 229, 235);
    public static final Color TABLE_ROW_ALT = new Color(248, 249, 251);
    public static final Color TABLE_SELECTION = new Color(219, 232, 255);

    public static final Font FONT_TITLE = new Font("SansSerif", Font.BOLD, 22);
    public static final Font FONT_SUBTITLE = new Font("SansSerif", Font.PLAIN, 13);
    public static final Font FONT_LABEL = new Font("SansSerif", Font.PLAIN, 13);
    public static final Font FONT_BUTTON = new Font("SansSerif", Font.BOLD, 13);
    public static final Font FONT_TABLE = new Font("SansSerif", Font.PLAIN, 13);
    public static final Font FONT_TABLE_HEADER = new Font("SansSerif", Font.BOLD, 12);

    private UiTheme() {}

    public static JPanel createPagePanel() {
        JPanel panel = new JPanel(new BorderLayout(18, 18));
        panel.setBorder(new EmptyBorder(24, 24, 24, 24));
        panel.setBackground(BACKGROUND);
        return panel;
    }

    public static JPanel createHeaderPanel(String title, String subtitle) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(FONT_TITLE);
        titleLabel.setForeground(TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(titleLabel);

        if (subtitle != null && !subtitle.isBlank()) {
            JLabel subtitleLabel = new JLabel(subtitle);
            subtitleLabel.setFont(FONT_SUBTITLE);
            subtitleLabel.setForeground(TEXT_MUTED);
            subtitleLabel.setBorder(new EmptyBorder(4, 0, 0, 0));
            subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(subtitleLabel);
        }

        return panel;
    }

    public static JPanel createCardPanel(LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        panel.setBackground(SURFACE);
        panel.setBorder(new CompoundBorder(new LineBorder(BORDER, 1, true), new EmptyBorder(16, 16, 16, 16)));
        return panel;
    }

    public static JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        return panel;
    }

    public static void addFormRow(JPanel panel, int row, String labelText, JComponent field) {
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.gridy = row;
        labelConstraints.anchor = GridBagConstraints.LINE_START;
        labelConstraints.insets = new Insets(6, 0, 6, 12);
        panel.add(createLabel(labelText), labelConstraints);

        GridBagConstraints fieldConstraints = new GridBagConstraints();
        fieldConstraints.gridx = 1;
        fieldConstraints.gridy = row;
        fieldConstraints.weightx = 1;
        fieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        fieldConstraints.insets = new Insets(6, 0, 6, 0);
        panel.add(field, fieldConstraints);
    }

    public static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_LABEL);
        label.setForeground(TEXT_PRIMARY);
        return label;
    }

    public static JLabel createSectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 12));
        label.setForeground(TEXT_MUTED);
        return label;
    }

    public static JTextField createTextField() {
        JTextField field = new JTextField();
        styleTextField(field);
        return field;
    }

    public static JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField();
        styleTextField(field);
        return field;
    }

    public static JTextArea createTextArea(int rows, int cols) {
        JTextArea area = new JTextArea(rows, cols);
        area.setFont(FONT_LABEL);
        area.setForeground(TEXT_PRIMARY);
        area.setBackground(SURFACE);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(null);
        area.setMargin(new Insets(6, 10, 6, 10));
        return area;
    }

    public static JComboBox<String> createComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setFont(FONT_LABEL);
        comboBox.setBackground(SURFACE);
        comboBox.setBorder(createInputBorder());
        return comboBox;
    }

    public static JButton createPrimaryButton(String text) {
        return createButton(text, PRIMARY, Color.WHITE);
    }

    public static JButton createSecondaryButton(String text) {
        return createButton(text, SECONDARY, TEXT_PRIMARY);
    }

    public static JButton createDangerButton(String text) {
        return createButton(text, DANGER, Color.WHITE);
    }

    public static JPanel createButtonBar(JButton... buttons) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        panel.setOpaque(false);
        for (JButton button : buttons) {
            panel.add(button);
        }
        return panel;
    }

    public static JScrollPane createScrollPane(Component view) {
        JScrollPane scrollPane = new JScrollPane(view);
        scrollPane.setBorder(new LineBorder(BORDER, 1, true));
        scrollPane.getViewport().setBackground(SURFACE);
        return scrollPane;
    }

    public static JScrollPane createInputScrollPane(JTextArea area) {
        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setBorder(createInputBorder());
        scrollPane.getViewport().setBackground(SURFACE);
        return scrollPane;
    }

    public static void styleTable(JTable table) {
        table.setFont(FONT_TABLE);
        table.setRowHeight(30);
        table.setFillsViewportHeight(true);
        table.setShowVerticalLines(false);
        table.setGridColor(BORDER);
        table.setSelectionBackground(TABLE_SELECTION);
        table.setSelectionForeground(TEXT_PRIMARY);
        table.setBackground(SURFACE);
        table.setForeground(TEXT_PRIMARY);

        JTableHeader header = table.getTableHeader();
        header.setFont(FONT_TABLE_HEADER);
        header.setBackground(PRIMARY);
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    component.setBackground(row % 2 == 0 ? SURFACE : TABLE_ROW_ALT);
                }
                return component;
            }
        });
    }

    private static JButton createButton(String text, Color background, Color foreground) {
        JButton button = new JButton(text);
        button.setFont(FONT_BUTTON);
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Color borderColor = background.equals(SECONDARY) ? BORDER : background.darker();
        button.setBorder(new CompoundBorder(new LineBorder(borderColor, 1, true), new EmptyBorder(8, 16, 8, 16)));
        return button;
    }

    private static void styleTextField(JTextField field) {
        field.setFont(FONT_LABEL);
        field.setForeground(TEXT_PRIMARY);
        field.setBackground(SURFACE);
        field.setCaretColor(TEXT_PRIMARY);
        field.setBorder(createInputBorder());
    }

    private static Border createInputBorder() {
        return new CompoundBorder(new LineBorder(BORDER, 1, true), new EmptyBorder(6, 10, 6, 10));
    }
}
