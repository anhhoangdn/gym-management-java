package controller;

import util.Operation;
import view.MemberView;

public class AddNewMember implements Operation {

    private MemberView memberView;

    public AddNewMember() {
        // TODO: initialize MemberView and any required dependencies
        this.memberView = new MemberView();
    }

    @Override
    public void execute() {
        // TODO: display the add member form, collect input,
        //       validate data, and save the new member to the database
    }
}
