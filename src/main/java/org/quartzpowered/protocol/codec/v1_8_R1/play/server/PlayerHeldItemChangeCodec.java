package org.quartzpowered.protocol.codec.v1_8_R1.play.server;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.shared.HeldItemChangePacket;

public class PlayerHeldItemChangeCodec implements Codec<HeldItemChangePacket> {
    @Override
    public void encode(Buffer buffer, HeldItemChangePacket packet) {
        buffer.writeByte(packet.getSlot());
    }

    @Override
    public void decode(Buffer buffer, HeldItemChangePacket packet) {
        packet.setSlot(buffer.readByte());
    }
}
