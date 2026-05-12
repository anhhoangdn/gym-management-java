package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PackageView extends JFrame {

    private JTextField txtId;
    private JTextField txtPackageName;
    private JTextField txtDuration;
    private JTextField txtPrice;
    private JTextArea  txtDescription;
    private JComboBox<String> cmbStatus;
    private JButton btnConfirm;
    private JButton btnCancel;
    private JTable table;
    private DefaultTableModel tableModel;

    public static PackageView showList() {
        PackageView view = new PackageView("Danh sách Gói tập", false, false);
        view.setVisible(true);
        return view;
    }

    public static PackageView showAddForm() {
        PackageView view = new PackageView("Thêm Gói tập mới", true, false);
        view.setVisible(true);
        return view;
    }

    public static PackageView showEditForm() {
        PackageView view = new PackageView("Cập nhật Gói tập", true, true);
        view.setVisible(true);
        return view;
    }

    public static PackageView showDeleteForm() {
        PackageView view = new PackageView("Xóa Gói tập", false, true);
        view.setVisible(true);
        return view;
    }

    private PackageView(String title, boolean showFullForm, boolean showIdField) {
        setTitle(title);
        setSize(760, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = UiTheme.createPagePanel();
        mainPanel.add(UiTheme.createHeaderPanel(title, buildSubtitle(showFullForm, showIdField)), BorderLayout.NORTH);

        if (showFullForm || showIdField) {
            JPanel formPanel = UiTheme.createFormPanel();
            int row = 0;
            if (showIdField) {
                txtId = UiTheme.createTextField();
                UiTheme.addFormRow(formPanel, row++, "ID Gói tập:", txtId);
            }
            if (showFullForm) {
                txtPackageName = UiTheme.createTextField();
                UiTheme.addFormRow(formPanel, row++, "Tên gói:", txtPackageName);

                txtDuration = UiTheme.createTextField();
                UiTheme.addFormRow(formPanel, row++, "Thời hạn (tháng):", txtDuration);

                txtPrice = UiTheme.createTextField();
                UiTheme.addFormRow(formPanel, row++, "Giá (VNĐ):", txtPrice);

                txtDescription = UiTheme.createTextArea(3, 20);
                UiTheme.addFormRow(formPanel, row++, "Mô tả:", UiTheme.createInputScrollPane(txtDescription));

                cmbStatus = UiTheme.createComboBox(new String[]{"Active (1)", "Inactive (0)"});
                UiTheme.addFormRow(formPanel, row++, "Trạng thái:", cmbStatus);
            }
            btnConfirm = UiTheme.createPrimaryButton("Xác nhận");
            btnCancel  = UiTheme.createSecondaryButton("Hủy");

            JPanel card = UiTheme.createCardPanel(new BorderLayout(0, 14));
            card.add(formPanel, BorderLayout.CENTER);
            card.add(UiTheme.createButtonBar(btnConfirm, btnCancel), BorderLayout.SOUTH);
            mainPanel.add(card, BorderLayout.CENTER);

        } else {
            String[] columns = {"ID", "Tên gói", "Thời hạn (tháng)", "Giá (VNĐ)", "Mô tả", "Trạng thái"};
            tableModel = new DefaultTableModel(columns, 0) {
                public boolean isCellEditable(int r, int c) { return false; }
            };
            table = new JTable(tableModel);
            UiTheme.styleTable(table);
            mainPanel.add(UiTheme.createScrollPane(table), BorderLayout.CENTER);
        }

        add(mainPanel);
    }

    private String buildSubtitle(boolean showFullForm, boolean showIdField) {
        if (showFullForm) {
            return "Cập nhật thông tin gói tập rõ ràng và đầy đủ.";
        }
        if (showIdField) {
            return "Nhập ID gói tập cần thao tác.";
        }
        return "Danh sách gói tập hiện tại trong hệ thống.";
    }

    public String getPackageId()    { return txtId != null ? txtId.getText().trim() : ""; }
    public String getPackageName()  { return txtPackageName != null ? txtPackageName.getText().trim() : ""; }
    public String getDuration()     { return txtDuration != null ? txtDuration.getText().trim() : ""; }
    public String getPrice()        { return txtPrice != null ? txtPrice.getText().trim() : ""; }
    public String getDescription()  { return txtDescription != null ? txtDescription.getText().trim() : ""; }
    public int    getStatus()       { return cmbStatus != null ? (cmbStatus.getSelectedIndex() == 0 ? 1 : 0) : 1; }
    public JButton getBtnConfirm()  { return btnConfirm; }
    public JButton getBtnCancel()   { return btnCancel; }
    public JTable  getTable()       { return table; }
    public DefaultTableModel getTableModel() { return tableModel; }

    public void fillForm(String id, String name, String duration, String price, String desc, int status) {
        if (txtId != null)          txtId.setText(id);
        if (txtPackageName != null) txtPackageName.setText(name);
        if (txtDuration != null)    txtDuration.setText(duration);
        if (txtPrice != null)       txtPrice.setText(price);
        if (txtDescription != null) txtDescription.setText(desc);
        if (cmbStatus != null)      cmbStatus.setSelectedIndex(status == 1 ? 0 : 1);
    }

    public void showMessage(String msg) { JOptionPane.showMessageDialog(this, msg); }
    public void showError(String msg)   { JOptionPane.showMessageDialog(this, msg, "Lỗi", JOptionPane.ERROR_MESSAGE); }
}
