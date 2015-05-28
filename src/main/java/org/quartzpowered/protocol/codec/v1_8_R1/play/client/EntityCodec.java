package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.client.EntityPacket;

public class EntityCodec implements Codec<EntityPacket> {
    @Override
    public void encode(Buffer buffer, EntityPacket packet) {
        buffer.writeVarInt(packet.getEntityId());
    }

    @Override
    public void decode(Buffer buffer, EntityPacket packet) {
        packet.setEntityId(buffer.readVarInt());
    }
}
