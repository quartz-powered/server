package org.quartzpowered.protocol.packet.play.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;
import org.quartzpowered.protocol.data.BlockPosition;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExplosionPacket extends Packet {
    private double x;
    private double y;
    private double z;
    private double radius;
    private List<BlockPosition> relativeBlockChanges = new ArrayList<>();
    private double playerMotionX;
    private double playerMotionY;
    private double playerMotionZ;
}
