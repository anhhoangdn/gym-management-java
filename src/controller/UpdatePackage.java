package controller;

import repository.PackageRepository;
import model.Package;
import util.InputValidator;
import util.Operation;
import view.PackageView;

public class UpdatePackage implements Operation {

    private final PackageRepository packageRepo;

    public UpdatePackage(PackageRepository packageRepo) {
        this.packageRepo = packageRepo;
    }

    @Override
    public void execute() {
        PackageView view = PackageView.showEditForm();

        view.getBtnCancel().addActionListener(e -> view.dispose());

        view.getBtnConfirm().addActionListener(e -> {
            String id       = view.getPackageId();
            String name     = view.getPackageName();
            String duration = view.getDuration();
            String price    = view.getPrice();
            String desc     = view.getDescription();
            int    status   = view.getStatus();

            if (!InputValidator.validateInt(id))       { view.showError("ID gói tập không hợp lệ!"); return; }
            if (name.isEmpty())                        { view.showError("Tên gói không được để trống!"); return; }
            if (!InputValidator.validateInt(duration)) { view.showError("Thời hạn phải là số nguyên dương!"); return; }
            if (!InputValidator.validateDouble(price)) { view.showError("Giá tiền không hợp lệ!"); return; }

            Package pkg = new Package(Integer.parseInt(id), name, Integer.parseInt(duration), Double.parseDouble(price), desc, status);

            boolean success = packageRepo.updatePackage(pkg);
            if (success) {
                view.showMessage("Cập nhật gói tập thành công!");
                view.dispose();
            } else {
                view.showError("Không tìm thấy gói tập hoặc cập nhật thất bại!");
            }
        });
    }
}