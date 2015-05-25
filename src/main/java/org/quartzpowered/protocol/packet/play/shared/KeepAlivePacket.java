package org.quartzpowered.protocol.packet.play.shared;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;

@Data
@EqualsAndHashCode(callSuper = true)
public class KeepAlivePacket extends Packet {
    private int keepAliveId;
}
