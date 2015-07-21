package org.quartzpowered.protocol.data;

public enum ScoreboardPosition {
    TAB_LIST,
    SIDEBAR,
    BELOW_NAME;

    public int getId() {
        return ordinal();
    }

    public static ScoreboardPosition fromId(int id) {
        return values()[id];
    }
}
