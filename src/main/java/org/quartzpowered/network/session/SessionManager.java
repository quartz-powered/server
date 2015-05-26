package org.quartzpowered.network.session;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SessionManager {
    private static final AttributeKey<Session> ATTRIBUTE_KEY = AttributeKey.newInstance("session");

    @Inject private SessionFactory factory;

    public Session get(Channel channel) {
        Attribute<Session> attribute = channel.attr(ATTRIBUTE_KEY);
        Session session = attribute.get();
        if (session == null) {
            attribute.setIfAbsent(factory.create(channel));
            session = attribute.get();
        }
        return session;
    }

    public Session get(ChannelHandlerContext ctx) {
        return get(ctx.channel());
    }
}
