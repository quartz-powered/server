package org.quartzpowered.network.protocol.codec;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.common.registry.Registry;
import org.quartzpowered.network.protocol.ProtocolState;

@Data
@EqualsAndHashCode(callSuper = false)
public class CodecRegistry extends Registry<Codec> {
    private final ProtocolState state;
}
