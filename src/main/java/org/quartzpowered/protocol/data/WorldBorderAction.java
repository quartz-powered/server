package org.quartzpowered.protocol.data;

public enum WorldBorderAction {
    SET_SIZE,
    LERP_SIZE,
    SET_CENTER,
    INITIALIZE,
    SET_WARNING_TIME,
    SET_WARNING_BLOCKS;

    public int getId() {
        return ordinal();
    }

    public static WorldBorderAction fromId(int id) {
        return values()[id];
    }
}
