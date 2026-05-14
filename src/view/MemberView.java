package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Khung quản lý Hội viên: 1 bảng + thanh tìm + 4 nút thao tác.
 * Add/Edit dùng MemberFormDialog (modal).
 */
public class MemberView extends JFrame {

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
    private final JButton btnEdit;
    private final JButton btnDelete;
    private final JButton btnClose;

    public MemberView() {
        setTitle("Quản lý Hội viên");
        setSize(820, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = UiTheme.createPagePanel();
        mainPanel.add(UiTheme.createHeaderPanel("Quản lý Hội viên",
                "Tìm kiếm, thêm, sửa, xóa hội viên."), BorderLayout.NORTH);

        JPanel card = UiTheme.createCardPanel(new BorderLayout(0, 12));

        // Thanh tìm kiếm
        JPanel searchBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        searchBar.setOpaque(false);
        searchBar.add(UiTheme.createLabel("Tìm theo:"));
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
        String[] columns = {"ID", "Họ", "Tên", "Email", "Số điện thoại"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        UiTheme.styleTable(table);
        card.add(UiTheme.createScrollPane(table), BorderLayout.CENTER);

        // Thanh nút thao tác
        btnAdd = UiTheme.createPrimaryButton("+ Thêm mới");
        btnEdit = UiTheme.createPrimaryButton("Sửa người đã chọn");
        btnDelete = UiTheme.createDangerButton("Xóa người đã chọn");
        btnClose = UiTheme.createSecondaryButton("Đóng");
        JPanel actionBar = UiTheme.createButtonBar(btnAdd, btnEdit, btnDelete, btnClose);
        card.add(actionBar, BorderLayout.SOUTH);

        mainPanel.add(card, BorderLayout.CENTER);
        add(mainPanel);

        // Enter trong ô tìm = bấm Tìm
        txtSearch.addActionListener(e -> btnSearch.doClick());
        btnClose.addActionListener(e -> dispose());
    }

    public String getSearchKeyword() { return txtSearch.getText().trim(); }
    public String getSearchField() { return rbEmail.isSelected() ? SEARCH_FIELD_EMAIL : SEARCH_FIELD_NAME; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JTable getTable() { return table; }

    public int getSelectedRow() { return table.getSelectedRow(); }
    public int getSelectedMemberId() {
        int row = table.getSelectedRow();
        if (row < 0) return -1;
        Object value = tableModel.getValueAt(row, 0);
        if (value instanceof Integer) return (Integer) value;
        try { return Integer.parseInt(value.toString()); } catch (Exception ex) { return -1; }
    }

    public void onSearch(ActionListener listener) { btnSearch.addActionListener(listener); }
    public void onReload(ActionListener listener) { btnReload.addActionListener(listener); }
    public void onAdd(ActionListener listener) { btnAdd.addActionListener(listener); }
    public void onEdit(ActionListener listener) { btnEdit.addActionListener(listener); }
    public void onDelete(ActionListener listener) { btnDelete.addActionListener(listener); }

    public void showMessage(String msg) { JOptionPane.showMessageDialog(this, msg); }
    public void showError(String msg) { JOptionPane.showMessageDialog(this, msg, "Lỗi", JOptionPane.ERROR_MESSAGE); }
    public boolean confirm(String msg, String title) {
        return JOptionPane.showConfirmDialog(this, msg, title, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
}
