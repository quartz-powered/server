package org.quartzpowered.network.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import org.quartzpowered.network.buffer.Buffer;

import java.util.List;

public class FrameCodec extends ByteToMessageCodec<ByteBuf> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) throws Exception {
        Buffer buffer = new Buffer(out);
        buffer.writeVarInt(in.readableBytes());
        buffer.writeBytes(in);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // check for length field readability
        in.markReaderIndex();
        if (!readableVarInt(in)) {
            return;
        }

        // check for contents readability
        Buffer buffer = new Buffer(in);
        int length = buffer.readVarInt();
        if (in.readableBytes() < length) {
            in.resetReaderIndex();
            return;
        }

        // read contents into buf
        out.add(in.readBytes(length));
    }

    private static boolean readableVarInt(ByteBuf buf) {
        if (buf.readableBytes() > 5) {
            // maximum varint size
            return true;
        }

        int idx = buf.readerIndex();
        byte in;
        do {
            if (buf.readableBytes() < 1) {
                buf.readerIndex(idx);
                return false;
            }
            in = buf.readByte();
        } while ((in & 0x80) != 0);

        buf.readerIndex(idx);
        return true;
    }
}
