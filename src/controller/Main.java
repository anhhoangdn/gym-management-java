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
import view.SignUpView;
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
        loginView.getBtnRegister().addActionListener(e -> openRegistration(loginView, userRepo));

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

    private void openRegistration(LoginView loginView, UserRepository userRepo) {
        SignUpView signUpView = new SignUpView();

        signUpView.getBtnCancel().addActionListener(e -> signUpView.dispose());
        signUpView.getBtnConfirm().addActionListener(
                e -> handleRegistration(signUpView, loginView, userRepo));

        signUpView.setVisible(true);
    }

    private void handleRegistration(SignUpView signUpView, LoginView loginView, UserRepository userRepo) {
        String firstName = signUpView.getFirstName();
        String lastName = signUpView.getLastName();
        String email = signUpView.getEmail();
        String phone = signUpView.getPhone();
        String password = new String(signUpView.getPassword());
        String confirmPassword = new String(signUpView.getConfirmPassword());

        try {
            if (!InputValidator.validateString(firstName) || !InputValidator.validateString(lastName)) {
                signUpView.showError("Vui lòng nhập đầy đủ họ và tên.");
                return;
            }
            if (!InputValidator.validateEmail(email)) {
                signUpView.showError("Email không hợp lệ.");
                return;
            }
            if (!InputValidator.validatePhoneNumber(phone)) {
                signUpView.showError("Số điện thoại không hợp lệ (cần 9-11 chữ số).");
                return;
            }
            if (password.trim().isEmpty()) {
                signUpView.showError("Mật khẩu không được để trống.");
                return;
            }
            if (password.length() < 6) {
                signUpView.showError("Mật khẩu phải có ít nhất 6 ký tự.");
                return;
            }
            if (confirmPassword.trim().isEmpty()) {
                signUpView.showError("Vui lòng xác nhận mật khẩu.");
                return;
            }
            if (!password.equals(confirmPassword)) {
                signUpView.showError("Xác nhận mật khẩu không khớp.");
                return;
            }
            if (userRepo.findByEmail(email) != null) {
                signUpView.showError("Email này đã được sử dụng. Vui lòng chọn email khác.");
                return;
            }

            Admin admin = new Admin();
            admin.setFirstName(firstName);
            admin.setLastName(lastName);
            admin.setEmail(email);
            admin.setPhoneNumber(phone);
            admin.setPassword(password); // Lưu tạm ở object để createUser dùng

            int id = userRepo.createUser(admin);
            if (id > 0) {
                signUpView.showMessage("Đăng ký thành công! Vui lòng đăng nhập.");
                signUpView.dispose();
                loginView.setEmail(email);
                loginView.clearPassword();
                loginView.focusPassword();
            } else {
                signUpView.showError("Đã xảy ra lỗi khi tạo tài khoản admin.");
            }
        } catch (Exception ex) {
            signUpView.showError("Không thể tạo tài khoản: " + ex.getMessage());
        }
    }

    private void openMenu(Admin admin, UserRepository userRepo, PackageRepository packageRepo,
            RegistrationRepository registrationRepo) {
        String adminName = formatAdminName(admin);
        MenuView menuView = new MenuView(adminName);


        menuView.getBtnAddPackage().addActionListener(e -> new AddNewPackage(packageRepo).execute());
        menuView.getBtnUpdatePackage().addActionListener(e -> new UpdatePackage(packageRepo).execute());
        menuView.getBtnDeletePackage().addActionListener(e -> new DeletePackage(packageRepo).execute());
        menuView.getBtnShowAllPackages().addActionListener(e -> new ShowAllPackages(packageRepo).execute());


        menuView.getBtnAddMember().addActionListener(e -> new AddNewMember(userRepo).execute());
        menuView.getBtnUpdateMember().addActionListener(e -> new UpdateMember(userRepo).execute());
        menuView.getBtnDeleteMember().addActionListener(e -> new DeleteMember(userRepo).execute());
        menuView.getBtnShowAllMembers().addActionListener(e -> new ShowAllMembers(userRepo).execute());

        menuView.getBtnAddRegistration().addActionListener(
                e -> new AddNewRegistration(registrationRepo, userRepo, packageRepo).execute());
        menuView.getBtnRenewRegistration().addActionListener(
                e -> new RenewRegistration(registrationRepo, packageRepo).execute());
        menuView.getBtnCancelRegistration().addActionListener(
                e -> new CancelRegistration(registrationRepo).execute());
        menuView.getBtnShowAllRegistrations().addActionListener(
                e -> new ShowAllRegistrations(registrationRepo, userRepo).execute());
        menuView.getBtnShowMemberRegistrations().addActionListener(
                e -> new ShowMemberRegistrations(registrationRepo, userRepo).execute());

        menuView.getBtnShowStatistics().addActionListener(
                e -> new ShowStatistics(userRepo, packageRepo, registrationRepo).execute());

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
