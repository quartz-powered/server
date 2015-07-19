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
package org.quartzpowered.server;

import lombok.Getter;
import net.engio.mbassy.listener.Handler;
import org.quartzpowered.common.eventbus.EventBus;
import org.quartzpowered.common.util.CryptoUtil;
import org.quartzpowered.engine.level.Level;
import org.quartzpowered.engine.level.LevelFactory;
import org.quartzpowered.engine.math.Vector3;
import org.quartzpowered.engine.object.GameObject;
import org.quartzpowered.engine.object.GameObjectFactory;
import org.quartzpowered.engine.object.component.Camera;
import org.quartzpowered.engine.object.component.Player;
import org.quartzpowered.network.server.NetworkServer;
import org.quartzpowered.network.session.Session;
import org.quartzpowered.protocol.data.*;
import org.quartzpowered.protocol.data.component.TextComponent;
import org.quartzpowered.protocol.data.info.PlayerInfo;
import org.quartzpowered.protocol.data.info.PlayerInfoAction;
import org.quartzpowered.protocol.packet.login.client.LoginResponsePacket;
import org.quartzpowered.protocol.packet.play.client.*;
import org.quartzpowered.protocol.packet.play.server.BlockPlacePacket;
import org.quartzpowered.server.event.player.PlayerLoginEvent;
import org.quartzpowered.server.event.player.PlayerQuitEvent;
import org.quartzpowered.server.network.HandshakeHandler;
import org.quartzpowered.server.network.LoginHandler;
import org.quartzpowered.server.network.PingHandler;
import org.quartzpowered.server.network.PlayHandler;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.quartzpowered.network.protocol.ProtocolState.PLAY;

@Singleton
public class Server {
    @Inject private Logger logger;
    @Inject private EventBus eventBus;

    @Inject private HandshakeHandler handshakeHandler;
    @Inject private PingHandler pingHandler;
    @Inject private LoginHandler loginHandler;
    @Inject private PlayHandler playHandler;

    @Inject private NetworkServer networkServer;
    @Inject private GameObjectFactory gameObjectFactory;

    @Getter
    private KeyPair keyPair;

    private final Level level;

    @Inject
    private Server(CryptoUtil cryptoUtil, LevelFactory levelFactory) {
        keyPair = cryptoUtil.generateRSAKeyPair();
        level = levelFactory.create();
    }

    public void main(String args[]) {
        registerHandlers();

        networkServer.bind(25565);
    }

    private void registerHandlers() {
        eventBus.subscribe(handshakeHandler);
        eventBus.subscribe(pingHandler);
        eventBus.subscribe(loginHandler);
        eventBus.subscribe(playHandler);
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
            joinGamePacket.setDimension(Dimension.OVERWORLD);
            joinGamePacket.setDifficulty(Difficulty.NORMAL);
            joinGamePacket.setLevelType("default");
            joinGamePacket.setEntityId(0xCAFEBABE);
            session.send(joinGamePacket);

            ChunkBulkPacket chunkBulkPacket = new ChunkBulkPacket();
            chunkBulkPacket.setSkylight(true);

            List<ChunkPacket> chunkPackets = new ArrayList<>();

            for (int cx = -1; cx < 6; cx++) {
                for (int cz = -1; cz < 6; cz++) {
                    ChunkPacket chunkPacket = new ChunkPacket();
                    chunkPacket.setX(cx);
                    chunkPacket.setZ(cz);

                    chunkPacket.setMask(0b1);

                    byte[] bytes = new byte[8192 + 2048 * 2 + 256];
                    int pos = 0;

                    for (int i = 0; i < 4096; i++) {
                        int y = i >> 8;
                        int z = ((cz + 1) << 4) + ((i >> 4) & 0xf);
                        int x = (cx << 4) + (i & 0xf);

                        z++;

                        int id = 35;
                        if (y != 10) {
                            id = 0;
                        }
                        int meta = ((x ^ z) & 1) != 0 ? (Math.abs(x + 0x80) >> 4) & 0xf : (Math.abs(z) >> 4) & 0xf;
                        int block = (id << 4) | meta;

                        bytes[pos++] = (byte) (block & 0xff);
                        bytes[pos++] = (byte) ((block >> 8) & 0xff);
                    }

                    for (int i = 0; i < 2048; i++) {
                        bytes[pos++] = (byte) 0xff;
                    }

                    for (int i = 0; i < 2048; i++) {
                        bytes[pos++] = (byte) 0xff;
                    }

                    for (int i = 0; i < 256; i++) {
                        bytes[pos++] = (byte) (i % 10);
                    }

                    chunkPacket.setData(bytes);
                    chunkPackets.add(chunkPacket);
                }
            }

            chunkBulkPacket.setChunks(chunkPackets);
            session.send(chunkBulkPacket);

            PlayerInfo info = new PlayerInfo();
            info.setProfile(session.getProfile());
            info.setGamemode(Gamemode.SURVIVAL);

            PlayerInfoPacket infoPacket = new PlayerInfoPacket();
            infoPacket.setAction(PlayerInfoAction.ADD);
            infoPacket.setInfo(Arrays.asList(info));
            session.send(infoPacket);

            GameObject playerObject = gameObjectFactory.create();
            playerObject.setName(session.getProfile().getName());

            playerObject.getTransform().setPosition(new Vector3(10, 11, 10));
            playerObject.setParent(level.getRoot());

            Camera camera = playerObject.addComponent(Camera.class);
            camera.addViewer(session);

            Player player = playerObject.addComponent(Player.class);
            player.setProfile(session.getProfile());

            session.getAttributes().set(PlayHandler.PLAYER_OBJECT, playerObject);
        });
        
    }

    @Handler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Session session = event.getSession();

        GameObject playerObject = session.getAttributes().get(PlayHandler.PLAYER_OBJECT);
        if (playerObject != null) {
            playerObject.setParent(null);
        }
    }
}
