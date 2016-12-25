package ru.mail.park.mechanics.base;

import javax.validation.constraints.NotNull;
import java.util.List;


@SuppressWarnings({"NullableProblems", "unused"})
public class ServerSnap {


    @NotNull
    public List<ServerPlayerSnap> players;

    @NotNull
    public List<ServerPlayerSnap> getPlayers() {
        return players;
    }

    public void setPlayers(@NotNull List<ServerPlayerSnap> players) {
        this.players = players;
    }

}
