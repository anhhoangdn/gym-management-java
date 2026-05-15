package controller;

import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import model.Member;
import model.RegistrationRow;
import model.User;
import repository.RegistrationRepository;
import repository.UserRepository;
import util.InputValidator;
import util.Operation;
import view.MemberFormDialog;
import view.MemberView;

public class MemberManagement implements Operation {

    private final UserRepository userRepo;
    private final RegistrationRepository registrationRepo;
    private MemberView view;

    public MemberManagement(UserRepository userRepo, RegistrationRepository registrationRepo) {
        this.userRepo = userRepo;
        this.registrationRepo = registrationRepo;
    }

    @Override
    public void execute() {
        if (view != null && view.isDisplayable()) {
            view.toFront();
            view.requestFocus();
            return;
        }

        view = new MemberView();
        view.onSearch(e -> loadData(view.getSearchField(), view.getSearchKeyword()));
        view.onReload(e -> { view.getTableModel().setRowCount(0); loadData(null, null); });
        view.onAdd(e -> openAddDialog());
        view.onEdit(e -> openEditDialog());
        view.onDelete(e -> deleteSelected());

        loadData(null, null);
        view.setVisible(true);
    }

    private void loadData(String field, String keyword) {
        List<Member> members = (field == null || keyword == null || keyword.isBlank())
                ? userRepo.findAllMembers()
                : userRepo.searchMembers(field, keyword);

        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);
        for (Member m : members) {
            model.addRow(new Object[]{
                    m.getId(), m.getFirstName(), m.getLastName(),
                    m.getEmail(), m.getPhoneNumber()
            });
        }
    }

    private void openAddDialog() {
        MemberFormDialog dialog = new MemberFormDialog(view, false);
        dialog.onSave(e -> handleSave(dialog, -1));
        dialog.setVisible(true);
    }

    private void openEditDialog() {
        int id = view.getSelectedMemberId();
        if (id < 0) {
            view.showError("Vui lòng chọn một hội viên trong bảng trước.");
            return;
        }
        User user = userRepo.findById(id);
        if (user == null) {
            view.showError("Không tìm thấy hội viên ID = " + id);
            return;
        }

        MemberFormDialog dialog = new MemberFormDialog(view, true);
        dialog.fill(user.getId(), user.getFirstName(), user.getLastName(),
                user.getEmail(), user.getPhoneNumber());
        dialog.onSave(e -> handleSave(dialog, id));
        dialog.setVisible(true);
    }

    private void handleSave(MemberFormDialog dialog, int editingId) {
        String firstName = dialog.getFirstName();
        String lastName = dialog.getLastName();
        String email = dialog.getEmail();
        String phone = dialog.getPhone();
        String password = dialog.getPassword();

        if (!InputValidator.validateString(firstName) || !InputValidator.validateString(lastName)) {
            dialog.showError("Họ và tên không được để trống.");
            return;
        }
        if (!InputValidator.validateEmail(email)) {
            dialog.showError("Email không hợp lệ.");
            return;
        }
        if (!InputValidator.validatePhoneNumber(phone)) {
            dialog.showError("Số điện thoại không hợp lệ (cần 9-11 chữ số).");
            return;
        }

        // Check email trùng
        User existing = userRepo.findByEmail(email);
        if (existing != null && existing.getId() != editingId) {
            dialog.showError("Email này đã được sử dụng bởi tài khoản khác.");
            return;
        }

        if (!dialog.isEditMode()) {
            // ADD - password bắt buộc
            if (password.isEmpty()) {
                dialog.showError("Mật khẩu không được để trống.");
                return;
            }
            if (password.length() < 6) {
                dialog.showError("Mật khẩu phải có ít nhất 6 ký tự.");
                return;
            }
            Member m = new Member();
            m.setFirstName(firstName);
            m.setLastName(lastName);
            m.setEmail(email);
            m.setPhoneNumber(phone);
            m.setPassword(password);
            int newId = userRepo.createUser(m);
            if (newId > 0) {
                dialog.showMessage("Thêm hội viên thành công! ID = " + newId);
                dialog.dispose();
                loadData(null, null);
            } else {
                dialog.showError("Thêm hội viên thất bại.");
            }
        } else {
            // EDIT
            boolean updatePassword = !password.isEmpty();
            if (updatePassword && password.length() < 6) {
                dialog.showError("Mật khẩu mới phải có ít nhất 6 ký tự.");
                return;
            }
            User user = userRepo.findById(editingId);
            if (user == null) {
                dialog.showError("Hội viên không còn tồn tại.");
                return;
            }
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPhoneNumber(phone);
            if (updatePassword) {
                user.setPassword(password);
            }
            boolean ok = userRepo.updateUser(user, updatePassword);
            if (ok) {
                dialog.showMessage("Cập nhật thành công.");
                dialog.dispose();
                loadData(null, null);
            } else {
                dialog.showError("Cập nhật thất bại.");
            }
        }
    }

    private void deleteSelected() {
        int id = view.getSelectedMemberId();
        if (id < 0) {
            view.showError("Vui lòng chọn một hội viên trong bảng trước.");
            return;
        }
        User user = userRepo.findById(id);
        if (user == null) {
            view.showError("Không tìm thấy hội viên ID = " + id);
            return;
        }

        String fullName = (user.getFirstName() + " " + user.getLastName()).trim();
        List<RegistrationRow> active = registrationRepo.findActiveByUserId(id);

        StringBuilder msg = new StringBuilder();
        msg.append("Bạn có chắc muốn xóa hội viên \"").append(fullName)
           .append("\" (ID = ").append(id).append(")?");
        if (!active.isEmpty()) {
            msg.append("\n\nCẢNH BÁO: Hội viên này đang có ").append(active.size())
               .append(" đăng ký ACTIVE:");
            for (RegistrationRow r : active) {
                msg.append("\n  - ").append(r.getPackageName())
                   .append(" (kết thúc ").append(r.getEndDate()).append(")");
            }
            msg.append("\n\nXóa hội viên sẽ xóa cả các đăng ký này.");
        }

        if (!view.confirm(msg.toString(), "Xác nhận xóa hội viên")) {
            return;
        }

        boolean ok = userRepo.deleteUser(id);
        if (ok) {
            view.showMessage("Đã xóa hội viên.");
            SwingUtilities.invokeLater(() -> loadData(null, null));
        } else {
            view.showError("Xóa thất bại.");
        }
    }
}
