package controller;

import repository.PackageRepository;
import util.InputValidator;
import util.Operation;
import view.PackageView;
import javax.swing.JOptionPane;
public class DeletePackage implements Operation {

    private final PackageRepository packageRepo;

    public DeletePackage(PackageRepository packageRepo) {
        this.packageRepo = packageRepo;
    }

    @Override
    public void execute() {
        PackageView view = PackageView.showDeleteForm();

        view.getBtnCancel().addActionListener(e -> view.dispose());

        view.getBtnConfirm().addActionListener(e -> {
            String id = view.getPackageId();

            if (!InputValidator.validateInt(id)) { view.showError("ID gói tập không hợp lệ!"); return; }

            int confirm = JOptionPane.showConfirmDialog(
                view,
                "Bạn có chắc muốn xóa gói tập ID = " + id + "?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION
            );
            if (confirm != JOptionPane.YES_OPTION) return;

            boolean success = packageRepo.deletePackage(Integer.parseInt(id));
            if (success) {
                view.showMessage("Xóa gói tập thành công!");
                view.dispose();
            } else {
                view.showError("Không tìm thấy gói tập hoặc xóa thất bại!");
            }
        });
    }
}