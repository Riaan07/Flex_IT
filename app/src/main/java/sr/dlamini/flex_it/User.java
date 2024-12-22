package sr.dlamini.flex_it;

import java.io.Serializable;

public class User implements Serializable {
    private int Id;
    private String FirstName;
    private String LastName;
    private String IdNumber;
    private String Email;
    protected String Password;

    public User(int id, String firstName, String lastName, String idNumber, String email, String password) {
        Id = id;
        FirstName = firstName;
        LastName = lastName;
        IdNumber = idNumber;
        Email = email;
        Password = password;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getIdNumber() {
        return IdNumber;
    }

    public void setIdNumber(String idNumber) {
        IdNumber = idNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}