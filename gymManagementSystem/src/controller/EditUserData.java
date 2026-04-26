package controller;

import util.Operation;
import view.MenuView;

public class EditUserData implements Operation {

    private MenuView menuView;

    public EditUserData() {
        // TODO: initialize MenuView and any required dependencies
        this.menuView = new MenuView();
    }

    @Override
    public void execute() {
        // TODO: display a form pre-filled with the current user's profile data,
        //       allow edits to firstName, lastName, email, phoneNumber,
        //       validate input, and update the record in the database
    }
}
