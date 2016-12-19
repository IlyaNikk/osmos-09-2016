package ru.mail.park.mechanics;

import org.jetbrains.annotations.NotNull;
import ru.mail.park.mechanics.base.ClientSnap;

/**
 * @author k.solovyev
 */
public interface GameMechanics {

    void addClientSnapshot(@NotNull Long userId, @NotNull ClientSnap clientSnap);

    void addUser(@NotNull Long user);

    void gmStep(long frameTime);

    void reset();
}
