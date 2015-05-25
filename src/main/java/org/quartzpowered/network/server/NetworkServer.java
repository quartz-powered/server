package org.quartzpowered.network.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.quartzpowered.network.pipeline.MinecraftChannelInitializer;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import static io.netty.channel.ChannelOption.SO_KEEPALIVE;
import static io.netty.channel.ChannelOption.TCP_NODELAY;

public class NetworkServer {
    @Inject private Logger logger;

    private final ServerBootstrap bootstrap = new ServerBootstrap();
    private final EventLoopGroup parentGroup = new NioEventLoopGroup();
    private final EventLoopGroup childGroup = new NioEventLoopGroup();

    @Inject
    private NetworkServer(MinecraftChannelInitializer channelInitializer) {
        bootstrap
                .group(parentGroup, childGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(channelInitializer)
                .childOption(TCP_NODELAY, true)
                .childOption(SO_KEEPALIVE, true);
    }

    public ChannelFuture bind(int port) {
        return bind(new InetSocketAddress(port));
    }

    public ChannelFuture bind(String host, int port) {
        return bind(new InetSocketAddress(host, port));
    }

    public ChannelFuture bind(SocketAddress address) {
        return bootstrap.bind(address).addListener(future -> {
            if (future.isSuccess()) {
                onBindSuccess(address);
            } else {
                onBindFailure(address, future.cause());
            }
            ;
        });
    }

    public void onBindSuccess(SocketAddress address) {
        logger.info("Bound to {}", address);
    }

    public void onBindFailure(SocketAddress address, Throwable cause) {
        logger.error("Failed to bind", cause);
    }

    public void shutdown() {
        parentGroup.shutdownGracefully();
        childGroup.shutdownGracefully();
    }
}
