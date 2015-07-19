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
import org.quartzpowered.protocol.data.BlockPosition;
import org.quartzpowered.protocol.packet.play.client.ExplosionPacket;

import java.util.List;

public class ExplosionCodec implements Codec<ExplosionPacket> {
    @Override
    public void encode(Buffer buffer, ExplosionPacket packet) {
        buffer.writeFloat((float) packet.getX());
        buffer.writeFloat((float) packet.getY());
        buffer.writeFloat((float) packet.getZ());
        buffer.writeFloat((float) packet.getRadius());

        List<BlockPosition> relativeBlockChanges = packet.getRelativeBlockChanges();
        buffer.writeInt(relativeBlockChanges.size());
        for (BlockPosition pos : relativeBlockChanges) {
            buffer.writeByte(pos.getX());
            buffer.writeByte(pos.getY());
            buffer.writeByte(pos.getZ());
        }

        buffer.writeFloat((float) packet.getPlayerMotionX());
        buffer.writeFloat((float) packet.getPlayerMotionY());
        buffer.writeFloat((float) packet.getPlayerMotionZ());
    }

    @Override
    public void decode(Buffer buffer, ExplosionPacket packet) {
        packet.setX(buffer.readFloat());
        packet.setY(buffer.readFloat());
        packet.setZ(buffer.readFloat());
        packet.setRadius(buffer.readFloat());

        List<BlockPosition> relativeBlockChanges = packet.getRelativeBlockChanges();
        int count = buffer.readInt();
        for (int i = 0; i < count; i++) {
            relativeBlockChanges.add(new BlockPosition(
                    buffer.readByte(),
                    buffer.readByte(),
                    buffer.readByte()
            ));
        }

        packet.setPlayerMotionX(buffer.readFloat());
        packet.setPlayerMotionY(buffer.readFloat());
        packet.setPlayerMotionZ(buffer.readFloat());
    }
}
