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
import org.quartzpowered.protocol.data.ParticleType;
import org.quartzpowered.protocol.packet.play.client.ParticlePacket;

import java.util.ArrayList;
import java.util.List;

public class ParticleCodec implements Codec<ParticlePacket> {
    @Override
    public void encode(Buffer buffer, ParticlePacket packet) {
        buffer.writeInt(packet.getType().getId());
        buffer.writeBoolean(packet.isLongDistance());
        buffer.writeFloat(packet.getX());
        buffer.writeFloat(packet.getY());
        buffer.writeFloat(packet.getZ());
        buffer.writeFloat(packet.getOffsetX());
        buffer.writeFloat(packet.getOffsetY());
        buffer.writeFloat(packet.getOffsetZ());
        buffer.writeFloat(packet.getParticleData());
        buffer.writeInt(packet.getParticleCount());

        List<Integer> data = packet.getData();
        buffer.writeVarInt(data.size());
        data.forEach(buffer::writeVarInt);
    }

    @Override
    public void decode(Buffer buffer, ParticlePacket packet) {
        packet.setType(ParticleType.fromId(buffer.readInt()));
        packet.setLongDistance(buffer.readBoolean());
        packet.setX(buffer.readFloat());
        packet.setY(buffer.readFloat());
        packet.setZ(buffer.readFloat());
        packet.setOffsetX(buffer.readFloat());
        packet.setOffsetY(buffer.readFloat());
        packet.setOffsetZ(buffer.readFloat());
        packet.setParticleCount(buffer.readInt());

        List<Integer> data = new ArrayList<>(buffer.readVarInt());
        for (int i = 0; i < data.size(); i++) {
            data.set(i, buffer.readVarInt());
        }
    }
}
