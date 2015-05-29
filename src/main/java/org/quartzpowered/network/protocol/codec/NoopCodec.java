package org.quartzpowered.network.protocol.codec;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.packet.Packet;

public class NoopCodec<T extends Packet> implements Codec<T> {
    @Override
    public void encode(Buffer buffer, T packet) {

    }

    @Override
    public void decode(Buffer buffer, T packet) {

    }
}
