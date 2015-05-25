package org.quartzpowered.network.protocol.codec;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.packet.Packet;

public interface Codec<T extends Packet> {
    public void encode(Buffer buffer, T packet);
    public void decode(Buffer buffer, T packet);
}
