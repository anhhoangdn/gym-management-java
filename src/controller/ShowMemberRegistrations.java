package controller;

import util.Operation;
import view.RegistrationView;

public class ShowMemberRegistrations implements Operation {

    private RegistrationView registrationView;

    public ShowMemberRegistrations() {
        // TODO: initialize RegistrationView and any required dependencies
        this.registrationView = new RegistrationView();
    }

    @Override
    public void execute() {
        // TODO: prompt for a member ID, fetch all registrations belonging to that member,
        //       and display them using registrationView.showRegistrationList()
    }
}
