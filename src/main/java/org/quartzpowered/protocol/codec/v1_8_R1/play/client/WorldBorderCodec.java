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
import org.quartzpowered.protocol.data.WorldBorderAction;
import org.quartzpowered.protocol.packet.play.client.WorldBorderPacket;

public class WorldBorderCodec implements Codec<WorldBorderPacket> {
    @Override
    public void encode(Buffer buffer, WorldBorderPacket packet) {
        buffer.writeVarInt(packet.getAction().getId());

        switch (packet.getAction()) {
            case SET_SIZE:
                buffer.writeDouble(packet.getNewRadius());
                break;
            case LERP_SIZE:
                buffer.writeDouble(packet.getOldRadius());
                buffer.writeDouble(packet.getNewRadius());
                buffer.writeVarLong(packet.getSpeed());
                break;
            case SET_CENTER:
                buffer.writeDouble(packet.getX());
                buffer.writeDouble(packet.getZ());
                break;
            case INITIALIZE:
                buffer.writeDouble(packet.getX());
                buffer.writeDouble(packet.getZ());
                buffer.writeDouble(packet.getOldRadius());
                buffer.writeDouble(packet.getNewRadius());
                buffer.writeVarLong(packet.getSpeed());
                buffer.writeVarInt(packet.getPortalTeleportBoundary());
                buffer.writeVarInt(packet.getWarningTime());
                buffer.writeVarInt(packet.getWarningBlocks());
                break;
            case SET_WARNING_TIME:
                buffer.writeVarInt(packet.getWarningTime());
                break;
            case SET_WARNING_BLOCKS:
                buffer.writeVarInt(packet.getWarningBlocks());
                break;
        }
    }

    @Override
    public void decode(Buffer buffer, WorldBorderPacket packet) {
        packet.setAction(WorldBorderAction.fromId(buffer.readVarInt()));

        switch (packet.getAction()) {
            case SET_SIZE:
                packet.setNewRadius(buffer.readDouble());
                break;
            case LERP_SIZE:
                packet.setOldRadius(buffer.readDouble());
                packet.setNewRadius(buffer.readDouble());
                packet.setSpeed(buffer.readVarLong());
                break;
            case SET_CENTER:
                packet.setX(buffer.readDouble());
                packet.setZ(buffer.readDouble());
                break;
            case INITIALIZE:
                packet.setX(buffer.readDouble());
                packet.setZ(buffer.readDouble());
                packet.setOldRadius(buffer.readDouble());
                packet.setNewRadius(buffer.readDouble());
                packet.setSpeed(buffer.readVarLong());
                packet.setPortalTeleportBoundary(buffer.readVarInt());
                packet.setWarningTime(buffer.readVarInt());
                packet.setWarningBlocks(buffer.readVarInt());
                break;
            case SET_WARNING_TIME:
                packet.setWarningTime(buffer.readVarInt());
                break;
            case SET_WARNING_BLOCKS:
                packet.setWarningBlocks(buffer.readVarInt());
                break;
        }
    }
}
