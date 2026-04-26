package controller;

import util.Operation;
import view.MenuView;

public class ChangePassword implements Operation {

    private MenuView menuView;

    public ChangePassword() {
        // TODO: initialize MenuView and any required dependencies
        this.menuView = new MenuView();
    }

    @Override
    public void execute() {
        // TODO: prompt the current user for their old password and a new password,
        //       validate both inputs, and update the password in the database
    }
}
