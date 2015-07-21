package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.client.SpawnExperiencePacket;

public class SpawnExperienceCodec implements Codec<SpawnExperiencePacket> {
    @Override
    public void encode(Buffer buffer, SpawnExperiencePacket packet) {
        buffer.writeVarInt(packet.getEntityId());
        buffer.writeFixedPointInt(packet.getX());
        buffer.writeFixedPointInt(packet.getY());
        buffer.writeFixedPointInt(packet.getZ());
        buffer.writeShort(packet.getCount());
    }

    @Override
    public void decode(Buffer buffer, SpawnExperiencePacket packet) {
        packet.setEntityId(buffer.readVarInt());
        packet.setX(buffer.readFixedPointInt());
        packet.setY(buffer.readFixedPointInt());
        packet.setZ(buffer.readFixedPointInt());
        packet.setCount(buffer.readShort());
    }
}
