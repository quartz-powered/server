package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.client.CollectItemPacket;

public class CollectItemCodec implements Codec<CollectItemPacket> {
    @Override
    public void encode(Buffer buffer, CollectItemPacket packet) {
        buffer.writeVarInt(packet.getCollectedEntityID());
        buffer.writeVarInt(packet.getCollectorEntityID());
    }

    @Override
    public void decode(Buffer buffer, CollectItemPacket packet) {
        packet.setCollectedEntityID(buffer.readVarInt());
        packet.setCollectorEntityID(buffer.readVarInt());
    }
}
