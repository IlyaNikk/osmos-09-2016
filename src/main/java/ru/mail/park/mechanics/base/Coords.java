package ru.mail.park.mechanics.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("PublicField")
public class Coords {

    public Coords(@JsonProperty("x") double x, @JsonProperty("y") double y) {
        this.x = x;
        this.y = y;
    }

    public double x;   //final ?
    public double y;

    public Coords(){}

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {

        return x;
    }

    public double getY() {
        return y;
    }

    @NotNull
    public Coords add(@NotNull Coords addition) {
        return new Coords(x + addition.x, y + addition.y);
    }
}
