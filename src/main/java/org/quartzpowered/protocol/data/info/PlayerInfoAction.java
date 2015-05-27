package org.quartzpowered.protocol.data.info;

public enum PlayerInfoAction {
    ADD,
    UPDATE_GAMEMODE,
    UPDATE_LATENCY,
    UPDATE_DISPLAY_NAME,
    REMOVE;

    public int getId() {
        return ordinal();
    }

    public static PlayerInfoAction fromId(int id) {
        return values()[id];
    }
}
