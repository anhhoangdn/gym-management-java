package controller;

import java.text.SimpleDateFormat;
import java.util.List;
import model.Registration;
import repository.RegistrationRepository;
import util.Operation;
import view.RegistrationView;
import javax.swing.table.DefaultTableModel;

public class ShowAllRegistrations implements Operation {

    private final RegistrationRepository regRepo;

    public ShowAllRegistrations(RegistrationRepository regRepo) {
        this.regRepo = regRepo;
    }

    @Override
    public void execute() {
        RegistrationView view = RegistrationView.showAllList();
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);

        List<Registration> list = regRepo.findAll();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (Registration reg : list) {
            Object[] row = {
                reg.getId(),
                reg.getUserId(),
                reg.getPackageId(),
                reg.getStartDate() != null ? sdf.format(reg.getStartDate()) : "",
                reg.getEndDate() != null ? sdf.format(reg.getEndDate()) : "",
                reg.getTotal(),
                reg.getStatusLabel()
            };
            model.addRow(row);
        }
    }
}
