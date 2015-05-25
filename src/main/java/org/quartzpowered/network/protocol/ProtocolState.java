package org.quartzpowered.network.protocol;

public enum ProtocolState {
    HANDSHAKE,
    STATUS,
    LOGIN,
    PLAY;

    public int getId() {
        return ordinal();
    }

    public static ProtocolState fromId(int id) {
        return values()[id];
    }
}
