package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.data.PotionEffect;
import org.quartzpowered.protocol.packet.play.client.EntityEffectPacket;

public class EntityEffectCodec implements Codec<EntityEffectPacket> {
    @Override
    public void encode(Buffer buffer, EntityEffectPacket packet) {
        buffer.writeVarInt(packet.getEntityId());
        buffer.writeByte(packet.getEffect().getId());
        buffer.writeByte(packet.getAmplifier());
        buffer.writeVarInt(packet.getDuration());
        buffer.writeBoolean(packet.isHideParticles());
    }

    @Override
    public void decode(Buffer buffer, EntityEffectPacket packet) {
        packet.setEntityId(buffer.readVarInt());
        packet.setEffect(PotionEffect.fromId(buffer.readByte()));
        packet.setAmplifier(buffer.readByte());
        packet.setDuration(buffer.readVarInt());
        packet.setHideParticles(buffer.readBoolean());
    }
}
