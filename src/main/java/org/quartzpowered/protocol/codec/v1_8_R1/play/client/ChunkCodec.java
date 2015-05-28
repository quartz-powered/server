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
package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.client.ChunkPacket;

public class ChunkCodec implements Codec<ChunkPacket> {

    @Override
    public void encode(Buffer buffer, ChunkPacket packet) {
        buffer.writeInt(packet.getX());
        buffer.writeInt(packet.getZ());

        buffer.writeBoolean(packet.isContinuous());
        buffer.writeShort(packet.getMask());
        buffer.writeByteArray(packet.getData());
    }

    @Override
    public void decode(Buffer buffer, ChunkPacket packet) {
        packet.setX(buffer.readInt());
        packet.setZ(buffer.readInt());

        boolean continuous = buffer.readBoolean();
        packet.setContinuous(continuous);

        int bitmask = buffer.readShort();
        packet.setMask(bitmask);

        int sectionCount = 0;
        for (int i = 0; i < 16; i++) {
            if ((bitmask & (1 << i)) > 0) {
                sectionCount++;
            }
        }

        int byteCount = 0;

        byteCount += 8192 * sectionCount;
        byteCount += 2048 * sectionCount;

        if (continuous) {
            byteCount += 256;
        }

        byte[] bytes = new byte[byteCount];

        buffer.readBytes(bytes);

        packet.setData(bytes);
    }
}
