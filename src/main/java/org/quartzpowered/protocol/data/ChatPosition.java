package org.quartzpowered.protocol.data;

public enum ChatPosition {
    CHAT,
    SYSTEM,
    ACTION_BAR;

    public int getId() {
        return ordinal();
    }

    public static ChatPosition fromId(int id) {
        return values()[id];
    }
}
