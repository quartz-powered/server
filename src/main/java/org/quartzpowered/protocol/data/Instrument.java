package org.quartzpowered.protocol.data;

public enum Instrument {
    HARP,
    DOUBLE_BASS,
    SNARE_DRUM,
    CLICKS,
    BASS_DRUM;

    public int getId() {
        return ordinal();
    }

    public static Instrument fromId(int id) {
        return values()[id];
    }
}
