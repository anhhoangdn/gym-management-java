package view;

import javax.swing.*;
import java.awt.*;

public class SignUpView extends JFrame {

    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtEmail;
    private JTextField txtPhone;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;
    private JButton btnConfirm;
    private JButton btnCancel;

    public SignUpView() {
        setTitle("Tạo tài khoản Admin");
        setSize(540, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = UiTheme.createPagePanel();
        mainPanel.add(UiTheme.createHeaderPanel("Tạo tài khoản Admin",
                "Đăng ký quản trị viên mới để truy cập hệ thống."), BorderLayout.NORTH);

        JPanel formPanel = UiTheme.createFormPanel();
        int row = 0;
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
        txtConfirmPassword = UiTheme.createPasswordField();
        UiTheme.addFormRow(formPanel, row++, "Xác nhận mật khẩu:", txtConfirmPassword);

        btnConfirm = UiTheme.createPrimaryButton("Đăng ký");
        btnCancel = UiTheme.createSecondaryButton("Hủy");

        JPanel card = UiTheme.createCardPanel(new BorderLayout(0, 14));
        card.add(formPanel, BorderLayout.CENTER);
        card.add(UiTheme.createButtonBar(btnConfirm, btnCancel), BorderLayout.SOUTH);
        mainPanel.add(card, BorderLayout.CENTER);
        add(mainPanel);
    }

    public String getFirstName() {
        return txtFirstName.getText().trim();
    }

    public String getLastName() {
        return txtLastName.getText().trim();
    }

    public String getEmail() {
        return txtEmail.getText().trim();
    }

    public String getPhone() {
        return txtPhone.getText().trim();
    }

    public String getPassword() {
        return new String(txtPassword.getPassword()).trim();
    }

    public String getConfirmPassword() {
        return new String(txtConfirmPassword.getPassword()).trim();
    }

    public JButton getBtnConfirm() {
        return btnConfirm;
    }

    public JButton getBtnCancel() {
        return btnCancel;
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}
