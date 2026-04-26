package controller;

import util.Operation;
import view.RegistrationView;

public class ShowAllRegistrations implements Operation {

    private RegistrationView registrationView;

    public ShowAllRegistrations() {
        // TODO: initialize RegistrationView and any required dependencies
        this.registrationView = new RegistrationView();
    }

    @Override
    public void execute() {
        // TODO: fetch all registrations from the database and
        //       display them using registrationView.showRegistrationList()
    }
}
