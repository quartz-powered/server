package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.client.SignEditorOpenPacket;

public class SignEditorOpenCodec implements Codec<SignEditorOpenPacket> {
    @Override
    public void encode(Buffer buffer, SignEditorOpenPacket packet) {
        buffer.writeBlockPosition(packet.getLocation());
    }

    @Override
    public void decode(Buffer buffer, SignEditorOpenPacket packet) {
        packet.setLocation(buffer.readBlockPosition());
    }
}
