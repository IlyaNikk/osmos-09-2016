package ru.mail.park.mechanics.base;

import org.jetbrains.annotations.NotNull;


@SuppressWarnings({"NullableProblems", "unused"})
public class ClientSnap {


    @NotNull
    private Sincos sincos;

    @NotNull
    private String button;

    private long frameTime;

    public Sincos getSincos() {
        return sincos;
    }

    public void setSincos(Sincos sincos) {
        this.sincos = sincos;
    }

    public ClientSnap() {
    }

    public ClientSnap(@NotNull String button) {
        this.button = button;
    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }

    public long getFrameTime() {
        return frameTime;
    }

    public void setFrameTime(long frameTime) {
        this.frameTime = frameTime;
    }
}
