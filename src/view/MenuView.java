package view;

import javax.swing.*;
import java.awt.*;

public class MenuView extends JFrame {

    private static final String TITLE = "Gym Management - Admin Panel";
    private static final String WELCOME_FORMAT = "Xin chào, %s";
    private static final String HEADER_SUBTITLE = "Bảng điều khiển quản trị";
    private static final String SECTION_PACKAGE = "Quản lý Gói tập";
    private static final String SECTION_MEMBER = "Quản lý Hội viên";
    private static final String SECTION_REGISTRATION = "Quản lý Đăng ký";
    private static final String SECTION_ACCOUNT = "Tài khoản Admin";
    private static final String BTN_SHOW_PACKAGES = "Xem danh sách gói tập";
    private static final String BTN_ADD_PACKAGE = "Thêm gói tập";
    private static final String BTN_UPDATE_PACKAGE = "Cập nhật gói tập";
    private static final String BTN_DELETE_PACKAGE = "Xóa gói tập";
    private static final String BTN_SHOW_MEMBERS = "Xem danh sách hội viên";
    private static final String BTN_ADD_MEMBER = "Thêm hội viên";
    private static final String BTN_UPDATE_MEMBER = "Cập nhật hội viên";
    private static final String BTN_DELETE_MEMBER = "Xóa hội viên";
    private static final String BTN_ADD_REGISTRATION = "Tạo đăng ký mới";
    private static final String BTN_RENEW_REGISTRATION = "Gia hạn đăng ký";
    private static final String BTN_CANCEL_REGISTRATION = "Hủy đăng ký";
    private static final String BTN_SHOW_ALL_REGISTRATIONS = "Xem tất cả đăng ký";
    private static final String BTN_SHOW_MEMBER_REGISTRATIONS = "Xem đăng ký theo hội viên";
    private static final String BTN_EDIT_USER_DATA = "Chỉnh sửa thông tin";
    private static final String BTN_CHANGE_PASSWORD = "Đổi mật khẩu";
    private static final String BTN_LOGOUT = "Đăng xuất";

    private JButton btnShowPackages;
    private JButton btnAddPackage;
    private JButton btnUpdatePackage;
    private JButton btnDeletePackage;
    private JButton btnShowMembers;
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
        setTitle(TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(620, 760);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents(adminName);
    }

    private void initComponents(String adminName) {
        JPanel mainPanel = UiTheme.createPagePanel();

        JPanel headerPanel = UiTheme.createHeaderPanel(String.format(WELCOME_FORMAT, adminName), HEADER_SUBTITLE);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        btnShowPackages  = UiTheme.createPrimaryButton(BTN_SHOW_PACKAGES);
        btnAddPackage    = UiTheme.createPrimaryButton(BTN_ADD_PACKAGE);
        btnUpdatePackage = UiTheme.createPrimaryButton(BTN_UPDATE_PACKAGE);
        btnDeletePackage = UiTheme.createPrimaryButton(BTN_DELETE_PACKAGE);
        btnShowMembers   = UiTheme.createPrimaryButton(BTN_SHOW_MEMBERS);
        btnAddMember     = UiTheme.createPrimaryButton(BTN_ADD_MEMBER);
        btnUpdateMember  = UiTheme.createPrimaryButton(BTN_UPDATE_MEMBER);
        btnDeleteMember  = UiTheme.createPrimaryButton(BTN_DELETE_MEMBER);
        btnAddRegistration         = UiTheme.createPrimaryButton(BTN_ADD_REGISTRATION);
        btnRenewRegistration       = UiTheme.createPrimaryButton(BTN_RENEW_REGISTRATION);
        btnCancelRegistration      = UiTheme.createPrimaryButton(BTN_CANCEL_REGISTRATION);
        btnShowAllRegistrations    = UiTheme.createPrimaryButton(BTN_SHOW_ALL_REGISTRATIONS);
        btnShowMemberRegistrations = UiTheme.createPrimaryButton(BTN_SHOW_MEMBER_REGISTRATIONS);
        btnEditUserData   = UiTheme.createPrimaryButton(BTN_EDIT_USER_DATA);
        btnChangePassword = UiTheme.createPrimaryButton(BTN_CHANGE_PASSWORD);
        btnLogout         = UiTheme.createDangerButton(BTN_LOGOUT);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);

        contentPanel.add(buildSection(SECTION_PACKAGE, btnShowPackages, btnAddPackage, btnUpdatePackage, btnDeletePackage));
        contentPanel.add(Box.createVerticalStrut(12));
        contentPanel.add(buildSection(SECTION_MEMBER, btnShowMembers, btnAddMember, btnUpdateMember, btnDeleteMember));
        contentPanel.add(Box.createVerticalStrut(12));
        contentPanel.add(buildSection(SECTION_REGISTRATION, btnAddRegistration, btnRenewRegistration,
                btnCancelRegistration, btnShowAllRegistrations, btnShowMemberRegistrations));
        contentPanel.add(Box.createVerticalStrut(12));
        contentPanel.add(buildSection(SECTION_ACCOUNT, btnEditUserData, btnChangePassword, btnLogout));

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

    public JButton getBtnShowPackages()           { return btnShowPackages; }
    public JButton getBtnAddPackage()             { return btnAddPackage; }
    public JButton getBtnUpdatePackage()          { return btnUpdatePackage; }
    public JButton getBtnDeletePackage()          { return btnDeletePackage; }
    public JButton getBtnShowMembers()            { return btnShowMembers; }
    public JButton getBtnAddMember()              { return btnAddMember; }
    public JButton getBtnUpdateMember()           { return btnUpdateMember; }
    public JButton getBtnDeleteMember()           { return btnDeleteMember; }
    public JButton getBtnAddRegistration()        { return btnAddRegistration; }
    public JButton getBtnRenewRegistration()      { return btnRenewRegistration; }
    public JButton getBtnCancelRegistration()     { return btnCancelRegistration; }
    public JButton getBtnShowAllRegistrations()   { return btnShowAllRegistrations; }
    public JButton getBtnShowMemberRegistrations() { return btnShowMemberRegistrations; }
    public JButton getBtnEditUserData()           { return btnEditUserData; }
    public JButton getBtnChangePassword()         { return btnChangePassword; }
    public JButton getBtnLogout()                 { return btnLogout; }
}
