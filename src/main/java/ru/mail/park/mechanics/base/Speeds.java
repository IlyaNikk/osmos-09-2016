package ru.mail.park.mechanics.base;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

/**
 * Created by serqeycheremisin on 22/12/2016.
 */
public class Speeds {


    public double vx;
    public double vy;

    public Speeds(){

    }

    public Speeds(@JsonProperty("vx") double vx, @JsonProperty("vy") double vy) {
        this.vx = vx;
        this.vy = vy;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }

//    @NotNull
//    public Coords add(@NotNull Speeds addition) {
//        return new Speeds(vx + addition.vx, vy + addition.vy);
//    }
}
