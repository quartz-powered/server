package org.quartzpowered.protocol.packet.play.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;
import org.quartzpowered.protocol.data.BlockPosition;

@Data
@EqualsAndHashCode(callSuper = true)
public class BlockActionPacket extends Packet {
    private BlockPosition location;
    private int byte1;
    private int byte2;
    private int blockType;
}
