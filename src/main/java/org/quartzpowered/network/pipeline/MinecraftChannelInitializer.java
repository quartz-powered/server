package org.quartzpowered.network.pipeline;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.quartzpowered.network.codec.CodecFactory;
import org.quartzpowered.network.codec.FrameCodec;
import org.quartzpowered.network.codec.PacketCodec;
import org.quartzpowered.network.session.Session;
import org.quartzpowered.network.session.SessionManager;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MinecraftChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Inject private CodecFactory codecFactory;
    @Inject private PacketHandler handler;
    @Inject private NoopHandler noop;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast("encryption", noop)
                .addLast("frame", codecFactory.createFrameCodec())
                .addLast("compression", noop)
                .addLast("packet", codecFactory.createPacketCodec())
                .addLast("handler", handler);
    }
}
