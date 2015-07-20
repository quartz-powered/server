package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.data.chat.component.serialize.ComponentSerializer;
import org.quartzpowered.protocol.packet.play.client.PlayerListHeaderFooterPacket;

public class PlayerListHeaderFooterCodec implements Codec<PlayerListHeaderFooterPacket> {
    @Override
    public void encode(Buffer buffer, PlayerListHeaderFooterPacket packet) {
        buffer.writeString(ComponentSerializer.toString(packet.getHeader()));
        buffer.writeString(ComponentSerializer.toString(packet.getFooter()));
    }

    @Override
    public void decode(Buffer buffer, PlayerListHeaderFooterPacket packet) {
        packet.setHeader(ComponentSerializer.parse(buffer.readString()));
        packet.setFooter(ComponentSerializer.parse(buffer.readString()));
    }
}
