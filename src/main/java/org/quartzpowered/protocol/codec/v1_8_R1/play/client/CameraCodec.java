package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.client.CameraPacket;

public class CameraCodec implements Codec<CameraPacket> {
    @Override
    public void encode(Buffer buffer, CameraPacket packet) {
        buffer.writeVarInt(packet.getId());
    }

    @Override
    public void decode(Buffer buffer, CameraPacket packet) {
        packet.setId(buffer.readVarInt());
    }
}
