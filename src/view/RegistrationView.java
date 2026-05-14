package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Khung quản lý Đăng ký: bảng + tìm theo tên/email hội viên + nút Add/Renew/Delete.
 */
public class RegistrationView extends JFrame {

    public static final String SEARCH_FIELD_NAME = "name";
    public static final String SEARCH_FIELD_EMAIL = "email";

    private final JRadioButton rbName;
    private final JRadioButton rbEmail;
    private final JTextField txtSearch;
    private final JButton btnSearch;
    private final JButton btnReload;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final JButton btnAdd;
    private final JButton btnRenew;
    private final JButton btnDelete;
    private final JButton btnClose;

    public RegistrationView() {
        setTitle("Quản lý Đăng ký");
        setSize(960, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = UiTheme.createPagePanel();
        mainPanel.add(UiTheme.createHeaderPanel("Quản lý Đăng ký",
                "Tìm theo tên hoặc email hội viên. Chọn 1 đăng ký để Gia hạn / Xóa."),
                BorderLayout.NORTH);

        JPanel card = UiTheme.createCardPanel(new BorderLayout(0, 12));

        // Thanh tìm
        JPanel searchBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        searchBar.setOpaque(false);
        searchBar.add(UiTheme.createLabel("Tìm hội viên theo:"));
        rbName = new JRadioButton("Tên", true);
        rbEmail = new JRadioButton("Email");
        rbName.setOpaque(false);
        rbEmail.setOpaque(false);
        ButtonGroup group = new ButtonGroup();
        group.add(rbName);
        group.add(rbEmail);
        searchBar.add(rbName);
        searchBar.add(rbEmail);
        txtSearch = UiTheme.createTextField();
        txtSearch.setColumns(UiTheme.SEARCH_FIELD_COLUMNS + 8);
        searchBar.add(txtSearch);
        btnSearch = UiTheme.createPrimaryButton("Tìm");
        btnReload = UiTheme.createSecondaryButton("Tải lại");
        searchBar.add(btnSearch);
        searchBar.add(btnReload);
        card.add(searchBar, BorderLayout.NORTH);

        // Bảng
        String[] columns = {"ID", "Hội viên", "Email", "Gói tập", "Bắt đầu", "Kết thúc", "Tổng (VNĐ)", "Trạng thái"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        UiTheme.styleTable(table);
        card.add(UiTheme.createScrollPane(table), BorderLayout.CENTER);

        // Nút thao tác
        btnAdd = UiTheme.createPrimaryButton("+ Đăng ký mới");
        btnRenew = UiTheme.createPrimaryButton("Gia hạn đăng ký đã chọn");
        btnDelete = UiTheme.createDangerButton("Xóa đăng ký đã chọn");
        btnClose = UiTheme.createSecondaryButton("Đóng");
        card.add(UiTheme.createButtonBar(btnAdd, btnRenew, btnDelete, btnClose), BorderLayout.SOUTH);

        mainPanel.add(card, BorderLayout.CENTER);
        add(mainPanel);

        txtSearch.addActionListener(e -> btnSearch.doClick());
        btnClose.addActionListener(e -> dispose());
    }

    public String getSearchKeyword() { return txtSearch.getText().trim(); }
    public String getSearchField() { return rbEmail.isSelected() ? SEARCH_FIELD_EMAIL : SEARCH_FIELD_NAME; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JTable getTable() { return table; }

    public int getSelectedRegistrationId() {
        int row = table.getSelectedRow();
        if (row < 0) return -1;
        Object value = tableModel.getValueAt(row, 0);
        if (value instanceof Integer) return (Integer) value;
        try { return Integer.parseInt(value.toString()); } catch (Exception ex) { return -1; }
    }

    public void onSearch(ActionListener listener) { btnSearch.addActionListener(listener); }
    public void onReload(ActionListener listener) { btnReload.addActionListener(listener); }
    public void onAdd(ActionListener listener) { btnAdd.addActionListener(listener); }
    public void onRenew(ActionListener listener) { btnRenew.addActionListener(listener); }
    public void onDelete(ActionListener listener) { btnDelete.addActionListener(listener); }

    public void showMessage(String msg) { JOptionPane.showMessageDialog(this, msg); }
    public void showError(String msg) { JOptionPane.showMessageDialog(this, msg, "Lỗi", JOptionPane.ERROR_MESSAGE); }
    public boolean confirm(String msg, String title) {
        return JOptionPane.showConfirmDialog(this, msg, title, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
}
