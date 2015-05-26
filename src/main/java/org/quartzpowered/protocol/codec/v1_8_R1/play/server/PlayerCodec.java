package org.quartzpowered.protocol.codec.v1_8_R1.play.server;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.server.PlayerPacket;

public class PlayerCodec implements Codec<PlayerPacket> {
    @Override
    public void encode(Buffer buffer, PlayerPacket packet) {
        buffer.writeBoolean(packet.isOnGround());
    }

    @Override
    public void decode(Buffer buffer, PlayerPacket packet) {
        packet.setOnGround(buffer.readBoolean());
    }
}
