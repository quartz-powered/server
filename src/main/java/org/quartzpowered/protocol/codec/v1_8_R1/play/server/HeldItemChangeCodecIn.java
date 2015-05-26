package org.quartzpowered.protocol.codec.v1_8_R1.play.server;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.server.HeldItemChangePacketIn;

public class HeldItemChangeCodecIn implements Codec<HeldItemChangePacketIn> {
    @Override
    public void encode(Buffer buffer, HeldItemChangePacketIn packet) {
        buffer.writeByte(packet.getSlot());
    }

    @Override
    public void decode(Buffer buffer, HeldItemChangePacketIn packet) {
        packet.setSlot(buffer.readByte());
    }
}
