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
package org.quartzpowered.server.network.auth;

import com.google.inject.assistedinject.Assisted;
import org.boon.json.ObjectMapper;
import org.quartzpowered.common.eventbus.EventBus;
import org.quartzpowered.common.util.UuidUtil;
import org.quartzpowered.network.session.Session;
import org.quartzpowered.network.session.profile.PlayerProfile;
import org.quartzpowered.network.session.profile.PlayerProperty;
import org.quartzpowered.protocol.data.component.TextComponent;
import org.quartzpowered.protocol.packet.common.client.KickPacket;
import org.quartzpowered.server.event.player.PlayerLoginEvent;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AuthRunnable implements Runnable {
    private static final String BASE_URL = "https://sessionserver.mojang.com/session/minecraft/hasJoined";

    @Inject private Logger logger;
    @Inject private ObjectMapper objectMapper;
    @Inject private UuidUtil uuidUtil;
    @Inject private EventBus eventBus;

    private final Session session;
    private final String hash;

    @Inject
    private AuthRunnable(@Assisted Session session, @Assisted String hash) {
        this.session = session;
        this.hash = hash;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void run() {
        try {
            String username = session.getVerifyUsername();
            String url = BASE_URL + "?username=" + username + "&serverId=" + hash;
            URLConnection connection = new URL(url).openConnection();

            Map response;
            try (InputStream is = connection.getInputStream()) {
                try {
                    response = objectMapper.fromJson(is, Map.class);
                } catch (Exception ex) {
                    logger.warn("Username \"" + username + "\" failed to authenticate!");
                    session.disconnect(new KickPacket(new TextComponent("Failed to verify username!")));
                    return;
                }
            }

            String name = (String) response.get("name");
            String id = (String) response.get("id");

            final UUID uuid;
            try {
                uuid = uuidUtil.fromFlatString(id);
            } catch (IllegalArgumentException ex) {
                logger.error("Returned authentication UUID invalid: " + id, ex);
                session.disconnect(new KickPacket(new TextComponent("Invalid UUID.")));
                return;
            }


            List<Map> responseProperties = (List<Map>) response.get("properties");

            List<PlayerProperty> properties = new ArrayList<>(responseProperties.size());
            for (Map property : responseProperties) {
                String propName = (String) property.get("name");
                String value = (String) property.get("value");
                String signature = (String) property.get("signature");
                properties.add(new PlayerProperty(propName, value, signature));
            }

            session.setProfile(new PlayerProfile(name, uuid, properties));

            eventBus.publish(new PlayerLoginEvent(session));
        } catch (Exception ex) {
            logger.error("Error in authentication thread", ex);
            session.disconnect(new KickPacket(new TextComponent("Internal error during authentication.")));
        }
    }
}
