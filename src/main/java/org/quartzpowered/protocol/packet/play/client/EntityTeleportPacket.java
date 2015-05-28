package org.quartzpowered.protocol.packet.play.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.engine.math.Vector3;
import org.quartzpowered.network.protocol.packet.Packet;

@Data
@EqualsAndHashCode(callSuper = true)
public class EntityTeleportPacket extends Packet {
    private int entityId;
    private double x;
    private double y;
    private double z;
    private double yaw;
    private double pitch;
    private boolean onGround;

    public void setPosition(Vector3 position) {
        this.x = position.getX();
        this.y = position.getY();
        this.z = position.getZ();
    }

    public void setRotation(double yaw, double pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }
}
