package ru.mail.park.model;

import com.fasterxml.jackson.annotation.JsonView;
import ru.mail.park.FakeDB.View;

/**
 * Created by SergeyCheremisin on 30/09/16.
 */
public class IdResponse {
    @JsonView(View.Summary.class)
    private Integer userid;

    public IdResponse(Integer id) {
        this.userid = id;
    }

    public void setId(Integer id) {
        this.userid = id;
    }

    public Integer getId() {

        return userid;
    }
}
