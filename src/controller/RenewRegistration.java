package controller;

import java.time.LocalDate;
import java.time.ZoneId;
import model.Package;
import model.Registration;
import repository.PackageRepository;
import repository.RegistrationRepository;
import util.InputValidator;
import util.Operation;
import view.RegistrationView;

public class RenewRegistration implements Operation {

    private final RegistrationRepository registrationRepo;
    private final PackageRepository packageRepo;

    public RenewRegistration(RegistrationRepository registrationRepo, PackageRepository packageRepo) {
        this.registrationRepo = registrationRepo;
        this.packageRepo = packageRepo;
    }

    @Override
    public void execute() {
        RegistrationView view = RegistrationView.showRenewForm();

        view.getBtnCancel().addActionListener(e -> view.dispose());

        view.getBtnConfirm().addActionListener(e -> {
            String registrationIdStr = view.getRegistrationId();
            if (!InputValidator.validateInt(registrationIdStr) || Integer.parseInt(registrationIdStr) <= 0) {
                view.showError("ID đăng ký không hợp lệ!");
                return;
            }

            int registrationId = Integer.parseInt(registrationIdStr);
            Registration registration = registrationRepo.findById(registrationId);
            if (registration == null) {
                view.showError("Không tìm thấy đăng ký với ID = " + registrationId);
                return;
            }

            Package gymPackage = packageRepo.findById(registration.getPackageId());
            if (gymPackage == null) {
                view.showError("Không tìm thấy gói tập cho đăng ký này.");
                return;
            }

            LocalDate currentEndDate = toLocalDate(registration.getEndDate());
            LocalDate newEndDate = currentEndDate.plusMonths(gymPackage.getDuration());
            double newTotal = registration.getTotal() + gymPackage.getPrice();

            boolean success = registrationRepo.renewRegistration(registrationId, java.sql.Date.valueOf(newEndDate), newTotal);
            if (success) {
                view.showMessage("Gia hạn đăng ký thành công! Ngày kết thúc mới: " + newEndDate);
                view.dispose();
            } else {
                view.showError("Gia hạn đăng ký thất bại. Vui lòng thử lại.");
            }
        });
    }

    private LocalDate toLocalDate(java.util.Date date) {
        if (date == null) {
            return LocalDate.now();
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
