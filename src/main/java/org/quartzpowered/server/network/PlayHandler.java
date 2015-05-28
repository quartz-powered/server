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

import com.google.inject.Inject;
import net.engio.mbassy.listener.Handler;
import org.quartzpowered.engine.math.Vector3;
import org.quartzpowered.engine.object.GameObject;
import org.quartzpowered.engine.object.component.Camera;
import org.quartzpowered.engine.object.component.Player;
import org.quartzpowered.engine.object.component.Transform;
import org.quartzpowered.network.protocol.packet.Packet;
import org.quartzpowered.network.session.Session;
import org.quartzpowered.network.session.attribute.AttributeKey;
import org.quartzpowered.protocol.data.ChatPosition;
import org.quartzpowered.protocol.data.component.PlayerComponent;
import org.quartzpowered.protocol.data.component.TextComponent;
import org.quartzpowered.protocol.packet.play.client.ChatMessagePacket;
import org.quartzpowered.protocol.packet.play.server.PlayerChatMessagePacket;
import org.quartzpowered.protocol.packet.play.server.PlayerLookPacket;
import org.quartzpowered.protocol.packet.play.server.PlayerPositionLookPacket;
import org.quartzpowered.protocol.packet.play.server.PlayerPositionPacket;
import org.quartzpowered.protocol.packet.play.shared.HeldItemChangePacket;
import org.quartzpowered.protocol.packet.play.shared.KeepAlivePacket;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class PlayHandler {
    public static final AttributeKey<GameObject> PLAYER_OBJECT = AttributeKey.create();

    @Inject private Logger logger;

    List<Session> sessionList = new ArrayList<>();

    /* @Handler
    public void onPacket(Packet packet) {
        logger.info("IN  {}", packet);
    }*/

    @Handler
    public void onPlayerChatMessage(PlayerChatMessagePacket packet) {
        Session session = packet.getSender();

        if(!sessionList.contains(session)) {
            sessionList.add(session);
        }

        KeepAlivePacket keepAlivePacket = new KeepAlivePacket();
        keepAlivePacket.setKeepAliveId(10);
        session.send(keepAlivePacket);

        String formatChat = session.getProfile().getName() + ": " + packet.getMessage();

        ChatMessagePacket chatMessagePacketOut = new ChatMessagePacket();
        chatMessagePacketOut.setMessage(new TextComponent(formatChat));
        chatMessagePacketOut.setPosition(ChatPosition.CHAT);

        for(Session listSession : sessionList){
            listSession.send(chatMessagePacketOut);
        }
        logger.info(formatChat);
    }

    @Handler
    public void onPlayerPosition(PlayerPositionPacket packet) {
        GameObject playerObject = packet.getSender().getAttributes().get(PLAYER_OBJECT);
        Camera camera = playerObject.getComponent(Camera.class);

        camera.setRemotePosition(packet.getPosition());
    }

    @Handler
    public void onPlayerPositionLook(PlayerPositionLookPacket packet) {
        GameObject playerObject = packet.getSender().getAttributes().get(PLAYER_OBJECT);
        Camera camera = playerObject.getComponent(Camera.class);

        camera.setRemotePosition(packet.getPosition());
        camera.setRemoteRotation(packet.getRotation());
    }

    @Handler
    public void onPlayerLook(PlayerLookPacket packet) {
        GameObject playerObject = packet.getSender().getAttributes().get(PLAYER_OBJECT);
        Camera camera = playerObject.getComponent(Camera.class);

        camera.setRemoteRotation(packet.getRotation());
    }
}
