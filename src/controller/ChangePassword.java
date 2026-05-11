package controller;

import model.User;
import repository.UserRepository;
import util.Operation;
import util.PasswordUtil;
import javax.swing.*;
import java.awt.*;

public class ChangePassword implements Operation {

    private final UserRepository userRepo;
    private final User currentUser;

    public ChangePassword(UserRepository userRepo, User currentUser) {
        this.userRepo = userRepo;
        this.currentUser = currentUser;
    }

    @Override
    public void execute() {
        JPasswordField txtOldPassword = new JPasswordField();
        JPasswordField txtNewPassword = new JPasswordField();
        JPasswordField txtConfirmPassword = new JPasswordField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Mật khẩu cũ:"));
        panel.add(txtOldPassword);
        panel.add(new JLabel("Mật khẩu mới:"));
        panel.add(txtNewPassword);
        panel.add(new JLabel("Nhập lại mật khẩu mới:"));
        panel.add(txtConfirmPassword);

        int result = JOptionPane.showConfirmDialog(null, panel,
                "Đổi mật khẩu", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String oldPass = new String(txtOldPassword.getPassword());
            String newPass = new String(txtNewPassword.getPassword());
            String confirmPass = new String(txtConfirmPassword.getPassword());

            if (!PasswordUtil.verifyPassword(oldPass, currentUser.getPassword())) {
                JOptionPane.showMessageDialog(null, "Mật khẩu cũ không chính xác!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (newPass.isEmpty() || newPass.length() < 6) {
                JOptionPane.showMessageDialog(null, "Mật khẩu mới phải từ 6 ký tự trở lên!", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!newPass.equals(confirmPass)) {
                JOptionPane.showMessageDialog(null, "Mật khẩu nhập lại không khớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            currentUser.setPassword(newPass);
            boolean success = userRepo.updateUser(currentUser, true);

            if (success) {
                currentUser.setPassword(PasswordUtil.hashPassword(newPass));
                JOptionPane.showMessageDialog(null, "Đổi mật khẩu thành công!");
            } else {
                JOptionPane.showMessageDialog(null, "Đổi mật khẩu thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
