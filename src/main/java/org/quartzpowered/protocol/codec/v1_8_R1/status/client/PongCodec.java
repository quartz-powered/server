package org.quartzpowered.protocol.codec.v1_8_R1.status.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.status.client.PongPacket;

public class PongCodec implements Codec<PongPacket> {
    @Override
    public void encode(Buffer buffer, PongPacket packet) {
        buffer.writeLong(packet.getTime());
    }

    @Override
    public void decode(Buffer buffer, PongPacket packet) {
        packet.setTime(buffer.readLong());
    }
}
