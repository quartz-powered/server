package org.quartzpowered.protocol.packet.login.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;

@Data
@EqualsAndHashCode(callSuper = true)
public class EncryptionRequestPacket extends Packet {
    private String sessionId;
    private byte[] publicKey;
    private byte[] verifyToken;
}
