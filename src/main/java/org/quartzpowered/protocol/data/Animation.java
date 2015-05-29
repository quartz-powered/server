package org.quartzpowered.protocol.data;

public enum Animation {
    SWING_ARM,
    TAKE_DAMAGE,
    LEAVE_BED,
    EAT_FOOD,
    CRITICAL_EFFECT,
    MAGIC_CRITICAL_EFFECT;

    public int getId() {
        return ordinal();
    }

    public static Animation fromId(int id) {
        return values()[id];
    }
}
