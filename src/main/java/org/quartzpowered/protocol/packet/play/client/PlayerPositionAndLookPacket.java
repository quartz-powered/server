package org.quartzpowered.protocol.packet.play.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlayerPositionAndLookPacket extends Packet {
    double x;
    double y;
    double z;
    float yaw;
    float pitch;
    int flags;
}
