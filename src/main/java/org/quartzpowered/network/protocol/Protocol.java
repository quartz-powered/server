package org.quartzpowered.network.protocol;

import lombok.Getter;
import lombok.ToString;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.network.protocol.codec.CodecRegistry;
import org.quartzpowered.network.protocol.packet.Packet;
import org.quartzpowered.network.protocol.packet.PacketRegistry;

import java.util.EnumMap;
import java.util.Map;

public abstract class Protocol {
    private final Map<ProtocolState, PacketRegistry> serverBoundPackets = new EnumMap<>(ProtocolState.class);
    private final Map<ProtocolState, PacketRegistry> clientBoundPackets = new EnumMap<>(ProtocolState.class);

    private final Map<ProtocolState, CodecRegistry> serverBoundCodecs = new EnumMap<>(ProtocolState.class);
    private final Map<ProtocolState, CodecRegistry> clientBoundCodecs = new EnumMap<>(ProtocolState.class);

    public Protocol() {
        registerPackets();
    }

    protected <T extends Packet> void serverBoundPacket(ProtocolState state, int id, Class<T> type, Codec<T> codec) {
        serverBoundPackets.computeIfAbsent(state, PacketRegistry::new).register(id, type);
        serverBoundCodecs.computeIfAbsent(state, CodecRegistry::new).register(id, codec);
    }

    protected <T extends Packet> void clientBoundPacket(ProtocolState state, int id, Class<T> type, Codec<T> codec) {
        clientBoundPackets.computeIfAbsent(state, PacketRegistry::new).register(id, type);
        clientBoundCodecs.computeIfAbsent(state, CodecRegistry::new).register(id, codec);
    }

    public abstract String getName();
    public abstract int getVersion();

    protected abstract void registerPackets();

    public PacketRegistry getServerBoundPackets(ProtocolState state) {
        return serverBoundPackets.get(state);
    }

    public PacketRegistry getClientBoundPackets(ProtocolState state) {
        return clientBoundPackets.get(state);
    }

    public CodecRegistry getServerBoundCodecs(ProtocolState state) {
        return serverBoundCodecs.get(state);
    }

    public CodecRegistry getClientBoundCodecs(ProtocolState state) {
        return clientBoundCodecs.get(state);
    }

    @Override
    public String toString() {
        return "Protocol(" +
                "name=" + getName() +
                ", version=" + getVersion() +
                ')';
    }
}
