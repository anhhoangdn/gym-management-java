package view;

import javax.swing.*;
import java.awt.*;

public class StatisticsView extends JFrame {

    private JLabel lblTotalRevenue;
    private JLabel lblTotalMembers;
    private JLabel lblActivePackages;
    private JLabel lblTotalRegistrations;
    private JButton btnClose;

    public StatisticsView() {
        setTitle("Thống kê hệ thống");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = UiTheme.createPagePanel();
        mainPanel.add(UiTheme.createHeaderPanel("Thống kê", "Tổng quan về hệ thống Gym"), BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        lblTotalRevenue = new JLabel("Tổng doanh thu: 0 VNĐ");
        lblTotalRevenue.setFont(UiTheme.FONT_LABEL);
        lblTotalRevenue.setForeground(UiTheme.TEXT_PRIMARY);

        lblTotalMembers = new JLabel("Tổng số hội viên: 0");
        lblTotalMembers.setFont(UiTheme.FONT_LABEL);
        lblTotalMembers.setForeground(UiTheme.TEXT_PRIMARY);

        lblActivePackages = new JLabel("Số gói tập đang hoạt động: 0");
        lblActivePackages.setFont(UiTheme.FONT_LABEL);
        lblActivePackages.setForeground(UiTheme.TEXT_PRIMARY);

        lblTotalRegistrations = new JLabel("Tổng số lượt đăng ký: 0");
        lblTotalRegistrations.setFont(UiTheme.FONT_LABEL);
        lblTotalRegistrations.setForeground(UiTheme.TEXT_PRIMARY);

        contentPanel.add(lblTotalRevenue);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(lblTotalMembers);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(lblActivePackages);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(lblTotalRegistrations);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        btnClose = UiTheme.createSecondaryButton("Đóng");
        btnClose.addActionListener(e -> dispose());
        mainPanel.add(UiTheme.createButtonBar(btnClose), BorderLayout.SOUTH);

        add(mainPanel);
    }

    public void setStatistics(double totalRevenue, int totalMembers, int activePackages, int totalRegistrations) {
        lblTotalRevenue.setText(String.format("Tổng doanh thu: %,.0f VNĐ", totalRevenue));
        lblTotalMembers.setText("Tổng số hội viên: " + totalMembers);
        lblActivePackages.setText("Số gói tập đang hoạt động: " + activePackages);
        lblTotalRegistrations.setText("Tổng số lượt đăng ký: " + totalRegistrations);
    }
}
