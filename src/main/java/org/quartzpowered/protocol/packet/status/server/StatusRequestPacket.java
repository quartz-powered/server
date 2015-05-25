package org.quartzpowered.protocol.packet.status.server;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;

@Data
@EqualsAndHashCode(callSuper = true)
public class StatusRequestPacket extends Packet {
}
