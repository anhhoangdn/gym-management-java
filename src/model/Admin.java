package model;

public class Admin extends User {

    public Admin() {
        super();
        setType(0);
    }

    public Admin(int id, String firstName, String lastName, String email,
                 String phoneNumber, String password, int type) {
        super(id, firstName, lastName, email, phoneNumber, password, type);
        setType(0);
    }

    @Override
    public void showList() {
        System.out.println(this);
    }
}
