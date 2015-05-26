package org.quartzpowered.protocol.codec.v1_8_R1.play.server;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.data.UseEntityType;
import org.quartzpowered.protocol.packet.play.server.UseEntityPacket;

public class UseEntityCodecIn implements Codec<UseEntityPacket> {
    @Override
    public void encode(Buffer buffer, UseEntityPacket packet) {
        buffer.writeVarInt(packet.getTarget());
        buffer.writeVarInt(packet.getType().getId());
        buffer.writeFloat(packet.getTargetX());
        buffer.writeFloat(packet.getTargetY());
        buffer.writeFloat(packet.getTargetZ());
    }

    @Override
    public void decode(Buffer buffer, UseEntityPacket packet) {
        packet.setTarget(buffer.readVarInt());
        packet.setType(UseEntityType.fromId(buffer.readVarInt()));
        packet.setTargetX(buffer.readFloat());
        packet.setTargetY(buffer.readFloat());
        packet.setTargetZ(buffer.readFloat());
    }
}
