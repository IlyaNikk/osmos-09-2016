package ru.mail.park.mechanics.requests;

import org.jetbrains.annotations.NotNull;
import ru.mail.park.mechanics.base.ServerPlayerSnap;
import ru.mail.park.mechanics.game.GameObject;
import ru.mail.park.model.Id;

import java.util.List;


public class InitGame {
    @SuppressWarnings("NullableProblems")
    public static final class Request {
        @NotNull
        private Long self;
        @NotNull
        private Id<GameObject> selfSquareId;
        @NotNull
        private List<ServerPlayerSnap> players;

        @NotNull
        public Long getSelf() {
            return self;
        }

        public void setSelf(@NotNull Long self) {
            this.self = self;
        }
        @NotNull
        public List<ServerPlayerSnap> getPlayers() {
            return players;
        }
        @NotNull
        public void setPlayers(@NotNull List<ServerPlayerSnap> players) {
            this.players = players;
        }

        public Id<GameObject> getSelfSquareId() {
            return selfSquareId;
        }

        public void setSelfSquareId(Id<GameObject> selfSquareId) {
            this.selfSquareId = selfSquareId;
        }
    }

}
