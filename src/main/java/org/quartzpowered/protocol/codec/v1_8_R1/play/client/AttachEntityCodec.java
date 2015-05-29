package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.client.AttachEntityPacket;

public class AttachEntityCodec implements Codec<AttachEntityPacket> {
    @Override
    public void encode(Buffer buffer, AttachEntityPacket packet) {
        buffer.writeInt(packet.getEntityID());
        buffer.writeVarInt(packet.getVehicleID());
        buffer.writeBoolean(packet.isLeash());
    }

    @Override
    public void decode(Buffer buffer, AttachEntityPacket packet) {
        packet.setEntityID(buffer.readInt());
        packet.setVehicleID(buffer.readInt());
        packet.setLeash(buffer.readBoolean());
    }
}
