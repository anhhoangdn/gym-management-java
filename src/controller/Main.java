package controller;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import model.Admin;
import repository.PackageRepository;
import repository.RegistrationRepository;
import repository.UserRepository;
import view.LoginView;
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
            if (!con.isValid(2)) {
                showError("Kết nối cơ sở dữ liệu không hợp lệ. Vui lòng kiểm tra cấu hình DB.");
                return false;
            }
            return true;
        } catch (SQLException | RuntimeException ex) {
            showError("Không thể kết nối cơ sở dữ liệu. Vui lòng kiểm tra .env và cấu hình DB.\n" + ex.getMessage());
            return false;
        }
    }

    private void showLogin(UserRepository userRepo, PackageRepository packageRepo,
            RegistrationRepository registrationRepo) {
        LoginView loginView = new LoginView();

        loginView.getBtnLogin().addActionListener(
                e -> handleLogin(loginView, userRepo, packageRepo, registrationRepo));

        loginView.setVisible(true);
    }

    private void handleLogin(LoginView loginView, UserRepository userRepo,
            PackageRepository packageRepo, RegistrationRepository registrationRepo) {
        String email = loginView.getEmail();
        String password = new String(loginView.getPassword());

        if (!InputValidator.validateEmail(email)) {
            loginView.showError("Email không hợp lệ.");
            return;
        }
        if (password.trim().isEmpty()) {
            loginView.showError("Mật khẩu không được để trống.");
            return;
        }

        Admin admin = userRepo.authenticateAdmin(email, password);
        if (admin == null) {
            loginView.showError("Sai email hoặc mật khẩu admin.");
            loginView.clearPassword();
            return;
        }

        loginView.dispose();
        openMenu(admin, userRepo, packageRepo, registrationRepo);
    }

    private void openMenu(Admin admin, UserRepository userRepo, PackageRepository packageRepo,
            RegistrationRepository registrationRepo) {
        String adminName = formatAdminName(admin);
        MenuView menuView = new MenuView(adminName);

        // 4 module chính - mỗi controller là singleton để tránh chồng cửa sổ
        MemberManagement memberMgmt = new MemberManagement(userRepo, registrationRepo);
        PackageManagement packageMgmt = new PackageManagement(packageRepo);
        RegistrationManagement registrationMgmt = new RegistrationManagement(registrationRepo, userRepo, packageRepo);
        ShowStatistics statistics = new ShowStatistics(userRepo, packageRepo, registrationRepo);

        menuView.getBtnManageMembers().addActionListener(e -> memberMgmt.execute());
        menuView.getBtnManagePackages().addActionListener(e -> packageMgmt.execute());
        menuView.getBtnManageRegistrations().addActionListener(e -> registrationMgmt.execute());
        menuView.getBtnShowStatistics().addActionListener(e -> statistics.execute());

        // Tài khoản admin
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

    private String formatAdminName(Admin admin) {
        String firstName = admin.getFirstName() != null ? admin.getFirstName() : "";
        String lastName = admin.getLastName() != null ? admin.getLastName() : "";
        String name = (firstName + " " + lastName).trim();
        if (name.isEmpty()) {
            return admin.getEmail();
        }
        return name;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

}
