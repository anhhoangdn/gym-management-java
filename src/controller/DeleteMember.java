package controller;

import repository.UserRepository;
import util.InputValidator;
import util.Operation;
import view.MemberView;
import javax.swing.JOptionPane;

public class DeleteMember implements Operation {

    private final UserRepository userRepo;

    public DeleteMember(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void execute() {
        MemberView view = MemberView.showDeleteForm();

        view.getBtnCancel().addActionListener(e -> view.dispose());

        view.getBtnConfirm().addActionListener(e -> {
            String id = view.getMemberId();

            if (!InputValidator.validateInt(id)) {
                view.showError("ID hội viên không hợp lệ!");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    view,
                    "Bạn có chắc muốn xóa hội viên ID = " + id + "?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION);

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            boolean success = userRepo.deleteUser(Integer.parseInt(id));
            if (success) {
                view.showMessage("Xóa hội viên thành công!");
                view.dispose();
            } else {
                view.showError("Không tìm thấy hội viên hoặc xóa thất bại!");
            }
        });
    }
}
