package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.client.EntityLookPacket;

public class EntityLookCodec implements Codec<EntityLookPacket> {
    @Override
    public void encode(Buffer buffer, EntityLookPacket packet) {
        buffer.writeVarInt(packet.getEntityId());
        buffer.writeAngle(packet.getYaw());
        buffer.writeAngle(packet.getPitch());
        buffer.writeBoolean(packet.isOnGround());
    }

    @Override
    public void decode(Buffer buffer, EntityLookPacket packet) {
        packet.setEntityId(buffer.readVarInt());
        packet.setYaw(buffer.readAngle());
        packet.setPitch(buffer.readAngle());
        packet.setOnGround(buffer.readBoolean());
    }
}
