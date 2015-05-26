package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.client.HeldItemChangePacketOut;

public class HeldItemChangeCodecOut implements Codec<HeldItemChangePacketOut> {
    @Override
    public void encode(Buffer buffer, HeldItemChangePacketOut packet) {
        buffer.writeShort(packet.getSlot());
    }

    @Override
    public void decode(Buffer buffer, HeldItemChangePacketOut packet) {
        packet.setSlot(buffer.readShort());
    }
}
