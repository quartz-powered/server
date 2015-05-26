package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.client.PlayerTeleportPacket;

public class PlayerTeleportCodec implements Codec<PlayerTeleportPacket> {
    @Override
    public void encode(Buffer buffer, PlayerTeleportPacket packet) {
        buffer.writeDouble(packet.getX());
        buffer.writeDouble(packet.getY());
        buffer.writeDouble(packet.getZ());
        buffer.writeFloat(packet.getYaw());
        buffer.writeFloat(packet.getPitch());
        buffer.writeByte(packet.getFlags());
    }

    @Override
    public void decode(Buffer buffer, PlayerTeleportPacket packet) {
        packet.setX(buffer.readDouble());
        packet.setY(buffer.readDouble());
        packet.setZ(buffer.readDouble());
        packet.setYaw(buffer.readFloat());
        packet.setPitch(buffer.readFloat());
        packet.setFlags(buffer.readByte());
    }
}
