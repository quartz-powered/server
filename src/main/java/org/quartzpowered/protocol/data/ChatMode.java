package org.quartzpowered.protocol.data;

public enum ChatMode {
    ENABLED,
    COMMANDS,
    HIDDEN;
    public int getId() {
        return ordinal();
    }

    public static ChatMode fromId(int id) {
        return values()[id];
    }
}
