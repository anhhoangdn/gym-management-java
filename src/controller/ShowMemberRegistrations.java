package controller;

import java.text.SimpleDateFormat;
import java.util.List;
import model.Registration;
import model.User;
import repository.RegistrationRepository;
import repository.UserRepository;
import util.InputValidator;
import util.Operation;
import util.UserDisplayHelper;
import view.RegistrationView;
import javax.swing.table.DefaultTableModel;

public class ShowMemberRegistrations implements Operation {

    private final RegistrationRepository regRepo;
    private final UserRepository userRepo;

    public ShowMemberRegistrations(RegistrationRepository regRepo, UserRepository userRepo) {
        this.regRepo = regRepo;
        this.userRepo = userRepo;
    }

    @Override
    public void execute() {
        RegistrationView view = RegistrationView.showMemberList();

        view.getBtnConfirm().addActionListener(e -> {
            String userIdStr = view.getUserId();
            if (!InputValidator.validateInt(userIdStr)) {
                view.showError("ID hội viên không hợp lệ!");
                return;
            }

            int userId = Integer.parseInt(userIdStr);
            List<Registration> list = regRepo.findByUserId(userId);
            
            DefaultTableModel model = view.getTableModel();
            model.setRowCount(0); // clear existing data

            if (list.isEmpty()) {
                view.showMessage("Không tìm thấy đăng ký nào cho hội viên ID = " + userId);
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            User member = userRepo.findById(userId);
            String memberName = UserDisplayHelper.buildMemberName(member);
            for (Registration reg : list) {
                Object[] row = {
                    reg.getId(),
                    userId,
                    memberName,
                    reg.getPackageId(),
                    reg.getStartDate() != null ? sdf.format(reg.getStartDate()) : "",
                    reg.getEndDate() != null ? sdf.format(reg.getEndDate()) : "",
                    reg.getTotal(),
                    reg.getStatusLabel()
                };
                model.addRow(row);
            }
        });
    }
}
