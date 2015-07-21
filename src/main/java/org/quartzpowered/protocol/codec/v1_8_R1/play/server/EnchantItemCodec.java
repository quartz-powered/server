package org.quartzpowered.protocol.codec.v1_8_R1.play.server;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.server.EnchantItemPacket;

public class EnchantItemCodec implements Codec<EnchantItemPacket> {
    @Override
    public void encode(Buffer buffer, EnchantItemPacket packet) {
        buffer.writeByte(packet.getWindowId());
        buffer.writeByte(packet.getEnchantment());
    }

    @Override
    public void decode(Buffer buffer, EnchantItemPacket packet) {
        packet.setWindowId(buffer.readByte());
        packet.setEnchantment(buffer.readByte());
    }
}
