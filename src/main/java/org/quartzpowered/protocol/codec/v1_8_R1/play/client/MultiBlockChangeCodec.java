package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.data.RelativeBlockRecord;
import org.quartzpowered.protocol.packet.play.client.MultiBlockChangePacket;

import java.util.List;

public class MultiBlockChangeCodec implements Codec<MultiBlockChangePacket> {
    @Override
    public void encode(Buffer buffer, MultiBlockChangePacket packet) {
        buffer.writeInt(packet.getChunkX());
        buffer.writeInt(packet.getChunkZ());

        buffer.writeVarInt(packet.getBlocks().size());
        for (RelativeBlockRecord record : packet.getBlocks()) {
            buffer.writeByte((record.getX() << 4) | (record.getZ() & 0xf));
            buffer.writeByte(record.getY());
            buffer.writeVarInt((record.getId() << 4) | (record.getData() & 0xf));
        }
    }

    @Override
    public void decode(Buffer buffer, MultiBlockChangePacket packet) {
        packet.setChunkX(buffer.readInt());
        packet.setChunkZ(buffer.readInt());

        List<RelativeBlockRecord> records = packet.getBlocks();
        int count = buffer.readVarInt();
        for (int i = 0; i < count; i++) {
            RelativeBlockRecord record = new RelativeBlockRecord();

            int pos = buffer.readByte();
            record.setX(pos >> 4);
            record.setZ(pos & 0xf);
            record.setY(buffer.readByte());

            int block = buffer.readVarInt();
            record.setId(block >> 4);
            record.setData(block & 0xf);

            records.add(record);
        }
    }
}
