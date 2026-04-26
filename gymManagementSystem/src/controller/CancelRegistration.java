package controller;

import util.Operation;
import view.RegistrationView;

public class CancelRegistration implements Operation {

    private RegistrationView registrationView;

    public CancelRegistration() {
        // TODO: initialize RegistrationView and any required dependencies
        this.registrationView = new RegistrationView();
    }

    @Override
    public void execute() {
        // TODO: display the registration list, allow the admin to select a registration,
        //       confirm cancellation, and update the registration status in the database
    }
}
