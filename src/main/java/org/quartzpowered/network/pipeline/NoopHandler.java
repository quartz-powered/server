package org.quartzpowered.network.pipeline;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;

import javax.inject.Singleton;

@Singleton
@ChannelHandler.Sharable
public final class NoopHandler extends ChannelHandlerAdapter {
}
