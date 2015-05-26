package org.quartzpowered.protocol.codec.v1_8_R1.play.server;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.server.CloseWindowPacketIn;

public class CloseWindowCodecIn implements Codec<CloseWindowPacketIn> {
    @Override
    public void encode(Buffer buffer, CloseWindowPacketIn packet) {
        buffer.writeByte(packet.getWindowId());
    }

    @Override
    public void decode(Buffer buffer, CloseWindowPacketIn packet) {
        packet.setWindowId(buffer.readByte());
    }
}
