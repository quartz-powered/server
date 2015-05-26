package org.quartzpowered.protocol.codec.v1_8_R1.play.server;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.server.PlayerDiggingPacket;

public class PlayerDiggingCodec implements Codec<PlayerDiggingPacket> {
    @Override
    public void encode(Buffer buffer, PlayerDiggingPacket packet) {
        buffer.writeByte(packet.getStatus());
        buffer.writeBlockPosition(packet.getLocation());
        buffer.writeByte(packet.getFace());
    }

    @Override
    public void decode(Buffer buffer, PlayerDiggingPacket packet) {
        packet.setStatus(buffer.readByte());
        packet.setLocation(buffer.readBlockPosition());
        packet.setFace(buffer.readByte());
    }
}
