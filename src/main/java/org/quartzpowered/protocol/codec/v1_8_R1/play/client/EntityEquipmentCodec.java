package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.data.EntityEquipmentSlot;
import org.quartzpowered.protocol.data.ItemSlot;
import org.quartzpowered.protocol.packet.play.client.EntityEquipmentPacket;

public class EntityEquipmentCodec implements Codec<EntityEquipmentPacket> {
    @Override
    public void encode(Buffer buffer, EntityEquipmentPacket packet) {
        buffer.writeVarInt(packet.getEntityId());
        buffer.writeShort(packet.getSlot().getId());
        packet.getItem().write(buffer);
    }

    @Override
    public void decode(Buffer buffer, EntityEquipmentPacket packet) {
        packet.setEntityId(buffer.readVarInt());
        packet.setSlot(EntityEquipmentSlot.fromId(buffer.readShort()));
        packet.setItem(new ItemSlot().read(buffer));
    }
}
