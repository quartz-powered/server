package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.client.ChunkDataPacket;

public class ChunkDataCodec implements Codec<ChunkDataPacket> {

    @Override
    public void encode(Buffer buffer, ChunkDataPacket packet) {
        buffer.writeInt(packet.getX());
        buffer.writeInt(packet.getZ());

        buffer.writeBoolean(packet.isContinuous());
        buffer.writeShort(packet.getMask());
        buffer.writeByteArray(packet.getData());
    }

    @Override
    public void decode(Buffer buffer, ChunkDataPacket packet) {
        packet.setX(buffer.readInt());
        packet.setZ(buffer.readInt());

        packet.setContinuous(buffer.readBoolean());
        packet.setMask(buffer.readShort());
        packet.setData(buffer.readByteArray());
    }
}
