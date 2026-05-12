package view;

import javax.swing.*;
import java.awt.*;

public class MenuView extends JFrame {

    private JButton btnAddPackage;
    private JButton btnUpdatePackage;
    private JButton btnDeletePackage;
    private JButton btnAddMember;
    private JButton btnUpdateMember;
    private JButton btnDeleteMember;
    private JButton btnAddRegistration;
    private JButton btnRenewRegistration;
    private JButton btnCancelRegistration;
    private JButton btnShowAllRegistrations;
    private JButton btnShowMemberRegistrations;
    private JButton btnEditUserData;
    private JButton btnChangePassword;
    private JButton btnLogout;

    public MenuView(String adminName) {
        setTitle("Gym Management - Admin Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(620, 760);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents(adminName);
    }

    private void initComponents(String adminName) {
        JPanel mainPanel = UiTheme.createPagePanel();

        JPanel headerPanel = UiTheme.createHeaderPanel("Xin chào, " + adminName, "Bảng điều khiển quản trị");
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        btnAddPackage    = UiTheme.createPrimaryButton("Thêm gói tập");
        btnUpdatePackage = UiTheme.createPrimaryButton("Cập nhật gói tập");
        btnDeletePackage = UiTheme.createPrimaryButton("Xóa gói tập");
        btnAddMember     = UiTheme.createPrimaryButton("Thêm hội viên");
        btnUpdateMember  = UiTheme.createPrimaryButton("Cập nhật hội viên");
        btnDeleteMember  = UiTheme.createPrimaryButton("Xóa hội viên");
        btnAddRegistration         = UiTheme.createPrimaryButton("Tạo đăng ký mới");
        btnRenewRegistration       = UiTheme.createPrimaryButton("Gia hạn đăng ký");
        btnCancelRegistration      = UiTheme.createPrimaryButton("Hủy đăng ký");
        btnShowAllRegistrations    = UiTheme.createPrimaryButton("Xem tất cả đăng ký");
        btnShowMemberRegistrations = UiTheme.createPrimaryButton("Xem đăng ký theo hội viên");
        btnEditUserData   = UiTheme.createPrimaryButton("Chỉnh sửa thông tin");
        btnChangePassword = UiTheme.createPrimaryButton("Đổi mật khẩu");
        btnLogout         = UiTheme.createDangerButton("Đăng xuất");

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);

        contentPanel.add(buildSection("Quản lý Gói tập", btnAddPackage, btnUpdatePackage, btnDeletePackage));
        contentPanel.add(Box.createVerticalStrut(12));
        contentPanel.add(buildSection("Quản lý Hội viên", btnAddMember, btnUpdateMember, btnDeleteMember));
        contentPanel.add(Box.createVerticalStrut(12));
        contentPanel.add(buildSection("Quản lý Đăng ký", btnAddRegistration, btnRenewRegistration,
                btnCancelRegistration, btnShowAllRegistrations, btnShowMemberRegistrations));
        contentPanel.add(Box.createVerticalStrut(12));
        contentPanel.add(buildSection("Tài khoản Admin", btnEditUserData, btnChangePassword, btnLogout));

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(UiTheme.BACKGROUND);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        add(mainPanel);
    }

    private JPanel buildSection(String title, JButton... buttons) {
        JPanel card = UiTheme.createCardPanel(new BorderLayout(0, 12));
        card.add(UiTheme.createSectionLabel(title), BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        buttonPanel.setOpaque(false);
        for (JButton button : buttons) {
            buttonPanel.add(button);
        }
        card.add(buttonPanel, BorderLayout.CENTER);
        return card;
    }

    public JButton getBtnAddPackage()               { return btnAddPackage; }
    public JButton getBtnUpdatePackage()            { return btnUpdatePackage; }
    public JButton getBtnDeletePackage()            { return btnDeletePackage; }
    public JButton getBtnAddMember()                { return btnAddMember; }
    public JButton getBtnUpdateMember()             { return btnUpdateMember; }
    public JButton getBtnDeleteMember()             { return btnDeleteMember; }
    public JButton getBtnAddRegistration()          { return btnAddRegistration; }
    public JButton getBtnRenewRegistration()        { return btnRenewRegistration; }
    public JButton getBtnCancelRegistration()       { return btnCancelRegistration; }
    public JButton getBtnShowAllRegistrations()     { return btnShowAllRegistrations; }
    public JButton getBtnShowMemberRegistrations()  { return btnShowMemberRegistrations; }
    public JButton getBtnEditUserData()             { return btnEditUserData; }
    public JButton getBtnChangePassword()           { return btnChangePassword; }
    public JButton getBtnLogout()                   { return btnLogout; }
}
