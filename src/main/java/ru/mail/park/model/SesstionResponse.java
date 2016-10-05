package ru.mail.park.model;

import com.fasterxml.jackson.annotation.JsonView;
import ru.mail.park.FakeDB.View;

/**
 * Created by SergeyCheremisin on 30/09/16.
 */
public class SesstionResponse {
        @JsonView(View.Summary.class)
        private Integer sessionid;
        @JsonView(View.Summary.class)
        private Integer userId;

    public SesstionResponse(Integer sessionId, Integer userId) {
        this.sessionid = sessionId;
        this.userId = userId;
    }

    public Integer getSessionId() {
        return sessionid;
    }

    public Integer getUserId() {

        return userId;
    }


    public void setSessionId(Integer sessionId) {
        this.sessionid = sessionId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
