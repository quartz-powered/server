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
package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.data.metadata.Metadata;
import org.quartzpowered.protocol.packet.play.client.SpawnMobPacket;

public class SpawnMobCodec implements Codec<SpawnMobPacket> {
    @Override
    public void encode(Buffer buffer, SpawnMobPacket packet) {
        buffer.writeVarInt(packet.getEntityId());
        buffer.writeByte(packet.getType());
        buffer.writeFixedPointInt(packet.getX());
        buffer.writeFixedPointInt(packet.getY());
        buffer.writeFixedPointInt(packet.getZ());
        buffer.writeAngle(packet.getYaw());
        buffer.writeAngle(packet.getPitch());
        buffer.writeAngle(packet.getHeadPitch());
        buffer.writeFixedPointShort(packet.getVelocityX());
        buffer.writeFixedPointShort(packet.getVelocityY());
        buffer.writeFixedPointShort(packet.getVelocityZ());
        packet.getMetadata().write(buffer);
    }

    @Override
    public void decode(Buffer buffer, SpawnMobPacket packet) {
        packet.setEntityId(buffer.readVarInt());
        packet.setType(buffer.readUnsignedByte());
        packet.setX(buffer.readFixedPointInt());
        packet.setY(buffer.readFixedPointInt());
        packet.setZ(buffer.readFixedPointInt());
        packet.setYaw(buffer.readAngle());
        packet.setPitch(buffer.readAngle());
        packet.setHeadPitch(buffer.readAngle());
        packet.setVelocityX(buffer.readFixedPointShort());
        packet.setVelocityY(buffer.readFixedPointShort());
        packet.setVelocityZ(buffer.readFixedPointShort());
        packet.setMetadata(new Metadata().read(buffer));
    }
}
