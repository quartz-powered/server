package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.client.BlockBreakAnimationPacket;

public class BlockBreakAnimationCodec implements Codec<BlockBreakAnimationPacket> {
    @Override
    public void encode(Buffer buffer, BlockBreakAnimationPacket packet) {
        buffer.writeVarInt(packet.getEntityId());
        buffer.writeBlockPosition(packet.getLocation());
        buffer.writeByte(packet.getDestroyStage());
    }

    @Override
    public void decode(Buffer buffer, BlockBreakAnimationPacket packet) {
        packet.setEntityId(buffer.readVarInt());
        packet.setLocation(buffer.readBlockPosition());
        packet.setDestroyStage(buffer.readByte());
    }
}
