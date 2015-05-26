/*
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
import org.quartzpowered.network.session.Session;
import org.quartzpowered.protocol.data.component.PlayersComponent;
import org.quartzpowered.protocol.data.component.StatusComponent;
import org.quartzpowered.protocol.data.component.TextComponent;
import org.quartzpowered.protocol.data.component.VersionComponent;
import org.quartzpowered.protocol.packet.status.client.PongPacket;
import org.quartzpowered.protocol.packet.status.client.StatusResponsePacket;
import org.quartzpowered.protocol.packet.status.server.PingPacket;
import org.quartzpowered.protocol.packet.status.server.StatusRequestPacket;

public class PingHandler {
    @Handler
    public void onStatusRequest(StatusRequestPacket packet) {
        Session session = packet.getSender();
        Protocol protocol = session.getProtocol();

        StatusResponsePacket response = new StatusResponsePacket();

        VersionComponent version = new VersionComponent();
        version.setName(protocol.getName());
        version.setProtocol(protocol.getVersion());

        PlayersComponent players = new PlayersComponent();
        players.setMax(9001);
        players.setOnline(-1);

        StatusComponent status = new StatusComponent();
        status.setVersion(version);
        status.setPlayers(players);

        status.setDescription(new TextComponent("Hello Quartz!"));

        response.setStatus(status);

        session.send(response);
    }


    @Handler
    public void handlePing(PingPacket packet) {
        Session session = packet.getSender();

        PongPacket response = new PongPacket();
        response.setTime(packet.getTime());

        session.send(response);
    }
}
