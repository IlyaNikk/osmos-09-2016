package ru.mail.park.json;

public class SessionMessage {
    private String sessionId;
    private Long userId;

    public SessionMessage(String sessionid, Long userid) {
        this.sessionId = sessionid;
        this.userId = userid;
    }

    public String getSessionId() {
        return sessionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setSessionId(String sessionid) {
        this.sessionId = sessionid;
    }

    public void setUserId(Long userid) {
        this.userId = userid;
    }
}
