package ru.mail.park.json;

public class Response<T> {
    private String level;

    private T message;

    public Response(String level, T message) {
        this.level = level;
        this.message = message;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }
}
