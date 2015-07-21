package org.quartzpowered.protocol.data;

public enum EntityEquipmentSlot {
    HELD,
    BOOTS,
    LEGGINGS,
    CHESTPLATE,
    HELMET;

    public int getId() {
        return ordinal();
    }

    public static EntityEquipmentSlot fromId(int id) {
        return values()[id];
    }
}
