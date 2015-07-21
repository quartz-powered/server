package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.client.SpawnGlobalEntityPacket;

public class SpawnGlobalEntityCodec implements Codec<SpawnGlobalEntityPacket> {
    @Override
    public void encode(Buffer buffer, SpawnGlobalEntityPacket packet) {
        buffer.writeVarInt(packet.getEntityId());
        buffer.writeByte(packet.getType());
        buffer.writeFixedPointInt(packet.getX());
        buffer.writeFixedPointInt(packet.getY());
        buffer.writeFixedPointInt(packet.getZ());
    }

    @Override
    public void decode(Buffer buffer, SpawnGlobalEntityPacket packet) {
        packet.setEntityId(buffer.readVarInt());
        packet.setType(buffer.readByte());
        packet.setX(buffer.readFixedPointInt());
        packet.setY(buffer.readFixedPointInt());
        packet.setZ(buffer.readFixedPointInt());
    }
}
