package controller;

import java.sql.Connection;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.GridLayout;
import model.Admin;
import repository.PackageRepository;
import repository.RegistrationRepository;
import repository.UserRepository;
import view.MenuView;
import util.DatabaseConnection;
import util.InputValidator;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().start());
    }

    private void start() {
        if (!verifyDatabaseConnection()) {
            return;
        }

        UserRepository userRepo = new UserRepository();
        PackageRepository packageRepo = new PackageRepository();
        RegistrationRepository registrationRepo = new RegistrationRepository();

        showLogin(userRepo, packageRepo, registrationRepo);
    }

    private boolean verifyDatabaseConnection() {
        try (Connection con = DatabaseConnection.getConnection()) {
            return true;
        } catch (Exception ex) {
            showError("Không thể kết nối cơ sở dữ liệu. Vui lòng kiểm tra .env và cấu hình DB.\n" + ex.getMessage());
            return false;
        }
    }

    private void showLogin(UserRepository userRepo, PackageRepository packageRepo, RegistrationRepository registrationRepo) {
        while (true) {
            Credentials credentials = promptLogin();
            if (credentials == null) {
                System.exit(0);
                return;
            }

            if (!InputValidator.validateEmail(credentials.email)) {
                showError("Email không hợp lệ.");
                continue;
            }
            if (!InputValidator.validateString(credentials.password)) {
                showError("Mật khẩu không được để trống.");
                continue;
            }

            Admin admin = userRepo.authenticateAdmin(credentials.email, credentials.password);
            if (admin == null) {
                showError("Sai email hoặc mật khẩu admin.");
                continue;
            }

            openMenu(admin, userRepo, packageRepo, registrationRepo);
            return;
        }
    }

    private void openMenu(Admin admin, UserRepository userRepo, PackageRepository packageRepo, RegistrationRepository registrationRepo) {
        String adminName = formatAdminName(admin);
        MenuView menuView = new MenuView(adminName);

        menuView.getBtnAddPackage().addActionListener(e -> new AddNewPackage(packageRepo).execute());
        menuView.getBtnUpdatePackage().addActionListener(e -> new UpdatePackage(packageRepo).execute());
        menuView.getBtnDeletePackage().addActionListener(e -> new DeletePackage(packageRepo).execute());

        menuView.getBtnAddMember().addActionListener(e -> new AddNewMember(userRepo).execute());
        menuView.getBtnUpdateMember().addActionListener(e -> new UpdateMember(userRepo).execute());
        menuView.getBtnDeleteMember().addActionListener(e -> new DeleteMember(userRepo).execute());

        menuView.getBtnAddRegistration().addActionListener(
                e -> new AddNewRegistration(registrationRepo, userRepo, packageRepo).execute());
        menuView.getBtnRenewRegistration().addActionListener(
                e -> new RenewRegistration(registrationRepo, packageRepo).execute());
        menuView.getBtnCancelRegistration().addActionListener(
                e -> new CancelRegistration(registrationRepo).execute());
        menuView.getBtnShowAllRegistrations().addActionListener(
                e -> new ShowAllRegistrations(registrationRepo).execute());
        menuView.getBtnShowMemberRegistrations().addActionListener(
                e -> new ShowMemberRegistrations(registrationRepo).execute());

        menuView.getBtnEditUserData().addActionListener(e -> new EditUserData(userRepo, admin).execute());
        menuView.getBtnChangePassword().addActionListener(e -> new ChangePassword(userRepo, admin).execute());
        menuView.getBtnLogout().addActionListener(e -> handleLogout(menuView, userRepo, packageRepo, registrationRepo));

        menuView.setVisible(true);
    }

    private void handleLogout(MenuView menuView, UserRepository userRepo, PackageRepository packageRepo,
                              RegistrationRepository registrationRepo) {
        int confirm = JOptionPane.showConfirmDialog(
                menuView,
                "Bạn có chắc muốn đăng xuất?",
                "Xác nhận đăng xuất",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        menuView.dispose();
        showLogin(userRepo, packageRepo, registrationRepo);
    }

    private Credentials promptLogin() {
        JTextField txtEmail = new JTextField();
        JPasswordField txtPassword = new JPasswordField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Email Admin:"));
        panel.add(txtEmail);
        panel.add(new JLabel("Mật khẩu:"));
        panel.add(txtPassword);

        int result = JOptionPane.showConfirmDialog(null, panel, "Đăng nhập Admin",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result != JOptionPane.OK_OPTION) {
            return null;
        }

        return new Credentials(txtEmail.getText().trim(), new String(txtPassword.getPassword()));
    }

    private String formatAdminName(Admin admin) {
        String name = (admin.getFirstName() + " " + admin.getLastName()).trim();
        if (name.isEmpty()) {
            return admin.getEmail();
        }
        return name;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    private static class Credentials {
        private final String email;
        private final String password;

        private Credentials(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }
}
