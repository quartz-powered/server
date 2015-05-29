package org.quartzpowered.protocol.packet.play.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;

@Data
@EqualsAndHashCode(callSuper = false)
public class TimeUpdatePacket extends Packet {
    private Long worldAge;
    private Long timeOfDay;
}
