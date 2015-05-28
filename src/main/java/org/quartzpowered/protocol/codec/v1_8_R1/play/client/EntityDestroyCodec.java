package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.client.EntityDestroyPacket;

import java.util.ArrayList;
import java.util.List;

public class EntityDestroyCodec implements Codec<EntityDestroyPacket> {
    @Override
    public void encode(Buffer buffer, EntityDestroyPacket packet) {
        List<Integer> entityIds = packet.getEntityIds();
        buffer.writeVarInt(entityIds.size());
        entityIds.forEach(buffer::writeVarInt);
    }

    @Override
    public void decode(Buffer buffer, EntityDestroyPacket packet) {
        List<Integer> entityIds = new ArrayList<>(buffer.readVarInt());
        for (int i = 0; i < entityIds.size(); i++) {
            entityIds.set(i, buffer.readVarInt());
        }
    }
}
