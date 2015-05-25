package org.quartzpowered.server.network;

import net.engio.mbassy.listener.Handler;
import org.quartzpowered.network.protocol.Protocol;
import org.quartzpowered.network.protocol.ProtocolRegistry;
import org.quartzpowered.network.protocol.ProtocolState;
import org.quartzpowered.network.session.Session;
import org.quartzpowered.protocol.data.component.TextComponent;
import org.quartzpowered.protocol.packet.common.client.KickPacket;
import org.quartzpowered.protocol.packet.handshake.server.HandshakePacket;

import javax.inject.Inject;
import java.util.EnumSet;

import static org.quartzpowered.network.protocol.ProtocolState.LOGIN;
import static org.quartzpowered.network.protocol.ProtocolState.STATUS;

public class HandshakeHandler {
    private static final EnumSet<ProtocolState> ALLOWED_STATES = EnumSet.of(STATUS, LOGIN);

    @Inject private ProtocolRegistry protocolRegistry;

    @Handler
    public void onHandshake(HandshakePacket packet) {
        Session session = packet.getSender();

        ProtocolState state = packet.getState();
        if (!ALLOWED_STATES.contains(state)) {
            session.disconnect();
            return;
        }

        session.setState(state);

        Protocol protocol = protocolRegistry.lookup(packet.getVersion());
        if (protocol == null) {
            KickPacket kickPacket = new KickPacket();
            kickPacket.setMessage(new TextComponent("Unsupported protocol version: " + packet.getVersion()));

            session.disconnect(kickPacket);
            return;
        }

        session.setProtocol(protocol);
    }
}
