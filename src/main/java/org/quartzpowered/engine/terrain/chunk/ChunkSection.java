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
package org.quartzpowered.engine.terrain.chunk;

public class ChunkSection {
    private final byte[] types = new byte[16 * 16 * 16 * 2];

    public static int getIndex(int x, int y, int z) {
        return ((y << 8) | (z << 4) | x) << 1;
    }

    public static int getType(int id, int meta) {
        return (id << 4) | meta;
    }

    public static int getId(int type) {
        return (type >> 4);
    }

    public static int getMeta(int type) {
        return type & 0xf;
    }

    public void setType(int x, int y, int z, int type) {
        final int index = getIndex(x, y, z);

        types[index] = (byte) (type & 0xff);
        types[index + 1] = (byte) ((type >> 8) & 0xff);
    }

    public int getType(int x, int y, int z) {
        final int index = getIndex(x, y, z);

        return types[index] & 0xff |
                ((types[index + 1] & 0xff) << 8);
    }
}
