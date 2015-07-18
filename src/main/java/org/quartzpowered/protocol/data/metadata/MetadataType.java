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
package org.quartzpowered.protocol.data.metadata;

import org.quartzpowered.engine.math.Vector3;
import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.protocol.data.BlockPosition;
import org.quartzpowered.protocol.data.ItemSlot;

import java.io.IOException;

public enum MetadataType {
    BYTE((buf, obj) -> buf.writeByte((Byte) obj), Buffer::readByte),
    SHORT((buf, obj) -> buf.writeShort((Short) obj), Buffer::readShort),
    INT((buf, obj) -> buf.writeInt((Integer) obj), Buffer::readInt),
    FLOAT((buf, obj) -> buf.writeFloat((Float) obj), Buffer::readFloat),
    STRING((buf, obj) -> buf.writeString((String) obj), Buffer::readString),
    ITEM_SLOT((buf, obj) -> ((ItemSlot) obj).write(buf), buf -> new ItemSlot().read(buf)),
    BLOCK_POSITION((buf, obj) -> {
        BlockPosition position = (BlockPosition) obj;
        buf.writeInt(position.getX());
        buf.writeInt(position.getY());
        buf.writeInt(position.getZ());
    }, buf -> new BlockPosition(buf.readInt(), buf.readInt(), buf.readInt())),
    ROTATION((buf, obj) -> {
        Vector3 vec = (Vector3) obj;
        buf.writeFloat((float) vec.getX());
        buf.writeFloat((float) vec.getY());
        buf.writeFloat((float) vec.getZ());
    }, buf -> new Vector3(buf.readFloat(), buf.readFloat(), buf.readFloat()));

    private WriteHandler writeHandler;
    private ReadHandler readHandler;

    MetadataType(WriteHandler writeHandler, ReadHandler readHandler) {
        this.writeHandler = writeHandler;
        this.readHandler = readHandler;
    }

    private static interface WriteHandler {
        public void write(Buffer buf, Object obj) throws IOException;
    }

    private static interface ReadHandler {
        public Object read(Buffer buf) throws IOException;
    }

    public void write(Buffer buf, Object obj) {
        try {
            writeHandler.write(buf, obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object read(Buffer buf) {
        try {
            return readHandler.read(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getId() {
        return ordinal();
    }

    public static MetadataType fromId(int id) {
        return values()[id];
    }
}
