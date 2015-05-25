package org.quartzpowered.protocol.codec.indentifier.handshake.server;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.ProtocolState;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.handshake.server.HandshakePacket;

public class HandshakeCodec implements Codec<HandshakePacket> {
    @Override
    public void encode(Buffer buffer, HandshakePacket packet) {
        buffer.writeVarInt(packet.getVersion());
        buffer.writeString(packet.getAddress());
        buffer.writeShort(packet.getPort());
        buffer.writeVarInt(packet.getState().getId());
    }

    @Override
    public void decode(Buffer buffer, HandshakePacket packet) {
        packet.setVersion(buffer.readVarInt());
        packet.setAddress(buffer.readString());
        packet.setPort(buffer.readUnsignedShort());
        packet.setState(ProtocolState.fromId(buffer.readVarInt()));
    }
}
