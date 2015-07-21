package org.quartzpowered.protocol.codec.v1_8_R1.play.shared;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.data.chat.component.serialize.ComponentSerializer;
import org.quartzpowered.protocol.packet.play.shared.UpdateSignPacket;

public class UpdateSignCodec implements Codec<UpdateSignPacket> {
    @Override
    public void encode(Buffer buffer, UpdateSignPacket packet) {
        buffer.writeBlockPosition(packet.getLocation());
        buffer.writeString(ComponentSerializer.toString(packet.getLine1()));
        buffer.writeString(ComponentSerializer.toString(packet.getLine2()));
        buffer.writeString(ComponentSerializer.toString(packet.getLine3()));
        buffer.writeString(ComponentSerializer.toString(packet.getLine4()));
    }

    @Override
    public void decode(Buffer buffer, UpdateSignPacket packet) {
        packet.setLocation(buffer.readBlockPosition());
        packet.setLine1(ComponentSerializer.parse(buffer.readString()));
        packet.setLine2(ComponentSerializer.parse(buffer.readString()));
        packet.setLine3(ComponentSerializer.parse(buffer.readString()));
        packet.setLine4(ComponentSerializer.parse(buffer.readString()));
    }
}
