package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.client.SetExperiencePacket;

public class SetExperienceCodec implements Codec<SetExperiencePacket> {
    @Override
    public void encode(Buffer buffer, SetExperiencePacket packet) {
        buffer.writeFloat(packet.getExperienceBar());
        buffer.writeVarInt(packet.getLevel());
        buffer.writeVarInt(packet.getLevel());
    }

    @Override
    public void decode(Buffer buffer, SetExperiencePacket packet) {
        packet.setExperienceBar(buffer.readFloat());
        packet.setLevel(buffer.readVarInt());
        packet.setTotalExperience(buffer.readVarInt());
    }
}
