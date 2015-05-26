package org.quartzpowered.protocol.codec.v1_8_R1.play.server;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.server.PlayerPositionPacket;

public class PlayerPositionCodec implements Codec<PlayerPositionPacket> {
    @Override
    public void encode(Buffer buffer, PlayerPositionPacket packet) {
        buffer.writeDouble(packet.getX());
        buffer.writeDouble(packet.getFeetY());
        buffer.writeDouble(packet.getZ());
        buffer.writeBoolean(packet.isOnGround());
    }

    @Override
    public void decode(Buffer buffer, PlayerPositionPacket packet) {
        packet.setX(buffer.readDouble());
        packet.setFeetY(buffer.readDouble());
        packet.setZ(buffer.readDouble());
        packet.setOnGround(buffer.readBoolean());
    }
}
