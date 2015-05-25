package org.quartzpowered.network.session;

import io.netty.channel.Channel;

public interface SessionFactory {
    public Session create(Channel channel);
}
