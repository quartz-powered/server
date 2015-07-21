package org.quartzpowered.protocol.packet.play.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;
import org.quartzpowered.protocol.data.BlockPosition;

@Data
@EqualsAndHashCode(callSuper = true)
public class BlockChangePacket extends Packet {
    private BlockPosition location;
    private int blockId;
}
