package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.client.UpdateHealthPacket;

/**
 * Created by Ryan on 5/25/2015
 * <p>
 * Project: server
 */
public class UpdateHealthCodec implements Codec<UpdateHealthPacket> {

    @Override
    public void encode(Buffer buffer, UpdateHealthPacket packet) {
        buffer.writeFloat(packet.getHealth());
        buffer.writeVarInt(packet.getFoodLevel());
        buffer.writeFloat(packet.getSaturation());
    }

    @Override
    public void decode(Buffer buffer, UpdateHealthPacket packet) {
        packet.setHealth(buffer.readFloat());
        packet.setFoodLevel(buffer.readVarInt());
        packet.setSaturation(buffer.readFloat());
    }
}
