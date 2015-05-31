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
package org.quartzpowered.protocol.codec.v1_8_R1.play.server;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.server.BlockPlacePacket;
import org.quartzpowered.protocol.packet.play.server.CreativeInventoryActionPacket;

public class CreativeInventoryActionCodec implements Codec<CreativeInventoryActionPacket> {
    @Override
    public void encode(Buffer buffer, CreativeInventoryActionPacket packet) {
        buffer.writeShort(packet.getSlot());
        buffer.writeShort(packet.getId());
        if(packet.getId() != -1) {
            buffer.writeByte(packet.getCount());
            buffer.writeShort(packet.getDamage());
            if(packet.getNbt() != 0) {
                /* TODO NBT DATA */
            }
        }
    }

    @Override
    public void decode(Buffer buffer, CreativeInventoryActionPacket packet) {
        packet.setSlot(buffer.readShort());
        short id = buffer.readShort();
        packet.setId(id);
        if(id != -1) {
            packet.setCount(buffer.readByte());
            packet.setDamage(buffer.readShort());
            byte nbt = buffer.readByte();
            packet.setNbt(nbt);
            if(nbt != 0) {
                /* TODO NBT DATA */
            }
        }
    }
}
