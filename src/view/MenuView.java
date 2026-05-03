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
        setSize(500, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents(adminName);
    }

    private void initComponents(String adminName) {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        mainPanel.setBackground(new Color(30, 30, 30));

        JLabel lblWelcome = new JLabel("Welcome, " + adminName, SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 22));
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JPanel btnPanel = new JPanel(new GridLayout(0, 1, 8, 8));
        btnPanel.setBackground(new Color(30, 30, 30));

        btnPanel.add(makeSectionLabel("── Quản lý Gói tập ──"));
        btnAddPackage    = makeButton("Thêm gói tập");
        btnUpdatePackage = makeButton("Cập nhật gói tập");
        btnDeletePackage = makeButton("Xóa gói tập");
        btnPanel.add(btnAddPackage);
        btnPanel.add(btnUpdatePackage);
        btnPanel.add(btnDeletePackage);

        btnPanel.add(makeSectionLabel("── Quản lý Hội viên ──"));
        btnAddMember    = makeButton("Thêm hội viên");
        btnUpdateMember = makeButton("Cập nhật hội viên");
        btnDeleteMember = makeButton("Xóa hội viên");
        btnPanel.add(btnAddMember);
        btnPanel.add(btnUpdateMember);
        btnPanel.add(btnDeleteMember);

        btnPanel.add(makeSectionLabel("── Quản lý Đăng ký ──"));
        btnAddRegistration         = makeButton("Tạo đăng ký mới");
        btnRenewRegistration       = makeButton("Gia hạn đăng ký");
        btnCancelRegistration      = makeButton("Hủy đăng ký");
        btnShowAllRegistrations    = makeButton("Xem tất cả đăng ký");
        btnShowMemberRegistrations = makeButton("Xem đăng ký theo hội viên");
        btnPanel.add(btnAddRegistration);
        btnPanel.add(btnRenewRegistration);
        btnPanel.add(btnCancelRegistration);
        btnPanel.add(btnShowAllRegistrations);
        btnPanel.add(btnShowMemberRegistrations);

        btnPanel.add(makeSectionLabel("── Tài khoản Admin ──"));
        btnEditUserData   = makeButton("Chỉnh sửa thông tin");
        btnChangePassword = makeButton("Đổi mật khẩu");
        btnLogout         = makeButton("Đăng xuất");
        btnLogout.setBackground(new Color(180, 50, 50));
        btnPanel.add(btnEditUserData);
        btnPanel.add(btnChangePassword);
        btnPanel.add(btnLogout);

        mainPanel.add(lblWelcome, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(btnPanel), BorderLayout.CENTER);
        add(mainPanel);
    }

    private JButton makeButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(new Color(52, 120, 200));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return btn;
    }

    private JLabel makeSectionLabel(String text) {
        JLabel lbl = new JLabel(text, SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.ITALIC, 12));
        lbl.setForeground(new Color(180, 180, 180));
        return lbl;
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