package org.quartzpowered.protocol.packet.play.client;

import com.flowpowered.nbt.Tag;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateEntityNbtPacket extends Packet {
    private int entityId;
    private Tag tag;
}
