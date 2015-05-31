package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.client.SoundEffectPacket;

public class SoundEffectCodec implements Codec<SoundEffectPacket> {
    @Override
    public void encode(Buffer buffer, SoundEffectPacket packet) {
        buffer.writeString(packet.getSoundName());
        buffer.writeInt(packet.getEffectPositionX());
        buffer.writeInt(packet.getEffectPositionY());
        buffer.writeInt(packet.getEffectPositionZ());
        buffer.writeFloat(packet.getVolume());
        buffer.writeByte(packet.getPitch());
    }

    @Override
    public void decode(Buffer buffer, SoundEffectPacket packet) {
        packet.setSoundName(buffer.readString());
        packet.setEffectPositionX(buffer.readInt());
        packet.setEffectPositionY(buffer.readInt());
        packet.setEffectPositionZ(buffer.readInt());
        packet.setVolume(buffer.readFloat());
        packet.setPitch(buffer.readByte());
    }
}
