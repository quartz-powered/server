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
