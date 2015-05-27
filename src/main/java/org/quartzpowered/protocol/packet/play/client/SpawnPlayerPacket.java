package org.quartzpowered.protocol.packet.play.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.engine.math.Quaternion;
import org.quartzpowered.engine.math.Vector3;
import org.quartzpowered.network.protocol.packet.Packet;
import org.quartzpowered.protocol.data.metadata.Metadata;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class SpawnPlayerPacket extends Packet {
    private int entityId;
    private UUID playerId;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private int heldItem;
    private Metadata metadata;

    public void setPosition(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setPosition(Vector3 position) {
        setPosition(position.getX(), position.getY(), position.getZ());
    }

    public Vector3 getPosition() {
        return new Vector3(x, y, z);
    }

    public void setRotation(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public void setRotation(Quaternion rotation) {
        setRotation(rotation.getYaw(), rotation.getPitch());
    }
}
