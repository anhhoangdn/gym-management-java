package controller;

import util.Operation;
import view.RegistrationView;

public class RenewRegistration implements Operation {

    private RegistrationView registrationView;

    public RenewRegistration() {
        // TODO: initialize RegistrationView and any required dependencies
        this.registrationView = new RegistrationView();
    }

    @Override
    public void execute() {
        // TODO: display the registration list, allow the admin to select a registration,
        //       extend the endDate based on the package duration,
        //       and update the registration record in the database
    }
}
