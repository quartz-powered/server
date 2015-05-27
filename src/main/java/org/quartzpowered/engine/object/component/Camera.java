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
import lombok.Setter;
import org.quartzpowered.engine.object.annotation.MessageHandler;
import org.quartzpowered.engine.object.annotation.Property;
import org.quartzpowered.engine.object.Component;
import org.quartzpowered.engine.object.GameObject;
import org.quartzpowered.engine.observe.Observer;
import org.quartzpowered.network.protocol.packet.Packet;
import org.quartzpowered.network.session.attribute.AttributeKey;
import org.quartzpowered.network.session.attribute.AttributeRegistry;
import org.quartzpowered.network.session.attribute.AttributeStorage;
import org.quartzpowered.protocol.packet.play.client.PlayerTeleportPacket;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class Camera extends Component implements Observer {
    public static final AttributeKey<Camera> CAMERA_ATTRIBUTE = AttributeKey.create();

    @Inject private AttributeRegistry attributeRegistry;

    @Getter @Setter
    @Property
    private double range = 10;

    private final List<Observer> observers = new ArrayList<>();

    public void removeViewer(Observer observer) {
        if (this.observers.remove(observer)) {
            this.attributeRegistry.get(observer).set(CAMERA_ATTRIBUTE, null);
        }
    }

    public void addViewer(Observer observer) {
        AttributeStorage attributes = this.attributeRegistry.get(observer);

        Camera previousCamera = attributes.get(CAMERA_ATTRIBUTE);
        if (previousCamera != null) {
            previousCamera.removeViewer(observer);
        }

        this.observers.add(observer);
        attributes.set(CAMERA_ATTRIBUTE, this);

        PlayerTeleportPacket teleportPacket = new PlayerTeleportPacket();

        Transform transform = gameObject.getTransform();
        teleportPacket.setPosition(transform.getPosition());
        teleportPacket.setRotation(transform.getRotation());

        observer.observe(teleportPacket);
    }

    @MessageHandler
    public void update() {
        GameObject root = gameObject.getRoot();
        updateObject(root);
    }

    private void updateObject(GameObject object) {
        double distance = object.getTransform().distanceSquared(object.getTransform());

        boolean isObserved = object.hasObserver(this);

        if (!isObserved && distance <= range * range) {
            object.startObserving(this);
        } else if (isObserved && distance >= range * range) {
            object.stopObserving(this);
        }

        for (GameObject child : object.getChildren()) {
            updateObject(child);
        }
    }

    @Override
    public void observe(Packet packet) {
        observers.forEach(observer -> observer.observe(packet));
    }
}
