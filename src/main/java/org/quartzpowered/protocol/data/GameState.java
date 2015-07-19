package org.quartzpowered.protocol.data;

public enum GameState {
    INVALID_BED(false),
    END_RAINING(false),
    BEGIN_RAINING(false),
    CHANGE_GAMEMODE(true),
    ENTER_CREDITS(false),
    DEMO_MESSAGE(true),
    ARROW_HITING_PLAYER(false),
    FADE_VALUE(true),
    FADE_TIME(true),
    PLAY_MOB_APPEARANCE(false);

    private boolean hasWriteValue;
    private GameState(boolean hasWriteValue) {
        this.hasWriteValue = hasWriteValue;
    }

    public boolean hasWriteValue() {
        return hasWriteValue;
    }

    public int getId() {
        return ordinal();
    }

    public static GameState fromId(int id) {
        return values()[id];
    }
}
