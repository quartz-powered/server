package org.quartzpowered.protocol.codec.v1_8_R1.play.client;


import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.data.ParticleType;
import org.quartzpowered.protocol.packet.play.client.ParticlePacket;

import java.util.ArrayList;
import java.util.List;

public class ParticleCodec implements Codec<ParticlePacket> {
    @Override
    public void encode(Buffer buffer, ParticlePacket packet) {
        buffer.writeInt(packet.getType().getId());
        buffer.writeBoolean(packet.isLongDistance());
        buffer.writeFloat(packet.getX());
        buffer.writeFloat(packet.getY());
        buffer.writeFloat(packet.getZ());
        buffer.writeFloat(packet.getOffsetX());
        buffer.writeFloat(packet.getOffsetY());
        buffer.writeFloat(packet.getOffsetZ());
        buffer.writeFloat(packet.getParticleData());
        buffer.writeInt(packet.getParticleCount());

        List<Integer> data = packet.getData();
        buffer.writeVarInt(data.size());
        data.forEach(buffer::writeVarInt);
    }

    @Override
    public void decode(Buffer buffer, ParticlePacket packet) {
        packet.setType(ParticleType.fromId(buffer.readInt()));
        packet.setLongDistance(buffer.readBoolean());
        packet.setX(buffer.readFloat());
        packet.setY(buffer.readFloat());
        packet.setZ(buffer.readFloat());
        packet.setOffsetX(buffer.readFloat());
        packet.setOffsetY(buffer.readFloat());
        packet.setOffsetZ(buffer.readFloat());
        packet.setParticleCount(buffer.readInt());

        List<Integer> data = new ArrayList<>(buffer.readVarInt());
        for (int i = 0; i < data.size(); i++) {
            data.set(i, buffer.readVarInt());
        }
    }
}
