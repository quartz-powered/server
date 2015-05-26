package org.quartzpowered.protocol.data;

public enum ClientStatusAction {
    PERFORM_RESPAWN,
    REQUEST_STATS,
    TAKING_INVENTORY;

    public int getId() {
        return ordinal();
    }

    public static ClientStatusAction fromId(int id) {
        return values()[id];
    }
}
