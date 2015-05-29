package org.quartzpowered.protocol.codec.v1_8_R1.play.server;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.server.ResourcePackStatusPacket;

public class ResourcePackStatusCodec implements Codec<ResourcePackStatusPacket> {
    @Override
    public void encode(Buffer buffer, ResourcePackStatusPacket packet) {
        buffer.writeString(packet.getHash());
        buffer.writeVarInt(packet.getResult());
    }

    @Override
    public void decode(Buffer buffer, ResourcePackStatusPacket packet) {
        packet.setHash(buffer.readString());
        packet.setResult(buffer.readVarInt());
    }
}
