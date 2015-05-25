package org.quartzpowered.network.protocol.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.ProtocolState;
import org.quartzpowered.common.registry.Registry;

@Data
@EqualsAndHashCode(callSuper = false)
public class PacketRegistry extends Registry<Class<? extends Packet>> {
    private final ProtocolState state;
}
