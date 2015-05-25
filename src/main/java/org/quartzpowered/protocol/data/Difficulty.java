package org.quartzpowered.protocol.data;

public enum Difficulty {
    PEACEFUL,
    EASY,
    NORMAL,
    HARD;

    public int getId() {
        return ordinal();
    }

    public static Difficulty fromId(int id) {
        return values()[id];
    }
}
