package controller;

import util.Operation;
import view.RegistrationView;

public class AddNewRegistration implements Operation {

    private RegistrationView registrationView;

    public AddNewRegistration() {
        // TODO: initialize RegistrationView and any required dependencies
        this.registrationView = new RegistrationView();
    }

    @Override
    public void execute() {
        // TODO: display the add registration form, collect input,
        //       validate data, calculate total, and save the new registration to the database
    }
}
