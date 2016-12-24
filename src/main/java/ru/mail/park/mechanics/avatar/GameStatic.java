package ru.mail.park.mechanics.avatar;

import ru.mail.park.mechanics.base.Coords;

import javax.validation.constraints.NotNull;

/**
 * Created by serqeycheremisin on 24/12/2016.
 */
public class GameStatic {
    @NotNull
    private Coords coords;

    @NotNull
    private double radius;

    @NotNull
    private int touch;

    public GameStatic(Coords coords, double radius, int touch) {
        this.coords = coords;
        this.radius = radius;
        this.touch = touch;
    }

    public int getTouch() {
        return touch;
    }

    public void setTouch(int touch) {
        this.touch = touch;
    }

    public Coords getCoords() {
        return coords;
    }

    public double getRadius() {
        return radius;
    }

    public void setCoords(Coords coords) {
        this.coords = coords;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
