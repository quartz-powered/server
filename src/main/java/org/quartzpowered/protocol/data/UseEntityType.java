package org.quartzpowered.protocol.data;

public enum UseEntityType {
    INTERACT,
    ATTACK,
    INTERACT_AT;

    public int getId() {
        return ordinal();
    }

    public static UseEntityType fromId(int id) {
        return values()[id];
    }
}
