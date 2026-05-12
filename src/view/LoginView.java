package view;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnRegister;

    public LoginView() {
        setTitle("Gym Management - Đăng nhập");
        setSize(480, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = UiTheme.createPagePanel();
        mainPanel.add(UiTheme.createHeaderPanel("Gym Management", "Đăng nhập quản trị viên"), BorderLayout.NORTH);

        JPanel card = UiTheme.createCardPanel(new BorderLayout(0, 16));

        JPanel formPanel = UiTheme.createFormPanel();
        int row = 0;
        txtEmail = UiTheme.createTextField();
        UiTheme.addFormRow(formPanel, row++, "Email:", txtEmail);
        txtPassword = UiTheme.createPasswordField();
        UiTheme.addFormRow(formPanel, row++, "Mật khẩu:", txtPassword);
        card.add(formPanel, BorderLayout.CENTER);

        btnLogin = UiTheme.createPrimaryButton("Đăng nhập");
        btnRegister = UiTheme.createSecondaryButton("Tạo tài khoản");

        JPanel footer = new JPanel();
        footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));
        footer.setOpaque(false);
        footer.add(UiTheme.createButtonBar(btnLogin, btnRegister));
        footer.add(Box.createVerticalStrut(10));

        JLabel demoLabel = new JLabel("Demo admin: admin@gym.com / admin123");
        demoLabel.setFont(UiTheme.FONT_SUBTITLE);
        demoLabel.setForeground(UiTheme.TEXT_MUTED);
        demoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        footer.add(demoLabel);

        card.add(footer, BorderLayout.SOUTH);
        mainPanel.add(card, BorderLayout.CENTER);
        add(mainPanel);

        getRootPane().setDefaultButton(btnLogin);
    }

    public String getEmail() {
        return txtEmail.getText().trim();
    }

    public String getPassword() {
        return new String(txtPassword.getPassword());
    }

    public void setEmail(String email) {
        txtEmail.setText(email);
    }

    public void clearPassword() {
        txtPassword.setText("");
    }

    public void focusPassword() {
        txtPassword.requestFocusInWindow();
    }

    public JButton getBtnLogin() {
        return btnLogin;
    }

    public JButton getBtnRegister() {
        return btnRegister;
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}
