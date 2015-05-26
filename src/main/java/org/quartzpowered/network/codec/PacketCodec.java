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

        Protocol protocol = session.getProtocol();
        PacketRegistry packets = protocol.getClientBoundPackets(session.getState());
        CodecRegistry codecs = protocol.getClientBoundCodecs(session.getState());

        int id = packets.getId(packet.getClass());
        Codec codec = codecs.lookup(id);

        Buffer buffer = new Buffer(out);

        buffer.writeVarInt(id);
        codec.encode(buffer, packet);
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
            logger.error("Unregistered codec for " + type);
            buffer.skipBytes(buffer.readableBytes());
            return;
        }

        Factory<Packet> packetFactory = factoryRegistry.get(type);
        Packet packet = packetFactory.create();

        codec.decode(buffer, packet);

        if (buffer.readableBytes() > 0) {
            logger.warn("Not all bytes read for {} - {} - {}", protocol, state, codec);
            buffer.skipBytes(buffer.readableBytes());
        }

        out.add(packet);
    }
}
