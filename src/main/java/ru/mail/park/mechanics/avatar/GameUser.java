package ru.mail.park.mechanics.avatar;

import org.jetbrains.annotations.NotNull;
import ru.mail.park.mechanics.base.ServerPlayerSnap;
import ru.mail.park.model.UserProfile;


public class GameUser {
    @NotNull
    private final UserProfile userProfile;
    @NotNull
    private  Square square;

    //TODO: Collider


    public GameUser(@NotNull UserProfile userProfile) {
        this.userProfile = userProfile;
        square = new Square();
    }


    @NotNull
    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setSquare(Square ob){
        this.square = ob;
    }

    public Square getSquare() {
        return square;
    }

    @NotNull
    public Long getId() {
        return userProfile.getId();
    }


    @NotNull
    public ServerPlayerSnap generateSnap() {

        final ServerPlayerSnap result = new ServerPlayerSnap();
        result.setUserId(getId());
        result.setPlayerSquare(square.getSnap());
        return result;
    }
}
