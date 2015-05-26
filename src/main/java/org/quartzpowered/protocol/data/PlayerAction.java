package org.quartzpowered.protocol.data;

public enum PlayerAction {
    START_DIGGING,
    CANCEL_DIGGING,
    FINISH_DIGGING,
    DROP_ITEMSTACK,
    DROP_ITEM,
    RELEASE_ITEM;

    public int getId() {
        return ordinal();
    }

    public static PlayerAction fromId(int id) {
        return values()[id];
    }
}
