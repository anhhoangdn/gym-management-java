package controller;

import view.MenuView;
import util.DatabaseConnection;

public class Main {

    public static void main(String[] args) {
        // TODO: initialize the application, establish database connection,
        //       prompt for login, and launch the admin menu on successful authentication
        MenuView menuView = new MenuView();
        menuView.showAdminMenu();
    }
}
