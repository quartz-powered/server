package org.quartzpowered.protocol.codec.v1_8_R1.status.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.data.component.StatusComponent;
import org.quartzpowered.protocol.packet.status.client.StatusResponsePacket;

public class StatusResponseCodec implements Codec<StatusResponsePacket> {
    @Override
    public void encode(Buffer buffer, StatusResponsePacket packet) {
        buffer.writeString(packet.getStatus().toJson());
    }

    @Override
    public void decode(Buffer buffer, StatusResponsePacket packet) {
        packet.setStatus(StatusComponent.fromJson(buffer.readString()));
    }
}
