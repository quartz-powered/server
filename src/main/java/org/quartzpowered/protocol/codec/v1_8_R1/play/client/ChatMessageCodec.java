package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.data.ChatPosition;
import org.quartzpowered.protocol.data.component.TextComponent;
import org.quartzpowered.protocol.packet.play.client.ChatMessagePacket;

public class ChatMessageCodec implements Codec<ChatMessagePacket> {
    @Override
    public void encode(Buffer buffer, ChatMessagePacket packet) {
        buffer.writeString(packet.getMessage().toJson());
        buffer.writeByte(packet.getPosition().getId());
    }

    @Override
    public void decode(Buffer buffer, ChatMessagePacket packet) {
        packet.setMessage(TextComponent.fromJson(buffer.readString()));
        packet.setPosition(ChatPosition.fromId(buffer.readByte()));
    }
}
