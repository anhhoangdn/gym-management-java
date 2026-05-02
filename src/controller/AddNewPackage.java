package controller;

import repository.PackageRepository;
import model.Package;
import util.InputValidator;
import util.Operation;
import view.PackageView;

public class AddNewPackage implements Operation {

    private final PackageRepository packageRepo;

    public AddNewPackage(PackageRepository packageRepo) {
        this.packageRepo = packageRepo;
    }

    @Override
    public void execute() {
        PackageView view = PackageView.showAddForm();

        view.getBtnCancel().addActionListener(e -> view.dispose());

        view.getBtnConfirm().addActionListener(e -> {
            String name     = view.getPackageName();
            String duration = view.getDuration();
            String price    = view.getPrice();
            String desc     = view.getDescription();
            int    status   = view.getStatus();

            if (name.isEmpty()) { view.showError("Tên gói không được để trống!"); return; }
            if (!InputValidator.validateInt(duration)) { view.showError("Thời hạn phải là số nguyên dương!"); return; }
            if (!InputValidator.validateDouble(price)) { view.showError("Giá tiền không hợp lệ!"); return; }

            Package pkg = new Package(0, name, Integer.parseInt(duration), Double.parseDouble(price), desc, status);

            int newId = packageRepo.createPackage(pkg);
            if (newId > 0) {
                view.showMessage("Thêm gói tập thành công! ID mới: " + newId);
                view.dispose();
            } else {
                view.showError("Thêm gói tập thất bại, vui lòng thử lại!");
            }
        });
    }
}