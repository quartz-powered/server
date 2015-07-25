package org.quartzpowered.protocol.packet.play.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;
import org.quartzpowered.protocol.data.metadata.Metadata;

@Data
@EqualsAndHashCode(callSuper = true)
public class SpawnMobPacket extends Packet {
    private int entityId;
    private int type;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private float headPitch;
    private double velocityX;
    private double velocityY;
    private double velocityZ;
    private Metadata metadata;
}
