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
package org.quartzpowered.engine.object.component;

import lombok.Getter;
import org.quartzpowered.engine.entity.EntityManager;
import org.quartzpowered.engine.math.Quaternion;
import org.quartzpowered.engine.math.Vector3;
import org.quartzpowered.engine.object.Component;
import org.quartzpowered.engine.object.message.MessageHandler;
import org.quartzpowered.engine.object.annotation.Property;
import org.quartzpowered.engine.observe.Observer;
import org.quartzpowered.network.session.profile.PlayerProfile;
import org.quartzpowered.protocol.data.Gamemode;
import org.quartzpowered.protocol.data.info.PlayerInfo;
import org.quartzpowered.protocol.data.info.PlayerInfoAction;
import org.quartzpowered.protocol.data.metadata.Metadata;
import org.quartzpowered.protocol.packet.play.client.*;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.util.Arrays;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Player extends Component {
    private static final int KEY_FRAME_INTERVAL = 20;

    @Inject private EntityManager entityManager;

    @Getter
    @Property
    private PlayerProfile profile;

    private int entityId;

    private final Vector3 previousPosition = new Vector3();
    private double previousPitch, previousYaw, previousHeadYaw;

    private int nextKeyFrame = KEY_FRAME_INTERVAL;

    @Getter
    private final Metadata metadata = new Metadata();

    public void setProfile(PlayerProfile profile) {
        this.profile = profile;

        // todo respawn player with different profile etc
    }

    @MessageHandler
    public void init() {
        entityId = entityManager.nextEntityId();
        metadata.setByte(10, 0xff);
    }

    @MessageHandler
    public void update() {
        Transform transform = gameObject.getTransform();
        Vector3 position = transform.getPosition();

        Vector3 delta = position.subtract(previousPosition);

        double deltaX = delta.getX();
        double deltaY = delta.getY();
        double deltaZ = delta.getZ();

        Quaternion rotation = transform.getRotation();
        Vector3 euler = rotation.getEuler();
        double pitch = euler.getX();
        double yaw = euler.getY();
        double headYaw = yaw;

        boolean relative = min(min(deltaX, deltaY), deltaZ) >= -4 &&
                max(max(deltaX, deltaY), deltaY) <= 4;

        boolean look = previousPitch != pitch || previousYaw != yaw;

        boolean headLook = previousHeadYaw != headYaw;

        boolean idle = (deltaX == 0 && deltaY == 0 && deltaZ == 0 && !look);

        boolean onGround = true;

        if (nextKeyFrame-- == 0) {
            nextKeyFrame = KEY_FRAME_INTERVAL;

            look = true;
            idle = false;
            relative = false;
        }

        if (idle) {
            EntityPacket entityPacket = new EntityPacket();
            entityPacket.setEntityId(entityId);
            gameObject.observe(entityPacket);
        } else if (relative) {
            if (look) {
                EntityLookMovePacket lookMovePacket = new EntityLookMovePacket();
                lookMovePacket.setEntityId(entityId);
                lookMovePacket.setDelta(deltaX, deltaY, deltaZ);
                lookMovePacket.setRotation(yaw, pitch);
                lookMovePacket.setOnGround(onGround);
                gameObject.observe(lookMovePacket);
            } else {
                EntityMovePacket movePacket = new EntityMovePacket();
                movePacket.setEntityId(entityId);
                movePacket.setDelta(deltaX, deltaY, deltaZ);
                movePacket.setOnGround(onGround);
                gameObject.observe(movePacket);
            }
        } else {
            EntityTeleportPacket teleportPacket = new EntityTeleportPacket();
            teleportPacket.setEntityId(entityId);
            teleportPacket.setPosition(position);
            teleportPacket.setRotation(yaw, pitch);
            teleportPacket.setOnGround(onGround);
            gameObject.observe(teleportPacket);
        }

        if (headLook) {
            EntityHeadLookPacket headLookPacket = new EntityHeadLookPacket();
            headLookPacket.setEntityId(entityId);
            headLookPacket.setHeadYaw(headYaw);
            gameObject.observe(headLookPacket);
        }

        previousPosition.set(position);
        previousPitch = pitch;
        previousYaw = yaw;
        previousHeadYaw = headYaw;
    }

    @MessageHandler
    public void startObserving(Observer observer) {
        Transform transform = gameObject.getTransform();

        PlayerInfo info = new PlayerInfo();
        info.setProfile(profile);
        info.setGamemode(Gamemode.SURVIVAL);

        PlayerInfoPacket infoPacket = new PlayerInfoPacket();
        infoPacket.setAction(PlayerInfoAction.ADD);
        infoPacket.setInfo(Arrays.asList(info));

        SpawnPlayerPacket spawnPacket = new SpawnPlayerPacket();
        spawnPacket.setEntityId(entityId);
        spawnPacket.setPlayerId(profile.getUniqueId());
        spawnPacket.setPosition(transform.getPosition());
        spawnPacket.setRotation(transform.getRotation());
        spawnPacket.setHeldItem(0);
        spawnPacket.setMetadata(metadata);

        observer.observe(infoPacket);
        observer.observe(spawnPacket);
    }

    @MessageHandler
    public void stopObserving(Observer observer) {
        PlayerInfo info = new PlayerInfo();
        info.setProfile(profile);

        PlayerInfoPacket infoPacket = new PlayerInfoPacket();
        infoPacket.setAction(PlayerInfoAction.REMOVE);
        infoPacket.setInfo(Arrays.asList(info));

        EntityDestroyPacket destroyPacket = new EntityDestroyPacket();
        destroyPacket.setEntityIds(Arrays.asList(entityId));

        observer.observe(destroyPacket);
        observer.observe(infoPacket);
    }
}
