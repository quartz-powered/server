package org.quartzpowered.protocol.codec.v1_8_R1.status.server;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.status.server.PingPacket;

public class PingCodec implements Codec<PingPacket> {
    @Override
    public void encode(Buffer buffer, PingPacket packet) {
        buffer.writeLong(packet.getTime());
    }

    @Override
    public void decode(Buffer buffer, PingPacket packet) {
        packet.setTime(buffer.readLong());
    }
}
