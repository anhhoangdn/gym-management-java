package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MemberView extends JFrame {

    private JTextField txtId;
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtEmail;
    private JTextField txtPhone;
    private JPasswordField txtPassword;
    private JButton btnConfirm;
    private JButton btnCancel;
    private JTable table;
    private DefaultTableModel tableModel;

    public static MemberView showList() {
        MemberView view = new MemberView("Danh sách Hội viên", false, false);
        view.setVisible(true);
        return view;
    }

    public static MemberView showAddForm() {
        MemberView view = new MemberView("Thêm Hội viên mới", true, false);
        view.setVisible(true);
        return view;
    }

    public static MemberView showEditForm() {
        MemberView view = new MemberView("Cập nhật Hội viên", true, true);
        view.setVisible(true);
        return view;
    }

    public static MemberView showDeleteForm() {
        MemberView view = new MemberView("Xóa Hội viên", false, true);
        view.setVisible(true);
        return view;
    }

    private MemberView(String title, boolean showFullForm, boolean showIdField) {
        setTitle(title);
        setSize(740, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = UiTheme.createPagePanel();
        mainPanel.add(UiTheme.createHeaderPanel(title, buildSubtitle(showFullForm, showIdField)), BorderLayout.NORTH);

        if (showFullForm || showIdField) {
            JPanel formPanel = UiTheme.createFormPanel();
            int row = 0;
            if (showIdField) {
                txtId = UiTheme.createTextField();
                UiTheme.addFormRow(formPanel, row++, "ID Hội viên:", txtId);
            }
            if (showFullForm) {
                txtFirstName = UiTheme.createTextField();
                UiTheme.addFormRow(formPanel, row++, "Họ:", txtFirstName);

                txtLastName = UiTheme.createTextField();
                UiTheme.addFormRow(formPanel, row++, "Tên:", txtLastName);

                txtEmail = UiTheme.createTextField();
                UiTheme.addFormRow(formPanel, row++, "Email:", txtEmail);

                txtPhone = UiTheme.createTextField();
                UiTheme.addFormRow(formPanel, row++, "Số điện thoại:", txtPhone);

                txtPassword = UiTheme.createPasswordField();
                UiTheme.addFormRow(formPanel, row++, "Mật khẩu:", txtPassword);
            }
            btnConfirm = UiTheme.createPrimaryButton("Xác nhận");
            btnCancel  = UiTheme.createSecondaryButton("Hủy");

            JPanel card = UiTheme.createCardPanel(new BorderLayout(0, 14));
            card.add(formPanel, BorderLayout.CENTER);
            card.add(UiTheme.createButtonBar(btnConfirm, btnCancel), BorderLayout.SOUTH);
            mainPanel.add(card, BorderLayout.CENTER);

        } else {
            String[] columns = {"ID", "Họ", "Tên", "Email", "Số điện thoại"};
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
            return "Nhập thông tin hội viên để hoàn tất thao tác.";
        }
        if (showIdField) {
            return "Nhập ID hội viên cần thao tác.";
        }
        return "Danh sách hội viên hiện tại trong hệ thống.";
    }

    public String getMemberId()     { return txtId != null ? txtId.getText().trim() : ""; }
    public String getFirstName()    { return txtFirstName != null ? txtFirstName.getText().trim() : ""; }
    public String getLastName()     { return txtLastName != null ? txtLastName.getText().trim() : ""; }
    public String getEmail()        { return txtEmail != null ? txtEmail.getText().trim() : ""; }
    public String getPhone()        { return txtPhone != null ? txtPhone.getText().trim() : ""; }
    public String getPassword()     { return txtPassword != null ? new String(txtPassword.getPassword()).trim() : ""; }
    public JButton getBtnConfirm()  { return btnConfirm; }
    public JButton getBtnCancel()   { return btnCancel; }
    public JTable  getTable()       { return table; }
    public DefaultTableModel getTableModel() { return tableModel; }

    public void fillForm(String id, String firstName, String lastName, String email, String phone) {
        if (txtId != null)        txtId.setText(id);
        if (txtFirstName != null) txtFirstName.setText(firstName);
        if (txtLastName != null)  txtLastName.setText(lastName);
        if (txtEmail != null)     txtEmail.setText(email);
        if (txtPhone != null)     txtPhone.setText(phone);
    }

    public void showMessage(String msg) { JOptionPane.showMessageDialog(this, msg); }
    public void showError(String msg)   { JOptionPane.showMessageDialog(this, msg, "Lỗi", JOptionPane.ERROR_MESSAGE); }
}
