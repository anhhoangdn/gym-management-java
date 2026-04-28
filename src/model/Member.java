package model;

public class Member extends User {

    public Member() {
        super();
        setType(1);
    }

    public Member(int id, String firstName, String lastName, String email,
                  String phoneNumber, String password, int type) {
        super(id, firstName, lastName, email, phoneNumber, password, type);
        setType(1);
    }

    @Override
    public void showList() {
        System.out.println(this);
    }
}
