package ru.mail.park.mechanics.internal;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import ru.mail.park.mechanics.avatar.GameStatic;
import ru.mail.park.mechanics.avatar.PositionPart;
import ru.mail.park.mechanics.game.GameObject;

import java.util.LinkedHashSet;
import java.util.Set;


@Service
public class MovementService {
    private final Set<GameObject> objectsToMove = new LinkedHashSet<GameObject>();

    private final Set<GameStatic> staticToDraw = new LinkedHashSet<GameStatic>();

    public void registerObjectToMove(@NotNull GameObject gameObject) {
        objectsToMove.add(gameObject);
    }
//
//    public void regiesterStaticToDraw(@NotNull GameStatic gameStatic){
//        staticToDraw.add(gameStatic);
//    }

    public void executeMoves() {

        for (GameObject gameObject : objectsToMove) {
            @Nullable final PositionPart part = gameObject.getPart(PositionPart.class);
            if (part == null) {
                continue;
            }
            part.setBody(part.getLastDesirablePoint());
        }
    }


    public void clear() {
        objectsToMove.clear();
    }
}
