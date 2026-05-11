package controller;

import model.Member;
import repository.UserRepository;
import util.InputValidator;
import util.Operation;
import view.MemberView;

public class UpdateMember implements Operation {

    private final UserRepository userRepo;

    public UpdateMember(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void execute() {
        MemberView view = MemberView.showEditForm();

        view.getBtnCancel().addActionListener(e -> view.dispose());

        view.getBtnConfirm().addActionListener(e -> {
            String id = view.getMemberId();
            String firstName = view.getFirstName();
            String lastName = view.getLastName();
            String email = view.getEmail();
            String phone = view.getPhone();
            String password = view.getPassword();

            if (!InputValidator.validateInt(id)) {
                view.showError("ID hội viên không hợp lệ!");
                return;
            }
            if (!InputValidator.validateString(firstName) ||
                    !InputValidator.validateString(lastName)) {
                view.showError("Vui lòng điền Họ và Tên.");
                return;
            }
            if (!InputValidator.validateEmail(email)) {
                view.showError("Email không hợp lệ.");
                return;
            }
            if (!InputValidator.validatePhoneNumber(phone)) {
                view.showError("Số điện thoại không hợp lệ (cần 9-11 chữ số).");
                return;
            }

            int memberId = Integer.parseInt(id);
            Member member = new Member();
            member.setId(memberId);
            member.setFirstName(firstName);
            member.setLastName(lastName);
            member.setEmail(email);
            member.setPhoneNumber(phone);

            boolean updatePassword = false;
            if (InputValidator.validateString(password)) {
                member.setPassword(password);
                updatePassword = true;
            }

            boolean success = userRepo.updateUser(member, updatePassword);
            if (success) {
                view.showMessage("Cập nhật hội viên thành công!");
                view.dispose();
            } else {
                view.showError("Không tìm thấy hội viên hoặc cập nhật thất bại!");
            }
        });
    }
}
