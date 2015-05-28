package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.client.EntityLookMovePacket;

public class EntityLookMoveCodec implements Codec<EntityLookMovePacket> {
    @Override
    public void encode(Buffer buffer, EntityLookMovePacket packet) {
        buffer.writeVarInt(packet.getEntityId());
        buffer.writeFixedPointByte(packet.getDeltaX());
        buffer.writeFixedPointByte(packet.getDeltaY());
        buffer.writeFixedPointByte(packet.getDeltaZ());
        buffer.writeAngle((float) packet.getYaw());
        buffer.writeAngle((float) packet.getPitch());
        buffer.writeBoolean(packet.isOnGround());

    }

    @Override
    public void decode(Buffer buffer, EntityLookMovePacket packet) {
        packet.setEntityId(buffer.readVarInt());
        packet.setDeltaX(buffer.readFixedPointByte());
        packet.setDeltaY(buffer.readFixedPointByte());
        packet.setDeltaZ(buffer.readFixedPointByte());
        packet.setYaw(buffer.readAngle());
        packet.setPitch(buffer.readAngle());
        packet.setOnGround(buffer.readBoolean());
    }
}
