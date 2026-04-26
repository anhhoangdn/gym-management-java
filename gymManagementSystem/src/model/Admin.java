package model;

public class Admin extends User {

    public Admin() {
        // TODO: initialize Admin-specific defaults
    }

    public Admin(int id, String firstName, String lastName, String email,
                 String phoneNumber, String password, int type) {
        // TODO: call super constructor with provided fields
    }

    @Override
    public void showList() {
        // TODO: display list of admins
    }
}
