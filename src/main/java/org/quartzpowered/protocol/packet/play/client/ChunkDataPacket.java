package org.quartzpowered.protocol.packet.play.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;
import org.quartzpowered.protocol.data.component.ChunkComponent;

@Data
@EqualsAndHashCode(callSuper = true)
public class ChunkDataPacket extends Packet {
    private int x, z;
    private boolean continuous;
    private int mask;
    private byte[] data;
}
