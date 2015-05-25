package org.quartzpowered.protocol.packet.status.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;
import org.quartzpowered.protocol.data.component.StatusComponent;

@Data
@EqualsAndHashCode(callSuper = true)
public class StatusResponsePacket extends Packet {
    private StatusComponent status;
}
