package org.quartzpowered.protocol.data;

public enum CombatEvent {
    ENTER_COMBAT,
    END_COMBAT,
    ENTITY_DEAD;

    public int getId() {
        return ordinal();
    }

    public static CombatEvent fromId(int id) {
        return values()[id];
    }
}
