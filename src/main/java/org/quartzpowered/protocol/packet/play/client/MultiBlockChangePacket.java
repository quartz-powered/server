package org.quartzpowered.protocol.packet.play.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;
import org.quartzpowered.protocol.data.RelativeBlockRecord;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class MultiBlockChangePacket extends Packet {
    private int chunkX;
    private int chunkZ;
    private List<RelativeBlockRecord> blocks = new ArrayList<>();
}
