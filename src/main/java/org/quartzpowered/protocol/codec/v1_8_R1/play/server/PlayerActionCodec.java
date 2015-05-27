package org.quartzpowered.protocol.codec.v1_8_R1.play.server;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.data.PlayerAction;
import org.quartzpowered.protocol.packet.play.server.PlayerActionPacket;

public class PlayerActionCodec implements Codec<PlayerActionPacket> {
    @Override
    public void encode(Buffer buffer, PlayerActionPacket packet) {
        buffer.writeByte(packet.getAction().getId());
        buffer.writeBlockPosition(packet.getLocation());
        buffer.writeByte(packet.getFace());
    }

    @Override
    public void decode(Buffer buffer, PlayerActionPacket packet) {
        packet.setAction(PlayerAction.fromId(buffer.readByte()));
        packet.setLocation(buffer.readBlockPosition());
        packet.setFace(buffer.readByte());
    }
}
