package org.quartzpowered.protocol.packet.play.server;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;
import org.quartzpowered.protocol.data.EntityAction;

@Data
@EqualsAndHashCode(callSuper = true)
public class EntityActionPacket extends Packet {
    private int entityID;
    private EntityAction actionId;
    private int horseJumpBoost;
}
