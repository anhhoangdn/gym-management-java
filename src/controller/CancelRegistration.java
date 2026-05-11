package controller;

import javax.swing.JOptionPane;
import model.Registration;
import repository.RegistrationRepository;
import util.InputValidator;
import util.Operation;
import view.RegistrationView;

public class CancelRegistration implements Operation {

    private final RegistrationRepository registrationRepo;

    public CancelRegistration(RegistrationRepository registrationRepo) {
        this.registrationRepo = registrationRepo;
    }

    @Override
    public void execute() {
        RegistrationView view = RegistrationView.showCancelForm();

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

            if (registration.getStatus() == 0) {
                view.showMessage("Đăng ký này đã bị hủy trước đó.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    view,
                    "Bạn có chắc muốn hủy đăng ký ID = " + registrationId + "?",
                    "Xác nhận hủy",
                    JOptionPane.YES_NO_OPTION);

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            boolean success = registrationRepo.cancelRegistration(registrationId);
            if (success) {
                view.showMessage("Hủy đăng ký thành công!");
                view.dispose();
            } else {
                view.showError("Hủy đăng ký thất bại. Vui lòng thử lại.");
            }
        });
    }
}
