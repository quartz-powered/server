package org.quartzpowered.protocol.packet.play.server;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlayerAbilitiesPacketIn extends Packet {
    private int flags;
    private float flyingSpeed;
    private float walkingSpeed;
}
