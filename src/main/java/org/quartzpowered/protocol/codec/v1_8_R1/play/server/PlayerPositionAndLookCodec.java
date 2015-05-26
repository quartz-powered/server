package org.quartzpowered.protocol.codec.v1_8_R1.play.server;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.server.PlayerPositionAndLookPacket;

public class PlayerPositionAndLookCodec implements Codec<PlayerPositionAndLookPacket> {
    @Override
    public void encode(Buffer buffer, PlayerPositionAndLookPacket packet) {
        buffer.writeDouble(packet.getX());
        buffer.writeDouble(packet.getFeetY());
        buffer.writeDouble(packet.getZ());
        buffer.writeFloat(packet.getYaw());
        buffer.writeFloat(packet.getPitch());
        buffer.writeBoolean(packet.isOnGround());
    }

    @Override
    public void decode(Buffer buffer, PlayerPositionAndLookPacket packet) {
        packet.setX(buffer.readDouble());
        packet.setFeetY(buffer.readDouble());
        packet.setZ(buffer.readDouble());
        packet.setYaw(buffer.readFloat());
        packet.setPitch(buffer.readFloat());
        packet.setOnGround(buffer.readBoolean());
    }
}
