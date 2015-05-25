package org.quartzpowered.engine.observe;

import org.quartzpowered.network.protocol.packet.Packet;

public interface Observer {
    public void observe(Packet packet);
}
