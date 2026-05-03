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
        setSize(700, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 248, 255));

        JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        if (showFullForm || showIdField) {
            JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
            formPanel.setBackground(new Color(240, 248, 255));

            if (showIdField) {
                formPanel.add(makeLabel("ID Hội viên:"));
                txtId = new JTextField();
                formPanel.add(txtId);
            }
            if (showFullForm) {
                formPanel.add(makeLabel("Họ:"));
                txtFirstName = new JTextField();
                formPanel.add(txtFirstName);

                formPanel.add(makeLabel("Tên:"));
                txtLastName = new JTextField();
                formPanel.add(txtLastName);

                formPanel.add(makeLabel("Email:"));
                txtEmail = new JTextField();
                formPanel.add(txtEmail);

                formPanel.add(makeLabel("Số điện thoại:"));
                txtPhone = new JTextField();
                formPanel.add(txtPhone);

                formPanel.add(makeLabel("Mật khẩu:"));
                txtPassword = new JPasswordField();
                formPanel.add(txtPassword);
            }

            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
            btnPanel.setBackground(new Color(240, 248, 255));
            btnConfirm = makeButton("Xác nhận", new Color(52, 120, 200));
            btnCancel  = makeButton("Hủy", new Color(180, 50, 50));
            btnPanel.add(btnConfirm);
            btnPanel.add(btnCancel);

            JPanel centerPanel = new JPanel(new BorderLayout());
            centerPanel.setBackground(new Color(240, 248, 255));
            centerPanel.add(formPanel, BorderLayout.CENTER);
            centerPanel.add(btnPanel, BorderLayout.SOUTH);
            mainPanel.add(centerPanel, BorderLayout.CENTER);

        } else {
            String[] columns = {"ID", "Họ", "Tên", "Email", "Số điện thoại"};
            tableModel = new DefaultTableModel(columns, 0) {
                public boolean isCellEditable(int r, int c) { return false; }
            };
            table = new JTable(tableModel);
            table.setFont(new Font("Arial", Font.PLAIN, 13));
            table.setRowHeight(28);
            table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
            table.getTableHeader().setBackground(new Color(52, 120, 200));
            table.getTableHeader().setForeground(Color.WHITE);
            mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        }

        add(mainPanel);
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