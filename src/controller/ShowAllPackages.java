package controller;

import java.util.List;
import model.Package;
import repository.PackageRepository;
import util.Operation;
import view.PackageView;
import javax.swing.table.DefaultTableModel;

public class ShowAllPackages implements Operation {

    private final PackageRepository packageRepo;

    public ShowAllPackages(PackageRepository packageRepo) {
        this.packageRepo = packageRepo;
    }

    @Override
    public void execute() {
        PackageView view = PackageView.showList();
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);

        List<Package> list = packageRepo.findAll();

        for (Package pkg : list) {
            Object[] row = {
                pkg.getId(),
                pkg.getPackageName(),
                pkg.getDuration(),
                pkg.getPrice(),
                pkg.getDescription(),
                pkg.getStatus() == 1 ? "Active" : "Inactive"
            };
            model.addRow(row);
        }
    }
}
