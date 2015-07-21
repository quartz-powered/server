package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.client.BlockChangePacket;

public class BlockChangeCodec implements Codec<BlockChangePacket> {
    @Override
    public void encode(Buffer buffer, BlockChangePacket packet) {
        buffer.writeBlockPosition(packet.getLocation());
        buffer.writeVarInt(packet.getBlockId());
    }

    @Override
    public void decode(Buffer buffer, BlockChangePacket packet) {
        packet.setLocation(buffer.readBlockPosition());
        packet.setBlockId(buffer.readVarInt());
    }
}
