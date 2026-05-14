package controller;

import java.util.List;
import model.Member;
import repository.UserRepository;
import util.Operation;
import view.MemberView;
import javax.swing.table.DefaultTableModel;

public class ShowAllMembers implements Operation {

    private final UserRepository userRepo;

    public ShowAllMembers(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void execute() {
        MemberView view = MemberView.showList();
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);

        List<Member> list = userRepo.findAllMembers();

        for (Member member : list) {
            Object[] row = {
                member.getId(),
                member.getFirstName(),
                member.getLastName(),
                member.getEmail(),
                member.getPhoneNumber()
            };
            model.addRow(row);
        }
    }
}
