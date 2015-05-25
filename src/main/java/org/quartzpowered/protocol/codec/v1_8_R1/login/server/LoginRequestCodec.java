package org.quartzpowered.protocol.codec.v1_8_R1.login.server;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.login.server.LoginRequestPacket;

public class LoginRequestCodec implements Codec<LoginRequestPacket> {
    @Override
    public void encode(Buffer buffer, LoginRequestPacket packet) {
        buffer.writeString(packet.getUsername());
    }

    @Override
    public void decode(Buffer buffer, LoginRequestPacket packet) {
        packet.setUsername(buffer.readString());
    }
}
