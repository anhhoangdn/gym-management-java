package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RegistrationView extends JFrame {

    private JTextField txtRegistrationId;
    private JTextField txtUserId;
    private JTextField txtPackageId;
    private JTextField txtStartDate;
    private JButton btnConfirm;
    private JButton btnCancel;
    private JTable table;
    private DefaultTableModel tableModel;

    public static RegistrationView showAllList() {
        RegistrationView view = new RegistrationView("Tất cả đăng ký gói tập", "all");
        view.setVisible(true);
        return view;
    }

    public static RegistrationView showMemberList() {
        RegistrationView view = new RegistrationView("Đăng ký theo Hội viên", "member");
        view.setVisible(true);
        return view;
    }

    public static RegistrationView showAddForm() {
        RegistrationView view = new RegistrationView("Tạo đăng ký mới", "add");
        view.setVisible(true);
        return view;
    }

    public static RegistrationView showRenewForm() {
        RegistrationView view = new RegistrationView("Gia hạn đăng ký", "renew");
        view.setVisible(true);
        return view;
    }

    public static RegistrationView showCancelForm() {
        RegistrationView view = new RegistrationView("Hủy đăng ký", "cancel");
        view.setVisible(true);
        return view;
    }

    private RegistrationView(String title, String mode) {
        setTitle(title);
        setSize(860, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = UiTheme.createPagePanel();

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);
        headerPanel.add(UiTheme.createHeaderPanel(title, buildSubtitle(mode)));

        switch (mode) {
            case "all":
                buildTable("all");
                mainPanel.add(UiTheme.createScrollPane(table), BorderLayout.CENTER);
                break;
            case "member":
                buildTable("member");
                JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
                searchPanel.setOpaque(false);
                searchPanel.add(UiTheme.createLabel("ID Hội viên:"));
                txtUserId  = UiTheme.createTextField();
                txtUserId.setColumns(12);
                btnConfirm = UiTheme.createPrimaryButton("Tìm");
                searchPanel.add(txtUserId);
                searchPanel.add(btnConfirm);
                headerPanel.add(Box.createVerticalStrut(10));
                headerPanel.add(searchPanel);
                mainPanel.add(UiTheme.createScrollPane(table), BorderLayout.CENTER);
                break;
            case "add":
                JPanel addForm = UiTheme.createFormPanel();
                txtUserId = UiTheme.createTextField();
                UiTheme.addFormRow(addForm, 0, "ID Hội viên:", txtUserId);
                txtPackageId = UiTheme.createTextField();
                UiTheme.addFormRow(addForm, 1, "ID Gói tập:", txtPackageId);
                txtStartDate = UiTheme.createTextField();
                UiTheme.addFormRow(addForm, 2, "Ngày bắt đầu (yyyy-MM-dd):", txtStartDate);
                mainPanel.add(buildFormWithButtons(addForm), BorderLayout.CENTER);
                break;
            case "renew":
            case "cancel":
                JPanel simpleForm = UiTheme.createFormPanel();
                txtRegistrationId = UiTheme.createTextField();
                UiTheme.addFormRow(simpleForm, 0, "ID Đăng ký:", txtRegistrationId);
                mainPanel.add(buildFormWithButtons(simpleForm), BorderLayout.CENTER);
                break;
        }

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        add(mainPanel);
    }

    private void buildTable(String mode) {
        String[] columns = mode.equals("all")
            ? new String[]{"ID", "Hội viên", "Gói tập", "Ngày bắt đầu", "Ngày kết thúc", "Tổng tiền", "Trạng thái"}
            : new String[]{"ID", "Gói tập", "Ngày bắt đầu", "Ngày kết thúc", "Tổng tiền", "Trạng thái"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        UiTheme.styleTable(table);
    }

    private JPanel buildFormWithButtons(JPanel formPanel) {
        btnConfirm = UiTheme.createPrimaryButton("Xác nhận");
        btnCancel  = UiTheme.createSecondaryButton("Hủy");

        JPanel wrapper = UiTheme.createCardPanel(new BorderLayout(0, 14));
        wrapper.add(formPanel, BorderLayout.CENTER);
        wrapper.add(UiTheme.createButtonBar(btnConfirm, btnCancel), BorderLayout.SOUTH);
        return wrapper;
    }

    private String buildSubtitle(String mode) {
        switch (mode) {
            case "member":
                return "Tra cứu đăng ký theo từng hội viên.";
            case "add":
                return "Tạo mới đăng ký gói tập cho hội viên.";
            case "renew":
                return "Gia hạn gói tập dựa trên ID đăng ký.";
            case "cancel":
                return "Hủy đăng ký gói tập khi cần thiết.";
            default:
                return "Tổng quan toàn bộ đăng ký hiện có.";
        }
    }

    public String getRegistrationId() { return txtRegistrationId != null ? txtRegistrationId.getText().trim() : ""; }
    public String getUserId()         { return txtUserId != null ? txtUserId.getText().trim() : ""; }
    public String getPackageId()      { return txtPackageId != null ? txtPackageId.getText().trim() : ""; }
    public String getStartDate()      { return txtStartDate != null ? txtStartDate.getText().trim() : ""; }
    public JButton getBtnConfirm()    { return btnConfirm; }
    public JButton getBtnCancel()     { return btnCancel; }
    public JTable  getTable()         { return table; }
    public DefaultTableModel getTableModel() { return tableModel; }

    public void showMessage(String msg) { JOptionPane.showMessageDialog(this, msg); }
    public void showError(String msg)   { JOptionPane.showMessageDialog(this, msg, "Lỗi", JOptionPane.ERROR_MESSAGE); }
}
