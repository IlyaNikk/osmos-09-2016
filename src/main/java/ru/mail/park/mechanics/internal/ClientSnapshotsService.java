package ru.mail.park.mechanics.internal;


import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.mail.park.mechanics.GameSession;
import ru.mail.park.mechanics.avatar.GameStatic;
import ru.mail.park.mechanics.avatar.GameUser;
import ru.mail.park.mechanics.avatar.PositionPart;
import ru.mail.park.mechanics.avatar.Square;
import ru.mail.park.mechanics.base.ClientSnap;
import ru.mail.park.mechanics.base.Coords;
import ru.mail.park.mechanics.base.Sincos;
import ru.mail.park.mechanics.base.Speeds;

import java.util.*;


@Service
public class ClientSnapshotsService {

    private final Map<Long, List<ClientSnap>> snaps = new HashMap<>();

    @NotNull
    private final MovementService movementService;

    public ClientSnapshotsService(@NotNull MovementService movementService) {
        this.movementService = movementService;
    }

    public void pushClientSnap(@NotNull Long user, @NotNull ClientSnap snap) {
        this.snaps.putIfAbsent(user, new ArrayList<>());
        final List<ClientSnap> clientSnaps = snaps.get(user);
        clientSnaps.add(snap);
    }

    @NotNull
    public List<ClientSnap> getSnapForUser(@NotNull Long user) {
        return snaps.getOrDefault(user, Collections.emptyList());
    }

    public void processSnapshotsFor(@NotNull GameSession gameSession) {
        final Collection<GameUser> players = new ArrayList<>();
        long lastFrameTime = 0;
        players.add(gameSession.getFirst());
        players.add(gameSession.getSecond());
//        players.add(gameSession.getThird());
        for (GameUser player : players) {
            final List<ClientSnap> playerSnaps = getSnapForUser(player.getId());
            if (playerSnaps.isEmpty()) {
                descSpeed(player, lastFrameTime);
                continue;
            }
            for (ClientSnap snap : playerSnaps) {
                lastFrameTime = snap.getFrameTime();
                processMovement(player, snap.getButton(), snap.getSincos(), snap.getFrameTime());
            }
            final ClientSnap lastSnap = playerSnaps.get(playerSnaps.size() - 1);

            //TODO:Firing
        }
        checkTouch(gameSession);
        touchFood(gameSession.getFirst(), gameSession.getStatic());
        touchFood(gameSession.getSecond(), gameSession.getStatic());
    }


    private void checkTouch(@NotNull GameSession gameSession){

        PositionPart positionPart1 = gameSession.getFirst().getSquare().claimPart(PositionPart.class);
        PositionPart positionPart2 = gameSession.getSecond().getSquare().claimPart(PositionPart.class);

        double radius1 = positionPart1.getTemp_radius();
        double radius2 = positionPart2.getTemp_radius();

        int touch1 = positionPart1.getTouch();
        int touch2 = positionPart2.getTouch();

        Coords coords1 = positionPart1.getBody();
        Coords coords2 = positionPart2.getBody();

        double lenRad = radius1 + radius2;
        double lenCor = Math.sqrt(Math.pow((coords1.getX() - coords2.getX()), 2) + Math.pow((coords1.getY() - coords2.getY()), 2));

        if(lenRad >= lenCor){
            if(radius1 > radius2){
                radius1 += 0.5;
                radius2 -= 0.5;
                touch1 = 1;
                touch2 = -1;
            }
            else if(radius1 < radius2){
                radius1 -= 0.5;
                radius2 += 0.5;
                touch1 = -1;
                touch2 = 1;
            }
            else{
                touch1 = 0;
                touch2 = 0;
            }
        }
        else{
            touch1 = 0;
            touch2 = 0;
        }

        if(positionPart1.getRadius() > positionPart1.getTemp_radius()){
            positionPart1.setTemp_radius(radius1);
            positionPart1.setRadius(positionPart1.getRadius() - 0.5);
            touch1 = -1;
        }
        else{
            positionPart1.setTemp_radius(radius1);
            positionPart1.setRadius(positionPart1.getTemp_radius());
        }
        if(positionPart2.getRadius() != positionPart2.getTemp_radius()){
            positionPart2.setTemp_radius(radius2);
            positionPart2.setRadius(positionPart2.getRadius() - 0.5);
            touch2 = -1;
        }
        else{
            positionPart2.setTemp_radius(radius2);
            positionPart2.setRadius(positionPart2.getTemp_radius());
        }

//        positionPart1.setRadius(radius1);
//        positionPart2.setRadius(radius2);
//        positionPart1.setTemp_radius(radius1);
//        positionPart2.setTemp_radius(radius2);


        positionPart1.setTouch(touch1);
        positionPart2.setTouch(touch2);


        movementService.registerObjectToMove(gameSession.getFirst().getSquare());
        movementService.registerObjectToMove(gameSession.getSecond().getSquare());
    }



    private void touchFood(@NotNull GameUser gameUser, GameStatic gameStatic[]){

        PositionPart positionPart = gameUser.getSquare().claimPart(PositionPart.class);

        double radius = positionPart.getTemp_radius();

        int touch = positionPart.getTouch();

        Coords coords = positionPart.getBody();


        for(int i=0; i<20; i++){
            double lenRad = gameStatic[i].getRadius() + radius;
            double lenCor = Math.sqrt(Math.pow((coords.getX() - gameStatic[i].getCoords().getX()), 2) + Math.pow((coords.getY() - gameStatic[i].getCoords().getY()), 2));
            if(lenRad >= lenCor){
                    radius += 0.5;
                    gameStatic[i].setRadius(gameStatic[i].getRadius() - 0.5);
                    touch = 1;
                    gameStatic[i].setTouch(-1);
            }
            else{
                touch = 0;
                gameStatic[i].setRadius(gameStatic[i].getRadius() - 0.5);
            }
        }

        movementService.registerObjectToMove(gameUser.getSquare());

    }


    private void descSpeed(@NotNull GameUser gameUser, long frameTime){
        PositionPart positionPart = gameUser.getSquare().claimPart(PositionPart.class);

        double vx = positionPart.getSpeeds().getVx();
        double vy = positionPart.getSpeeds().getVy();
        double x = positionPart.getBody().getX();
        double y = positionPart.getBody().getY();

        if(vx > 0)
            vx -= 3;
        else if(vx < 0)
            vx += 3;
        if(vy > 0)
            vy -= 3;
        else if(vy < 0)
            vy += 3;


        x += vx / 100 * 16;
        y += vy / 100 * 16;

        positionPart.getBody().setX(x);
        positionPart.getBody().setY(y);
        positionPart.getSpeeds().setVx(vx);
        positionPart.getSpeeds().setVy(vy);
        movementService.registerObjectToMove(gameUser.getSquare());

    }

    private void processMovement(@NotNull GameUser gameUser, String button, Sincos sincos, long frameTime) {

        final PositionPart positionPart = gameUser.getSquare().claimPart(PositionPart.class);
        final Coords body = positionPart.getBody();
        final Speeds speeds = positionPart.getSpeeds();
        double radius = positionPart.getTemp_radius();
        Flag flag = new Flag();
        double vx = speeds.getVx();
        double vy = speeds.getVy();
        double x = body.x;
        double y = body.y;
        switch (button) {
            case "w": {
                vx = -1 * (80 * sincos.sin);
                vy = -1 * (80 * sincos.cos);
                x += vx / 100 * frameTime;
                y += vy / 100 * frameTime;
                final Coords tempCoords = checkReact(x, y, radius, flag);
                final Speeds tempSpeeds = changeSpeeds(vx, vy, flag);
                moveSquareBy(gameUser.getSquare(), tempCoords.getX(), tempCoords.getY(), tempSpeeds.getVx(), tempSpeeds.getVy());
                break;
            }
            case "s": {
                vx = (80 * sincos.sin);
                vy = (80 * sincos.cos);
                x += vx / 100 * frameTime;
                y += vy / 100 * frameTime;
                final Coords tempCoords = checkReact(x, y,radius, flag);
                final Speeds tempSpeeds = changeSpeeds(vx, vy, flag);
                moveSquareBy(gameUser.getSquare(), tempCoords.getX(), tempCoords.getY(), tempSpeeds.getVx(), tempSpeeds.getVy());
                break;
            }
            case "a": {
                vx = -1 * (80 * sincos.cos);
                vy = (80 * sincos.sin);
                x += vx / 100 * frameTime;
                y += vy / 100 * frameTime;
                final Coords tempCoords = checkReact(x, y,radius, flag);
                final Speeds tempSpeeds = changeSpeeds(vx, vy, flag);
                moveSquareBy(gameUser.getSquare(), tempCoords.getX(), tempCoords.getY(), tempSpeeds.getVx(), tempSpeeds.getVy());
                break;
            }
            case "d": {
                vx = (80 * sincos.cos);
                vy = -1 * (80 * sincos.sin);
                x += vx / 100 * frameTime;
                y += vy / 100 * frameTime;
                final Coords tempCoords = checkReact(x, y, radius, flag);
                final Speeds tempSpeeds = changeSpeeds(vx, vy, flag);
                moveSquareBy(gameUser.getSquare(), tempCoords.getX(), tempCoords.getY(), tempSpeeds.getVx(), tempSpeeds.getVy());
                break;
            }
            case "space": {
                radius += 20;
                positionPart.setRadius(radius);
                positionPart.setTouch(1);
                movementService.registerObjectToMove(gameUser.getSquare());
            }
            case "None": {
                break;
            }
        }
    }

    class Flag{
        public int flag;

        public Flag(){
            flag = 0;
        }
    }


    private Coords checkReact(double x, double y, double radius, Flag flag){

        Coords tempCoords = new Coords();

        if(x >= 2000 ){
            x = (2000 - radius);
            flag.flag = 1;
        }
        else if(x <= -2000){
            x = radius - 2000;
            flag.flag = 1;
        }
        if(y >= 2000){
            y = (2000 - radius);
            flag.flag = 2;
        }
        else if(y <= -2000){
            y = (radius - 2000);
            flag.flag = 2;
        }


        tempCoords.setX(x);
        tempCoords.setY(y);

        return tempCoords;
    }


    public Speeds changeSpeeds(double vx, double vy, Flag flag){
        Speeds speeds = new Speeds();
        if(flag.flag == 1){
            vx *= -1;
        }
        if(flag.flag == 2){
            vy *= -1;
        }

//        if(vx > 10)
//            vx-=3;
//        if(vx < -10)
//            vx-=3;
//        if(vy > 10)
//            vy-=3;
//        if(vy < -10)
//            vy+=3;

        speeds.setVx(vx);
        speeds.setVy(vy);

        return speeds;
    }


    private void moveSquareBy(@NotNull Square square, double dx, double dy, double vx, double vy) {
            final PositionPart positionPart = square.claimPart(PositionPart.class);
            positionPart.addDesirableCoords(new Coords(dx, dy));
            positionPart.addDesirableSpeeds(new Speeds(vx, vy));
            movementService.registerObjectToMove(square);
        }


    public void clear() {
        snaps.clear();
    }

}
