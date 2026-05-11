package controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import model.Package;
import model.Registration;
import model.User;
import repository.PackageRepository;
import repository.RegistrationRepository;
import repository.UserRepository;
import util.InputValidator;
import util.Operation;
import view.RegistrationView;

public class AddNewRegistration implements Operation {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    private final RegistrationRepository registrationRepo;
    private final UserRepository userRepo;
    private final PackageRepository packageRepo;

    public AddNewRegistration(RegistrationRepository registrationRepo, UserRepository userRepo, PackageRepository packageRepo) {
        this.registrationRepo = registrationRepo;
        this.userRepo = userRepo;
        this.packageRepo = packageRepo;
    }

    @Override
    public void execute() {
        RegistrationView view = RegistrationView.showAddForm();

        view.getBtnCancel().addActionListener(e -> view.dispose());

        view.getBtnConfirm().addActionListener(e -> {
            String userIdStr = view.getUserId();
            String packageIdStr = view.getPackageId();
            String startDateStr = view.getStartDate();

            if (!InputValidator.validateInt(userIdStr) || Integer.parseInt(userIdStr) <= 0) {
                view.showError("ID hội viên không hợp lệ!");
                return;
            }

            if (!InputValidator.validateInt(packageIdStr) || Integer.parseInt(packageIdStr) <= 0) {
                view.showError("ID gói tập không hợp lệ!");
                return;
            }

            LocalDate startDate = parseDate(startDateStr);
            if (startDate == null) {
                view.showError("Ngày bắt đầu không hợp lệ (yyyy-MM-dd)!");
                return;
            }

            int userId = Integer.parseInt(userIdStr);
            User user = userRepo.findById(userId);
            if (user == null || user.getType() != 1) {
                view.showError("Không tìm thấy hội viên với ID = " + userId);
                return;
            }

            int packageId = Integer.parseInt(packageIdStr);
            Package gymPackage = packageRepo.findById(packageId);
            if (gymPackage == null) {
                view.showError("Không tìm thấy gói tập với ID = " + packageId);
                return;
            }
            if (gymPackage.getStatus() != 1) {
                view.showError("Gói tập này đang không hoạt động.");
                return;
            }

            LocalDate endDate = startDate.plusMonths(gymPackage.getDuration());
            Registration registration = new Registration(
                    0,
                    userId,
                    packageId,
                    java.sql.Date.valueOf(startDate),
                    java.sql.Date.valueOf(endDate),
                    gymPackage.getPrice(),
                    1
            );

            int newId = registrationRepo.createRegistration(registration);
            if (newId > 0) {
                view.showMessage("Tạo đăng ký thành công! ID mới: " + newId);
                view.dispose();
            } else {
                view.showError("Không thể tạo đăng ký mới. Vui lòng thử lại.");
            }
        });
    }

    private LocalDate parseDate(String value) {
        if (!InputValidator.validateString(value)) {
            return null;
        }
        try {
            return LocalDate.parse(value.trim(), DATE_FORMATTER);
        } catch (DateTimeParseException ex) {
            return null;
        }
    }
}
