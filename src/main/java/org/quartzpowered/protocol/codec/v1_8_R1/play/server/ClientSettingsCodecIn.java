package org.quartzpowered.protocol.codec.v1_8_R1.play.server;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.data.ChatMode;
import org.quartzpowered.protocol.packet.play.server.ClientSettingsPacketIn;

public class ClientSettingsCodecIn implements Codec<ClientSettingsPacketIn> {
    @Override
    public void encode(Buffer buffer, ClientSettingsPacketIn packet) {
        buffer.writeString(packet.getLocale());
        buffer.writeByte(packet.getViewDistance());
        buffer.writeByte(packet.getChatMode().getId());
        buffer.writeBoolean(packet.isChatColors());
        buffer.writeByte(packet.getDisplayedSkinParts());
    }

    @Override
    public void decode(Buffer buffer, ClientSettingsPacketIn packet) {
        packet.setLocale(buffer.readString());
        packet.setViewDistance(buffer.readByte());
        packet.setChatMode(ChatMode.fromId(buffer.readByte()));
        packet.setChatColors(buffer.readBoolean());
        packet.setDisplayedSkinParts(buffer.readByte());
    }
}
