package ru.mail.park.mechanics.base;

import ru.mail.park.mechanics.avatar.GameStatic;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by serqeycheremisin on 25/12/2016.
 */
@SuppressWarnings({"NullableProblems", "unused"})
public class ServerSnapStatic {

    @NotNull
    public List<GameStatic> statics;

    @NotNull
    public List<GameStatic> getStatics() {
        return statics;
    }

    public void setStatics(@NotNull List<GameStatic> statics) {
        this.statics = statics;
    }

}
