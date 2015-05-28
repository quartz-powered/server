package org.quartzpowered.protocol.packet.play.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;

@Data
@EqualsAndHashCode(callSuper = true)
public class EntityLookMovePacket extends Packet {
    private int entityId;
    private double deltaX;
    private double deltaY;
    private double deltaZ;
    private double yaw;
    private double pitch;
    private boolean onGround;

    public void setDelta(double deltaX, double deltaY, double deltaZ) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.deltaZ = deltaZ;
    }

    public void setRotation(double yaw, double pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }
}
