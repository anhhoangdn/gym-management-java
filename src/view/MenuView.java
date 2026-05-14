package view;

import javax.swing.*;
import java.awt.*;

public class MenuView extends JFrame {

    private static final String TITLE = "Gym Management - Admin Panel";
    private static final String WELCOME_FORMAT = "Xin chào, %s";
    private static final String HEADER_SUBTITLE = "Bảng điều khiển quản trị";

    private JButton btnManageMembers;
    private JButton btnManagePackages;
    private JButton btnManageRegistrations;
    private JButton btnShowStatistics;
    private JButton btnEditUserData;
    private JButton btnChangePassword;
    private JButton btnLogout;

    public MenuView(String adminName) {
        setTitle(TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(720, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents(adminName);
    }

    private void initComponents(String adminName) {
        JPanel mainPanel = UiTheme.createPagePanel();

        JPanel headerPanel = UiTheme.createHeaderPanel(
                String.format(WELCOME_FORMAT, adminName), HEADER_SUBTITLE);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Khu vực 4 module chính: 2x2 grid
        btnManageMembers = createModuleButton("Quản lý Hội viên");
        btnManagePackages = createModuleButton("Quản lý Gói tập");
        btnManageRegistrations = createModuleButton("Quản lý Đăng ký");
        btnShowStatistics = createModuleButton("Thống kê");

        JPanel modulesPanel = new JPanel(new GridLayout(2, 2, 16, 16));
        modulesPanel.setOpaque(false);
        modulesPanel.add(btnManageMembers);
        modulesPanel.add(btnManagePackages);
        modulesPanel.add(btnManageRegistrations);
        modulesPanel.add(btnShowStatistics);

        JPanel modulesCard = UiTheme.createCardPanel(new BorderLayout());
        modulesCard.add(modulesPanel, BorderLayout.CENTER);

        // Khu vực tài khoản
        btnEditUserData = UiTheme.createPrimaryButton("Chỉnh sửa thông tin");
        btnChangePassword = UiTheme.createPrimaryButton("Đổi mật khẩu");
        btnLogout = UiTheme.createDangerButton("Đăng xuất");

        JPanel accountCard = UiTheme.createCardPanel(new BorderLayout(0, 8));
        accountCard.add(UiTheme.createSectionLabel("TÀI KHOẢN ADMIN"), BorderLayout.NORTH);
        accountCard.add(UiTheme.createButtonBar(btnEditUserData, btnChangePassword, btnLogout),
                BorderLayout.CENTER);

        JPanel center = new JPanel(new BorderLayout(0, 16));
        center.setOpaque(false);
        center.add(modulesCard, BorderLayout.CENTER);
        center.add(accountCard, BorderLayout.SOUTH);

        mainPanel.add(center, BorderLayout.CENTER);
        add(mainPanel);
    }

    private JButton createModuleButton(String text) {
        JButton btn = UiTheme.createPrimaryButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 16));
        btn.setPreferredSize(new Dimension(280, 100));
        return btn;
    }

    public JButton getBtnManageMembers() { return btnManageMembers; }
    public JButton getBtnManagePackages() { return btnManagePackages; }
    public JButton getBtnManageRegistrations() { return btnManageRegistrations; }
    public JButton getBtnShowStatistics() { return btnShowStatistics; }
    public JButton getBtnEditUserData() { return btnEditUserData; }
    public JButton getBtnChangePassword() { return btnChangePassword; }
    public JButton getBtnLogout() { return btnLogout; }
}
