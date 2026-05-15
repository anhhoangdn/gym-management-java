package controller;

import java.text.DecimalFormat;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import model.Package;
import repository.PackageRepository;
import util.InputValidator;
import util.Operation;
import view.PackageFormDialog;
import view.PackageView;

public class PackageManagement implements Operation {

    private static final DecimalFormat PRICE_FORMAT = new DecimalFormat("#,###");

    private final PackageRepository packageRepo;
    private PackageView view;

    public PackageManagement(PackageRepository packageRepo) {
        this.packageRepo = packageRepo;
    }

    @Override
    public void execute() {
        if (view != null && view.isDisplayable()) {
            view.toFront();
            view.requestFocus();
            return;
        }

        view = new PackageView();
        view.onSearch(e -> loadData(view.getSearchKeyword()));
        view.onReload(e -> loadData(null));
        view.onAdd(e -> openAddDialog());
        view.onEdit(e -> openEditDialog());

        loadData(null);
        view.setVisible(true);
    }

    private void loadData(String keyword) {
        List<Package> packages = (keyword == null || keyword.isBlank())
                ? packageRepo.findAll()
                : packageRepo.searchByName(keyword);

        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);
        for (Package p : packages) {
            model.addRow(new Object[]{
                    p.getId(), p.getPackageName(), p.getDuration(),
                    PRICE_FORMAT.format(p.getPrice()),
                    p.getDescription(),
                    p.getStatus() == 1 ? "Hoạt động" : "Ngừng hoạt động"
            });
        }
    }

    private void openAddDialog() {
        PackageFormDialog dialog = new PackageFormDialog(view, false);
        dialog.onSave(e -> handleSave(dialog, -1));
        dialog.setVisible(true);
    }

    private void openEditDialog() {
        int id = view.getSelectedPackageId();
        if (id < 0) {
            view.showError("Vui lòng chọn một gói tập trong bảng trước.");
            return;
        }
        Package pkg = packageRepo.findById(id);
        if (pkg == null) {
            view.showError("Không tìm thấy gói tập ID = " + id);
            return;
        }

        PackageFormDialog dialog = new PackageFormDialog(view, true);
        dialog.fill(pkg.getId(), pkg.getPackageName(), pkg.getDuration(),
                pkg.getPrice(), pkg.getDescription(), pkg.getStatus());
        dialog.onSave(e -> handleSave(dialog, id));
        dialog.setVisible(true);
    }

    private void handleSave(PackageFormDialog dialog, int editingId) {
        String name = dialog.getPackageName();
        String durationStr = dialog.getDurationText();
        String priceStr = dialog.getPriceText();
        String description = dialog.getDescription();

        if (!InputValidator.validateString(name)) {
            dialog.showError("Tên gói không được để trống.");
            return;
        }
        if (!InputValidator.validateInt(durationStr) || Integer.parseInt(durationStr) <= 0) {
            dialog.showError("Thời hạn phải là số nguyên dương (tháng).");
            return;
        }
        if (!InputValidator.validateDouble(priceStr) || Double.parseDouble(priceStr) < 0) {
            dialog.showError("Giá phải là số không âm.");
            return;
        }

        int duration = Integer.parseInt(durationStr);
        double price = Double.parseDouble(priceStr);

        if (!dialog.isEditMode()) {
            // ADD - status mặc định = 1
            Package pkg = new Package(0, name, duration, price, description, 1);
            int newId = packageRepo.createPackage(pkg);
            if (newId > 0) {
                dialog.showMessage("Thêm gói tập thành công! ID = " + newId);
                dialog.dispose();
                loadData(null);
            } else {
                dialog.showError("Thêm gói tập thất bại.");
            }
        } else {
            // EDIT - cho phép đổi status
            Package pkg = new Package(editingId, name, duration, price, description, dialog.getStatus());
            boolean ok = packageRepo.updatePackage(pkg);
            if (ok) {
                dialog.showMessage("Cập nhật gói tập thành công.");
                dialog.dispose();
                loadData(null);
            } else {
                dialog.showError("Cập nhật thất bại.");
            }
        }
    }
}
