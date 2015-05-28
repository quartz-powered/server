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
package org.quartzpowered.protocol.data.metadata;

import org.quartzpowered.engine.math.Vector3;
import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.protocol.data.BlockPosition;
import org.quartzpowered.protocol.data.ItemSlot;

import java.io.IOException;
import java.util.Vector;

public enum MetadataType {
    BYTE((buf, obj) -> buf.writeByte((Byte) obj)),
    SHORT((buf, obj) -> buf.writeShort((Short) obj)),
    INT((buf, obj) -> buf.writeInt((Integer) obj)),
    FLOAT((buf, obj) -> buf.writeFloat((Float) obj)),
    STRING((buf, obj) -> buf.writeString((String) obj)),
    ITEM_SLOT((buf, obj) -> ((ItemSlot) obj).write(buf)),
    BLOCK_POSITION((buf, obj) -> {
        BlockPosition position = (BlockPosition) obj;
        buf.writeInt(position.getX());
        buf.writeInt(position.getY());
        buf.writeInt(position.getZ());
    }),
    ROTATION((buf, obj) -> {
        Vector3 vec = (Vector3) obj;
        buf.writeFloat((float) vec.getX());
        buf.writeFloat((float) vec.getY());
        buf.writeFloat((float) vec.getZ());
    });

    private WriteHandler writeHandler;

    MetadataType(WriteHandler writeHandler) {
        this.writeHandler = writeHandler;
    }

    private static interface WriteHandler {
        public void write(Buffer buf, Object obj) throws IOException;
    }

    public void write(Buffer buf, Object obj) {
        try {
            writeHandler.write(buf, obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return ordinal();
    }

    public static MetadataType fromId(int id) {
        return values()[id];
    }
}
