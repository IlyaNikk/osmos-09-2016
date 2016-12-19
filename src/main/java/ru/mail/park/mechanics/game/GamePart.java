package ru.mail.park.mechanics.game;


public interface GamePart {

    boolean shouldBeSnaped();

    Snap<? extends GamePart> takeSnap();
}
