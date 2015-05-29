package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.client.RemoveEntityEffectPacket;

public class RemoveEntityEffectCodec implements Codec<RemoveEntityEffectPacket> {
    @Override
    public void encode(Buffer buffer, RemoveEntityEffectPacket packet) {
        buffer.writeVarInt(packet.getEntityID());
        buffer.writeByte(packet.getEffectID());
    }

    @Override
    public void decode(Buffer buffer, RemoveEntityEffectPacket packet) {
        packet.setEntityID(buffer.readInt());
        packet.setEffectID(buffer.readByte());
    }
}
