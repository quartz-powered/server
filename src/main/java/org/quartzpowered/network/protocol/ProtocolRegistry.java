package org.quartzpowered.network.protocol;

import org.quartzpowered.common.registry.Registry;

public abstract class ProtocolRegistry extends Registry<Protocol> {
    public void register(Protocol protocol) {
        register(protocol.getVersion(), protocol);
    }
}
