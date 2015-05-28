package org.quartzpowered.protocol.packet.play.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;

@Data
@EqualsAndHashCode(callSuper = true)
public class EntityMovePacket extends Packet {
    private int entityId;
    private double deltaX;
    private double deltaY;
    private double deltaZ;
    private boolean onGround;

    public void setDelta(double deltaX, double deltaY, double deltaZ) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.deltaZ = deltaZ;
    }
}
