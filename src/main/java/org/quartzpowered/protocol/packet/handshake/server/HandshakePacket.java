package org.quartzpowered.protocol.packet.handshake.server;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.ProtocolState;
import org.quartzpowered.network.protocol.packet.Packet;

@Data
@EqualsAndHashCode(callSuper = true)
public class HandshakePacket extends Packet {
    private int version;
    private String address;
    private int port;
    private ProtocolState state;
}
