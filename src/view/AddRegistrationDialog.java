package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Dialog Đăng ký mới: 1) Chọn hội viên qua bảng có search;
 * 2) Chọn gói qua combo (chỉ gói active); 3) Ngày bắt đầu; 4) Xác nhận.
 */
public class AddRegistrationDialog extends JDialog {

    public static final String SEARCH_FIELD_NAME = "name";
    public static final String SEARCH_FIELD_EMAIL = "email";

    // Phần chọn user
    private final JRadioButton rbName;
    private final JRadioButton rbEmail;
    private final JTextField txtSearch;
    private final JButton btnSearch;
    private final JTable userTable;
    private final DefaultTableModel userTableModel;
    private final JLabel lblSelectedUser;

    // Phần chọn gói + ngày
    private final JComboBox<String> cmbPackage;
    private final JTextField txtStartDate;
    private final JLabel lblEndDate;
    private final JLabel lblTotal;

    private final JButton btnConfirm;
    private final JButton btnCancel;

    public AddRegistrationDialog(Window owner) {
        super(owner, "Đăng ký mới", ModalityType.APPLICATION_MODAL);
        setSize(740, 720);
        setMinimumSize(new Dimension(680, 500));
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel mainPanel = UiTheme.createPagePanel();
        mainPanel.add(UiTheme.createHeaderPanel("Đăng ký mới",
                "Chọn hội viên - chọn gói - chọn ngày bắt đầu."),
                BorderLayout.NORTH);

        JPanel card = UiTheme.createCardPanel(new BorderLayout(0, 12));
        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        // ===== Section 1: Chọn user =====
        JLabel section1 = UiTheme.createSectionLabel("1. CHỌN HỘI VIÊN");
        section1.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(section1);
        content.add(Box.createVerticalStrut(6));

        JPanel searchBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        searchBar.setOpaque(false);
        searchBar.setAlignmentX(Component.LEFT_ALIGNMENT);
        searchBar.add(UiTheme.createLabel("Tìm theo:"));
        rbName = new JRadioButton("Tên", true);
        rbEmail = new JRadioButton("Email");
        rbName.setOpaque(false);
        rbEmail.setOpaque(false);
        ButtonGroup g = new ButtonGroup();
        g.add(rbName);
        g.add(rbEmail);
        searchBar.add(rbName);
        searchBar.add(rbEmail);
        txtSearch = UiTheme.createTextField();
        txtSearch.setColumns(UiTheme.SEARCH_FIELD_COLUMNS + 4);
        searchBar.add(txtSearch);
        btnSearch = UiTheme.createPrimaryButton("Tìm");
        searchBar.add(btnSearch);
        content.add(searchBar);
        content.add(Box.createVerticalStrut(6));

        String[] userCols = {"ID", "Họ", "Tên", "Email"};
        userTableModel = new DefaultTableModel(userCols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        userTable = new JTable(userTableModel);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        UiTheme.styleTable(userTable);
        JScrollPane userScroll = UiTheme.createScrollPane(userTable);
        userScroll.setPreferredSize(new Dimension(640, 200));
        userScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(userScroll);

        lblSelectedUser = new JLabel("Chưa chọn hội viên");
        lblSelectedUser.setFont(UiTheme.FONT_LABEL);
        lblSelectedUser.setForeground(UiTheme.TEXT_MUTED);
        lblSelectedUser.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblSelectedUser.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 0));
        content.add(lblSelectedUser);

        content.add(Box.createVerticalStrut(12));

        // ===== Section 2: Chọn gói + ngày =====
        JLabel section2 = UiTheme.createSectionLabel("2. CHỌN GÓI VÀ NGÀY BẮT ĐẦU");
        section2.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(section2);
        content.add(Box.createVerticalStrut(6));

        JPanel form = UiTheme.createFormPanel();
        form.setAlignmentX(Component.LEFT_ALIGNMENT);
        int row = 0;
        cmbPackage = UiTheme.createComboBox(new String[0]);
        UiTheme.addFormRow(form, row++, "Gói tập:", cmbPackage);

        txtStartDate = UiTheme.createTextField();
        UiTheme.addFormRow(form, row++, "Ngày bắt đầu (yyyy-MM-dd):", txtStartDate);

        lblEndDate = new JLabel("-");
        lblEndDate.setFont(UiTheme.FONT_LABEL);
        lblEndDate.setForeground(UiTheme.TEXT_PRIMARY);
        UiTheme.addFormRow(form, row++, "Ngày kết thúc (tự tính):", lblEndDate);

        lblTotal = new JLabel("-");
        lblTotal.setFont(UiTheme.FONT_LABEL);
        lblTotal.setForeground(UiTheme.TEXT_PRIMARY);
        UiTheme.addFormRow(form, row++, "Tổng tiền (tự tính):", lblTotal);

        content.add(form);

        // Bọc trong scroll để khi dialog thu nhỏ vẫn xem được toàn bộ.
        JScrollPane contentScroll = new JScrollPane(content);
        contentScroll.setBorder(null);
        contentScroll.setOpaque(false);
        contentScroll.getViewport().setOpaque(false);
        contentScroll.getVerticalScrollBar().setUnitIncrement(16);
        card.add(contentScroll, BorderLayout.CENTER);

        btnConfirm = UiTheme.createPrimaryButton("Xác nhận");
        btnCancel = UiTheme.createSecondaryButton("Hủy");
        card.add(UiTheme.createButtonBar(btnConfirm, btnCancel), BorderLayout.SOUTH);

        mainPanel.add(card, BorderLayout.CENTER);
        setContentPane(mainPanel);

        txtSearch.addActionListener(e -> btnSearch.doClick());
        btnCancel.addActionListener(e -> dispose());
    }

    // ===== User selection =====
    public String getSearchKeyword() { return txtSearch.getText().trim(); }
    public String getSearchField() { return rbEmail.isSelected() ? SEARCH_FIELD_EMAIL : SEARCH_FIELD_NAME; }
    public DefaultTableModel getUserTableModel() { return userTableModel; }
    public JTable getUserTable() { return userTable; }

    public int getSelectedUserId() {
        int row = userTable.getSelectedRow();
        if (row < 0) return -1;
        Object v = userTableModel.getValueAt(row, 0);
        if (v instanceof Integer) return (Integer) v;
        try { return Integer.parseInt(v.toString()); } catch (Exception ex) { return -1; }
    }

    public void setSelectedUserLabel(String text) {
        lblSelectedUser.setText(text);
        lblSelectedUser.setForeground(text == null || text.startsWith("Chưa")
                ? UiTheme.TEXT_MUTED : UiTheme.PRIMARY);
    }

    // ===== Package & date =====
    public JComboBox<String> getPackageCombo() { return cmbPackage; }
    public String getStartDateText() { return txtStartDate.getText().trim(); }
    public void setStartDateText(String text) { txtStartDate.setText(text); }
    public JTextField getStartDateField() { return txtStartDate; }
    public void setEndDateLabel(String text) { lblEndDate.setText(text); }
    public void setTotalLabel(String text) { lblTotal.setText(text); }

    // ===== Listeners =====
    public void onSearch(ActionListener listener) { btnSearch.addActionListener(listener); }
    public void onConfirm(ActionListener listener) { btnConfirm.addActionListener(listener); }

    public void showMessage(String msg) { JOptionPane.showMessageDialog(this, msg); }
    public void showError(String msg) { JOptionPane.showMessageDialog(this, msg, "Lỗi", JOptionPane.ERROR_MESSAGE); }
}
