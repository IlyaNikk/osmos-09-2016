package ru.mail.park.model;

/**
 * Created by SergeyCheremisin on 22/09/16.
 */

public class UserProfile {
    private String login;
    private String email;
    private String password;

    public UserProfile(String login, String email, String password) {
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public UserProfile(){}


    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {return login;}

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
