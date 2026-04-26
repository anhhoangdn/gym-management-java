package controller;

import util.Operation;
import view.MemberView;

public class DeleteMember implements Operation {

    private MemberView memberView;

    public DeleteMember() {
        // TODO: initialize MemberView and any required dependencies
        this.memberView = new MemberView();
    }

    @Override
    public void execute() {
        // TODO: display the member list, allow the admin to select a member,
        //       confirm deletion, and remove the member from the database
    }
}
