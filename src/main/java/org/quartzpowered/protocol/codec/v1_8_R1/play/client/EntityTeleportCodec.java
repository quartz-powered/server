package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.client.EntityTeleportPacket;

public class EntityTeleportCodec implements Codec<EntityTeleportPacket> {
    @Override
    public void encode(Buffer buffer, EntityTeleportPacket packet) {
        buffer.writeVarInt(packet.getEntityId());
        buffer.writeFixedPointInt(packet.getX());
        buffer.writeFixedPointInt(packet.getY());
        buffer.writeFixedPointInt(packet.getZ());
        buffer.writeAngle((float) packet.getYaw());
        buffer.writeAngle((float) packet.getPitch());
        buffer.writeBoolean(packet.isOnGround());
    }

    @Override
    public void decode(Buffer buffer, EntityTeleportPacket packet) {
        packet.setEntityId(buffer.readVarInt());
        packet.setX(buffer.readFixedPointInt());
        packet.setY(buffer.readFixedPointInt());
        packet.setZ(buffer.readFixedPointInt());
        packet.setYaw(buffer.readAngle());
        packet.setPitch(buffer.readAngle());
        packet.setOnGround(buffer.readBoolean());

    }
}
