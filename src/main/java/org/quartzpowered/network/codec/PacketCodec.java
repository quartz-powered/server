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
package org.quartzpowered.network.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import org.quartzpowered.common.factory.Factory;
import org.quartzpowered.common.factory.FactoryRegistry;
import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.Protocol;
import org.quartzpowered.network.protocol.ProtocolState;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.network.protocol.codec.CodecRegistry;
import org.quartzpowered.network.protocol.packet.Packet;
import org.quartzpowered.network.protocol.packet.PacketRegistry;
import org.quartzpowered.network.session.Session;
import org.quartzpowered.network.session.SessionManager;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.util.List;

public class PacketCodec extends ByteToMessageCodec<Packet> {
    @Inject private Logger logger;
    @Inject private SessionManager sessionManager;
    @Inject private FactoryRegistry factoryRegistry;

    @Override
    @SuppressWarnings("unchecked")
    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) throws Exception {
        Session session = sessionManager.get(ctx);

        ProtocolState state = session.getState();

        Protocol protocol = session.getProtocol();
        PacketRegistry packets = protocol.getClientBoundPackets(state);
        CodecRegistry codecs = protocol.getClientBoundCodecs(state);

        if (packets == null || codecs == null) {
            logger.error("No client bound packets/codecs registered for {} - {}", protocol, state);
            return;
        }

        Class<? extends Packet> type = packet.getClass();

        int id = packets.getId(type);
        Codec codec = codecs.lookup(id);

        if (codec == null) {
            logger.error("Unregistered client bound codec for {} - {} - {}", protocol, state, type);
            return;
        }

        Buffer buffer = new Buffer(out);

        buffer.writeVarInt(id);

        try {
            codec.encode(buffer, packet);
        } catch (Exception ex) {
            logger.error(String.format("Exception while encoding packet for %s - %s - %s", protocol, state, codec.getClass()), ex);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Session session = sessionManager.get(ctx);

        ProtocolState state = session.getState();

        Protocol protocol = session.getProtocol();
        PacketRegistry packets = protocol.getServerBoundPackets(state);
        CodecRegistry codecs = protocol.getServerBoundCodecs(state);

        if (packets == null || codecs == null) {
            logger.error("No server bound packets/codecs registered for {} - {}", protocol, state);
            return;
        }

        Buffer buffer = new Buffer(in);
        int id = buffer.readVarInt();

        Codec codec = codecs.lookup(id);
        Class<? extends Packet> type = packets.lookup(id);

        if (type == null) {
            logger.warn("Unknown packet id: 0x{}", Integer.toHexString(id));
            buffer.skipBytes(buffer.readableBytes());
            return;
        }

        if (codec == null) {
            logger.error("Unregistered server bound codec for {} - {} - {}", protocol, state, type);
            buffer.skipBytes(buffer.readableBytes());
            return;
        }

        Factory<Packet> packetFactory = factoryRegistry.get(type);
        Packet packet = packetFactory.create();

        try {
            codec.decode(buffer, packet);
        } catch (Exception ex) {
            logger.warn(String.format("Exception while decoding packet for %s - %s - %s", protocol, state, codec.getClass()), ex);
        }

        if (buffer.readableBytes() > 0) {
            logger.warn("Not all bytes read for {} - {} - {}", protocol, state, codec.getClass());
            buffer.skipBytes(buffer.readableBytes());
        }

        out.add(packet);
    }
}
