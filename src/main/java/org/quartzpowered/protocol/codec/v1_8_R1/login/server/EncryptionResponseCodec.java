package org.quartzpowered.protocol.codec.v1_8_R1.login.server;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.login.server.EncryptionResponsePacket;

public class EncryptionResponseCodec implements Codec<EncryptionResponsePacket> {
    @Override
    public void encode(Buffer buffer, EncryptionResponsePacket packet) {
        buffer.writeByteArray(packet.getSharedSecret());
        buffer.writeByteArray(packet.getVerifyToken());
    }

    @Override
    public void decode(Buffer buffer, EncryptionResponsePacket packet) {
        packet.setSharedSecret(buffer.readByteArray());
        packet.setVerifyToken(buffer.readByteArray());
    }
}
