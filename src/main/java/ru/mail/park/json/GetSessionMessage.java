package ru.mail.park.json;

public class GetSessionMessage {
    private String sessionid;

    public GetSessionMessage(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getSessionId() {
        return sessionid;
    }

    public void setSessionId(String sessionid) {
        this.sessionid = sessionid;
    }

}
