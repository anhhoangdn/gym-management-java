package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PackageView extends JFrame {

    private JTextField txtId;
    private JTextField txtPackageName;
    private JTextField txtDuration;
    private JTextField txtPrice;
    private JTextArea  txtDescription;
    private JComboBox<String> cmbStatus;
    private JButton btnConfirm;
    private JButton btnCancel;
    private JTable table;
    private DefaultTableModel tableModel;

    public static PackageView showList() {
        PackageView view = new PackageView("Danh sách Gói tập", false, false);
        view.setVisible(true);
        return view;
    }

    public static PackageView showAddForm() {
        PackageView view = new PackageView("Thêm Gói tập mới", true, false);
        view.setVisible(true);
        return view;
    }

    public static PackageView showEditForm() {
        PackageView view = new PackageView("Cập nhật Gói tập", true, true);
        view.setVisible(true);
        return view;
    }

    public static PackageView showDeleteForm() {
        PackageView view = new PackageView("Xóa Gói tập", false, true);
        view.setVisible(true);
        return view;
    }

    private PackageView(String title, boolean showFullForm, boolean showIdField) {
        setTitle(title);
        setSize(700, 500);
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
                formPanel.add(makeLabel("ID Gói tập:"));
                txtId = new JTextField();
                formPanel.add(txtId);
            }
            if (showFullForm) {
                formPanel.add(makeLabel("Tên gói:"));
                txtPackageName = new JTextField();
                formPanel.add(txtPackageName);

                formPanel.add(makeLabel("Thời hạn (tháng):"));
                txtDuration = new JTextField();
                formPanel.add(txtDuration);

                formPanel.add(makeLabel("Giá (VNĐ):"));
                txtPrice = new JTextField();
                formPanel.add(txtPrice);

                formPanel.add(makeLabel("Mô tả:"));
                txtDescription = new JTextArea(3, 20);
                formPanel.add(new JScrollPane(txtDescription));

                formPanel.add(makeLabel("Trạng thái:"));
                cmbStatus = new JComboBox<>(new String[]{"Active (1)", "Inactive (0)"});
                formPanel.add(cmbStatus);
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
            String[] columns = {"ID", "Tên gói", "Thời hạn (tháng)", "Giá (VNĐ)", "Mô tả", "Trạng thái"};
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

    public String getPackageId()    { return txtId != null ? txtId.getText().trim() : ""; }
    public String getPackageName()  { return txtPackageName != null ? txtPackageName.getText().trim() : ""; }
    public String getDuration()     { return txtDuration != null ? txtDuration.getText().trim() : ""; }
    public String getPrice()        { return txtPrice != null ? txtPrice.getText().trim() : ""; }
    public String getDescription()  { return txtDescription != null ? txtDescription.getText().trim() : ""; }
    public int    getStatus()       { return cmbStatus != null ? (cmbStatus.getSelectedIndex() == 0 ? 1 : 0) : 1; }
    public JButton getBtnConfirm()  { return btnConfirm; }
    public JButton getBtnCancel()   { return btnCancel; }
    public JTable  getTable()       { return table; }
    public DefaultTableModel getTableModel() { return tableModel; }

    public void fillForm(String id, String name, String duration, String price, String desc, int status) {
        if (txtId != null)          txtId.setText(id);
        if (txtPackageName != null) txtPackageName.setText(name);
        if (txtDuration != null)    txtDuration.setText(duration);
        if (txtPrice != null)       txtPrice.setText(price);
        if (txtDescription != null) txtDescription.setText(desc);
        if (cmbStatus != null)      cmbStatus.setSelectedIndex(status == 1 ? 0 : 1);
    }

    public void showMessage(String msg) { JOptionPane.showMessageDialog(this, msg); }
    public void showError(String msg)   { JOptionPane.showMessageDialog(this, msg, "Lỗi", JOptionPane.ERROR_MESSAGE); }
}