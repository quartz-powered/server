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
package org.quartzpowered.protocol.packet.play.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.engine.math.Quaternion;
import org.quartzpowered.engine.math.Vector3;
import org.quartzpowered.network.protocol.packet.Packet;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlayerTeleportPacket extends Packet {
    private double x;
    private double y;
    private double z;
    private double yaw;
    private double pitch;
    private int flags;

    public void setPosition(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setPosition(Vector3 position) {
        setPosition(position.getX(), position.getY(), position.getZ());
    }

    public Vector3 getPosition() {
        return new Vector3(x, y, z);
    }

    public void setRotation(double yaw, double pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public void setRotation(Quaternion rotation) {
        Vector3 euler = rotation.getEuler();
        setRotation(euler.getY(), euler.getX());
    }
}
