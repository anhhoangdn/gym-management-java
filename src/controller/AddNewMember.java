package controller;

import model.Member;
import repository.UserRepository;
import util.InputValidator;
import util.Operation;
import view.MemberView;

public class AddNewMember implements Operation {

    private final UserRepository userRepo;

    public AddNewMember(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void execute() {
        MemberView view = MemberView.showAddForm();

        view.getBtnCancel().addActionListener(e -> view.dispose());

        view.getBtnConfirm().addActionListener(e -> {
            String firstName = view.getFirstName();
            String lastName = view.getLastName();
            String email = view.getEmail();
            String phone = view.getPhone();
            String password = view.getPassword();

            if (!InputValidator.validateString(firstName) ||
                    !InputValidator.validateString(lastName) ||
                    !InputValidator.validateString(password)) {
                view.showError("Vui lòng điền đầy đủ các thông tin cơ bản: Họ, Tên và Mật khẩu.");
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

            if (userRepo.findByEmail(email) != null) {
                view.showError("Email này đã được sử dụng. Vui lòng chọn email khác.");
                return;
            }

            Member member = new Member();
            member.setFirstName(firstName);
            member.setLastName(lastName);
            member.setEmail(email);
            member.setPhoneNumber(phone);
            member.setPassword(password);

            int id = userRepo.createUser(member);
            if (id > 0) {
                view.showMessage("Thêm hội viên thành công! ID của hội viên mới là: " + id);
                view.dispose();
            } else {
                view.showError("Đã xảy ra lỗi khi lưu hội viên vào cơ sở dữ liệu.");
            }
        });
    }
}
