package controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Member;
import model.Registration;
import repository.RegistrationRepository;
import repository.UserRepository;
import util.Operation;
import util.UserDisplayHelper;
import view.RegistrationView;
import javax.swing.table.DefaultTableModel;

public class ShowAllRegistrations implements Operation {

    private final RegistrationRepository regRepo;
    private final UserRepository userRepo;

    public ShowAllRegistrations(RegistrationRepository regRepo, UserRepository userRepo) {
        this.regRepo = regRepo;
        this.userRepo = userRepo;
    }

    @Override
    public void execute() {
        RegistrationView view = RegistrationView.showAllList();
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);

        List<Registration> list = regRepo.findAll();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<Integer, String> memberNames = buildMemberNames();

        for (Registration reg : list) {
            int userId = reg.getUserId();
            String memberName = memberNames.get(userId);
            if (memberName == null) {
                memberName = UserDisplayHelper.buildMemberName(null);
            }
            Object[] row = {
                reg.getId(),
                userId,
                memberName,
                reg.getPackageId(),
                reg.getStartDate() != null ? sdf.format(reg.getStartDate()) : "",
                reg.getEndDate() != null ? sdf.format(reg.getEndDate()) : "",
                reg.getTotal(),
                reg.getStatusLabel()
            };
            model.addRow(row);
        }
    }

    private Map<Integer, String> buildMemberNames() {
        Map<Integer, String> names = new HashMap<>();
        List<Member> members = userRepo.findAllMembers();
        for (Member member : members) {
            names.put(member.getId(), UserDisplayHelper.buildMemberName(member));
        }
        return names;
    }
}
