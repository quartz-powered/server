package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.client.WindowPropertyPacket;

public class WindowPropertyCodec implements Codec<WindowPropertyPacket> {
    @Override
    public void encode(Buffer buffer, WindowPropertyPacket packet) {
        buffer.writeByte(packet.getWindowId());
        buffer.writeShort(packet.getProperty());
        buffer.writeShort(packet.getValue());
    }

    @Override
    public void decode(Buffer buffer, WindowPropertyPacket packet) {
        packet.setWindowId(buffer.readByte());
        packet.setProperty(buffer.readShort());
        packet.setValue(buffer.readShort());
    }
}
