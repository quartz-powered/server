package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.client.UseBedPacket;

public class UseBedCodec implements Codec<UseBedPacket> {
    @Override
    public void encode(Buffer buffer, UseBedPacket packet) {
        buffer.writeVarInt(packet.getEntityId());
        buffer.writeBlockPosition(packet.getLocation());
    }

    @Override
    public void decode(Buffer buffer, UseBedPacket packet) {
        packet.setEntityId(buffer.readVarInt());
        packet.setLocation(buffer.readBlockPosition());
    }
}
