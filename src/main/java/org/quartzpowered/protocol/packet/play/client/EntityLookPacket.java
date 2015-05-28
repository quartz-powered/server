package org.quartzpowered.protocol.packet.play.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;

@Data
@EqualsAndHashCode(callSuper = true)
public class EntityLookPacket extends Packet {
    private int entityId;
    private float yaw;
    private float pitch;
    private boolean onGround;
}
