package org.quartzpowered.protocol.packet.play.server;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;
import org.quartzpowered.protocol.data.UseEntityType;

@Data
@EqualsAndHashCode(callSuper = true)
public class UseEntityPacket extends Packet {
    private int target;
    private UseEntityType type;
    private float targetX;
    private float targetY;
    private float targetZ;
}
