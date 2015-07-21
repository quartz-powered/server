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
package org.quartzpowered.protocol.codec.v1_8_R1.play.shared;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.data.chat.component.serialize.ComponentSerializer;
import org.quartzpowered.protocol.packet.play.shared.UpdateSignPacket;

public class UpdateSignCodec implements Codec<UpdateSignPacket> {
    @Override
    public void encode(Buffer buffer, UpdateSignPacket packet) {
        buffer.writeBlockPosition(packet.getLocation());
        buffer.writeString(ComponentSerializer.toString(packet.getLine1()));
        buffer.writeString(ComponentSerializer.toString(packet.getLine2()));
        buffer.writeString(ComponentSerializer.toString(packet.getLine3()));
        buffer.writeString(ComponentSerializer.toString(packet.getLine4()));
    }

    @Override
    public void decode(Buffer buffer, UpdateSignPacket packet) {
        packet.setLocation(buffer.readBlockPosition());
        packet.setLine1(ComponentSerializer.parse(buffer.readString()));
        packet.setLine2(ComponentSerializer.parse(buffer.readString()));
        packet.setLine3(ComponentSerializer.parse(buffer.readString()));
        packet.setLine4(ComponentSerializer.parse(buffer.readString()));
    }
}
