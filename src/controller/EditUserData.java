package controller;

import model.User;
import repository.UserRepository;
import util.InputValidator;
import util.Operation;
import javax.swing.*;
import java.awt.*;

public class EditUserData implements Operation {

    private final UserRepository userRepo;
    private final User currentUser;

    public EditUserData(UserRepository userRepo, User currentUser) {
        this.userRepo = userRepo;
        this.currentUser = currentUser;
    }

    @Override
    public void execute() {
        JTextField txtFirstName = new JTextField(currentUser.getFirstName());
        JTextField txtLastName = new JTextField(currentUser.getLastName());
        JTextField txtEmail = new JTextField(currentUser.getEmail());
        JTextField txtPhone = new JTextField(currentUser.getPhoneNumber());

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Họ:"));
        panel.add(txtFirstName);
        panel.add(new JLabel("Tên:"));
        panel.add(txtLastName);
        panel.add(new JLabel("Email:"));
        panel.add(txtEmail);
        panel.add(new JLabel("Số điện thoại:"));
        panel.add(txtPhone);

        int result = JOptionPane.showConfirmDialog(null, panel, 
                 "Chỉnh sửa thông tin cá nhân", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            String firstName = txtFirstName.getText();
            String lastName = txtLastName.getText();
            String email = txtEmail.getText();
            String phone = txtPhone.getText();

            if (!InputValidator.validateString(firstName) || !InputValidator.validateString(lastName)) {
                JOptionPane.showMessageDialog(null, "Họ và tên không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!InputValidator.validateEmail(email)) {
                JOptionPane.showMessageDialog(null, "Email không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!InputValidator.validatePhoneNumber(phone)) {
                JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!email.equals(currentUser.getEmail())) {
                User existingUser = userRepo.findByEmail(email);
                if (existingUser != null && existingUser.getId() != currentUser.getId()) {
                    JOptionPane.showMessageDialog(null, "Email này đã được sử dụng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            currentUser.setFirstName(firstName);
            currentUser.setLastName(lastName);
            currentUser.setEmail(email);
            currentUser.setPhoneNumber(phone);

            boolean success = userRepo.updateUser(currentUser, false);
            if (success) {
                JOptionPane.showMessageDialog(null, "Cập nhật thông tin thành công!");
            } else {
                JOptionPane.showMessageDialog(null, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
