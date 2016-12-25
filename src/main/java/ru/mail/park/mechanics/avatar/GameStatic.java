package ru.mail.park.mechanics.avatar;

import ru.mail.park.mechanics.base.Coords;

import javax.validation.constraints.NotNull;

/**
 * Created by serqeycheremisin on 24/12/2016.
 */
public class GameStatic {

    @NotNull
    private int id;

    @NotNull
    private Coords coords;

    @NotNull
    private double radius;

    @NotNull
    private int touch;

    public GameStatic(int id,Coords coords, double radius, int touch) {
        this.id = id;
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

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

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
