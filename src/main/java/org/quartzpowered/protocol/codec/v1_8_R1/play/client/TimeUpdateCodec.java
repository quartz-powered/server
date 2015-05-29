package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.client.TimeUpdatePacket;

public class TimeUpdateCodec implements Codec<TimeUpdatePacket> {
    @Override
    public void encode(Buffer buffer, TimeUpdatePacket packet) {
        buffer.writeLong(packet.getWorldAge());
        buffer.writeLong(packet.getTimeOfDay());
    }

    @Override
    public void decode(Buffer buffer, TimeUpdatePacket packet) {
        packet.setWorldAge(buffer.readLong());
        packet.setTimeOfDay(buffer.readLong());
    }
}
