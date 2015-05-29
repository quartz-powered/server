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

import io.netty.buffer.ByteBuf;
import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.network.session.Session;
import org.quartzpowered.protocol.data.Block;
import org.quartzpowered.protocol.data.ChatMode;
import org.quartzpowered.protocol.data.Location;
import org.quartzpowered.protocol.packet.play.server.BlockPlacePacket;
import org.quartzpowered.protocol.packet.play.server.ClientSettingsPacket;

public class BlockPlaceCodec implements Codec<BlockPlacePacket> {
    @Override
    public void encode(Buffer buffer, BlockPlacePacket packet) {

        System.out.println("ENCODING");
        Location loc = packet.getLocation();

        long val = (((int)loc.getX() & 0x3FFFFFF) << 38) | (((int)loc.getY() & 0xFFF) << 26) | ((int)loc.getZ() & 0x3FFFFFF);

        buffer.writeLong(val);
        buffer.writeByte(packet.getFace());

        buffer.writeShort(packet.getBlock().getId());
        buffer.writeByte(packet.getBlock().getCount());
        buffer.writeShort(packet.getBlock().getDamage());

        System.out.println("TEST");

        //buffer.writeBytes(packet.getBlock().getBuf());

        buffer.writeByte(packet.getCursorX());
        buffer.writeByte(packet.getCursorY());
        buffer.writeByte(packet.getCursorZ());

        buffer.writeByte(packet.getCursorX());
        buffer.writeByte(packet.getCursorY());
        buffer.writeByte(packet.getCursorZ());


    }

    @Override
    public void decode(Buffer buffer, BlockPlacePacket packet) {

        long val = buffer.readLong();
        long x = val >> 38;
        long y = (val >> 26) & 0xFFF;
        long z = val << 38 >> 38;
        y++;

        if(x >= 33554432)
            x -= 67108864;
        if(y >= 2048)
            y -= 4096;
        if(z >= 33554432)
            z -= 67108864;

        packet.setLocation(new Location(x, y, z));
        packet.setFace(buffer.readByte());

        int id = buffer.readShort();
        if(id == -1)
            return;

        int count = buffer.readByte();
        if(count == 0)
            return;
        short damage = buffer.readShort();
        Block block = new Block(id, count, damage);
        packet.setBlock(block);

        packet.setCursorX(buffer.readByte());
        packet.setCursorY(buffer.readByte());
        packet.setCursorZ(buffer.readByte());

        /*???*/
        buffer.readByte();

    }
}
