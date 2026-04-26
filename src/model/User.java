package model;

public abstract class User {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private int type;

    public User() {
        // TODO: initialize default values if needed
    }

    public User(int id, String firstName, String lastName, String email,
                String phoneNumber, String password, int type) {
        // TODO: assign fields
    }

    // Getters and Setters

    public int getId() {
        // TODO: return id
        return id;
    }

    public void setId(int id) {
        // TODO: set id
        this.id = id;
    }

    public String getFirstName() {
        // TODO: return firstName
        return firstName;
    }

    public void setFirstName(String firstName) {
        // TODO: set firstName
        this.firstName = firstName;
    }

    public String getLastName() {
        // TODO: return lastName
        return lastName;
    }

    public void setLastName(String lastName) {
        // TODO: set lastName
        this.lastName = lastName;
    }

    public String getEmail() {
        // TODO: return email
        return email;
    }

    public void setEmail(String email) {
        // TODO: set email
        this.email = email;
    }

    public String getPhoneNumber() {
        // TODO: return phoneNumber
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        // TODO: set phoneNumber
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        // TODO: return password
        return password;
    }

    public void setPassword(String password) {
        // TODO: set password
        this.password = password;
    }

    public int getType() {
        // TODO: return type
        return type;
    }

    public void setType(int type) {
        // TODO: set type
        this.type = type;
    }

    // Abstract Methods

    public abstract void showList();
}
