package org.quartzpowered.protocol.codec.v1_8_R1.play.server;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.server.ChatMessagePacketIn;

public class ChatMessageCodecIn implements Codec<ChatMessagePacketIn> {
    @Override
    public void encode(Buffer buffer, ChatMessagePacketIn packet) {
        buffer.writeString(packet.getMessage());
    }

    @Override
    public void decode(Buffer buffer, ChatMessagePacketIn packet) {
        packet.setMessage(buffer.readString());
    }
}
