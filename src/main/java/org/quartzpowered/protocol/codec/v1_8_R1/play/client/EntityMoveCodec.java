package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.client.EntityMovePacket;

public class EntityMoveCodec implements Codec<EntityMovePacket> {
    @Override
    public void encode(Buffer buffer, EntityMovePacket packet) {
        buffer.writeVarInt(packet.getEntityId());
        buffer.writeFixedPointByte(packet.getDeltaX());
        buffer.writeFixedPointByte(packet.getDeltaY());
        buffer.writeFixedPointByte(packet.getDeltaZ());
        buffer.writeBoolean(packet.isOnGround());
    }

    @Override
    public void decode(Buffer buffer, EntityMovePacket packet) {
        packet.setEntityId(buffer.readVarInt());
        packet.setDeltaX(buffer.readFixedPointByte());
        packet.setDeltaY(buffer.readFixedPointByte());
        packet.setDeltaZ(buffer.readFixedPointByte());
        packet.setOnGround(buffer.readBoolean());
    }
}
