package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.client.ChunkBulkPacket;
import org.quartzpowered.protocol.packet.play.client.ChunkDataPacket;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class ChunkBulkCodec implements Codec<ChunkBulkPacket> {

    @Override
    public void encode(Buffer buffer, ChunkBulkPacket packet) {
        final List<ChunkDataPacket> chunks = packet.getChunks();

        buffer.writeBoolean(packet.isSkylight());
        buffer.writeVarInt(chunks.size());
        for (ChunkDataPacket chunk : chunks) {
            buffer.writeInt(chunk.getX());
            buffer.writeInt(chunk.getZ());
            buffer.writeShort(chunk.getMask());
        }
        for (ChunkDataPacket chunk : chunks) {
            buffer.writeBytes(chunk.getData());
        }
    }

    @Override
    public void decode(Buffer buffer, ChunkBulkPacket packet) {
        throw new NotImplementedException();
    }
}
