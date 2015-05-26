package org.quartzpowered.protocol.packet.play.server;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlayerLookPacket extends Packet {
    private float yaw;
    private float pitch;
    private boolean onGround;
}
