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
import org.quartzpowered.protocol.packet.play.client.SoundEffectPacket;

public class SoundEffectCodec implements Codec<SoundEffectPacket> {
    @Override
    public void encode(Buffer buffer, SoundEffectPacket packet) {
        buffer.writeString(packet.getSoundName());
        buffer.writeInt(packet.getEffectPositionX());
        buffer.writeInt(packet.getEffectPositionY());
        buffer.writeInt(packet.getEffectPositionZ());
        buffer.writeFloat(packet.getVolume());
        buffer.writeByte(packet.getPitch());
    }

    @Override
    public void decode(Buffer buffer, SoundEffectPacket packet) {
        packet.setSoundName(buffer.readString());
        packet.setEffectPositionX(buffer.readInt());
        packet.setEffectPositionY(buffer.readInt());
        packet.setEffectPositionZ(buffer.readInt());
        packet.setVolume(buffer.readFloat());
        packet.setPitch(buffer.readByte());
    }
}
