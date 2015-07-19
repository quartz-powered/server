/**
 * This file is a component of Quartz Powered, this license makes sure any work
 * associated with Quartz Powered, must follow the conditions of the license included.
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Quartz Powered
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.quartzpowered.server.network;

import net.engio.mbassy.listener.Handler;
import org.quartzpowered.network.protocol.Protocol;
import org.quartzpowered.network.protocol.ProtocolRegistry;
import org.quartzpowered.network.protocol.ProtocolState;
import org.quartzpowered.network.session.Session;
import org.quartzpowered.protocol.data.chat.component.TextComponent;
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
