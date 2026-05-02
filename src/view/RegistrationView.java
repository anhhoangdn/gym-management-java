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
        setSize(800, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 248, 255));

        JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        switch (mode) {
            case "all":
                buildTable("all");
                mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
                break;
            case "member":
                buildTable("member");
                JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                searchPanel.setBackground(new Color(240, 248, 255));
                searchPanel.add(new JLabel("ID Hội viên:"));
                txtUserId  = new JTextField(10);
                btnConfirm = makeButton("Tìm", new Color(52, 120, 200));
                searchPanel.add(txtUserId);
                searchPanel.add(btnConfirm);
                mainPanel.add(searchPanel, BorderLayout.NORTH);
                mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
                break;
            case "add":
                JPanel addForm = new JPanel(new GridLayout(0, 2, 10, 10));
                addForm.setBackground(new Color(240, 248, 255));
                addForm.add(makeLabel("ID Hội viên:"));
                txtUserId = new JTextField(); addForm.add(txtUserId);
                addForm.add(makeLabel("ID Gói tập:"));
                txtPackageId = new JTextField(); addForm.add(txtPackageId);
                addForm.add(makeLabel("Ngày bắt đầu (yyyy-MM-dd):"));
                txtStartDate = new JTextField(); addForm.add(txtStartDate);
                mainPanel.add(buildFormWithButtons(addForm), BorderLayout.CENTER);
                break;
            case "renew":
            case "cancel":
                JPanel simpleForm = new JPanel(new GridLayout(0, 2, 10, 10));
                simpleForm.setBackground(new Color(240, 248, 255));
                simpleForm.add(makeLabel("ID Đăng ký:"));
                txtRegistrationId = new JTextField(); simpleForm.add(txtRegistrationId);
                mainPanel.add(buildFormWithButtons(simpleForm), BorderLayout.CENTER);
                break;
        }

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
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(52, 120, 200));
        table.getTableHeader().setForeground(Color.WHITE);
    }

    private JPanel buildFormWithButtons(JPanel formPanel) {
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnPanel.setBackground(new Color(240, 248, 255));
        btnConfirm = makeButton("Xác nhận", new Color(52, 120, 200));
        btnCancel  = makeButton("Hủy", new Color(180, 50, 50));
        btnPanel.add(btnConfirm);
        btnPanel.add(btnCancel);
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(new Color(240, 248, 255));
        wrapper.add(formPanel, BorderLayout.CENTER);
        wrapper.add(btnPanel, BorderLayout.SOUTH);
        return wrapper;
    }

    private JLabel makeLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Arial", Font.BOLD, 13));
        return lbl;
    }

    private JButton makeButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(130, 40));
        return btn;
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