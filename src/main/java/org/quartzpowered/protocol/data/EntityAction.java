package org.quartzpowered.protocol.data;

public enum EntityAction {
    CROUCH,
    UNCROUCH,
    LEAVE_BED,
    START_SPRINTING,
    STOP_SPRINTING,
    JUMP_WITH_HORSE,
    OPEN_INVENTORY;
    public int getId() {
        return ordinal();
    }

    public static EntityAction fromId(int id) {
        return values()[id];
    }
}
