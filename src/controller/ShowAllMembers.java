package controller;

import java.util.List;
import javax.swing.table.DefaultTableModel;
import model.Member;
import repository.UserRepository;
import util.Operation;
import view.MemberView;

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

        List<Member> members = userRepo.findAllMembers();
        for (Member member : members) {
            Object[] row = {
                    member.getId(),
                    valueOrEmpty(member.getFirstName()),
                    valueOrEmpty(member.getLastName()),
                    valueOrEmpty(member.getEmail()),
                    valueOrEmpty(member.getPhoneNumber())
            };
            model.addRow(row);
        }
    }

    private String valueOrEmpty(String value) {
        return value == null ? "" : value;
    }
}
