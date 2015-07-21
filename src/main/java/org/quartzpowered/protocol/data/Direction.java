package org.quartzpowered.protocol.data;

public enum Direction {
    DOWN,
    UP,
    SOUTH,
    WEST,
    NORTH,
    EAST;

    public int getId() {
        return ordinal();
    }

    public static Direction fromId(int id) {
        return values()[id];
    }
}
