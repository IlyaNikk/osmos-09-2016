package ru.mail.park.mechanics;

import org.jetbrains.annotations.NotNull;
import ru.mail.park.mechanics.avatar.GameStatic;
import ru.mail.park.mechanics.avatar.GameUser;
import ru.mail.park.mechanics.base.Coords;
import ru.mail.park.model.Id;
import ru.mail.park.model.UserProfile;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;


public class GameSession {
    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);
    @NotNull
    private final Id<GameSession> sessionId;
    @NotNull
    private final GameUser first;
    @NotNull
    private final GameUser second;
    @NotNull
    private List<GameStatic> gameStatics;
//    private final GameUser third;

    public GameSession(@NotNull UserProfile user1, @NotNull UserProfile user2) {
        this.sessionId = Id.of(ID_GENERATOR.getAndIncrement());
        this.first = new GameUser(user1);
        this.second =  new GameUser(user2);

        this.gameStatics = new LinkedList<>();

        final Random random = new Random();

        for(int i=0; i<20; i++){
            this.gameStatics.add(new GameStatic(i, new Coords((double)random.nextInt(1000), (double)random.nextInt(1000)*(-1)), random.nextInt(10)+15, 0));
//            this.gameStatics[i].setRadius(random.nextInt(10)+15);
//            this.gameStatics[i].setCoords(new Coords((double)random.nextInt(1000), (double)random.nextInt(-1000)));
        }
//        this.third = new GameUser(user3);
    }

    @NotNull
    public GameUser getEnemy(@NotNull GameUser user) {
        if (user == first) {
            return second;
        }
        if (user == second) {
            return first;
        }
        throw new IllegalArgumentException("Requested enemy for game but user not participant");
    }

    @NotNull
    public GameUser getSelf(@NotNull Long userId) {
        if (first.getUserProfile().getId().equals(userId)) {
            return first;
        }
        if (second.getUserProfile().getId().equals(userId)) {
            return second;
        }
//        if (third.getUserProfile().getId().equals(userId)) {
//            return second;
//        }
        throw new IllegalArgumentException("Request self for game but user not participate it");
    }

    @NotNull
    public GameUser getFirst() {
        return first;
    }

    @NotNull
    public GameUser getSecond() {
        return second;
    }

    @NotNull
    public GameStatic getStatic(int id) {return gameStatics.get(id); }

    @NotNull
    public List<GameStatic> getStatic() {return gameStatics; }

//    @NotNull
//    public void initGameStatic(){
//        for(int i=0; i<20; i++){
//            this.gameStatics[i].setRadius(random.nextInt(10)+15);
//            this.gameStatics[i].setCoords(new Coords((double)random.nextInt(1000), (double)random.nextInt(-1000)));
//        }
//    }


//    @NotNull
//    public GameUser getThird() { return third; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameSession that = (GameSession) o;

        return sessionId.equals(that.sessionId);

    }

    @Override
    public int hashCode() {
        return sessionId.hashCode();
    }
}