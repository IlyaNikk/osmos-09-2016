package ru.mail.park.mechanics.avatar;

import org.jetbrains.annotations.NotNull;
import ru.mail.park.mechanics.game.GameObject;
import ru.mail.park.mechanics.game.GamePart;
import ru.mail.park.mechanics.game.Snap;
import ru.mail.park.model.Id;

import java.util.List;


public class Square extends GameObject {

    public Square() {
        addPart(PositionPart.class, new PositionPart());
    }

    @Override
    public @NotNull SquareSnap getSnap() {
        return new SquareSnap(this);
    }

    @SuppressWarnings("unused")
    public static final class SquareSnap implements Snap<Square> {

        @NotNull
        private final List<Snap<? extends GamePart>> partSnaps;

        @NotNull
        private final Id<GameObject> id;

        public SquareSnap(@NotNull Square square) {
            this.partSnaps = square.getPartSnaps();
            this.id = square.getId();
        }

        @NotNull
        public Id<GameObject> getId() {
            return id;
        }

        @NotNull
        @Override
        public String getPartName() {
            return Square.class.getSimpleName();
        }

        @NotNull
        public List<Snap<? extends GamePart>> getPartSnaps() {
            return partSnaps;
        }
    }


}
