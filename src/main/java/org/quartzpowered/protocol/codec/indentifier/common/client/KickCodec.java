package org.quartzpowered.protocol.codec.indentifier.common.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.data.component.TextComponent;
import org.quartzpowered.protocol.packet.common.client.KickPacket;

public class KickCodec implements Codec<KickPacket> {
    @Override
    public void encode(Buffer buffer, KickPacket packet) {
        buffer.writeString(packet.getMessage().toJson());
    }

    @Override
    public void decode(Buffer buffer, KickPacket packet) {
        packet.setMessage(TextComponent.fromJson(buffer.readString()));
    }
}
