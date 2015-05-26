package org.quartzpowered.protocol.codec.v1_8_R1.play.server;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.server.PluginMessagePacketIn;

public class PluginMessageCodecIn implements Codec<PluginMessagePacketIn> {
    @Override
    public void encode(Buffer buffer, PluginMessagePacketIn packet) {
        buffer.writeString(packet.getChannel());
        buffer.writeByteArray(packet.getData());
    }

    @Override
    public void decode(Buffer buffer, PluginMessagePacketIn packet) {
        packet.setChannel(buffer.readString());
        packet.setData(buffer.readByteArray());
    }
}
