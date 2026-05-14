package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Khung quản lý Gói tập: 1 bảng + thanh tìm theo tên + nút Thêm/Sửa.
 * Không có nút Xóa - chỉ có thể chuyển trạng thái sang Inactive qua Sửa.
 */
public class PackageView extends JFrame {

    private final JTextField txtSearch;
    private final JButton btnSearch;
    private final JButton btnReload;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final JButton btnAdd;
    private final JButton btnEdit;
    private final JButton btnClose;

    public PackageView() {
        setTitle("Quản lý Gói tập");
        setSize(900, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = UiTheme.createPagePanel();
        mainPanel.add(UiTheme.createHeaderPanel("Quản lý Gói tập",
                "Tìm theo tên gói. Không thể xóa gói - chỉ chuyển trạng thái Ngừng hoạt động."),
                BorderLayout.NORTH);

        JPanel card = UiTheme.createCardPanel(new BorderLayout(0, 12));

        // Thanh tìm
        JPanel searchBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        searchBar.setOpaque(false);
        searchBar.add(UiTheme.createLabel("Tìm theo tên gói:"));
        txtSearch = UiTheme.createTextField();
        txtSearch.setColumns(UiTheme.SEARCH_FIELD_COLUMNS + 8);
        searchBar.add(txtSearch);
        btnSearch = UiTheme.createPrimaryButton("Tìm");
        btnReload = UiTheme.createSecondaryButton("Tải lại");
        searchBar.add(btnSearch);
        searchBar.add(btnReload);
        card.add(searchBar, BorderLayout.NORTH);

        // Bảng
        String[] columns = {"ID", "Tên gói", "Thời hạn (tháng)", "Giá (VNĐ)", "Mô tả", "Trạng thái"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        UiTheme.styleTable(table);
        card.add(UiTheme.createScrollPane(table), BorderLayout.CENTER);

        // Nút thao tác (không có Delete)
        btnAdd = UiTheme.createPrimaryButton("+ Thêm gói");
        btnEdit = UiTheme.createPrimaryButton("Sửa gói đã chọn");
        btnClose = UiTheme.createSecondaryButton("Đóng");
        card.add(UiTheme.createButtonBar(btnAdd, btnEdit, btnClose), BorderLayout.SOUTH);

        mainPanel.add(card, BorderLayout.CENTER);
        add(mainPanel);

        txtSearch.addActionListener(e -> btnSearch.doClick());
        btnClose.addActionListener(e -> dispose());
    }

    public String getSearchKeyword() { return txtSearch.getText().trim(); }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JTable getTable() { return table; }

    public int getSelectedPackageId() {
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

    public void showMessage(String msg) { JOptionPane.showMessageDialog(this, msg); }
    public void showError(String msg) { JOptionPane.showMessageDialog(this, msg, "Lỗi", JOptionPane.ERROR_MESSAGE); }
}
