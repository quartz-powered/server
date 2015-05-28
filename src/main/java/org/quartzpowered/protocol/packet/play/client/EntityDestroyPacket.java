package org.quartzpowered.protocol.packet.play.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class EntityDestroyPacket extends Packet {
    private List<Integer> entityIds;
}
