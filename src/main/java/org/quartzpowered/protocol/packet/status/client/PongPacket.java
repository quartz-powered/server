package org.quartzpowered.protocol.packet.status.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;

@Data
@EqualsAndHashCode(callSuper = true)
public class PongPacket extends Packet {
    private long time;
}
