package controller;

import java.time.LocalDate;
import java.time.ZoneId;
import javax.swing.JOptionPane;
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
            if (gymPackage.getDuration() <= 0) {
                view.showError("Thời hạn gói tập không hợp lệ.");
                return;
            }

            if (registration.getEndDate() == null) {
                view.showError("Đăng ký thiếu ngày kết thúc, không thể gia hạn.");
                return;
            }

            LocalDate currentEndDate = toLocalDate(registration.getEndDate());
            LocalDate baseDate = currentEndDate.isBefore(LocalDate.now()) ? LocalDate.now() : currentEndDate;
            LocalDate newEndDate = baseDate.plusMonths(gymPackage.getDuration());
            double newTotal = registration.getTotal() + gymPackage.getPrice();

            String packageLabel = buildPackageLabel(gymPackage);
            int confirm = JOptionPane.showConfirmDialog(
                    view,
                    "Gia hạn gói tập " + packageLabel + " cho đăng ký ID = " + registrationId + "?",
                    "Xác nhận gia hạn",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            boolean success = registrationRepo.renewRegistration(registrationId, java.sql.Date.valueOf(newEndDate), newTotal);
            if (success) {
                view.showMessage("Gia hạn " + packageLabel + " thành công! Ngày kết thúc mới: " + newEndDate);
                view.dispose();
            } else {
                view.showError("Gia hạn đăng ký thất bại. Vui lòng thử lại.");
            }
        });
    }

    private LocalDate toLocalDate(java.util.Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private String buildPackageLabel(Package gymPackage) {
        String name = gymPackage.getPackageName() != null ? gymPackage.getPackageName().trim() : "";
        if (name.isEmpty()) {
            return "gói tập ID = " + gymPackage.getId();
        }
        return "gói tập \"" + name + "\" (ID = " + gymPackage.getId() + ")";
    }
}
