package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.client.EntityStatusPacket;

public class EntityStatusCodec implements Codec<EntityStatusPacket> {
    @Override
    public void encode(Buffer buffer, EntityStatusPacket packet) {
        buffer.writeInt(packet.getEntityID());
        buffer.writeByte(packet.getEntityStatus());
    }

    @Override
    public void decode(Buffer buffer, EntityStatusPacket packet) {
        packet.setEntityID(buffer.readInt());
        packet.setEntityStatus(buffer.readByte());
    }
}
