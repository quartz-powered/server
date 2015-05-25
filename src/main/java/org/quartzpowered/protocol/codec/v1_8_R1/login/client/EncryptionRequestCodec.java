package org.quartzpowered.protocol.codec.v1_8_R1.login.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.login.client.EncryptionRequestPacket;

public class EncryptionRequestCodec implements Codec<EncryptionRequestPacket> {
    @Override
    public void encode(Buffer buffer, EncryptionRequestPacket packet) {
        buffer.writeString(packet.getSessionId());
        buffer.writeByteArray(packet.getPublicKey());
        buffer.writeByteArray(packet.getVerifyToken());
    }

    @Override
    public void decode(Buffer buffer, EncryptionRequestPacket packet) {
        packet.setSessionId(buffer.readString());
        packet.setPublicKey(buffer.readByteArray());
        packet.setVerifyToken(buffer.readByteArray());
    }
}
