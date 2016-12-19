package ru.mail.park.mechanics.internal;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.mail.park.mechanics.GameSession;
import ru.mail.park.mechanics.avatar.GameUser;
import ru.mail.park.mechanics.avatar.PositionPart;
import ru.mail.park.mechanics.avatar.Square;
import ru.mail.park.mechanics.base.ClientSnap;
import ru.mail.park.mechanics.base.Coords;
import ru.mail.park.mechanics.base.Sincos;

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
        players.add(gameSession.getFirst());
        players.add(gameSession.getSecond());
        for (GameUser player : players) {
            final List<ClientSnap> playerSnaps = getSnapForUser(player.getId());
            if (playerSnaps.isEmpty()) {
                continue;
            }
            for (ClientSnap snap : playerSnaps) {
                processMovement(player, snap.getButton(), snap.getSincos(), snap.getFrameTime());
            }
            final ClientSnap lastSnap = playerSnaps.get(playerSnaps.size() - 1);

            //TODO:Firing
        }
    }


    private void processMovement(@NotNull GameUser gameUser, String button, Sincos sincos, long frameTime) {

        final PositionPart positionPart = gameUser.getSquare().claimPart(PositionPart.class);
        final Coords body = positionPart.getBody();
        double vx = 0d;
        double vy = 0d;
        double x = body.x;
        double y = body.y;

        switch (button) {
            case "w": {
                vx = -1 * (40 * sincos.sin);
                vy = -1 * (40 * sincos.cos);
                x += vx / 100 * frameTime;
                TempCoords tempCoords = checkReact(x, y);
                moveSquareBy(gameUser.getSquare(), tempCoords.getX(), tempCoords.getY());
                break;
            }
            case "s": {
                vx = (40 * sincos.sin);
                vy = (40 * sincos.cos);
                x += vx / 100 * frameTime;
                y += vy / 100 * frameTime;
                TempCoords tempCoords = checkReact(x, y);
                moveSquareBy(gameUser.getSquare(), tempCoords.getX(), tempCoords.getY());
                break;
            }
            case "a": {
                vx = -1 * (40 * sincos.cos);
                vy = (40 * sincos.sin);
                x += vx / 100 * frameTime;
                y += vy / 100 * frameTime;
                TempCoords tempCoords = checkReact(x, y);
                moveSquareBy(gameUser.getSquare(), tempCoords.getX(), tempCoords.getY());
                break;
            }
            case "d": {
                vx = (40 * sincos.cos);
                vy = -1 * (40 * sincos.sin);
                x += vx / 100 * frameTime;
                y += vy / 100 * frameTime;
                TempCoords tempCoords = checkReact(x, y);
                moveSquareBy(gameUser.getSquare(), tempCoords.getX(), tempCoords.getY());
                break;
            }
            case "None": {
                break;
            }
        }
    }

    class TempCoords{
        public double x;
        public double y;

        TempCoords(){}

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public void setX(double x) {
            this.x = x;
        }

        public void setY(double y) {
            this.y = y;
        }
    }

    private TempCoords  checkReact(double x, double y){

        TempCoords tempCoords = new TempCoords();

        if(x >= 1000 ){
            x = (1000 - 70);
        }
        else if(x <= -1000){
            x = 70 - 1000;
        }
        if(y >= 1000){
            y = (1000 - 100);
        }
        else if(y <= -1000){
            y = (100 - 1000);
        }

        tempCoords.setX(x);
        tempCoords.setY(y);

        return tempCoords;
    }

    private void moveSquareBy(@NotNull Square square, double dx, double dy) {
            final PositionPart positionPart = square.claimPart(PositionPart.class);
            positionPart.addDesirableCoords(new Coords(dx, dy));
            movementService.registerObjectToMove(square);
        }

    public void clear() {
        snaps.clear();
    }

}
