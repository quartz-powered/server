package org.quartzpowered.protocol.packet.play.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;

@Data
@EqualsAndHashCode(callSuper = true)
public class SpawnGlobalEntityPacket extends Packet {
    private int entityId;
    private int type;
    private double x;
    private double y;
    private double z;
}
