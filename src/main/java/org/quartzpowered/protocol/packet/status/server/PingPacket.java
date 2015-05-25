package org.quartzpowered.protocol.packet.status.server;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;

@Data
@EqualsAndHashCode(callSuper = true)
public class PingPacket extends Packet {
    private long time;
}
