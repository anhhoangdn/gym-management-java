package controller;

import util.Operation;
import view.MemberView;

public class UpdateMember implements Operation {

    private MemberView memberView;

    public UpdateMember() {
        // TODO: initialize MemberView and any required dependencies
        this.memberView = new MemberView();
    }

    @Override
    public void execute() {
        // TODO: display the member list, allow the admin to select a member,
        //       show the edit member form, collect updated data,
        //       validate input, and update the member record in the database
    }
}
