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

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.quartzpowered.network.pipeline.MinecraftChannelInitializer;
import org.slf4j.Logger;

import javax.inject.Inject;

import java.net.InetSocketAddress;

import static io.netty.channel.ChannelOption.SO_KEEPALIVE;
import static io.netty.channel.ChannelOption.TCP_NODELAY;

public class NetworkClient {
    @Inject private Logger logger;

    private final Bootstrap bootstrap = new Bootstrap();
    private final EventLoopGroup eventLoop = new NioEventLoopGroup();

    @Inject
    private NetworkClient(MinecraftChannelInitializer channelInitializer) {
        bootstrap
                .group(eventLoop)
                .channel(NioServerSocketChannel.class)
                .handler(channelInitializer)
                .option(TCP_NODELAY, true)
                .option(SO_KEEPALIVE, true);
    }

    public ChannelFuture connect(InetSocketAddress address) {
        return bootstrap.connect(address).addListener(future -> {
            if (future.isSuccess()) {
                onConnectSuccess(address);
            } else {
                onConnectFailure(address, future.cause());
            }
        });
    }

    private void onConnectSuccess(InetSocketAddress address) {
        logger.info("Connected to {}", address);
    }

    private void onConnectFailure(InetSocketAddress address, Throwable cause) {
        logger.error("Failed to bind", cause);
    }

    public void shutdown() {
        eventLoop.shutdownGracefully();
    }
}
