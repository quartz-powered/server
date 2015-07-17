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
package org.quartzpowered.network.client;

import com.google.inject.assistedinject.Assisted;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;
import org.quartzpowered.network.init.NetworkChannelInitializerFactory;
import org.quartzpowered.network.protocol.packet.Packet;
import org.quartzpowered.network.session.Session;
import org.quartzpowered.network.session.SessionManager;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.net.InetSocketAddress;

import static io.netty.channel.ChannelOption.SO_KEEPALIVE;
import static io.netty.channel.ChannelOption.TCP_NODELAY;

public class NetworkClient {
    @Inject private Logger logger;
    @Inject private SessionManager sessionManager;

    private final Bootstrap bootstrap = new Bootstrap();
    private final EventLoopGroup eventLoop = new NioEventLoopGroup();

    @Getter
    private Channel channel;

    @Getter
    private Session session;

    @Inject
    private NetworkClient(NetworkChannelInitializerFactory channelInitializerFactory,
                          @Assisted SimpleChannelInboundHandler<Packet> handler) {
        bootstrap
                .group(eventLoop)
                .channel(NioSocketChannel.class)
                .handler(channelInitializerFactory.create(true, handler))
                .option(TCP_NODELAY, true)
                .option(SO_KEEPALIVE, true);
    }

    public ChannelFuture connect(InetSocketAddress address) {
        ChannelFuture future = bootstrap.connect(address);
        return future.addListener(ignored -> {
            channel = future.channel();
            session = sessionManager.get(channel);

            if (future.isSuccess()) {
                onConnectSuccess(address);
            } else {
                onConnectFailure(address, future.cause());
            }
        });
    }

    private void onConnectSuccess(InetSocketAddress address) {
        logger.debug("Connected to {}", address);
    }

    private void onConnectFailure(InetSocketAddress address, Throwable cause) {
        logger.error("Failed to connect", cause);
    }

    public void shutdown() {
        eventLoop.shutdownGracefully();
    }
}
