package org.quartzpowered.protocol.packet.play.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;
import org.quartzpowered.protocol.data.EntityEquipmentSlot;
import org.quartzpowered.protocol.data.ItemSlot;

@Data
@EqualsAndHashCode(callSuper = true)
public class EntityEquipmentPacket extends Packet {
    private int entityId;
    private EntityEquipmentSlot slot;
    private ItemSlot item;
}
