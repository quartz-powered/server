package org.quartzpowered.protocol.codec.v1_8_R1.play.server;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.server.PlayerAbilitiesPacketIn;

public class PlayerAbilitiesCodecIn implements Codec<PlayerAbilitiesPacketIn> {
    @Override
    public void encode(Buffer buffer, PlayerAbilitiesPacketIn packet) {
        buffer.writeByte(packet.getFlags());
        buffer.writeFloat(packet.getFlyingSpeed());
        buffer.writeFloat(packet.getWalkingSpeed());
    }

    @Override
    public void decode(Buffer buffer, PlayerAbilitiesPacketIn packet) {
        packet.setFlags(buffer.readByte());
        packet.setFlyingSpeed(buffer.readFloat());
        packet.setWalkingSpeed(buffer.readFloat());
    }
}
