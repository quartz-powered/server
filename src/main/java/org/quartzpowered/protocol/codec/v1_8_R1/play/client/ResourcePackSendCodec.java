package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.client.ResourcePackSendPacket;

public class ResourcePackSendCodec implements Codec<ResourcePackSendPacket> {
    @Override
    public void encode(Buffer buffer, ResourcePackSendPacket packet) {
        buffer.writeString(packet.getUrl());
        buffer.writeString(packet.getHash());
    }

    @Override
    public void decode(Buffer buffer, ResourcePackSendPacket packet) {
        packet.setUrl(buffer.readString());
        packet.setHash(buffer.readString());
    }
}
