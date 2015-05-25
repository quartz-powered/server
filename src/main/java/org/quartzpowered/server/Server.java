package org.quartzpowered.server;

import lombok.Getter;
import net.engio.mbassy.listener.Handler;
import org.quartzpowered.common.eventbus.EventBus;
import org.quartzpowered.common.util.CryptoUtil;
import org.quartzpowered.network.server.NetworkServer;
import org.quartzpowered.network.session.Session;
import org.quartzpowered.protocol.data.ChatPosition;
import org.quartzpowered.protocol.data.Difficulty;
import org.quartzpowered.protocol.data.Dimension;
import org.quartzpowered.protocol.data.Gamemode;
import org.quartzpowered.protocol.data.component.TextComponent;
import org.quartzpowered.protocol.packet.login.client.LoginResponsePacket;
import org.quartzpowered.protocol.packet.play.client.ChatMessagePacket;
import org.quartzpowered.protocol.packet.play.client.JoinGamePacket;
import org.quartzpowered.protocol.packet.play.client.PlayerPositionAndLookPacket;
import org.quartzpowered.server.event.player.PlayerLoginEvent;
import org.quartzpowered.server.network.HandshakeHandler;
import org.quartzpowered.server.network.LoginHandler;
import org.quartzpowered.server.network.PingHandler;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.security.KeyPair;

import static org.quartzpowered.network.protocol.ProtocolState.*;

@Singleton
public class Server {
    @Inject private Logger logger;
    @Inject private EventBus eventBus;

    @Inject private HandshakeHandler handshakeHandler;
    @Inject private PingHandler pingHandler;
    @Inject private LoginHandler loginHandler;

    @Inject private NetworkServer networkServer;

    @Getter
    private KeyPair keyPair;

    @Inject
    private Server(CryptoUtil cryptoUtil) {
        keyPair = cryptoUtil.generateRSAKeyPair();
    }

    public void main(String args[]) {
        registerHandlers();

        networkServer.bind(25565);
    }

    private void registerHandlers() {
        eventBus.subscribe(handshakeHandler);
        eventBus.subscribe(pingHandler);
        eventBus.subscribe(loginHandler);
        eventBus.subscribe(this);
    }

    @Handler
    public void onPlayerLogin(PlayerLoginEvent event) {
        Session session = event.getSession();

        LoginResponsePacket loginResponse = new LoginResponsePacket();
        loginResponse.setUuid(session.getProfile().getUniqueId());
        loginResponse.setUsername(session.getProfile().getName());
        session.send(loginResponse).addListener(future -> {

            session.setState(PLAY);

            JoinGamePacket joinGamePacket = new JoinGamePacket();
            joinGamePacket.setGamemode(Gamemode.CREATIVE);
            joinGamePacket.setDimension(Dimension.NETHER);
            joinGamePacket.setDifficulty(Difficulty.NORMAL);
            joinGamePacket.setLevelType("default");
            session.send(joinGamePacket);

            PlayerPositionAndLookPacket playerPositionAndLookPacket = new PlayerPositionAndLookPacket();
            session.send(playerPositionAndLookPacket);

            ChatMessagePacket chatMessagePacket =  new ChatMessagePacket();
            chatMessagePacket.setMessage(new TextComponent("Welcome to QuartzPowered!"));
            chatMessagePacket.setPosition(ChatPosition.CHAT);
            session.send(chatMessagePacket);

        });

    }
}
