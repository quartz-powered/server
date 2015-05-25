package org.quartzpowered.protocol.data;

public enum Gamemode {
    SURVIVAL,
    CREATIVE,
    ADVENTURE,
    SPECTATOR;

    public int getId() {
        return ordinal();
    }

    public static Gamemode fromId(int id) {
        return values()[id];
    }
}
