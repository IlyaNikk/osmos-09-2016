package ru.mail.park.json;

public class SignUpMessage {
    private String login;

    public SignUpMessage(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }
}