package ru.mail.park.mechanics.avatar;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.mail.park.mechanics.base.Coords;
import ru.mail.park.mechanics.base.Direction;
import ru.mail.park.mechanics.base.Speeds;
import ru.mail.park.mechanics.base.Way;
import ru.mail.park.mechanics.game.GamePart;
import ru.mail.park.mechanics.game.Snap;

import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("unused")
public class PositionPart implements GamePart {
    @NotNull
    private Coords body;

    @NotNull
    private Direction movingTo;

    @NotNull
    private Speeds speeds;

    @NotNull
    @JsonProperty("radius")
    private double radius;

    @NotNull
    private double temp_radius;

    @NotNull
    private int touch;


    public void setRadius(double radius){
        this.radius = radius;
    }

    public void setTemp_radius(double temp_radius) { this.temp_radius = temp_radius; }

    public void setTouch(int touch){this.touch = touch; }

    public void setSpeeds(Speeds speeds) {
        this.speeds = speeds;
    }

    public double getRadius(){
        return radius;
    }

    public double getTemp_radius() { return temp_radius; }

    public int getTouch() {return touch; }

    @NotNull
    private final List<Coords> desirablePath = new ArrayList<>();


    public PositionPart() {
        body = new Coords(0.0f, 0.0f);

        speeds = new Speeds(0.0f, 0.0f);

        radius = 25d;

        temp_radius = radius;

        movingTo = Way.None.getRadial();

        touch = 0;
    }

    public void executeMovement() {
        if(!desirablePath.isEmpty()) {
            body = desirablePath.get(desirablePath.size() - 1);
        }
        desirablePath.clear();
    }


//    @NotNull
//    public List<Coords> getDesirablePath() {
//        return desirablePath;
//    }

    @NotNull
    public Coords getLastDesirablePoint() {
        if (desirablePath.isEmpty()) {
            return body;
        }
        return desirablePath.get(desirablePath.size() - 1);
    }

    public void addDesirableCoords(@Nullable Coords desirableCoords) {
        desirablePath.add(desirableCoords);
    }

    public void addDesirableSpeeds(@Nullable Speeds desirableSpeeds) {
        this.speeds.setVx(desirableSpeeds.vx);
        this.speeds.setVy(desirableSpeeds.vy);
    }

    @NotNull
    public Coords getBody() {
        return body;
    }

    @NotNull
    public Speeds getSpeeds(){
        return speeds;
    }

    public void setBody(@NotNull Coords body) {
        this.body = body;
    }

    @NotNull
    public Direction getMovingTo() {
        return movingTo;
    }

    public void setMovingTo(@NotNull Direction movingTo) {
        this.movingTo = movingTo;
    }

    @Override
    public boolean shouldBeSnaped() {
        return true;
    }

    @Override
    public PositionSnap takeSnap() {
        return new PositionSnap(this);
    }

    public static final class PositionSnap implements Snap<PositionPart> {
        @NotNull
        private final Coords body;
        @NotNull
        private final Direction movingTo;

        @NotNull
        private final Speeds speeds;

        @NotNull
        private final double radius;

        @NotNull
        private final double temp_radius;

        @NotNull
        private final int touch;

        public PositionSnap(@NotNull PositionPart positionPart) {
            body = positionPart.body;
            movingTo = positionPart.movingTo;
            radius = positionPart.radius;
            temp_radius = positionPart.temp_radius;
            speeds = positionPart.speeds;
            touch = positionPart.touch;
        }

        @NotNull
        @Override
        public String getPartName() {
            return PositionPart.class.getSimpleName();
        }

        @NotNull
        public Coords getBody() {
            return body;
        }
        @NotNull
        public Direction getMovingTo() {
            return movingTo;
        }

        @NotNull
        public Speeds getSpeeds() { return speeds;}

        @NotNull
        public double getRadius() { return radius;}

        @NotNull
        public double getTemp_radius() { return temp_radius; }

        @NotNull
        public int getTouch() {return touch; }
    }
}
