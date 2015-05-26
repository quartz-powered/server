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
package org.quartzpowered.network.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import io.netty.handler.codec.MessageToMessageCodec;
import org.quartzpowered.network.buffer.Buffer;

import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public final class CompressionHandler extends MessageToMessageCodec<ByteBuf, ByteBuf> {
    private static final int COMPRESSION_LEVEL = Deflater.DEFAULT_COMPRESSION;

    private final int threshold;
    private final Inflater inflater;
    private final Deflater deflater;

    public CompressionHandler(int threshold) {
        this.threshold = threshold;
        inflater = new Inflater();
        deflater = new Deflater(COMPRESSION_LEVEL);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        Buffer prefixBuf = new Buffer(ctx.alloc().buffer(5));
        ByteBuf contentsBuf;

        if (msg.readableBytes() >= threshold) {
            // message should be compressed
            int index = msg.readerIndex();
            int length = msg.readableBytes();

            byte[] sourceData = new byte[length];
            msg.readBytes(sourceData);
            deflater.setInput(sourceData);
            deflater.finish();

            byte[] compressedData = new byte[length];
            int compressedLength = deflater.deflate(compressedData);
            deflater.reset();

            if (compressedLength == 0) {
                // compression failed in some weird way
                throw new EncoderException("Failed to compress message of size " + length);
            } else if (compressedLength >= length) {
                // compression increased the size. threshold is probably too low
                // send as an uncompressed packet
                prefixBuf.writeVarInt(0);
                msg.readerIndex(index);
                msg.retain();
                contentsBuf = msg;
            } else {
                // all is well
                prefixBuf.writeVarInt(length);
                contentsBuf = Unpooled.wrappedBuffer(compressedData, 0, compressedLength);
            }
        } else {
            // message should be sent through
            prefixBuf.writeVarInt(0);
            msg.retain();
            contentsBuf = msg;
        }

        out.add(Unpooled.wrappedBuffer(prefixBuf, contentsBuf));
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        Buffer buffer = new Buffer(msg);
        int index = buffer.readerIndex();
        int uncompressedSize = buffer.readVarInt();
        if (uncompressedSize == 0) {
            // message is uncompressed
            int length = buffer.readableBytes();
            if (length >= threshold) {
                // invalid
                throw new DecoderException("Received uncompressed message of size " + length + " greater than threshold " + threshold);
            }

            ByteBuf buf = ctx.alloc().buffer(length);
            buffer.readBytes(buf, length);
            out.add(buf);
        } else {
            // message is compressed
            byte[] sourceData = new byte[buffer.readableBytes()];
            buffer.readBytes(sourceData);
            inflater.setInput(sourceData);

            byte[] destData = new byte[uncompressedSize];
            int resultLength = inflater.inflate(destData);
            inflater.reset();

            if (resultLength == 0) {
                // might be a leftover from before compression was enabled (no compression header)
                // uncompressedSize is likely to be < threshold
                buffer.readerIndex(index);
                buffer.retain();
                out.add(buffer);
            } else if (resultLength != uncompressedSize) {
                throw new DecoderException("Received compressed message claiming to be of size " + uncompressedSize + " but actually " + resultLength);
            } else {
                out.add(Unpooled.wrappedBuffer(destData));
            }
        }
    }

}
