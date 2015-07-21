package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.client.BlockActionPacket;

public class BlockActionCodec implements Codec<BlockActionPacket> {
    @Override
    public void encode(Buffer buffer, BlockActionPacket packet) {
        buffer.writeBlockPosition(packet.getLocation());
        buffer.writeByte(packet.getByte1());
        buffer.writeByte(packet.getByte2());
        buffer.writeVarInt(packet.getBlockType());
    }

    @Override
    public void decode(Buffer buffer, BlockActionPacket packet) {
        packet.setLocation(buffer.readBlockPosition());
        packet.setByte1(buffer.readByte());
        packet.setByte2(buffer.readByte());
        packet.setBlockType(buffer.readVarInt());
    }
}
