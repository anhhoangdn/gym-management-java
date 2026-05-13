package controller;

import java.util.List;
import javax.swing.table.DefaultTableModel;
import model.Package;
import repository.PackageRepository;
import util.Operation;
import view.PackageView;

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

        List<Package> packages = packageRepo.findAll();
        for (Package gymPackage : packages) {
            Object[] row = {
                    gymPackage.getId(),
                    valueOrEmpty(gymPackage.getPackageName()),
                    gymPackage.getDuration(),
                    gymPackage.getPrice(),
                    valueOrEmpty(gymPackage.getDescription()),
                    gymPackage.getStatusLabel()
            };
            model.addRow(row);
        }
    }

    private String valueOrEmpty(String value) {
        return value == null ? "" : value;
    }
}
