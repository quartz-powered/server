package org.quartzpowered.protocol.codec.v1_8_R1.play.server;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.server.SpectatePacket;

public class SpectateCodec implements Codec<SpectatePacket> {
    @Override
    public void encode(Buffer buffer, SpectatePacket packet) {
        buffer.writeUuid(packet.getTargetPlayer());
    }

    @Override
    public void decode(Buffer buffer, SpectatePacket packet) {
        packet.setTargetPlayer(buffer.readUuid());
    }
}
