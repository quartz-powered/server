package org.quartzpowered.protocol.codec.v1_8_R1.common.client;

import lombok.Data;
import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.common.client.CompressionPacket;

@Data
public class CompressionCodec implements Codec<CompressionPacket> {
    @Override
    public void encode(Buffer buffer, CompressionPacket packet) {
        buffer.writeVarInt(packet.getThreshold());
    }

    @Override
    public void decode(Buffer buffer, CompressionPacket packet) {
        packet.setThreshold(buffer.readVarInt());
    }
}
