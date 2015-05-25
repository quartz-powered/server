package org.quartzpowered.protocol.codec.indentifier;

import org.quartzpowered.network.protocol.Protocol;
import org.quartzpowered.protocol.codec.indentifier.handshake.server.HandshakeCodec;
import org.quartzpowered.protocol.codec.indentifier.common.client.KickCodec;
import org.quartzpowered.protocol.packet.common.client.KickPacket;
import org.quartzpowered.protocol.packet.handshake.server.HandshakePacket;

import static org.quartzpowered.network.protocol.ProtocolState.HANDSHAKE;
import static org.quartzpowered.network.protocol.ProtocolState.LOGIN;

public class IdentifierProtocol extends Protocol {
    @Override
    public String getName() {
        return "identifier";
    }

    @Override
    public int getVersion() {
        return -1;
    }

    @Override
    protected void registerPackets() {
        serverBoundPacket(HANDSHAKE, 0, HandshakePacket.class, new HandshakeCodec());
        clientBoundPacket(LOGIN, 0, KickPacket.class, new KickCodec());
    }
}
