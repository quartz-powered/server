package org.quartzpowered.protocol.codec.v1_8_R1.play.shared;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.shared.KeepAlivePacket;

public class KeepAliveCodec implements Codec<KeepAlivePacket> {
    @Override
    public void encode(Buffer buffer, KeepAlivePacket packet) {
        buffer.writeVarInt(packet.getKeepAliveId());
    }

    @Override
    public void decode(Buffer buffer, KeepAlivePacket packet) {
        packet.setKeepAliveId(buffer.readVarInt());
    }
}
