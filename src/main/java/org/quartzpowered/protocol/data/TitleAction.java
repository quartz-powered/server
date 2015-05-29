package org.quartzpowered.protocol.data;

public enum TitleAction {
    SET_TITLE,
    SET_SUBTITLE,
    SET_TIMES,
    HIDE,
    RESET;

    public int getId() {
        return ordinal();
    }

    public static TitleAction fromId(int id) {
        return values()[id];
    }

}