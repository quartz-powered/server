package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.data.Animation;
import org.quartzpowered.protocol.packet.play.client.AnimationPacket;

public class AnimationCodec implements Codec<AnimationPacket> {
    @Override
    public void encode(Buffer buffer, AnimationPacket packet) {
        buffer.writeVarInt(packet.getEntityId());
        buffer.writeByte(packet.getAnimation().getId());
    }

    @Override
    public void decode(Buffer buffer, AnimationPacket packet) {
        packet.setEntityId(buffer.readVarInt());
        packet.setAnimation(Animation.fromId(buffer.readByte()));
    }
}
