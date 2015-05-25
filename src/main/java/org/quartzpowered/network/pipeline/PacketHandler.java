package org.quartzpowered.network.pipeline;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.quartzpowered.common.eventbus.EventBus;
import org.quartzpowered.network.protocol.packet.Packet;
import org.quartzpowered.network.session.Session;
import org.quartzpowered.network.session.SessionManager;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@ChannelHandler.Sharable
public class PacketHandler extends SimpleChannelInboundHandler<Packet> {
    @Inject private SessionManager sessionManager;
    @Inject private EventBus eventBus;

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Packet packet) throws Exception {
        Session session = sessionManager.get(ctx.channel());
        packet.setSender(session);

        // dispatch packet on eventbus
        eventBus.publish(packet);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
