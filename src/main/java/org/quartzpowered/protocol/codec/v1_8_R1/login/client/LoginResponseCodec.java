package org.quartzpowered.protocol.codec.v1_8_R1.login.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.login.client.LoginResponsePacket;

import java.util.Objects;
import java.util.UUID;

public class LoginResponseCodec implements Codec<LoginResponsePacket> {
    @Override
    public void encode(Buffer buffer, LoginResponsePacket packet) {
        buffer.writeString(packet.getUuid().toString());
        buffer.writeString(packet.getUsername());
    }

    @Override
    public void decode(Buffer buffer, LoginResponsePacket packet) {
        packet.setUuid(UUID.fromString(buffer.readString()));
        packet.setUsername(buffer.readString());
    }
}
