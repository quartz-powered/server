package org.quartzpowered.protocol.codec.v1_8_R1.play.server;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.server.PlayerLookPacket;

public class PlayerLookCodec implements Codec<PlayerLookPacket> {
    @Override
    public void encode(Buffer buffer, PlayerLookPacket packet) {
        buffer.writeFloat(packet.getYaw());
        buffer.writeFloat(packet.getPitch());
        buffer.writeBoolean(packet.isOnGround());
    }

    @Override
    public void decode(Buffer buffer, PlayerLookPacket packet) {
        packet.setYaw(buffer.readFloat());
        packet.setPitch(buffer.readFloat());
        packet.setOnGround(buffer.readBoolean());
    }
}
