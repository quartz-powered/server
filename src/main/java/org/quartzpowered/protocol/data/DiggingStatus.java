package org.quartzpowered.protocol.data;

public enum DiggingStatus {
    START_DIGGING,
    CANCEL_DIGGING,
    FINISH_DIGGING,
    DROP_ITEMSTACK,
    DROP_ITEM,
    RELEASE_ITEM;

    public int getId() {
        return ordinal();
    }

    public static DiggingStatus fromId(int id) {
        return values()[id];
    }
}
