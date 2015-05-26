package org.quartzpowered.protocol.packet.play.server;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;
import org.quartzpowered.protocol.data.BlockPosition;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlayerDiggingPacket extends Packet {
    private int status;
    private BlockPosition location;
    private int face;
}
