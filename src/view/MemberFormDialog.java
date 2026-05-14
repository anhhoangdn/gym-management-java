package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Dialog modal dùng cho cả Thêm mới và Cập nhật hội viên.
 * - Chế độ Add: ID ẩn, password bắt buộc.
 * - Chế độ Edit: ID readonly, password để trống = giữ nguyên.
 */
public class MemberFormDialog extends JDialog {

    private final boolean editMode;
    private final JLabel lblId;
    private final JTextField txtFirstName;
    private final JTextField txtLastName;
    private final JTextField txtEmail;
    private final JTextField txtPhone;
    private final JPasswordField txtPassword;
    private final JButton btnSave;
    private final JButton btnCancel;

    public MemberFormDialog(Window owner, boolean editMode) {
        super(owner, editMode ? "Cập nhật hội viên" : "Thêm hội viên mới", ModalityType.APPLICATION_MODAL);
        this.editMode = editMode;
        setSize(500, 560);
        setMinimumSize(new Dimension(460, 360));
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel mainPanel = UiTheme.createPagePanel();
        mainPanel.add(UiTheme.createHeaderPanel(getTitle(),
                editMode ? "Sửa thông tin, để trống mật khẩu nếu không đổi." :
                           "Nhập đầy đủ thông tin để tạo hội viên mới."),
                BorderLayout.NORTH);

        JPanel card = UiTheme.createCardPanel(new BorderLayout(0, 14));
        JPanel form = UiTheme.createFormPanel();
        int row = 0;

        lblId = new JLabel("");
        lblId.setFont(UiTheme.FONT_LABEL);
        lblId.setForeground(UiTheme.TEXT_MUTED);
        if (editMode) {
            UiTheme.addFormRow(form, row++, "ID:", lblId);
        }

        txtFirstName = UiTheme.createTextField();
        UiTheme.addFormRow(form, row++, "Họ:", txtFirstName);
        txtLastName = UiTheme.createTextField();
        UiTheme.addFormRow(form, row++, "Tên:", txtLastName);
        txtEmail = UiTheme.createTextField();
        UiTheme.addFormRow(form, row++, "Email:", txtEmail);
        txtPhone = UiTheme.createTextField();
        UiTheme.addFormRow(form, row++, "Số điện thoại:", txtPhone);
        txtPassword = UiTheme.createPasswordField();
        UiTheme.addFormRow(form, row++,
                editMode ? "Mật khẩu mới (để trống = giữ):" : "Mật khẩu:",
                txtPassword);

        // Bọc form trong wrapper rồi cho vào JScrollPane để khi dialog nhỏ
        // vẫn có thể scroll xuống thấy hết các trường.
        JPanel formWrapper = new JPanel(new BorderLayout());
        formWrapper.setOpaque(false);
        formWrapper.add(form, BorderLayout.NORTH);

        JScrollPane formScroll = new JScrollPane(formWrapper);
        formScroll.setBorder(null);
        formScroll.setOpaque(false);
        formScroll.getViewport().setOpaque(false);
        formScroll.getVerticalScrollBar().setUnitIncrement(16);
        card.add(formScroll, BorderLayout.CENTER);

        btnSave = UiTheme.createPrimaryButton("Lưu");
        btnCancel = UiTheme.createSecondaryButton("Hủy");
        card.add(UiTheme.createButtonBar(btnSave, btnCancel), BorderLayout.SOUTH);

        mainPanel.add(card, BorderLayout.CENTER);
        setContentPane(mainPanel);

        btnCancel.addActionListener(e -> dispose());
        getRootPane().setDefaultButton(btnSave);
    }

    public void fill(int id, String firstName, String lastName, String email, String phone) {
        lblId.setText(String.valueOf(id));
        txtFirstName.setText(firstName == null ? "" : firstName);
        txtLastName.setText(lastName == null ? "" : lastName);
        txtEmail.setText(email == null ? "" : email);
        txtPhone.setText(phone == null ? "" : phone);
        txtPassword.setText("");
    }

    public boolean isEditMode() { return editMode; }
    public String getFirstName() { return txtFirstName.getText().trim(); }
    public String getLastName() { return txtLastName.getText().trim(); }
    public String getEmail() { return txtEmail.getText().trim(); }
    public String getPhone() { return txtPhone.getText().trim(); }
    public String getPassword() { return new String(txtPassword.getPassword()); }

    public void onSave(ActionListener listener) { btnSave.addActionListener(listener); }

    public void showMessage(String msg) { JOptionPane.showMessageDialog(this, msg); }
    public void showError(String msg) { JOptionPane.showMessageDialog(this, msg, "Lỗi", JOptionPane.ERROR_MESSAGE); }
}
