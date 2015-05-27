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
package org.quartzpowered.network.buffer;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.Delegate;
import org.quartzpowered.protocol.data.BlockPosition;

import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;

@Data
@EqualsAndHashCode(callSuper = false)
public class Buffer extends ByteBuf {
    @Delegate
    private final ByteBuf buf;

    public int readVarInt() {
        int result = 0;
        int count = 0;
        while (true) {
            byte in = readByte();
            result |= (in & 0x7f) << (count++ * 7);
            if (count > 5) {
                throw new BufferException("VarInt byte count > 5");
            }
            if ((in & 0x80) != 0x80) {
                break;
            }
        }
        return result;
    }

    public void writeVarInt(int value) {
        while (true) {
            byte part = (byte) (value & 0x7f);
            value >>>= 7;
            if (value != 0) {
                part |= 0x80;
            }
            writeByte(part);
            if (value == 0) {
                break;
            }
        }
    }

    public byte[] readByteArray() {
        int size = readVarInt();
        byte[] result = new byte[size];
        readBytes(result);
        return result;
    }

    public void writeByteArray(@NonNull byte[] value) {
        writeVarInt(value.length);
        writeBytes(value);
    }

    public String readString() {
        return new String(readByteArray(), UTF_8);
    }

    public void writeString(String value) {
        writeByteArray(value.getBytes(UTF_8));
    }

    public Buffer readRemainingBytes() {
        return new Buffer(readBytes(readableBytes()));
    }

    public BlockPosition readBlockPosition() {
        long position = readLong();

        return new BlockPosition(
                (int) (position >> 38),
                (int) ((position >> 26) & 0xfff),
                (int) ((position << 38) >> 38)
        );
    }

    public void writeBlockPosition(BlockPosition position) {
        writeLong(((position.getX() & 0x3FFFFFF) << 6) |
                ((position.getY() & 0xFFF) << 26) |
                (position.getZ() & 0x3FFFFFF));
    }

    public double readFixedPoint() {
        return (double) readInt() / 32;
    }

    public void writeFixedPoint(double value) {
        writeInt((int) (value * 32));
    }

    public void writeAngle(float angle) {
        writeByte((int) (angle * 256f / 360f));
    }

    public float readAngle() {
        return readByte() * 360f / 256f;
    }

    public void writeUuid(UUID uuid) {
        writeLong(uuid.getMostSignificantBits());
        writeLong(uuid.getLeastSignificantBits());
    }

    public UUID readUuid() {
        long mostSignificantBits = readLong();
        long leastSignificantBits = readLong();

        return new UUID(
                mostSignificantBits,
                leastSignificantBits
        );
    }
}
