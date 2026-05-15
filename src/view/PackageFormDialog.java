package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Dialog Thêm/Sửa gói tập. Chế độ Edit có combo Trạng thái.
 */
public class PackageFormDialog extends JDialog {

    private final boolean editMode;
    private final JLabel lblId;
    private final JTextField txtName;
    private final JTextField txtDuration;
    private final JTextField txtPrice;
    private final JTextArea txtDescription;
    private final JComboBox<String> cmbStatus;
    private final JButton btnSave;
    private final JButton btnCancel;

    public PackageFormDialog(Window owner, boolean editMode) {
        super(owner, editMode ? "Cập nhật gói tập" : "Thêm gói tập mới", ModalityType.APPLICATION_MODAL);
        this.editMode = editMode;
        setSize(560, 640);
        setMinimumSize(new Dimension(500, 420));
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel mainPanel = UiTheme.createPagePanel();
        mainPanel.add(UiTheme.createHeaderPanel(getTitle(),
                editMode ? "Cập nhật thông tin gói. Có thể chuyển sang Ngừng hoạt động."
                         : "Nhập thông tin gói tập mới."),
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

        txtName = UiTheme.createTextField();
        UiTheme.addFormRow(form, row++, "Tên gói:", txtName);

        txtDuration = UiTheme.createTextField();
        UiTheme.addFormRow(form, row++, "Thời hạn (tháng):", txtDuration);

        txtPrice = UiTheme.createTextField();
        UiTheme.addFormRow(form, row++, "Giá (VNĐ):", txtPrice);

        txtDescription = UiTheme.createTextArea(3, 20);
        UiTheme.addFormRow(form, row++, "Mô tả:", UiTheme.createInputScrollPane(txtDescription));

        cmbStatus = UiTheme.createComboBox(new String[]{"Hoạt động", "Ngừng hoạt động"});
        if (editMode) {
            UiTheme.addFormRow(form, row++, "Trạng thái:", cmbStatus);
        }

        // Bọc form trong scroll pane để form dài vẫn scroll được.
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

    public void fill(int id, String name, int duration, double price, String description, int status) {
        lblId.setText(String.valueOf(id));
        txtName.setText(name == null ? "" : name);
        txtDuration.setText(String.valueOf(duration));
        txtPrice.setText(String.valueOf((long) price));
        txtDescription.setText(description == null ? "" : description);
        cmbStatus.setSelectedIndex(status == 1 ? 0 : 1);
    }

    public boolean isEditMode() { return editMode; }
    public String getPackageName() { return txtName.getText().trim(); }
    public String getDurationText() { return txtDuration.getText().trim(); }
    public String getPriceText() { return txtPrice.getText().trim(); }
    public String getDescription() { return txtDescription.getText().trim(); }
    /** 1 = active, 0 = inactive. Mặc định 1 khi thêm mới. */
    public int getStatus() { return cmbStatus.getSelectedIndex() == 0 ? 1 : 0; }

    public void onSave(ActionListener listener) { btnSave.addActionListener(listener); }

    public void showMessage(String msg) { JOptionPane.showMessageDialog(this, msg); }
    public void showError(String msg) { JOptionPane.showMessageDialog(this, msg, "Lỗi", JOptionPane.ERROR_MESSAGE); }
}
