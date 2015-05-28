package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.client.EntityHeadLookPacket;

public class EntityHeadLookCodec implements Codec<EntityHeadLookPacket> {
    @Override
    public void encode(Buffer buffer, EntityHeadLookPacket packet) {
        buffer.writeVarInt(packet.getEntityId());
        buffer.writeAngle((float) packet.getHeadYaw());
    }

    @Override
    public void decode(Buffer buffer, EntityHeadLookPacket packet) {
        packet.setEntityId(buffer.readVarInt());
        packet.setHeadYaw(buffer.readAngle());
    }
}
